package com.seewo.user.api.service;

/**
 * Created by zxm on 2018/2/9.
 */
public class UserAPIService {

    public User getUserById(Long id) {
        User user = new User();

        if (id.equals(1)) {
            user.setNickName("zxm");
            user.setUid(1L);
        } else {
            user.setNickName("用户不存在");
            user.setUid(0L);
        }

        return user;
    }

}
