package top.weidaboy.servicemsm.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import top.weidaboy.commonutils.R;
import top.weidaboy.servicemsm.service.AsyncService;

import javax.mail.MessagingException;
import javax.validation.constraints.Email;
import java.text.SimpleDateFormat;
import java.util.Date;

@Api("测试QQ邮件发送")
@RestController
@RequestMapping("/servicemsm/email")
@CrossOrigin
public class SendEmailController {

    @Autowired
    private AsyncService asyncService;
    /**
     * 发送异步邮件
     */
    @PostMapping("/sendAsyncEmail")
    @ApiOperation("异步邮件测试")
    public R sendAsyncEmail(
             @RequestParam("ASubject") String subject,
             @RequestParam("Acontent") String content,
             @RequestParam("Apeople") @Email(message = "邮箱格式地址有误") String setToPerpel
    ){
        try {
            System.out.println(subject);
            System.out.println(content);
            System.out.println(setToPerpel);
            //主题 内容  收件人
            asyncService.sendAsyncEmail(subject,content,setToPerpel);
            return R.ok();
        }catch (MessagingException e){
            return R.error();
        }
    }

    /**
     * 发送同步邮件
     */
    @PostMapping("/sendSyncEmail")
    @ApiOperation("同步邮件测试")
    public void sendSyncEmail(  @RequestParam("Subject") String subject,
                                @RequestParam("content") String content,
                                @RequestParam("people")
                               @Email(message = "邮箱格式地址有误") String setToPerpel)
            throws MessagingException {
        try {
            asyncService.sendSyncEmail(subject,content,setToPerpel);
        }catch (MessagingException e){

        }

    }

    /**
     * 定时发送邮件
     *  //秒   分   时     日   月   周几
     */
    @Scheduled(cron = "30 30 22 1/1 * ? ")
    @ApiOperation("定时任务测试")
    public R EmailScheduled(){

        //设置日期格式
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        String nowTime = sm.format(new Date());
        try {
            asyncService.sendAsyncEmail(
                    "今日邮件",
                    "你好渣男一号",
                    "1280900632@qq.com");
        }catch (MessagingException e){
            return R.error();
        }
        return R.ok();
    }
}
