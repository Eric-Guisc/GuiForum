package priv.gsc.guiforum.controller.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import priv.gsc.guiforum.entity.Category;
import priv.gsc.guiforum.entity.Post;
import priv.gsc.guiforum.entity.Tag;
import priv.gsc.guiforum.entity.User;
import priv.gsc.guiforum.service.*;
import priv.gsc.guiforum.util.GuiForumUtils;
import priv.gsc.guiforum.util.HostHolder;
import priv.gsc.guiforum.util.JsonResult;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {

//    @Autowired
//    private HostHolder hostHolder;

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


    @GetMapping("/{postId}")
    public String getPostDetail(@PathVariable("postId") int postId, Model model) {
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

        return "/home/post-detail";
    }
}
