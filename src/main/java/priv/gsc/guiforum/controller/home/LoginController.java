package priv.gsc.guiforum.controller.home;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import priv.gsc.guiforum.service.UserService;
import priv.gsc.guiforum.util.GuiForumEnum;
import priv.gsc.guiforum.util.JsonResult;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private GuiForumEnum.REMEMBERME rememberme;

    @Autowired
    private UserService userService;

    @Value("${server.servlet.context-path}")
    private String contextPath;


    @GetMapping("/login")
    public String getLoginPage() {
        return "/home/login";
    }

    @PostMapping(value ="/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String login(String usernameOrEmail, String password, String kaptcha, boolean rememberMe, HttpSession session, HttpServletResponse response) {
        // 检查验证码
        String code = (String) session.getAttribute("kaptcha");
        if (StringUtils.isBlank(kaptcha) || StringUtils.isBlank(code) || !code.equalsIgnoreCase(kaptcha)) {
            return JsonResult.error("验证码错误");
        }

        // 检验输入是否完整
        if (StringUtils.isBlank(usernameOrEmail) || StringUtils.isBlank(password)) {
            return JsonResult.error("请填写完整信息");
        }

        // 判断rememberMe
        int expiredSeconds = rememberMe ? rememberme.REMEMBER_EXPIRED_SECONDS.getExpiredSeconds() : rememberme.DEFAULT_EXPIRED_SECONDS.getExpiredSeconds();

        Map<String, Object> map = userService.login(usernameOrEmail, password, expiredSeconds);
        if (map.containsKey("ticket")) {    // 登录通过
            // 把LoginTicket中的ticket，通过Cookie发放给客户端浏览器
            Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
            cookie.setPath(contextPath);
            cookie.setMaxAge(expiredSeconds);
            response.addCookie(cookie);
            return JsonResult.success("登录成功");
        } else if (map.containsKey("usernameOrEmailMsg")) {
            return JsonResult.error(map.get("usernameOrEmailMsg").toString());
        } else if (map.containsKey("activationMsg")) {
            return JsonResult.error(map.get("activationMsg").toString());
        } else if (map.containsKey("passwordMsg")) {
            return JsonResult.error(map.get("passwordMsg").toString());
        } else {
            return JsonResult.error("其他错误");
        }

    }

    @GetMapping("/logout")
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/login";
    }

}
