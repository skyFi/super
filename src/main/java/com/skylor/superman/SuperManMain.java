package com.skylor.superman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by darcy on 2015/12/17.
 */
@EnableAutoConfiguration
@ComponentScan
public class SuperManMain {

    public static void main(String[] args) {
        SpringApplication.run(SuperManMain.class);
    }
 }
