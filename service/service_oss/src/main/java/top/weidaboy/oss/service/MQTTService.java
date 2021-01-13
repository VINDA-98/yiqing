package top.weidaboy.oss.service;
import com.aliyuncs.exceptions.ClientException;
public interface MQTTService {
    //云端下发数据
    String SendData(String item,String data)  throws ClientException;
}
