package priv.gsc.guiforum.controller.home;

import com.google.code.kaptcha.Producer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import priv.gsc.guiforum.service.UserService;
import priv.gsc.guiforum.util.GuiForumEnum;
import priv.gsc.guiforum.util.JsonResult;
import priv.gsc.guiforum.util.RegexUtil;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

@Controller
public class RegisterController {

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private Producer kaptchaProducer;

    @Autowired
    private UserService userService;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    private GuiForumEnum.ACTIVATION activationEnum;


    @GetMapping("/register")
    public String getRegisterPage() {
        return "/home/register";
    }

    @GetMapping("/kaptcha")
    public void getKaptcha(HttpServletResponse response, HttpSession session) {
        // 生成验证码
        String text = kaptchaProducer.createText();
        BufferedImage image = kaptchaProducer.createImage(text);

        // 将验证码存入到session中，供用户注册时验证
        session.setAttribute("kaptcha", text);

        // 将图片输出到浏览器
        response.setContentType("image/png");
        try {
            ServletOutputStream os = response.getOutputStream();
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
            logger.error("响应验证码失败：" + e.getMessage());
        }
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String register(String username, String password, String email, String kaptcha, HttpSession session) {
        // 检查验证码
        String code = (String) session.getAttribute("kaptcha");
        if (StringUtils.isBlank(kaptcha) || StringUtils.isBlank(code) || !code.equalsIgnoreCase(kaptcha)) {
            return JsonResult.error("验证码错误");
        }

        // 检验输入是否完整
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(email)) {
            return JsonResult.error("请填写完整信息");
        }
        // 检验邮箱是否合法
        if (!RegexUtil.isEmail(email)) {
            return JsonResult.error("邮箱格式不合法");
        }
        // 检验密码长度是否合法
        if (password.length() < 6 || password.length() >20) {
            return JsonResult.error("用户密码长度应该为6～20位");
        }

        Map<String, Object> registerMap = userService.register(username, password, email);
        if (registerMap == null || registerMap.isEmpty()) {
            return JsonResult.success("注册成功,我们已经向您的邮箱发送了一封激活邮件,请尽快激活!");
        } else if (registerMap.containsKey("usernameMsg")) {
            return JsonResult.error((String) registerMap.get("usernameMsg"));
        } else if (registerMap.containsKey("emailMsg")) {
            return JsonResult.error((String) registerMap.get("emailMsg"));
        } else {
            return JsonResult.error("请重新注册");
        }
    }

    @GetMapping("/activation/{userId}/{code}")
    @ResponseBody
    public String activation(Model model, @PathVariable("userId") int userId, @PathVariable("code") String code) {
        int activation = userService.activation(userId, code);
        if (activation == activationEnum.ACTIVATION_SUCCESS.getCode()) {
            return JsonResult.success("激活成功，您的账号可以正常使用了！");
        } else if (activation == activationEnum.ACTIVATION_REPEAT.getCode()) {
            return JsonResult.error("该账号已经激活过了!");
        } else if (activation == activationEnum.ACTIVATION_FAILURE.getCode()) {
            return JsonResult.error("激活失败，您提供的激活码不正确！");
        }
        return JsonResult.error("其他错误");
    }

//    @LoginRequired
//    @GetMapping("/test")
//    @ResponseBody
//    public String test() {
//        return "test 一下";
//    }

}
