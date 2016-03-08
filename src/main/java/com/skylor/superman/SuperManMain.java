package com.skylor.superman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by darcy on 2015/12/17.
 */
@EnableAutoConfiguration
@ComponentScan
@EntityScan(basePackages = {"com.skylor.superman.model"})
@EnableJpaRepositories(basePackages = {"com.skylor.superman.dao"})
public class SuperManMain {

    public static void main(String[] args) {
        SpringApplication.run(SuperManMain.class);
    }
 }
