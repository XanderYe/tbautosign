package cn.xanderye.tbautosign.controller;

import cn.xanderye.tbautosign.base.ResultBean;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 2020/8/31.
 *
 * @author XanderYe
 */
@RestController
@RequestMapping("tool")
public class ToolController {
    /**
     * 解析地址得到弹幕地址
     * @param params
     * @return cn.xanderye.tbautosign.base.ResultBean
     * @author XanderYe
     * @date 2020/8/31
     */
    @PostMapping("danmu")
    public ResultBean danmu(@RequestBody JSONObject params) {
        String url = params.getString("url");
        return new ResultBean(url);
    }
}
