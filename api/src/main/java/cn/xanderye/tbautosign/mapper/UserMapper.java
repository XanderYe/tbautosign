package cn.xanderye.tbautosign.mapper;

import cn.xanderye.tbautosign.entity.User;

/**
 * Created by Xander on 2018-11-05.
 */
public interface UserMapper {
    User selectByUid(long uid);

    User selectByUsername(String username);

    User selectByToken(String token);

    void insertUser(User user);

    void updateUser(User user);
}