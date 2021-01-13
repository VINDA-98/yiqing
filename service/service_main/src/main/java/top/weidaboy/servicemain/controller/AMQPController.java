package top.weidaboy.servicemain.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.weidaboy.commonutils.R;
import top.weidaboy.servicemain.service.AMQPService;

@Api(description="amqp控制类")
@RestController
@CrossOrigin //跨域
@RequestMapping("/servicemain/amqp")
public class AMQPController {

    @Autowired
    AMQPService amqpService;

    //打开AMQP连接
    @GetMapping("/open")
    public R ReciveAmqp() throws Exception {
        amqpService.ReceiveData();
        return  R.ok();
    }

    //关闭AMQP连接
    @GetMapping("/close")
    public R ColseAmqp() throws Exception {
        amqpService.closeData();
        return  R.ok();
    }
}
