package top.weidaboy.oss.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.iot.model.v20180120.SetDevicePropertyRequest;
import com.aliyuncs.iot.model.v20180120.SetDevicePropertyResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.stereotype.Service;
import top.weidaboy.oss.service.MQTTService;



@Service
public class MQTTServiceImpl implements MQTTService {
    @Override
    public String SendData(String item,String data) throws ClientException {
        String accessKey = "LTAI4G1gxt7tHossFAFyaBqp";  //将光标定位到账号头像上，选择accesskeys即可查看。
        String accessSecret = "8NKnVKP6MlkwsRcHRAZxg5ssSyeRxb";
        DefaultProfile.addEndpoint("cn-shanghai", "cn-shanghai", "Iot", "iot.cn-shanghai.aliyuncs.com");
        IClientProfile profile = DefaultProfile.getProfile("cn-shanghai", accessKey, accessSecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        SetDevicePropertyRequest request = new SetDevicePropertyRequest();
        request.setProductKey("a163xm9UrzF");
        request.setDeviceName("A001");
        JSONObject itemJson = new JSONObject();
        if(null != item && null!= data){
            if(item.equals("mk")){
                itemJson.put("mk", 1);  //打开口罩识别
            }
            if(item.equals("fe")){
                itemJson.put("fe", 1);  //打开人脸识别
            }
            if(item.equals("wq")){
                itemJson.put("wq", 1);   //退出K210
            }
            if(item.equals("photo")){
                itemJson.put("photo", 1);       //拍照
                itemJson.put("name", data);     //拍照
            }
            if(item.equals("L1")){
                itemJson.put(item, Integer.parseInt(data));      //开关灯
            }
            System.out.println(itemJson);
            request.setItems(itemJson.toString());
        }
        try {
            SetDevicePropertyResponse response = client.getAcsResponse(request);
            System.out.println(response.getRequestId() + ", success: " + response.getSuccess());
        } catch (ClientException e) {
            e.printStackTrace();
        }
        if(null != itemJson)return itemJson.toString();
        return null;
    }
}

