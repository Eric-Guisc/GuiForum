package priv.gsc.guiforum.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import priv.gsc.guiforum.entity.Post;

import java.util.List;

@Mapper
public interface PostMapper {

    // 新增帖子
    int insertPost(Post post);

    // 分页查询帖子，userId为可选参数（不设置时即查询全部帖子，设置时为查询某用户的帖子）
    List<Post> selectPosts(int userId, int offset, int limit);

    // @Param注解用于给参数取别名
    // 如果只有一个参数，并且在<if>里使用，则必须加别名
    // 查询某用户的帖子总条数或所有用户帖子总条数
    int selectPostRows(@Param("userId") int userId);

    Post selectPostById(int id);

    // 更新帖子的评论数量
    int updateCommentRows(int id, int commentCount);
}
