package cn.xanderye.tbautosign.controller;

import cn.xanderye.tbautosign.base.RequestContextHolder;
import cn.xanderye.tbautosign.config.CaptchaCache;
import cn.xanderye.tbautosign.entity.User;
import cn.xanderye.tbautosign.enums.ErrorCode;
import cn.xanderye.tbautosign.exception.BusinessException;
import cn.xanderye.tbautosign.service.UserService;
import cn.xanderye.tbautosign.base.ResultBean;
import cn.xanderye.tbautosign.VO.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Xander on 2018-11-05.
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CaptchaCache captchaCache;

    @Value("${upload.root}")
    private String uploadRoot;

    @Value("${user.avatar}")
    private String avatarPath;

    @PostMapping("login")
    public ResultBean login(String username, String password) {
        return new ResultBean<>(this.userService.login(username, password));
    }

    @PostMapping("register")
    public ResultBean register(User user, String uuid, String verCode) {
        String code = captchaCache.get(uuid);
        this.userService.register(user, code, verCode);
        return new ResultBean<>();
    }

    @GetMapping("check")
    public ResultBean checkUser(String username) {
        if (StringUtils.isEmpty(username)) {
            throw new BusinessException(ErrorCode.PARAMETER_EMPTY);
        }
        User findUser = userService.findUserByUsername(username);
        if (findUser != null) {
            return ResultBean.error(ErrorCode.ACCOUNT_EXIST);
        }
        return new ResultBean<>();
    }

    @GetMapping("info")
    public ResultBean info() {
        User user = RequestContextHolder.get();
        user.setPassword(null);
        return new ResultBean(user);
    }

    @GetMapping("token/{token}")
    public ResultBean selectByToken(@PathVariable String token) {
        User user = RequestContextHolder.get();
        UserVo userDto = new UserVo();
        userDto.setUid(user.getUid());
        userDto.setUsername(user.getUsername());
        userDto.setToken(user.getToken());
        userDto.setAvatar(user.getAvatar());
        return new ResultBean<>(userDto);
    }

    /**
     * 上传头像
     *
     * @param file
     * @return com.xander.mdblog.base.ResultBean
     * @author XanderYe
     * @date 2019-07-11
     */
    @PostMapping("uploadAvatar")
    public ResultBean uploadAvatar(@RequestParam("avatar") MultipartFile file) throws Exception {
        User user = RequestContextHolder.get();
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        String fileName = UUID.randomUUID().toString() + "." + suffix;
        String destFileName = uploadRoot + avatarPath + File.separator + fileName;
        File destFile = new File(destFileName);
        destFile.getParentFile().mkdirs();
        file.transferTo(destFile);
        String avatar = avatarPath + "/" + fileName;
        User updateUser = new User();
        updateUser.setUid(user.getUid());
        updateUser.setAvatar(avatar);
        userService.updateUser(updateUser);
        return new ResultBean<>(avatar);
    }
}