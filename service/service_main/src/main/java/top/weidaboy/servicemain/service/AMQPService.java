package top.weidaboy.servicemain.service;
public interface AMQPService {
    //AMQP监听单片机发送的联网数据
    void  ReceiveData()  throws Exception;
    //断开连接
    void  closeData()  throws Exception;
}
