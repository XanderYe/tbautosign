package cn.xanderye.tbautosign.controller;

import cn.xanderye.tbautosign.base.ResultBean;
import cn.xanderye.tbautosign.config.CaptchaCache;
import cn.xanderye.tbautosign.constant.Constant;
import cn.xanderye.tbautosign.util.VerifyCodeUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author 叶振东
 * @date 2019/8/27
 */
@RestController
public class CaptchaController {
    @Autowired
    CaptchaCache captchaCache;

    /**
     * 验证码
     *
     * @return com.xander.mdblog.base.ResultBean
     * @author yezhendong
     * @date 2019-07-11
     */
    @GetMapping("captcha")
    public ResultBean captcha() {
        JSONObject captcha = new JSONObject();
        String uuid = UUID.randomUUID().toString();
        String code = VerifyCodeUtil.generateVerifyCode(4);
        String image = VerifyCodeUtil.imageBase64(Constant.IMAGE_WIDTH, Constant.IMAGE_HEIGHT, code);
        captchaCache.put(uuid, code);
        captcha.put("uuid", uuid);
        captcha.put("image", image);
        return new ResultBean<>(captcha);
    }
}
