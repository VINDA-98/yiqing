package top.weidaboy.servicemsm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableAsync         //开启异步注解功能
@EnableScheduling    //开启定时功能
@EnableFeignClients //开启调用服务功能
@ComponentScan(basePackages = {"top.weidaboy"})
public class ServiceMSMApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceMSMApplication.class,args);
    }
}
