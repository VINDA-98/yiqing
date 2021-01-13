package top.weidaboy.servicemain.util;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class NewID {
    //生成ID
    public String createdNewID(){
        //生成ID
        Format dateFormat = new SimpleDateFormat("YYYYMMddHHmmss");
        Random random = new Random();
        int rannum = (int) (random.nextDouble() * (99 - 10 + 1)) + 10;// 获取2位随机数
        return  ""+dateFormat.format(new Date())+rannum;
    }

}
