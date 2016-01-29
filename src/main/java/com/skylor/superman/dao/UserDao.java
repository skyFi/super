package com.skylor.superman.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skylor.superman.model.User;

/**
 * Created by darcy on 2016/1/29.
 */
public interface UserDao extends JpaRepository<User, Long>{
}
