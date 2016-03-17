package org.hendrix.core.sample;

import org.hendrix.core.sample.beans.Glass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.hendrix")
public class AppConfig {
    @Bean
    public Glass bellyBean() {
        return new Glass();
    }
}
