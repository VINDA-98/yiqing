package top.weidaboy.servicemain.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.qpid.jms.JmsConnection;
import org.apache.qpid.jms.JmsConnectionListener;
import org.apache.qpid.jms.message.JmsInboundMessageDispatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.weidaboy.servicemain.entity.Student;
import top.weidaboy.servicemain.entity.Teacher;
import top.weidaboy.servicemain.entity.Temp;
import top.weidaboy.servicemain.service.AMQPService;
import top.weidaboy.servicemain.service.StudentService;
import top.weidaboy.servicemain.service.TeacherService;
import top.weidaboy.servicemain.service.TempService;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.net.URI;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@Service
public class AMQPServiceImpl implements AMQPService {

    @Autowired
    TempService tempService;

    @Autowired
    StudentService studentService;

    @Autowired
    TeacherService teacherService;

    private final static Logger logger = LoggerFactory.getLogger(AMQPServiceImpl.class);
    private Connection connection = null;

    //业务处理异步线程池，线程池参数可以根据您的业务特点调整；或者您也可以用其他异步方式处理接收到的消息  发送心跳包
    private final static ExecutorService executorService = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors(),
            Runtime.getRuntime().availableProcessors() * 2, 60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(50000));


    @Override
    public void ReceiveData() throws Exception {
        //参数说明，请参见上一篇文档：AMQP客户端接入说明。
        String accessKey = "LTAI4G1gxt7tHossFAFyaBqp";
        String accessSecret = "8NKnVKP6MlkwsRcHRAZxg5ssSyeRxb";
        String consumerGroupId = "AaZisUIcfiOqvYHoJCo9000100";  //去服务端订阅页面的消费组列表查
        long timeStamp = System.currentTimeMillis();
        //签名方法：支持hmacmd5，hmacsha1和hmacsha256
        String signMethod = "hmacsha1";
        //控制台服务端订阅中消费组状态页客户端ID一栏将显示clientId参数。
        //建议使用机器UUID、MAC地址、IP等唯一标识等作为clientId。便于您区分识别不同的客户端。
        String clientId = "A001";  //随意取
        //UserName组装方法，请参见上一篇文档：AMQP客户端接入说明。
        String userName = clientId + "|authMode=aksign"
                + ",signMethod=" + signMethod
                + ",timestamp=" + timeStamp
                + ",authId=" + accessKey
                + ",consumerGroupId=" + consumerGroupId
                + "|";
        //password组装方法，请参见上一篇文档：AMQP客户端接入说明。
        String signContent = "authId=" + accessKey + "&timestamp=" + timeStamp;
        String password = doSign(signContent,accessSecret, signMethod);
        //按照qpid-jms的规范，组装连接URL。
        //麻烦看看这里的注释，用户id点击右上角的头像，进入个人信息页面查询，你的地区id(我的是zn-shanghai)
        String connectionUrl = "failover:(amqps:" +
                "//1804755136523300.iot-amqp.cn-shanghai.aliyuncs.com:" +
                "5671?amqp.idleTimeout=80000)"
                + "?failover.reconnectDelay=30";

        Hashtable<String, String> hashtable = new Hashtable<>();
        hashtable.put("connectionfactory.SBCF",connectionUrl);
        hashtable.put("queue.QUEUE", "default");
        hashtable.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.qpid.jms.jndi.JmsInitialContextFactory");
        Context context = new InitialContext(hashtable);
        ConnectionFactory cf = (ConnectionFactory)context.lookup("SBCF");
        Destination queue = (Destination)context.lookup("QUEUE");
        // Create Connection
        connection = cf.createConnection(userName, password);
        ((JmsConnection) connection).addConnectionListener(myJmsConnectionListener);
        // Create Session
        // Session.CLIENT_ACKNOWLEDGE: 收到消息后，需要手动调用message.acknowledge()
        // Session.AUTO_ACKNOWLEDGE: SDK自动ACK（推荐）
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        connection.start();
        // Create Receiver Link
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(messageListener);
    }

    //关闭连接
    @Override
    public void closeData() throws Exception {
        //参数说明，请参见上一篇文档：AMQP客户端接入说明。
        connection.close();
    }


    private  MessageListener messageListener = new MessageListener() {
        @Override
        public void onMessage(Message message) {
            try {
                //1.收到消息之后一定要ACK
                // 推荐做法：创建Session选择Session.AUTO_ACKNOWLEDGE，这里会自动ACK。
                // 其他做法：创建Session选择Session.CLIENT_ACKNOWLEDGE，这里一定要调message.acknowledge()来ACK。
                // message.acknowledge();
                //2.建议异步处理收到的消息，确保onMessage函数里没有耗时逻辑。
                // 如果业务处理耗时过程过长阻塞住线程，可能会影响SDK收到消息后的正常回调。
                executorService.submit(() -> processMessage(message));
            } catch (Exception e) {
                logger.error("submit task occurs exception ", e);
            }
        }
    };

    /**
     * 在这里处理您收到消息后的具体业务逻辑。
     */
    private  void processMessage(Message message) {
        try {
            byte[] body = message.getBody(byte[].class);
            String content = new String(body);
            String topic = message.getStringProperty("topic");
            String messageId = message.getStringProperty("messageId");
            //打印具体内容就好了
            if (null != content){
                JSONObject jsonObject = JSONObject.parseObject(content);
                System.out.println("Content:"+content);
                //当温度不为空，游客模式的时候
                if(jsonObject.getJSONObject("items") != null &&
                            jsonObject.getJSONObject("items").getJSONObject("temperature")  != null){
                    Temp temp = new Temp();
                    Format dateFormat = new SimpleDateFormat("YYYYMMddHHmmss");
                    Random random = new Random();
                    int rannum = (int) (random.nextDouble() * (99 - 10 + 1)) + 10;// 获取2位随机数

                    //生成ID
                    String id = dateFormat.format(new Date())+rannum;

                    //获取不为空的温度
                    String temperature = jsonObject.getJSONObject("items").getJSONObject("temperature").get("value").toString();
                    //设置温度
                    temp.setTemp(temperature.substring(0,4));
                    System.out.println("Temperature:"+temperature);

                    //名字与温度不为空的时候
                    if( jsonObject.getJSONObject("items").getJSONObject("name") != null ){
                        String name = jsonObject.getJSONObject("items").getJSONObject("name").get("value").toString();
                        //System.out.println("Name:"+name);
                        //temp.setName(name);
                        //根据名称来判断是学生或者老师的ID，获得对应的用户姓名
                        Teacher teacher = teacherService.ReciveName(name);
                        Student student = studentService.ReciveName(name);

                        if(teacher != null){
                            //判断是否是同一天的信息
                            QueryWrapper<Temp> queryWrapper = new QueryWrapper<>();;  //添加查询条件
                            queryWrapper.orderByDesc("gmt_tempend"); //按照时间降序排列
                            queryWrapper.eq("name",teacher.getName());//获得用户姓名
                            //yyyy-MM-dd HH:mm:ss
                            Format dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                            queryWrapper.like("gmt_tempend",dateFormat1.format(new Date()));//获得修改时间

                            Temp one = tempService.getOne(queryWrapper);
                            //如果存在，是同一天数据
                            if(null != one){
                                one.setGmtTempend(new Date());
                                tempService.update(temp,queryWrapper);
                            }//如果不存在，插入新数据
                            else{
                                temp.setId(id); //设置ID
                                temp.setName(teacher.getName());
                                temp.setUserid(teacher.getId());
                                temp.setCollegeid(teacher.getCollegeid());
                                temp.setClassid("无");
                                temp.setMajorid("无");
                                tempService.save(temp);
                                System.out.println("认证成功！！！");
                            }
                        }
                        if(student != null){
                            //判断是否是同一天的信息
                            QueryWrapper<Temp> queryWrapper = new QueryWrapper<>();;  //添加查询条件
                            queryWrapper.orderByDesc("gmt_modified"); //按照时间降序排列
                            queryWrapper.eq("name",student.getName());//获得用户姓名
                            //yyyy-MM-dd HH:mm:ss
                            Format dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                            queryWrapper.like("gmt_modified",dateFormat1.format(new Date()));//获得修改时间
                            Temp one = tempService.getOne(queryWrapper);
                            //如果存在，是同一天数据
                            if(null != one){
                                tempService.update(temp,queryWrapper);
                            }//如果不存在，插入新数据
                            else{
                                temp.setId(id); //设置ID
                                temp.setName(student.getName());
                                temp.setUserid(student.getId());
                                temp.setCollegeid(student.getCollegeid());
                                temp.setMajorid(student.getMajorid());
                                temp.setClassid(student.getClassid());
                                tempService.save(temp);
                                System.out.println("认证成功！！！");
                            }

                        }
                        if(student == null && teacher == null){
                            temp.setId(id);
                            temp.setUserid(id);
                            temp.setName("游客"+dateFormat.format(new Date()));
                            temp.setClassid("无");
                            temp.setMajorid("无");
                            temp.setCollegeid("无");
                            tempService.save(temp);
                        }
                    }else{ //游客模式
                        //设置ID
                        temp.setId(id);
                        temp.setUserid(id);
                        temp.setClassid("无");
                        temp.setMajorid("无");
                        temp.setCollegeid("无");
                        temp.setName("游客"+dateFormat.format(new Date()));
                        tempService.save(temp);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("processMessage occurs error ", e);
        }
    }

    private static JmsConnectionListener myJmsConnectionListener = new JmsConnectionListener() {
        /**
         * 连接成功建立
         */
        @Override
        public void onConnectionEstablished(URI remoteURI) {
            //logger.info("onConnectionEstablished, remoteUri:{}", remoteURI);
            System.out.println("连接成功");
        }

        /**
         * 尝试过最大重试次数之后，最终连接失败。
         */
        @Override
        public void onConnectionFailure(Throwable error) {
            //logger.error("onConnectionFailure, {}", error.getMessage());
            System.out.println("尝试过最大重试次数之后，最终连接失败。");
        }

        /**
         * 连接中断。
         */
        @Override
        public void onConnectionInterrupted(URI remoteURI) {
            //logger.info("onConnectionInterrupted, remoteUri:{}", remoteURI);
            System.out.println("连接中断。");
        }

        /**
         * 连接中断后又自动重连上。
         */
        @Override
        public void onConnectionRestored(URI remoteURI) {
            //logger.info("onConnectionRestored, remoteUri:{}", remoteURI);
            System.out.println("连接中断后又自动重连上中。。。");
        }

        @Override
        public void onInboundMessage(JmsInboundMessageDispatch envelope) {}

        @Override
        public void onSessionClosed(Session session, Throwable cause) {}

        @Override
        public void onConsumerClosed(MessageConsumer consumer, Throwable cause) {}

        @Override
        public void onProducerClosed(MessageProducer producer, Throwable cause) {}
    };

    /**
     * password签名计算方法，请参见上一篇文档：AMQP客户端接入说明。
     */
    private static String doSign(String toSignString, String secret, String signMethod) throws Exception {
        SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(), signMethod);
        Mac mac = Mac.getInstance(signMethod);
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(toSignString.getBytes());
        return Base64.encodeBase64String(rawHmac);
    }

}

