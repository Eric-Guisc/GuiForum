package priv.gsc.guiforum.controller.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import priv.gsc.guiforum.entity.User;
import priv.gsc.guiforum.service.FollowService;
import priv.gsc.guiforum.service.UserService;
import priv.gsc.guiforum.util.GuiForumEnum;
import priv.gsc.guiforum.util.HostHolder;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;

    @Autowired
    private FollowService followService;

    private GuiForumEnum.ENTITYTYPE entitytype;

    @GetMapping(value = "/profile/{userId}")
    public String getProfile(@PathVariable("userId") int userId, Model model) {
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new RuntimeException("该用户不存在！");
        }

        // 用户
        model.addAttribute("user", user);
        // 关注数量
        model.addAttribute("followCount", followService.findFollowCount(userId, entitytype.ENTITY_TYPE_USER.getCode()));
        // 粉丝数量
        model.addAttribute("followerCount", followService.findFollowerCount(entitytype.ENTITY_TYPE_USER.getCode(), userId));
        // 是否已关注
        boolean hasFollowed = false;
        if (hostHolder.getUser() != null) {
            hasFollowed = followService.hasFollowed(hostHolder.getUser().getId(), entitytype.ENTITY_TYPE_USER.getCode(), userId);
        }
        model.addAttribute("hasFollowed", hasFollowed);

        return "/home/profile";
    }
}
