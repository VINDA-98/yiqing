package top.weidaboy.oss.controller;

import com.aliyuncs.exceptions.ClientException;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.weidaboy.commonutils.R;
import top.weidaboy.oss.service.MQTTService;

@Api("测试阿里云MQTT")
@RestController
@RequestMapping("/eduoss/mqtt")
@CrossOrigin
public class MQTTController {

    @Autowired
    MQTTService mqttService;

    @GetMapping("/send/{item}/{data}")
    public R sendMqtt(
            @ApiParam(name = "item", value = "发送给单片机的指令", required = true)
            @PathVariable String item,
            @ApiParam(name = "data", value = "发送给单片机的指令内容", required = true)
            @PathVariable  String data
            ) throws ClientException {
        String s = mqttService.SendData(item, data);
        if(null != s) return R.ok();
        return R.error();
    }
}
