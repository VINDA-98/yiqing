package top.weidaboy.servicemain.client;

import com.netflix.client.ClientException;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.weidaboy.commonutils.R;

/*
*   创建client包
    @FeignClient注解用于指定从哪个服务中调用功能 ，名称与被调用的服务名保持一致。
    @GetMapping注解用于对被调用的微服务进行地址映射。
    @PathVariable注解一定要指定参数名称，否则出错
    @Component注解防止，在其他位置注入CodClient时idea报错
* */
//确认需要连接的注册服务名称，必须在nacos中有，且名字一致，要哪个服务，就添加哪个连接
//fallback 就是服务器宕机后的熔断实现方法
@FeignClient(name = "service-oss",fallback =  MQTTFeignClient.class) //调用的服务名称
@Component
public interface MQTTclient {

    @GetMapping("/eduoss/mqtt/send/{item}/{data}")
    public R sendMqtt(
            @ApiParam(name = "item", value = "发送给单片机的指令", required = true)
            @PathVariable String item,
            @ApiParam(name = "data", value = "发送给单片机的指令内容", required = true)
            @PathVariable  String data
    ) throws ClientException;
    /*
    *   "mk" 1 :启动口罩
        "fe" 1 :启动人脸
        "photo","name" 1 :启动认证并且加上名字
        "wq" 1 :退出当前模式
        "hw" 1 :使用红外人脸测温
        "L1" 1 : 控制LED
    * */
}
