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
            editText.setText(generate());
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
        StringBuilder id = new StringBuilder();

        Region region = Region.getRegionByName(spinner.getSelectedItem().toString());
        id.append(region.code);
        id.append(getGenderCode());

        int checksum = 0;

        Random random = new Random();
        for (int i = 1; i < 8; i++) {
            int number = random.nextInt(9) + 1;
            id.append(number);
            checksum += number * (8 - i);       //將值乘上權重 (權重順序：7~1)
        }

        checksum += Integer.parseInt(getGenderCode()) * 8;      //性別代號乘上權重
        checksum += (region.value % 10) * 9;        //地區值取出個位數後乘上權重
        checksum += region.value / 10 % 10;         //地區值取出十位數後乘上權重 (權重為一故直接加入 checksum)
        checksum = checksum % 10;                   //將 checksum Mod 10

        if (checksum != 0) {
            id.append(10 - checksum);
        } else {
            id.append(0);
        }

        return id.toString();
    }

    private String getGenderCode() {
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());

        if (radioButton.getText().equals("男性")) {
            return "1";
        } else {
            return "2";
        }
    }

}
