package com.seewo.user.api.service;

/**
 * Created by zxm on 2018/2/9.
 */
public class User {

    private Long uid;

    private String nickName;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
