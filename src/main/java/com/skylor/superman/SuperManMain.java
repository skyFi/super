package com.skylor.superman;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.skylor.superman.rest.CommonRestServiceClient;
import com.skylor.superman.rest.RestServiceClient;

/**
 * Created by darcy on 2015/12/17.
 */
@EnableAutoConfiguration
@ComponentScan
@EntityScan(basePackages = {"com.skylor.superman.model"})
@EnableJpaRepositories(basePackages = {"com.skylor.superman.dao"})
@EnableTransactionManagement
public class SuperManMain {

    @Value("${super.worker.service.url}")
    private String workerServiceUrl;

    public static void main(String[] args) {
        SpringApplication.run(SuperManMain.class);

    }
    @Bean
    public RestServiceClient workerClient() {
        return new CommonRestServiceClient(workerServiceUrl);
    }
}
