package com.example.example1;

import java.util.ArrayList;

enum Region {
    Taipei_City('A', 10, "台北市"),
    Taichung_City('B', 11, "台中市"),
    Keelung_City('C', 12, "基隆市"),
    Tainan_City('D', 13, "台南市"),
    Kaohsiung_City('E', 14, "高雄市"),
    New_Taipei_City('F', 15, "新北市"),
    Yilan_County('G', 16, "宜蘭縣"),
    Taoyuan_City('H', 17, "桃園市"),
    Chiayi_City('I', 34, "嘉義市"),
    Hsinchu_County('J', 18, "新竹縣"),
    Miaoli_County('K', 19, "苗栗縣"),
    Nantou_County('M', 21,  "南投縣"),
    Changhua_County('N', 22, "彰化縣"),
    Hsinchu_City('O', 35, "新竹市"),
    Yunlin_County('P', 23, "雲林縣"),
    Chiayi_County('Q', 24, "嘉義縣"),
    Pingtung_County('T', 27, "屏東縣"),
    Hualien_County('U', 28, "花蓮縣"),
    Taitung_County('V', 29, "台東縣"),
    Kinmen_County('W', 32, "金門縣"),
    Penghu_County('X', 30, "澎湖縣"),
    Lienchiang_County('Z', 33, "連江縣");

    char code;
    int value;
    String name;

    Region(char code, int value, String name) {
        this.code = code;
        this.value = value;
        this.name = name;
    }

    public static ArrayList<String> getRegionList() {
        ArrayList<String> list = new ArrayList<>();
        for (Region value : Region.values()) {
            list.add(value.name);
        }
        return list;
    }

    public static Region getRegionByName(String name) {
        for (Region value : Region.values()) {
            if (value.name.equals(name)) return value;
        }
        return null;
    }
}
