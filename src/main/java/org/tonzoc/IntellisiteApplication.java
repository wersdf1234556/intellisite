package org.tonzoc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableCaching
@EnableSwagger2
@MapperScan(basePackages = "org.tonzoc.mapper")
@SpringBootApplication
@EnableScheduling //必须加此注解
public class IntellisiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntellisiteApplication.class, args);
    }

}
