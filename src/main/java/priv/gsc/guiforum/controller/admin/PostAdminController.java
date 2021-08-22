package priv.gsc.guiforum.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import priv.gsc.guiforum.entity.Category;
import priv.gsc.guiforum.entity.Post;
import priv.gsc.guiforum.entity.User;
import priv.gsc.guiforum.service.CategoryService;
import priv.gsc.guiforum.service.PostService;
import priv.gsc.guiforum.service.PostTagService;
import priv.gsc.guiforum.service.TagService;
import priv.gsc.guiforum.util.HostHolder;
import priv.gsc.guiforum.util.JsonResult;
import priv.gsc.guiforum.util.SensitiveFilter;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/post")
public class PostAdminController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PostService postService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Autowired
    private TagService tagService;

    @Autowired
    private PostTagService postTagService;

    @GetMapping
    public String getPosts() {
        return "/admin/post";
    }

    @GetMapping("/new")
    public String getPublishPostPage(Model model) {
        // 所有分类
        List<Category> categories = categoryService.findCategories();
        model.addAttribute("categories", categories);
        return "/admin/add-post";
    }

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String save(Post post, String tags) {
        // 1.获得登录用户
        User user = hostHolder.getUser();
        post.setUserId(user.getId());

        // 2.使用文本过滤器过滤标题和内容
        String title = sensitiveFilter.filter(post.getTitle());
        String content = sensitiveFilter.filter(post.getContent());
        post.setTitle(title);
        post.setContent(content);

        // 3.设置创建时间
        post.setCreateTime(new Date());

        // 4.新增帖子
        postService.addPost(post);

        // 5.根据tags生成对应的tag记录以及post_tag记录
        if (tags != null) {
            List<Integer> tagIds = tagService.addTagsByString(tags);
            postTagService.addPostTags(post.getId(), tagIds);
        }

        return JsonResult.success("添加帖子成功");
    }
}
