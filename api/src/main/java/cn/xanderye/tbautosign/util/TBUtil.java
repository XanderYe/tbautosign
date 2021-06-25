package cn.xanderye.tbautosign.util;

import cn.xanderye.tbautosign.DTO.TBResult;
import cn.xanderye.tbautosign.entity.TBInfo;
import com.alibaba.fastjson.JSON;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;


public class TBUtil {


    public static Document getDoc(String url) {

        Document doc = null;
        try {
            doc = Jsoup.connect(url).header("Accept", "text/*, application/xml")
                    .header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3").ignoreContentType(true)
                    .timeout(10000)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0")
                    // .referrer("http://tieba.baidu.com/i/28870963/forum")
                    .get();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return doc;
    }

    public static Document getDoc(String url, Map<String, String> cookies) {

        Document doc = null;
        try {
            doc = Jsoup.connect(url).header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3").cookies(cookies)
                    .ignoreContentType(true).timeout(10000)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0")
                    // .referrer("http://tieba.baidu.com/i/28870963/forum")
                    .get();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return doc;
    }

    public static Document getDoc(String url, Map<String, String> data, Map<String, String> cookies) {

        Document doc = null;
        try {
            doc = Jsoup.connect(url).header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3").cookies(cookies).data(data)
                    .timeout(10000).ignoreContentType(true)
                    .userAgent(
                            "Mozilla/5.0 (iPhone; CPU iPhone OS 9_2 like Mac OS X) AppleWebKit/601.1 (KHTML, like Gecko) CriOS/47.0.2526.70 Mobile/13C71 Safari/601.1.46")
                    .post();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return doc;
    }

    /**
     * 根据BDUSS获取用户的昵称
     *
     * @param BDUSS
     * @return
     */
    public static String getUserName(String BDUSS) {
        String url = "https://tieba.baidu.com/mg/o/profile?format=json&eqid=&refer=";
        String userName = null;
        Map<String, String> cookies = new HashMap<>();
        String BAIDUID = null;
        try {
            BAIDUID = MD5Util.encry(System.currentTimeMillis() / 1000L + "").toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        cookies.put("BDUSS", BDUSS);
        cookies.put("BAIDUID", BAIDUID);
        Document doc = getDoc(url, cookies);
        if (doc != null) {
            String jsonStr = unicodeToUtf8(doc.text());
            try {
                JSONObject res = new JSONObject(jsonStr);
                userName = res.getJSONObject("data").getJSONObject("user").getString("name");
            } catch (JSONException e) {

            }
        }
        return userName;
    }

    /**
     * 获取userid
     *
     * @param userName
     * @return
     */
    public static String getUserID(String userName) {
        String userId = null;
        String url = null;
        try {
            url = "http://tieba.baidu.com/home/get/panel?ie=utf-8&un=" + URLEncoder.encode(userName, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        Document doc = getDoc(url);
        if (doc != null) {
            String jsonStr = unicodeToUtf8(doc.text());
            try {

                JSONObject json = new JSONObject(jsonStr);
                userId = json.getJSONObject("data").get("id").toString();
            } catch (JSONException e) {
                userId = "172363399";
            }
        }
        return userId;
    }

    public static String getTieba(String userId, String BDUSS) {
        String result = null;
        String url = "http://c.tieba.baidu.com/c/f/forum/like";
        int pn = 0;// 默认一页就够了
        Map<String, String> data = new TreeMap<String, String>();
        data.put("_client_id", "wappc_" + System.currentTimeMillis() / 1000L + "_258");
        data.put("_client_type", 2 + "");
        data.put("_client_version", "6.5.8");
        data.put("_phone_imei", "357143042411618");
        data.put("from", "baidu_appstore");
        data.put("is_guest", "1");
        data.put("model", "H60-L01");
        data.put("page_no", pn + "");
        data.put("page_size", "200");
        data.put("timestamp", System.currentTimeMillis() / 1000L + "903");
        data.put("uid", userId);
        StringBuffer sb = new StringBuffer();
        Iterator<String> it = data.keySet().iterator();
        while (it.hasNext()) {
            // it.next()得到的是key，tm.get(key)得到obj
            String key = it.next();
            Object value = data.get(key);
            sb.append(key);
            sb.append("=");
            sb.append(value);
        }
        String sign_str = sb.toString();
        String sign = null;
        try {
            sign = MD5Util.encry(sign_str + "tiebaclient!!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        data.put("sign", sign);
        Map<String, String> cookies = new HashMap<String, String>();
        cookies.put("BDUSS", BDUSS);
        Document doc = getDoc(url, data, cookies);
        if (doc != null) {
            result = unicodeToUtf8(doc.text());
        }
        return result;
    }

    public static List<TBInfo> getTieba(String jsonStr) {
        List<TBInfo> lt = new ArrayList<TBInfo>();
        TBInfo tbInfo;
        try {
            JSONObject json = new JSONObject(jsonStr);
            JSONObject forum_list = json.getJSONObject("forum_list");
            JSONArray js_non = forum_list.getJSONArray("non-gconforum");
            JSONArray js = forum_list.getJSONArray("gconforum");
            for (int i = 0; i < js_non.length(); i++) {
                tbInfo = new TBInfo();
                tbInfo.setTitle(js_non.getJSONObject(i).getString("name"));
                tbInfo.setTiebaId(js_non.getJSONObject(i).getString("id"));
                tbInfo.setCurScore(js_non.getJSONObject(i).getInt("cur_score"));
                tbInfo.setLevelId(js_non.getJSONObject(i).getInt("level_id"));
                tbInfo.setLevelName(js_non.getJSONObject(i).getString("level_name"));
                lt.add(tbInfo);
            }
            for (int i = 0; i < js.length(); i++) {
                tbInfo = new TBInfo();
                tbInfo.setTitle(js.getJSONObject(i).getString("name"));
                tbInfo.setTiebaId(js.getJSONObject(i).getString("id"));
                tbInfo.setCurScore(js.getJSONObject(i).getInt("cur_score"));
                tbInfo.setLevelId(js.getJSONObject(i).getInt("level_id"));
                tbInfo.setLevelName(js.getJSONObject(i).getString("level_name"));
                lt.add(tbInfo);
            }
        } catch (JSONException e) {

        }

        return lt;
    }


    public static List<String> getTBNames(String jsonStr) {
        List<String> lt = new ArrayList<String>();
        try {
            JSONObject json = new JSONObject(jsonStr);
            JSONObject forum_list = json.getJSONObject("forum_list");
            JSONArray js_non = forum_list.getJSONArray("non-gconforum");
            JSONArray js = forum_list.getJSONArray("gconforum");
            for (int i = 0; i < js_non.length(); i++) {
                lt.add(js_non.getJSONObject(i).getString("name"));
            }
            for (int i = 0; i < js.length(); i++) {
                lt.add(js.getJSONObject(i).getString("name"));
            }
        } catch (JSONException e) {

        }

        return lt;
    }

    /*
     * 获取TBS
     */
    public static String getTBS(String BDUSS) {
        String tbs = null;
        String url = "http://tieba.baidu.com/dc/common/tbs";
        Map<String, String> cookies = new HashMap<String, String>();
        cookies.put("BDUSS", BDUSS);
        Document doc = getDoc(url, cookies);
        if (doc != null) {
            try {
                JSONObject json = new JSONObject(doc.text());
                tbs = json.getString("tbs");
            } catch (JSONException e) {
                // TODO Auto-generated catch block

            }
        }
        return tbs;
    }

    /**
     * 客户端签到 经验+6 连续+8
     *
     * @param BDUSS
     * @param kw    贴吧名称
     * @param fid   1或0
     */
    public static String DoSign_Client(String BDUSS, String kw, String fid) {

        String url = "http://c.tieba.baidu.com/c/c/forum/sign";
        Map<String, String> cookies = new HashMap<String, String>();
        cookies.put("BDUSS", BDUSS);
        Map<String, String> data = new TreeMap<String, String>();
        data.put("BDUSS", BDUSS);
        data.put("_client_id", "03-00-DA-59-05-00-72-96-06-00-01-00-04-00-4C-43-01-00-34-F4-02-00-BC-25-09-00-4E-36");
        data.put("_client_type", "4");
        data.put("_client_version", "1.2.1.17");
        data.put("_phone_imei", "540b43b59d21b7a4824e1fd31b08e9a6");
        data.put("fid", fid);
        data.put("kw", kw);
        data.put("net_type", "3");
        data.put("tbs", getTBS(BDUSS));
        StringBuffer sb = new StringBuffer();
        for (String key : data.keySet()) {
            Object value = data.get(key);
            sb.append(key);
            sb.append("=");
            sb.append(value);
        }
        String sign_str = sb.toString();
        String sign = null;
        try {
            sign = MD5Util.encry(sign_str + "tiebaclient!!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        data.put("sign", sign);
        Document doc = getDoc(url, data, cookies);
        String result = null;
        if (doc != null) {
            try {
                JSONObject json = new JSONObject(doc.text());
                result = TBResult.getName(Integer.parseInt(json.get("error_code").toString()));
            } catch (JSONException e) {
            }

        }
        return result;
    }


    /**
     * 客户端签到 经验+6 连续+8
     *
     * @param BDUSS
     * @param kw    贴吧名称
     * @param fid   1或0
     */
    public static long DoSign_Client_getCode(String BDUSS, String kw, String fid) {

        String url = "http://c.tieba.baidu.com/c/c/forum/sign";
        Map<String, String> cookies = new HashMap<String, String>();
        cookies.put("BDUSS", BDUSS);
        Map<String, String> data = new TreeMap<String, String>();
        data.put("BDUSS", BDUSS);
        data.put("_client_id", "03-00-DA-59-05-00-72-96-06-00-01-00-04-00-4C-43-01-00-34-F4-02-00-BC-25-09-00-4E-36");
        data.put("_client_type", "4");
        data.put("_client_version", "1.2.1.17");
        data.put("_phone_imei", "540b43b59d21b7a4824e1fd31b08e9a6");
        data.put("fid", fid);
        data.put("kw", kw);
        data.put("net_type", "3");
        data.put("tbs", getTBS(BDUSS));
        StringBuffer sb = new StringBuffer();
        Iterator<String> it = data.keySet().iterator();
        while (it.hasNext()) {
            // it.next()得到的是key，tm.get(key)得到obj
            String key = it.next();
            Object value = data.get(key);
            sb.append(key);
            sb.append("=");
            sb.append(value);
        }
        String sign_str = sb.toString();
        String sign = null;
        try {
            sign = MD5Util.encry(sign_str + "tiebaclient!!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        data.put("sign", sign);
        Document doc = getDoc(url, data, cookies);
        long result = -1;
        if (doc != null) {
            try {
                JSONObject json = new JSONObject(doc.text());
                result = Integer.parseInt(json.get("error_code").toString());
            } catch (JSONException e) {
            }

        }
        return result;
    }

    //unicode转UTF-8
    public static String unicodeToUtf8(String asciicode) {
        String[] asciis = asciicode.split("\\\\u");
        String nativeValue = asciis[0];
        try {
            for (int i = 1; i < asciis.length; i++) {
                String code = asciis[i];
                nativeValue += (char) Integer.parseInt(code.substring(0, 4), 16);
                if (code.length() > 4) {
                    nativeValue += code.substring(4);
                }
            }
        } catch (NumberFormatException e) {
            return asciicode;
        }
        return nativeValue;
    }
}
