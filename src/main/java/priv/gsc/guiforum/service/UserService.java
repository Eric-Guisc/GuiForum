package priv.gsc.guiforum.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import priv.gsc.guiforum.dao.LoginTicketMapper;
import priv.gsc.guiforum.dao.UserMapper;
import priv.gsc.guiforum.entity.LoginTicket;
import priv.gsc.guiforum.entity.User;
import priv.gsc.guiforum.util.GuiForumEnum;
import priv.gsc.guiforum.util.GuiForumUtils;
import priv.gsc.guiforum.util.MailClient;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserService {

    @Value("${forum.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    private GuiForumEnum.ACTIVATION activationEnum;

    public User findUserById(int id) {
        User user = userMapper.selectUserById(id);
        return user;
    }

    public Map<String, Object> register(String username, String password, String email) {
        Map<String, Object> map = new HashMap<>();

        // 验证用户名、邮箱
        User user1 = userMapper.selectUserByName(username);
        if (user1 != null) {
            map.put("usernameMsg", "该用户名已经存在！");
            return map;
        }
        User user2 = userMapper.selectUserByEmail(email);
        if (user2 != null) {
            map.put("emailMsg", "该邮箱已经被注册！");
            return map;
        }

        // 注册用户
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setSalt(GuiForumUtils.generateUUID().substring(0, 5));
        user.setPassword(GuiForumUtils.md5(password + user.getSalt()));
        user.setAvatar(String.format("/images/avatar/%d.jpeg", new Random().nextInt(40)+1));
        user.setType(0);            // 普通用户
        user.setActivationStatus(0);// 未激活
        user.setActivationCode(GuiForumUtils.generateUUID());
        user.setCreateTime(new Date());
        userMapper.insertUser(user);


        // 发送激活邮件
        Context context = new Context();
        context.setVariable("email",user.getEmail());
        // url : http://localhost:8080/forum/activation/123/code
        String url = domain + contextPath + "/activation/" + user.getId() + "/" + user.getActivationCode();
        context.setVariable("url",url);
        String content = templateEngine.process("mail/activation", context);
        mailClient.sendMail(user.getEmail(),"账号激活",content);

        return map;
    }

    public int activation(int userId, String code) {
        User user = userMapper.selectUserById(userId);
        if (user.getActivationStatus() == 1) {  // 重复激活
            return activationEnum.ACTIVATION_REPEAT.getCode();
        }
        else if (user.getActivationCode().equals(code)) {   // 激活用户
            userMapper.updateActivationStatus(userId, 1);
            return activationEnum.ACTIVATION_SUCCESS.getCode();
        } else {    // 激活失败
            return activationEnum.ACTIVATION_FAILURE.getCode();
        }
    }

    public Map<String, Object> login(String usernameOrEmail, String password, int expiredSeconds) {
        Map<String, Object> map = new HashMap<>();

        // 验证用户名或邮箱
        User userByUsername = userMapper.selectUserByName(usernameOrEmail);
        User userByEmail = userMapper.selectUserByEmail(usernameOrEmail);
        User user = userByUsername != null ? userByUsername : (userByEmail != null ? userByEmail : null);
        if (user == null) {
            map.put("usernameOrEmailMsg", "该用户名或邮箱不存在");
            return map;
        }
        // 验证激活状态
        if (user.getActivationStatus() == 0) {
            map.put("activationMsg", "该账号未激活");
            return map;
        }
        // 验证密码
        password = GuiForumUtils.md5(password + user.getSalt());
        if (!password.equals(user.getPassword())) {
            map.put("passwordMsg", "密码错误");
            return map;
        }

        // 生成登录凭证
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(GuiForumUtils.generateUUID());
        loginTicket.setValidStatus(0);      // 有效
        loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds * 1000));
        loginTicketMapper.insertLoginTicket(loginTicket);
        map.put("ticket", loginTicket.getTicket());

        return map;
    }

    public void logout(String ticket) {
        loginTicketMapper.updateValidStatus(ticket, 1);
    }

}
