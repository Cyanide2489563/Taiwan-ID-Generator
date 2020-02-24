package com.example.example1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Spinner spinner;        //下拉式選單
    private EditText editText;      //可編輯文字
    private Button generate;        //按鈕 (生成)
    private Button copy;            //按鈕 (複製)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadComponent();    //載入 UI 元件
    }

    private void loadComponent() {
        spinner = findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Region.getRegionList()));
        editText = findViewById(R.id.textInput);
        generate = findViewById(R.id.generate);
        copy = findViewById(R.id.copy);

        generate.setOnClickListener(generateClick);
        copy.setOnClickListener(copyClick);
    }

    private Button.OnClickListener generateClick = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            String id = "";
            StringBuilder checksum = new StringBuilder();

            Region region = Region.getRegionByName(spinner.getSelectedItem().toString());
            id += region.code;

            RadioGroup radioGroup = findViewById(R.id.radioGroup);
            RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());

            if (radioButton.getText().equals("男性")) {
                checksum.append('1');
            } else {
                checksum.append('2');
            }

            for (int i = 0; i <= 8; i++) {
                checksum.append(new Random().nextInt(10) + 1);
            }
            id += checksum;
            checksum.insert(0, region.value);

            String[] splitCheckSum = checksum.toString().split("");
            int c = (Integer.valueOf(splitCheckSum[2]) * 9) + (Integer.valueOf(splitCheckSum[1]));

            for (int digit = 3; digit <= 9; digit++) {
                c += Integer.valueOf(splitCheckSum[digit]) * (11 - digit);
            }

            if ((c % 10) == 0) {
                id += 0;
            } else {
                id += 10 - (c % 10);
            }
            editText.setText(id);
        }
    };

    private Button.OnClickListener copyClick = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

            if (!editText.getText().toString().equals("")) {
                clipboardManager.setPrimaryClip(ClipData.newPlainText("text", editText.getText()));
                Toast.makeText(MainActivity.this, "已複製至剪貼簿", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "尚未生成身分證字號", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private String generate() {
        String id = "";
        String checksum = "";

        Region region = Region.getRegionByName(spinner.getSelectedItem().toString());
        id += region.code;

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());

        if (radioButton.getText().equals("男性")) {
            checksum += '1';
        } else {
            checksum += '2';
        }

        for (int i = 0; i <= 8; i++) {
            checksum += new Random().nextInt(10) + 1;
        }
        id += checksum;
        checksum = region.value + checksum;

        String[] splitCheckSum = checksum.split("");
        int c = (Integer.valueOf(splitCheckSum[2]) * 9) + (Integer.valueOf(splitCheckSum[1]));

        for (int digit = 3; digit <= 9; digit++) {
            c += Integer.valueOf(splitCheckSum[digit]) * (11 - digit);
        }

        if ((c % 10) == 0) {
            id += 0;
        } else {
            id += 10 - (c % 10);
        }

        return id;
    }
}
