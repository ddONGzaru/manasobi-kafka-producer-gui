package io.manasobi.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;
import java.util.Locale;

/**
 * Created by manasobi on 2017-04-14.
 */
@Configuration
public class KafkaConfig {

    public static String METADATA_BROKER_LIST ;

    @PostConstruct
    private void init() {

        MessageSource messageSource = messageSource();

        METADATA_BROKER_LIST = messageSource.getMessage("text.kafka.broker.url", null, Locale.getDefault());
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public MessageSource messageSource() {

        ReloadableResourceBundleMessageSource resourceBundleMessageSource = new ReloadableResourceBundleMessageSource();

        resourceBundleMessageSource.setBasename("classpath:io/manasobi/view/messages");
        resourceBundleMessageSource.setDefaultEncoding("UTF-8");
        resourceBundleMessageSource.setFallbackToSystemLocale(true);

        return resourceBundleMessageSource;
    }

}
