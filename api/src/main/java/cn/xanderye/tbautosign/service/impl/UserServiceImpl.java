package cn.xanderye.tbautosign.service.impl;

import cn.xanderye.tbautosign.base.RequestContextHolder;
import cn.xanderye.tbautosign.entity.User;
import cn.xanderye.tbautosign.enums.ErrorCode;
import cn.xanderye.tbautosign.mapper.UserMapper;
import cn.xanderye.tbautosign.service.UserService;
import cn.xanderye.tbautosign.util.SecureRandomUtil;
import cn.xanderye.tbautosign.constant.Constant;
import cn.xanderye.tbautosign.exception.BusinessException;
import cn.xanderye.tbautosign.util.MD5Util;
import cn.xanderye.tbautosign.VO.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

/**
 * Created by Xander on 2018-11-05.
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    UserVo userVo;

    @Override
    public UserVo selectByUid(long uid) {
        userVo = new UserVo();
        if (uid == 0) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR, "", "");
        }
        User user = userMapper.selectByUid(uid);
        userVo.setUid(user.getUid());
        userVo.setUsername(user.getUsername());
        userVo.setToken(user.getToken());
        userVo.setAvatar(user.getAvatar());
        return userVo;
    }

    @Override
    public User selectByToken(String token) {
        return userMapper.selectByToken(token);
    }

    @Override
    public UserVo login(String username, String password) {
        this.userVo = new UserVo();
        if ((StringUtils.isEmpty(username)) || (StringUtils.isEmpty(password)))
            throw new BusinessException(ErrorCode.PARAMETER_EMPTY, "username={},password={}", username, password);
        User user = userMapper.selectByUsername(username);
        if (user == null)
            throw new BusinessException(ErrorCode.ACCOUNT_OR_PASSWORD_ERROR, "username={},password={}", username, password);
        try {
            if (!MD5Util.validatePwd(user.getPassword(), Constant.SALT, password))
                throw new BusinessException(ErrorCode.ACCOUNT_OR_PASSWORD_ERROR, "user={}", user);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        userVo.setUid(user.getUid());
        userVo.setUsername(user.getUsername());
        userVo.setToken(user.getToken());
        userVo.setAvatar(user.getAvatar());
        return userVo;
    }

    @Override
    public void register(User user, String code, String verCode) {
        if (StringUtils.isEmpty(code) || !code.toLowerCase().equals(verCode.toLowerCase()))
            throw new BusinessException(ErrorCode.CAPTCHA_ERROR, "code={},verCode={}", code, verCode);
        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword()))
            throw new BusinessException(ErrorCode.PARAMETER_EMPTY, "username={},password={}", user.getUsername(), user.getPassword());
        User tmp = userMapper.selectByUsername(user.getUsername());
        if (tmp != null)
            throw new BusinessException(ErrorCode.ACCOUNT_EXIST, "username={},password={}", user.getUsername(), user.getPassword());
        if (user.getPassword().length() < 6)
            throw new BusinessException(ErrorCode.UNSAFE_PASSWORD, "username={},password={}", user.getUsername(), user.getPassword());
        user.setPassword(MD5Util.encryptPwd(user.getPassword(), Constant.SALT));
        user.setToken(SecureRandomUtil.nextHex(64));
        userMapper.insertUser(user);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }
}