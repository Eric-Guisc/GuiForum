package priv.gsc.guiforum.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import priv.gsc.guiforum.GuiForumApplication;
import priv.gsc.guiforum.dao.*;
import priv.gsc.guiforum.entity.*;
import priv.gsc.guiforum.util.GuiForumUtils;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = GuiForumApplication.class)
public class MapperTest {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private PostTagMapper postTagMapper;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    // *****************  PostMapper  **********************
    @Test
    public void insertPost() {
        Post post = new Post();
        post.setUserId(1);
        post.setTitle("第二条测试贴");
        post.setContent("再添加一条");
        post.setType(1);
        post.setRecommendStatus(1);
        post.setCommentCount(0);
        post.setScore(0);
        post.setCategoryId(1);
        post.setCreateTime(new Date());

        int count = postMapper.insertPost(post);
        System.out.println(count);
    }

    @Test
    public void selectPosts() {
        List<Post> posts = postMapper.selectPosts(1, 0, 10);
        for (Post post : posts)
            System.out.println(post);

        int count = postMapper.selectPostRows(1);
        System.out.println(count);
    }


    // *****************  UserMapper  **********************
    @Test
    public void insertUser() {
        User user = new User();
        user.setUsername("guisc");
        user.setPassword("123456");
        user.setSalt("abcd");
        user.setEmail("test@qq.com");
        user.setAvatar("http://www.nowcoder.com/101.png");
        user.setType(1);
        user.setCreateTime(new Date());

        int count = userMapper.insertUser(user);
        System.out.println(count);
    }

    @Test
    public void selectUserById() {
        User user = userMapper.selectUserById(1);
        System.out.println(user);
    }

    @Test
    public void selectUserByName() {
        User user = userMapper.selectUserByName("guisc");
        System.out.println(user);
    }

    @Test
    public void selectUserByEmail() {
        User user = userMapper.selectUserByEmail("test@qq.com");
        System.out.println(user);
    }

    @Test
    public void updateActivationStatus() {
        int count = userMapper.updateActivationStatus(1, 0);
        System.out.println(count);
    }


    // *****************  CategoryMapper  **********************
    @Test
    public void insertCategory() {
        Category category = new Category();
        category.setName("前端开发");
        category.setCreateTime(new Date());
        int count = categoryMapper.insertCategory(category);
        System.out.println(count);
    }

    @Test
    public void selectCategories() {
        List<Category> categories = categoryMapper.selectCategories();
        for (Category category : categories)
            System.out.println(category);
    }

    @Test
    public void selectCategoryById() {
        Category category = categoryMapper.selectCategoryById(1);
        System.out.println(category);
    }



    // *****************  TagMapper  **********************

    @Test
    public void insertTag() {
        Tag tag = new Tag();
        tag.setName("Spring");
        tag.setCreateTime(new Date());
        int count = tagMapper.insertTag(tag);
        System.out.println(count);
    }

    @Test
    public void selectTags() {
        List<Tag> tags = tagMapper.selectTags();
        for (Tag tag : tags)
            System.out.println(tag);
    }

    @Test
    public void selectTagById() {
        Tag tag = tagMapper.selectTagById(2);
        System.out.println(tag);
    }

    @Test
    public void selectTagsByIds() {
        List<Tag> tags = tagMapper.selectTagsByIds(postTagMapper.selectTagIdsByPostId(1));
        for (Tag tag : tags)
            System.out.println(tag);
    }


    // *****************  PostTagMapper  **********************

    @Test
    public void insertPostTag() {
        PostTag postTag = new PostTag();
        postTag.setPostId(2);
        postTag.setTagId(2);
        int count = postTagMapper.insertPostTag(postTag);
        System.out.println(count);
    }

    @Test
    public void selectTagIdByPostId() {
        List<Integer> integers = postTagMapper.selectTagIdsByPostId(1);
        System.out.println(integers);
    }


    // *****************  LoginTicketMapper  **********************

    @Test
    public void insertLoginTicket() {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(1);
        loginTicket.setTicket(GuiForumUtils.generateUUID());
        loginTicket.setValidStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + 3600 * 2 * 1000));
        int count = loginTicketMapper.insertLoginTicket(loginTicket);
        System.out.println(count);
    }

    @Test
    public void selectLoginTicketByTicket() {
        LoginTicket loginTicket = loginTicketMapper.selectLoginTicketByTicket("107727b74022447d9838c2a68da3832e");
        System.out.println(loginTicket);
    }

    @Test
    public void updateValidStatus() {
        int count = loginTicketMapper.updateValidStatus("1a5cc08a864c4682a3dc234334955e8d", 1);
        System.out.println(count);
    }


}
