package priv.gsc.guiforum.controller.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import priv.gsc.guiforum.entity.Event;
import priv.gsc.guiforum.entity.User;
import priv.gsc.guiforum.event.EventProducer;
import priv.gsc.guiforum.service.LikeService;
import priv.gsc.guiforum.util.GuiForumEnum;
import priv.gsc.guiforum.util.HostHolder;
import priv.gsc.guiforum.util.JsonResult;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LikeController {

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private LikeService likeService;

    @Autowired
    private EventProducer eventProducer;

    private GuiForumEnum.TOPIC topic;

    // 异步
    @PostMapping(value = "/like", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String like(int entityType, int entityId, int entityUserId, int postId) {
        User user = hostHolder.getUser();
        likeService.like(user.getId(), entityType, entityId);                                   // 点赞
        long likeCount = likeService.findEntityLikeCount(entityType, entityId);                 // 点赞数量
        int likeStatus = likeService.findEntityLikeStatus(user.getId(), entityType, entityId);  // 点赞状态
        // 使用Map将结果返回给前端
        Map<String, Object> map = new HashMap<>();
        map.put("likeCount", likeCount);
        map.put("likeStatus", likeStatus);

        // 触发点赞事件
        if (likeStatus == 1) {
            Event event = new Event()
                    .setTopic(topic.TOPIC_LIKE.getTopic())
                    .setUserId(user.getId())
                    .setEntityType(entityType)
                    .setEntityId(entityId)
                    .setEntityUserId(entityUserId)
                    .setData("postId", postId);
            eventProducer.fireEvent(event);
        }

        return JsonResult.success(null, map);
    }

}
