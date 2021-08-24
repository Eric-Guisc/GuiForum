package priv.gsc.guiforum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.gsc.guiforum.dao.CommentMapper;
import priv.gsc.guiforum.dao.PostMapper;
import priv.gsc.guiforum.entity.Comment;
import priv.gsc.guiforum.util.GuiForumEnum;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private PostMapper postMapper;

    private GuiForumEnum.ENTITYTYPE entitytype;

    public int addComment(Comment comment) {
        if (comment == null) {
            throw new IllegalArgumentException("参数不能为空！");
        }

        // 先添加评论
        int count = commentMapper.insertComment(comment);

        // 再更新帖子评论数量（该条评论是帖子的评论，而不是回复；如果该评论是条回复，则不更新帖子中的评论数量，在PostController中会进行查询回复的数量）
        if (comment.getEntityType() == entitytype.ENTITY_TYPE_POST.getCode()) {
            int rows = commentMapper.selectCommentRows(comment.getEntityType(), comment.getEntityId());
            postMapper.updateCommentRows(comment.getEntityId(), rows);
        }

        return count;
    }

    public List<Comment> findCommentsByEntityTypeAndEntityId(int entityType, int entityId, int offset, int limit) {
        List<Comment> comments = commentMapper.selectCommentsByEntityTypeAndEntityId(entityType, entityId, offset, limit);
        return comments;
    }

    public int findCommentRows(int entityType, int entityId) {
        int count = commentMapper.selectCommentRows(entityType, entityId);
        return count;
    }
}
