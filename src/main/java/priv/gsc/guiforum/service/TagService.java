package priv.gsc.guiforum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.gsc.guiforum.dao.TagMapper;
import priv.gsc.guiforum.entity.Tag;

import java.util.ArrayList;
import java.util.Date;
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

    public List<Integer> addTagsByString(String strings) {
        String[] tags = strings.split("，");
        List<Integer> res = new ArrayList<>();
        if (tags != null) {
            for (String tag : tags) {
                Tag newTag = new Tag();
                newTag.setName(tag);
                newTag.setCreateTime(new Date());
                tagMapper.insertTag(newTag);
                res.add(newTag.getId());
            }
        }
        return res;
    }
}
