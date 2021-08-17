package priv.gsc.guiforum.dao;

import org.apache.ibatis.annotations.Mapper;
import priv.gsc.guiforum.entity.Tag;

import java.util.List;

@Mapper
public interface TagMapper {

    // 新增标签
    int insertTag(Tag tag);

    // 查询所有标签
    List<Tag> selectTags();

    // 根据Id查询标签
    Tag selectTagById(int id);

    // 根据Ids查询所有标签
    List<Tag> selectTagsByIds(List<Integer> ids);
}
