package cn.com.fintheircing.customer.config;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RetryConfig {

    @Bean
    @Primary
    Retryer feignRetry(){
        return Retryer.NEVER_RETRY;
    }
}
