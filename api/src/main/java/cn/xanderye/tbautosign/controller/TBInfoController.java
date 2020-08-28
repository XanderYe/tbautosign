package cn.xanderye.tbautosign.controller;

import cn.xanderye.tbautosign.entity.TBInfo;
import cn.xanderye.tbautosign.service.TBInfoService;
import cn.xanderye.tbautosign.base.ResultBean;
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

    @PostMapping("refresh/{tbid}")
    public ResultBean refresh(@PathVariable long tbid) {
        tbInfoService.refreshTBInfos(tbid);
        return new ResultBean<>();
    }

    @PostMapping("sign/{tbid}")
    public ResultBean sign(@PathVariable long tbid) {
        tbInfoService.signByTbid(tbid);
        return new ResultBean<>();
    }


    @DeleteMapping("tid/{tid}")
    public ResultBean delete(@PathVariable long tid) {
        tbInfoService.deleteTBInfo(tid);
        return new ResultBean<>();
    }
}
