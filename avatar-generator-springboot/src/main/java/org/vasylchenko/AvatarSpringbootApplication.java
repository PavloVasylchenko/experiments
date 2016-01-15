package org.vasylchenko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class AvatarSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(AvatarSpringbootApplication.class, args);
    }
}
