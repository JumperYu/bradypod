package com.bradypod.framework.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/***
 *
 *   http请求地址和资源文件映射如下:
 *
 *   {application}/{profile}[/{label}]
 *   {application}-{profile}.yml
 *   {label}/{application}-{profile}.yml
 *   {application}-{profile}.properties
 *   {label}/{application}-{profile}.properties
 *
 */
@EnableConfigServer
@SpringBootApplication
public class ConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}