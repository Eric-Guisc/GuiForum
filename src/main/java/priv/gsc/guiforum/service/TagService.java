package priv.gsc.guiforum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.gsc.guiforum.dao.TagMapper;
import priv.gsc.guiforum.entity.Tag;

import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagMapper tagMapper;

    public List<Tag> findTags() {
        List<Tag> tags = tagMapper.selectTags();
        return tags;
    }

    public Tag findTagById(int id) {
        Tag tag = tagMapper.selectTagById(id);
        return tag;
    }

    public List<Tag> findTagsByIds(List<Integer> ids) {
        List<Tag> tags = tagMapper.selectTagsByIds(ids);
        return tags;
    }
}
