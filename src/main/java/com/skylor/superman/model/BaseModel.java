//package com.skylor.superman.model;
//
//import javax.persistence.Column;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.MappedSuperclass;
//import javax.persistence.Version;
//
///**
// * Created by darcy on 2016/1/29.
// */
//@MappedSuperclass
//public abstract class BaseModel {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    protected Long id;
//
//
//    @Column(nullable = false)
//    @Version
//    protected long version;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public long getVersion() {
//        return version;
//    }
//
//    public void setVersion(long version) {
//        this.version = version;
//    }
//}
