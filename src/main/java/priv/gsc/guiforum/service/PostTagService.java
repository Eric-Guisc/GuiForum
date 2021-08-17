package priv.gsc.guiforum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.gsc.guiforum.dao.PostTagMapper;

import java.util.List;

@Service
public class PostTagService {

    @Autowired
    private PostTagMapper postTagMapper;


    public List<Integer> findTagIdsByPostId(int postId) {
        List<Integer> tagIds = postTagMapper.selectTagIdsByPostId(postId);
        return tagIds;
    }
}
