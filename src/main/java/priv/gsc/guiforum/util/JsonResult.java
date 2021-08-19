package priv.gsc.guiforum.util;

import com.alibaba.fastjson.JSONObject;

// 转换成JSON字符串
public class JsonResult {

    // 状态码  0：失败  1：成功
    // 返回信息
    // 返回的数据

    public static String success(String msg, Object result) {
        JSONObject json = new JSONObject();
        json.put("code", 1);
        json.put("msg", msg);
        if (result != null)
            json.put("result", result);
        return json.toJSONString();
    }

    public static String success(String msg) {
        return success(msg, null);
    }

    public static String success() {
        return success("操作成功");
    }

    public static String error(String msg, Object result) {
        JSONObject json = new JSONObject();
        json.put("code", 0);
        json.put("msg", msg);
        if (result != null)
            json.put("result", result);
        return json.toJSONString();
    }

    public static String error(String msg) {
        return error(msg, null);
    }

}
