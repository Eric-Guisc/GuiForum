package priv.gsc.guiforum.util;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.Locale;

public class GuiForumUtils {

    public static String timeParseToPrettyTime(Date date) {
        Locale locale = Locale.CHINESE;
        Locale.setDefault(Locale.CHINESE);
        PrettyTime t = new PrettyTime();
        return t.format(date);
    }
}
