package priv.gsc.guiforum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.gsc.guiforum.dao.PostMapper;
import priv.gsc.guiforum.entity.Post;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostMapper postMapper;

    public List<Post> findPosts(int userId, int offset, int limit) {
        List<Post> posts = postMapper.selectPosts(userId, offset, limit);
        return posts;
    }

    public int findPostRows(int userId) {
        int count = postMapper.selectPostRows(userId);
        return count;
    }

    public int addPost(Post post) {
        int count = postMapper.insertPost(post);
        return count;
    }

    public Post findPostById(int id) {
        Post post = postMapper.selectPostById(id);
        return post;
    }

    public List<Post> findPostsByIds(List<Integer> ids, int offset, int limit) {
        List<Post> posts = postMapper.selectPostsByIds(ids, offset, limit);
        return posts;
    }
}
