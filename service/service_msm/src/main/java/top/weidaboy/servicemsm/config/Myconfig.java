package top.weidaboy.servicemsm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.weidaboy.servicemsm.util.EmailUtils;

@Configuration
public class Myconfig {
    @Bean
    public EmailUtils emailUtils(){
        return new EmailUtils();
    }
}
