package priv.gsc.guiforum.util;

import org.apache.commons.lang3.StringUtils;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class GuiForumUtils {

    // 将 标准时间字符串 --> xxx前 这种格式
    public static String timeParseToPrettyTime(Date date) {
        Locale locale = Locale.CHINESE;
        Locale.setDefault(Locale.CHINESE);
        PrettyTime t = new PrettyTime();
        return t.format(date);
    }

    // 生成随机字符串
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    // md5加密
    // password --> xxxxxx
    // password + salt --> xxxxxxyyy
    public static String md5(String key) {
        if (StringUtils.isBlank(key))
            return null;
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }
}
