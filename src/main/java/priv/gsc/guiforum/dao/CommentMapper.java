package priv.gsc.guiforum.dao;

import org.apache.ibatis.annotations.Mapper;
import priv.gsc.guiforum.entity.Comment;

import java.util.List;

@Mapper
public interface CommentMapper {

    // 新增评论
    int insertComment(Comment comment);

    // 根据entityType和entityId 分页 查询评论数据
    List<Comment> selectCommentsByEntityTypeAndEntityId(int entityType, int entityId, int offset, int limit);

    // 根据entityType和entityId查询评论的数量
    int selectCommentRows(int entityType, int entityId);

    // 根据Id查找评论
    Comment selectCommentById(int id);
}
