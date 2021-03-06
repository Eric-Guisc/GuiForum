package priv.gsc.guiforum.controller.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import priv.gsc.guiforum.entity.Event;
import priv.gsc.guiforum.entity.User;
import priv.gsc.guiforum.event.EventProducer;
import priv.gsc.guiforum.service.FollowService;
import priv.gsc.guiforum.util.GuiForumEnum;
import priv.gsc.guiforum.util.HostHolder;
import priv.gsc.guiforum.util.JsonResult;

@Controller
public class FollowController {

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private FollowService followService;

    @Autowired
    private EventProducer eventProducer;

    private GuiForumEnum.TOPIC topic;

    // 异步 关注
    @PostMapping(value = "/follow", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String follow(int entityType, int entityId) {
        User user = hostHolder.getUser();
        followService.follow(user.getId(), entityType, entityId);

        // 触发关注事件
        Event event = new Event()
                .setTopic(topic.TOPIC_FOLLOW.getTopic())
                .setUserId(user.getId())
                .setEntityType(entityType)
                .setEntityId(entityId)
                .setEntityUserId(entityId);
        eventProducer.fireEvent(event);

        return JsonResult.success("已关注！");
    }

    // 异步 取消关注
    @PostMapping(value = "/unfollow", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String unfollow(int entityType, int entityId) {
        User user = hostHolder.getUser();
        followService.unfollow(user.getId(), entityType, entityId);
        return JsonResult.success("已取消关注！");
    }
}
