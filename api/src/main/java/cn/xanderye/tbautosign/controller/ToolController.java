package cn.xanderye.tbautosign.controller;

import cn.xanderye.tbautosign.base.ResultBean;
import cn.xanderye.tbautosign.enums.ErrorCode;
import cn.xanderye.tbautosign.exception.BusinessException;
import cn.xanderye.tbautosign.util.WebpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.stream.FileImageInputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created on 2020/8/31.
 *
 * @author XanderYe
 */
@Slf4j
@RestController
@RequestMapping("tool")
public class ToolController {

    @Value("${upload.root}")
    private String uploadRoot;

    @Value("${tool.tmp}")
    private String toolTmp;

    @Resource
    private HttpServletResponse response;

    private static final Map<String, String> EXT_MAP = new HashMap<String, String>() {{
        put("jpg", "image/jpeg");
        put("png", "image/png");
    }};

    /**
     * webp转换
     * @param file
     * @param ext
     * @return void
     * @author XanderYe
     * @date 2020/10/6
     */
    @PostMapping("webpConvert")
    public void webpConvert(@RequestParam("webp") MultipartFile file, @RequestParam(name = "ext", defaultValue = "jpg") String ext) {
        if (file.isEmpty()) {
            throw new BusinessException(ErrorCode.PARAMETER_EMPTY);
        }
        if (!EXT_MAP.containsKey(ext.toLowerCase())) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR);
        }
        OutputStream os = null;
        File destFile = null;
        try {
            String fileName = UUID.randomUUID().toString() + ".webp";
            String destFileName = uploadRoot + toolTmp + File.separator + fileName;
            destFile = new File(destFileName);
            destFile.getParentFile().mkdirs();
            file.transferTo(destFile);
            byte[] bytes = WebpUtil.webpToImage(destFile, ext);
            os = response.getOutputStream();
            response.setContentType(EXT_MAP.get(ext.toLowerCase()));
            os.write(bytes);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                destFile.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 解析地址得到弹幕地址
     *
     * @param params
     * @return cn.xanderye.tbautosign.base.ResultBean
     * @author XanderYe
     * @date 2020/8/31
     */
    @PostMapping("danmu")
    public ResultBean danmu(@RequestBody JSONObject params) {
        String url = params.getString("url");
        if (StringUtils.isEmpty(url)) {
            throw new BusinessException(ErrorCode.PARAMETER_EMPTY);
        }
        String cid;
        if (url.contains("bangumi")) {
            cid = bangumiUrl(url);
        } else {
            cid = normalUrl(url);
        }
        if (cid == null) {
            throw new BusinessException(url + "解析错误");
        }
        String resLink = "https://api.bilibili.com/x/v1/dm/list.so?oid=" + cid;
        log.info("url:{}, commentUrl:{}", url, resLink);
        return new ResultBean(resLink);
    }

    public String normalUrl(String url) {
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
                    break;
                }
            }
            String embedPlayer = initialStateJson.getJSONObject("videoData").getString("embedPlayer");
            cid = StringUtils.substringBetween(embedPlayer, "cid=", "&aid");
        } catch (Exception ignored) {

        }
        return cid;
    }

    public String bangumiUrl(String url) {
        String cid = null;
        try {
            Long id = Long.valueOf(url.substring(url.lastIndexOf("ep") + 2));
            Document doc = Jsoup.connect(url)
                    .userAgent("5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.163 Safari/537.36")
                    .get();
            Elements elements = doc.select("body");
            Elements scripts = elements.get(0).select("script");
            JSONObject initialStateJson = null;
            for (Element element : scripts) {
                if (element.toString().contains("__INITIAL_STATE__")) {
                    String initialState = StringUtils.substringBetween(element.toString(), "__INITIAL_STATE__=", ";(function");
                    initialStateJson = JSON.parseObject(initialState);
                    break;
                }
            }
            JSONArray epList = initialStateJson.getJSONArray("epList");
            if (epList != null && epList.size() > 0) {
                for (int i = 0; i < epList.size(); i++) {
                    JSONObject jsonObject = epList.getJSONObject(i);
                    if (id.equals(jsonObject.getLong("id"))) {
                        cid = jsonObject.getString("cid");
                        break;
                    }
                }
            }
        } catch (Exception ignored) {

        }
        return cid;
    }
}
