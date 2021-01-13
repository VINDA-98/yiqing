package top.weidaboy.commonutils.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {

    public  static  String getMessage(Exception e){
        //StringWriter() 无参构造函数，使用默认初始字符串缓冲区大小创建一个新字符串writer
        StringWriter sw  = null;
        //具有自动行刷新的缓冲字符输出流，特点是可以按行写出字符串，并且可以自动行刷新。
        PrintWriter pw = null;
        try {

            sw = new StringWriter();
            pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
        }finally {
            if( sw != null){
                try {
                    sw.close();
                }catch (IOException e1){
                e1.printStackTrace();
                }
            }
            if(pw != null){
                pw.close();
            }
        }
        return sw.toString();
    }
}
