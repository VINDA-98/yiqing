package top.weidaboy.servicemsm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import top.weidaboy.servicemsm.util.EmailUtils;

import javax.mail.MessagingException;

@Validated
@Service
public class AsyncService {
    @Autowired
    private EmailUtils emailUtils;
    /**
     * 声明这是一个异步服务
     */
    @Async
    public void sendAsyncEmail(String subject, String text,String setToPerpel) throws MessagingException {
//        try {
//            Thread.sleep(3000);
//        }catch (InterruptedException  e){
//            e.printStackTrace();
//        }
        //System.out.println("异步：邮件正在发送....");
        emailUtils.SendMail(subject,text,setToPerpel);
    }

    public void sendSyncEmail(String subject, String text,String setToPerpel) throws MessagingException {
//        try {
//            Thread.sleep(3000);
//        }catch (InterruptedException  e){
//            e.printStackTrace();
//        }
        //System.out.println("同步：邮件正在发送....");
        emailUtils.SendMail(subject,text,setToPerpel);
    }
}
