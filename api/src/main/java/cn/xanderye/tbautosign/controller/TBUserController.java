package cn.xanderye.tbautosign.controller;

import cn.xanderye.tbautosign.base.RequestContextHolder;
import cn.xanderye.tbautosign.entity.TBUser;
import cn.xanderye.tbautosign.entity.User;
import cn.xanderye.tbautosign.enums.ErrorCode;
import cn.xanderye.tbautosign.exception.BusinessException;
import cn.xanderye.tbautosign.service.TBUserService;
import cn.xanderye.tbautosign.base.ResultBean;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Xander on 2018-11-10.
 */
@RestController
@RequestMapping("tbuser")
public class TBUserController {
    @Autowired
    TBUserService tbUserService;

    @PostMapping("BDUSS")
    public ResultBean saveBDUSS(@RequestBody TBUser tbUser) {
        String BDUSS = tbUser.getBDUSS();
        if (StringUtils.isEmpty(BDUSS)) {
            throw new BusinessException(ErrorCode.PARAMETER_EMPTY);
        }
        User user = RequestContextHolder.get();
        if (tbUser.getTid() == null) {
            tbUserService.insertTBUser(user.getUid(), BDUSS);
        } else {
            tbUserService.updateTBUser(tbUser);
        }
        return new ResultBean<>();
    }

    @GetMapping("tbusers")
    public ResultBean findTBUsers() {
        User user = RequestContextHolder.get();
        return new ResultBean<>(tbUserService.selectTBUsersByUid(user.getUid()));
    }

    @PostMapping("delete")
    public ResultBean delete(@RequestBody JSONObject params) {
        Integer id = params.getInteger("id");
        tbUserService.deleteTBUser(id);
        return new ResultBean<>();
    }
}
