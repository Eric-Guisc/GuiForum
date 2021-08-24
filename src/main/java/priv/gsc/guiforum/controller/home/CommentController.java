package priv.gsc.guiforum.controller.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import priv.gsc.guiforum.entity.Comment;
import priv.gsc.guiforum.entity.User;
import priv.gsc.guiforum.service.CommentService;
import priv.gsc.guiforum.util.HostHolder;
import priv.gsc.guiforum.util.JsonResult;
import priv.gsc.guiforum.util.SensitiveFilter;

import java.util.Date;

@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String addComment(int entityType, int entityId, int targetUserId, String content) {
        User user = hostHolder.getUser();
        if (user == null) {
            return JsonResult.error("你还没有登录，不能评论");
        }

        Comment comment = new Comment();
        comment.setUserId(user.getId());
        comment.setEntityType(entityType);
        comment.setEntityId(entityId);
        comment.setTargetUserId(targetUserId);
        comment.setContent(sensitiveFilter.filter(content));
        comment.setShowStatus(0);
        comment.setCreateTime(new Date());
        commentService.addComment(comment);

        return JsonResult.success("评论添加成功");
    }


}
