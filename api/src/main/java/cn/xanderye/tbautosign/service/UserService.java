package cn.xanderye.tbautosign.service;

import cn.xanderye.tbautosign.entity.User;
import cn.xanderye.tbautosign.VO.UserVo;

/**
 * Created by Xander on 2018-11-05.
 */
public interface UserService {
    /**
     * 根据uid查询用户
     * @param uid
     * @return cn.xanderye.tbautosign.VO.UserVo
     * @author yezhendong
     * @date 2020/8/27
     */
    UserVo selectByUid(long uid);

    /**
     * 根据token查询用户
     * @param token
     * @return cn.xanderye.tbautosign.entity.User
     * @author yezhendong
     * @date 2020/8/27
     */
    User selectByToken(String token);

    /**
     * 登录
     * @param username
     * @param password
     * @return cn.xanderye.tbautosign.VO.UserVo
     * @author yezhendong
     * @date 2020/8/27
     */
    UserVo login(String username, String password);

    /**
     * 注册
     * @param user
     * @param code
     * @param verCode
     * @return void
     * @author yezhendong
     * @date 2020/8/27
     */
    void register(User user, String code, String verCode);

    /**
     * 更新用户信息
     * @param user
     * @return void
     * @author yezhendong
     * @date 2020/8/27
     */
    void updateUser(User user);
}