package com.etc.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@MapperScan("com.etc.boot.mapper")
@Slf4j
public class Application {
    public static void main(String[] args) {
        log.info("开始启动应用...");
        SpringApplication.run(Application.class, args);
        log.info("应用启动成功！访问端口：8081");
    }
}
