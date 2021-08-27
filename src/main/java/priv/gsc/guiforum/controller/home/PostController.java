package priv.gsc.guiforum.controller.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import priv.gsc.guiforum.entity.*;
import priv.gsc.guiforum.service.*;
import priv.gsc.guiforum.util.GuiForumEnum;
import priv.gsc.guiforum.util.GuiForumUtils;
import priv.gsc.guiforum.util.HostHolder;
import priv.gsc.guiforum.util.JsonResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PostTagService postTagService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeService likeService;

    private GuiForumEnum.ENTITYTYPE entitytype;


    @GetMapping("/{postId}")
    public String getPostDetail(@PathVariable("postId") int postId, Model model, Page page) {
        if (postId <= 0)
            throw new IllegalArgumentException("帖子ID不正确");

        // 帖子
        Post post = postService.findPostById(postId);
        if (post == null)
            return JsonResult.error("没找到对应Id的帖子");
        model.addAttribute("post", post);

        // 时间转换成 XXX前 这种格式
        model.addAttribute("time", GuiForumUtils.timeParseToPrettyTime(post.getCreateTime()));

        // 用户
        User user = userService.findUserById(post.getUserId());
        if (user == null)
            return JsonResult.error("没找到对应帖子的用户");
        model.addAttribute("user", user);

        // 分类
        Category category = categoryService.findCategoryById(post.getCategoryId());
        if (category == null)
            return JsonResult.error("没找到对应帖子的分类");
        model.addAttribute("category", category);

        // 标签
        List<Integer> tagIds = postTagService.findTagIdsByPostId(postId);
        List<Tag> tags = new ArrayList<>();
        if (tagIds != null) {
            for (Integer tagId : tagIds) {
                Tag tag = tagService.findTagById(tagId);
                tags.add(tag);
            }
        }
        model.addAttribute("tags", tags);

        // 点赞数量
        long entityLikeCount = likeService.findEntityLikeCount(entitytype.ENTITY_TYPE_POST.getCode(), postId);
        model.addAttribute("likeCount", entityLikeCount);

        // 点赞状态
        int likeStatus = hostHolder.getUser() == null ? 0 : likeService.findEntityLikeStatus(hostHolder.getUser().getId(), entitytype.ENTITY_TYPE_POST.getCode(), postId);
        model.addAttribute("likeStatus", likeStatus);

        // 评论
        // 评论分页信息
        page.setLimit(5);
        page.setPath("/post/" + postId);
        page.setRows(commentService.findCommentRows(entitytype.ENTITY_TYPE_POST.getCode(), postId));

        // 评论：给帖子的评论
        // 回复：给评论的评论

        // 评论列表
        List<Comment> commentList = commentService.findCommentsByEntityTypeAndEntityId(
                entitytype.ENTITY_TYPE_POST.getCode(), postId, page.getOffset(), page.getLimit());
        // 评论VO列表
        List<Map<String, Object>> commentVoList = new ArrayList<>();
        if (commentList != null) {
            for (Comment comment : commentList) {
                // 评论VO = 评论 + 作者 + 转换后的时间表示 + 回复VO列表 + 回复数量 + 点赞数量 + 点赞状态
                Map<String, Object> commentVo = new HashMap<>();
                commentVo.put("comment", comment);
                commentVo.put("user", userService.findUserById(comment.getUserId()));
                commentVo.put("time", GuiForumUtils.timeParseToPrettyTime(comment.getCreateTime()));

                // 回复列表
                List<Comment> replyList = commentService.findCommentsByEntityTypeAndEntityId(
                        entitytype.ENTITY_TYPE_COMMENT.getCode(), comment.getId(), 0, Integer.MAX_VALUE);
                // 回复VO列表
                List<Map<String, Object>> replyVoList = new ArrayList<>();
                if (replyList != null) {
                    for (Comment reply : replyList) {
                        // 回复VO = 回复 + 作者 + 回复目标
                        Map<String, Object> replyVo = new HashMap<>();
                        replyVo.put("reply", reply);
                        replyVo.put("user", userService.findUserById(reply.getUserId()));
                        replyVo.put("targetUser", userService.findUserById(reply.getTargetUserId()));

                        replyVoList.add(replyVo);
                    }
                }

                commentVo.put("replyVoList", replyVoList);
                commentVo.put("replyCount", commentService.findCommentRows(entitytype.ENTITY_TYPE_COMMENT.getCode(), comment.getId()));

                commentVo.put("likeCount", likeService.findEntityLikeCount(entitytype.ENTITY_TYPE_COMMENT.getCode(), comment.getId())); // 点赞数量
                likeStatus = hostHolder.getUser() == null ? 0 : likeService.findEntityLikeStatus(hostHolder.getUser().getId(), entitytype.ENTITY_TYPE_COMMENT.getCode(), comment.getId());
                commentVo.put("likeStatus", likeStatus);    // 点赞状态

                commentVoList.add(commentVo);
            }
        }
        model.addAttribute("commentVoList", commentVoList);


        return "/home/post-detail";
    }
}
