package top.weidaboy.servicemain.util;

import net.sourceforge.pinyin4j.PinyinHelper;

public class PinYin {
    public  String StrToPinYin(String name){
        // System.out.println(teacher);
        String convert = "";
        for (int i = 0; i < name.length(); i++) {
            char word = name.charAt(i);
            // 先判断其是否是汉字
            if(String.valueOf(word).matches("[\\u4E00-\\u9FA5]+")){
                //提取汉字的首字母
                String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
                if (pinyinArray != null) {
                    convert += pinyinArray[0].charAt(0);
                } else {
                    convert += word;
                }
            }
        }
        //转大写
        //String s = convert.toUpperCase();
        //转小写
        if(convert != null) return  convert.toLowerCase();
        return null;
    }
}
