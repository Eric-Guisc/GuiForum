package priv.gsc.guiforum.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import priv.gsc.guiforum.GuiForumApplication;
import priv.gsc.guiforum.util.GuiForumUtils;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = GuiForumApplication.class)
public class UtilTest {

    @Test
    public void testPrettyTime() {
        String time = GuiForumUtils.timeParseToPrettyTime(new Date());
        System.out.println(time);
    }

    @Test
    public void testMd5() {
        String s = GuiForumUtils.md5("guisc1" + "50ab8");
        System.out.println(s);
    }
}
