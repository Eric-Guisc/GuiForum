package priv.gsc.guiforum.controller.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import priv.gsc.guiforum.entity.Page;
import priv.gsc.guiforum.entity.Post;
import priv.gsc.guiforum.service.*;
import priv.gsc.guiforum.util.GuiForumEnum;
import priv.gsc.guiforum.util.GuiForumUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private PostTagService postTagService;

    @Autowired
    private LikeService likeService;

    private GuiForumEnum.ENTITYTYPE entitytype;


    @GetMapping("/index")
    public String getIndexPage(Model model, Page page) {
        // 人为制造一个服务器错误
//        Integer.valueOf("abc");

        // 方法调用前，SpringMVC会自动实例化Model和Page，并将Page注入到Model中，所以，在thymeleaf中可以直接访问Page对象中的数据。

        // Page的current、limit由前端传入(不传入，在Page中也有默认值)，rows、path需要设置
        page.setRows(postService.findPostRows(0));
        page.setPath("/index");

        // 使用Map来包装post和user作为一条数据
        List<Map<String, Object>> postVO = new ArrayList<>();
        List<Post> posts = postService.findPosts(0, page.getOffset(), page.getLimit());
        if (posts != null) {
            for (Post post : posts) {
                Map<String, Object> map = new HashMap<>();
                map.put("post", post);
                map.put("time", GuiForumUtils.timeParseToPrettyTime(post.getCreateTime()));
                map.put("user", userService.findUserById(post.getUserId()));
                map.put("category", categoryService.findCategoryById(post.getCategoryId()));
                List<Integer> tagIds = postTagService.findTagIdsByPostId(post.getId());
                map.put("tags", tagService.findTagsByIds(tagIds));
                map.put("likeCount", likeService.findEntityLikeCount(entitytype.ENTITY_TYPE_POST.getCode(), post.getId())); // 使用Redis查询的点赞数量
                postVO.add(map);
            }
        }
        model.addAttribute("postVO", postVO);

        return "/index";
    }

    @GetMapping("/error")
    public String getErrorPage() {
        return "/error/500";
    }
}
