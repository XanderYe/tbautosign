package cn.xanderye.tbautosign.controller;

import cn.xanderye.tbautosign.base.ResultBean;
import cn.xanderye.tbautosign.exception.BusinessException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
        String cid = null;
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.163 Safari/537.36")
                    .get();
            Elements elements = doc.select("head");
            Elements scripts = elements.get(0).select("script");
             JSONObject initialStateJson = null;
            for (Element element : scripts) {
                if (element.toString().contains("__INITIAL_STATE__")) {
                    String initialState = StringUtils.substringBetween(element.toString(), "__INITIAL_STATE__=", ";(function");
                    initialStateJson = JSON.parseObject(initialState);
                }
            }
            String embedPlayer = initialStateJson.getJSONObject("videoData").getString("embedPlayer");
            cid = StringUtils.substringBetween(embedPlayer, "cid=", "&aid");
        } catch (Exception e) {
            throw new BusinessException("解析错误");
        }
        if (cid == null) {
            throw new BusinessException("解析错误");
        }
        String resLink = "https://api.bilibili.com/x/v1/dm/list.so?oid=" + cid;
        return new ResultBean(resLink);
    }
}
