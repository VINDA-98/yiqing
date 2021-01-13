package top.weidaboy.servicemain.client;

import com.netflix.client.ClientException;
import org.springframework.stereotype.Component;
import top.weidaboy.commonutils.R;


@Component
public class MQTTFeignClient implements MQTTclient {
    @Override
    public R sendMqtt(String item, String data) throws ClientException {
        return R.ok().message("发送指令失败");
    }
}
