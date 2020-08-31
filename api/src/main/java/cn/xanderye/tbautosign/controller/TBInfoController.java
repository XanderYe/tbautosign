package cn.xanderye.tbautosign.controller;

import cn.xanderye.tbautosign.entity.TBInfo;
import cn.xanderye.tbautosign.service.TBInfoService;
import cn.xanderye.tbautosign.base.ResultBean;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Xander on 2018-11-10.
 */
@RestController
@RequestMapping("tbinfo")
public class TBInfoController {
    @Autowired
    TBInfoService tbInfoService;

    @GetMapping("getTbInfoList")
    public ResultBean getTbInfoList(Long tbUserId, Integer page, Integer rows) {
        // 默认第一页
        page = page == null ? 1 : page;
        rows = rows == null ? 20 : rows;
        PageInfo<TBInfo> tbInfoPageInfo = tbInfoService.getTBInfoPageInfoByTbUserId(tbUserId, page, rows);
        return new ResultBean<>(tbInfoPageInfo);
    }

    @PostMapping("refresh")
    public ResultBean refresh(@RequestBody JSONObject params) {
        long tbUserId = params.getInteger("tbUserId");
        tbInfoService.refreshTBInfos(tbUserId);
        return new ResultBean<>();
    }

    @PostMapping("sign")
    public ResultBean sign(@RequestBody JSONObject params) {
        Long tbUserId = params.getLong("tbUserId");
        Long tid = params.getLong("tid");
        tbInfoService.signByTbUserId(tbUserId, tid);
        return new ResultBean<>();
    }


    @PostMapping("delete")
    public ResultBean delete(@RequestBody JSONObject params) {
        long tid = params.getInteger("tid");
        tbInfoService.deleteTBInfo(tid);
        return new ResultBean<>();
    }
}
