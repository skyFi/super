//package com.skylor.superman.model;
//
//import java.util.Date;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.Id;
//import javax.persistence.Version;
//
//import com.skylor.superman.enumeration.SessionKeyStatus;
//import com.skylor.superman.enumeration.UserType;
//
///**
// * 系统用户
// */
//@Entity
//public class User {
//
//    @Id
//    private Long id;
//
//    @Column(nullable = false, unique = true)
//    private String username;
//
//    private String password;
//
//    @Enumerated(value = EnumType.STRING)
//    private UserType userType;
//
//    private String sessionKey;
//
//    @Enumerated(value = EnumType.STRING)
//    private SessionKeyStatus sessionKeyStatus;
//
//    private String refreshToken;
//
//    private Date createDate;
//
//    private Date invalidateDate;
//
//    @Version
//    private Integer optlock;
//
//    private Date lastLoginTime;
//
//    /**
//     * 初始化状态，true表示初始化成功，false表示初始化未完成
//     */
//    private Boolean initStatus = false;
//
//    /**
//     * 最后授权时间
//     */
//    private Date lastAuthorizeTime;
//
//    /**
//     * session key 失效时间
//     */
//    private Date sessionExpiredTime;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public UserType getUserType() {
//        return userType;
//    }
//
//    public void setUserType(UserType userType) {
//        this.userType = userType;
//    }
//
//    public String getSessionKey() {
//        return sessionKey;
//    }
//
//    public void setSessionKey(String sessionKey) {
//        this.sessionKey = sessionKey;
//    }
//
//    public SessionKeyStatus getSessionKeyStatus() {
//        return sessionKeyStatus;
//    }
//
//    public void setSessionKeyStatus(SessionKeyStatus sessionKeyStatus) {
//        this.sessionKeyStatus = sessionKeyStatus;
//    }
//
//    public String getRefreshToken() {
//        return refreshToken;
//    }
//
//    public void setRefreshToken(String refreshToken) {
//        this.refreshToken = refreshToken;
//    }
//
//    public Date getCreateDate() {
//        return createDate;
//    }
//
//    public void setCreateDate(Date createDate) {
//        this.createDate = createDate;
//    }
//
//    public Date getInvalidateDate() {
//        return invalidateDate;
//    }
//
//    public void setInvalidateDate(Date invalidateDate) {
//        this.invalidateDate = invalidateDate;
//    }
//
//    public Integer getOptlock() {
//        return optlock;
//    }
//
//    public void setOptlock(Integer optlock) {
//        this.optlock = optlock;
//    }
//
//    public Date getLastLoginTime() {
//        return lastLoginTime;
//    }
//
//    public void setLastLoginTime(Date lastLoginTime) {
//        this.lastLoginTime = lastLoginTime;
//    }
//
//    public Boolean isInitStatus() {
//        return initStatus;
//    }
//
//    public void setInitStatus(Boolean initStatus) {
//        this.initStatus = initStatus;
//    }
//
//    public Date getLastAuthorizeTime() {
//        return lastAuthorizeTime;
//    }
//
//    public void setLastAuthorizeTime(Date lastAuthorizeTime) {
//        this.lastAuthorizeTime = lastAuthorizeTime;
//    }
//
//    public Date getSessionExpiredTime() {
//        return sessionExpiredTime;
//    }
//
//    public void setSessionExpiredTime(Date sessionExpiredTime) {
//        this.sessionExpiredTime = sessionExpiredTime;
//    }
//
//}
