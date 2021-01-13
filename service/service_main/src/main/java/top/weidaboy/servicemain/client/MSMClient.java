package top.weidaboy.servicemain.client;

import com.netflix.client.ClientException;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.weidaboy.commonutils.R;

import javax.validation.constraints.Email;

@FeignClient(name = "service-msm") //调用的服务名称
@Component
public interface MSMClient{

    @PostMapping("/servicemsm/email/sendAsyncEmail")
    public R sendAsyncEmail(
            @RequestParam("ASubject") String subject,
            @RequestParam("Acontent") String content,
            @RequestParam("Apeople") @Email(message = "邮箱格式地址有误") String setToPerpel
    );


    @PostMapping("/servicemsm/email/sendSyncEmail")
    public void sendSyncEmail( @RequestParam("Subject") String subject,
                               @RequestParam("content") String content,
                               @RequestParam("people")
                               @Email(message = "邮箱格式地址有误") String setToPerpel);
}
