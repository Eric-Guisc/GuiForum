package priv.gsc.guiforum.dao;

import org.apache.ibatis.annotations.Mapper;
import priv.gsc.guiforum.entity.PostTag;

import java.util.List;

@Mapper
public interface PostTagMapper {

    // 新增帖子标签关系
    int insertPostTag(PostTag postTag);

    // 根据帖子ID查询所有对应的标签ID
    List<Integer> selectTagIdsByPostId(int postId);

    // 根据帖子Id和标签Id新增关系
    int insertPostTags(int postId, int tagId);
}
