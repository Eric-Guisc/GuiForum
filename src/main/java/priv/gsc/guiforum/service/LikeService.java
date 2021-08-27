package priv.gsc.guiforum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;
import priv.gsc.guiforum.util.GuiForumEnum;
import priv.gsc.guiforum.util.RedisKeyUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class LikeService {

    @Autowired
    private RedisTemplate redisTemplate;

    private GuiForumEnum.ENTITYTYPE entitytype;

    // 点赞 取消赞
    public void like(int userId, int entityType, int entityId) {
//        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
//        boolean isMember = redisTemplate.opsForSet().isMember(entityLikeKey, userId);// 检查值userId是否在键entityLikeKey中
//        if (isMember) {
//            // 该用户已经点过赞了，所以此次是取消赞
//            redisTemplate.opsForSet().remove(entityLikeKey, userId);
//        } else {
//            // 该用户之前没点过赞，所以此次是点赞
//            redisTemplate.opsForSet().add(entityLikeKey, userId);
//        }
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
                String postLikeByUserKey = RedisKeyUtil.getPostLikeByUserKey(userId);
                boolean isMember = redisTemplate.opsForSet().isMember(entityLikeKey, userId);// 检查值userId是否在键entityLikeKey中
                operations.multi(); // 开启事务
                if (isMember) {
                    // 该用户已经点过赞了，所以此次是取消赞
                    redisTemplate.opsForSet().remove(entityLikeKey, userId);
                    if (entityType == entitytype.ENTITY_TYPE_POST.getCode()) {
                        // 如果entityType的类型是帖子，postLikeByUserKey移除该帖子的id
                        redisTemplate.opsForSet().remove(postLikeByUserKey, entityId);
                    }
                } else {
                    // 该用户之前没点过赞，所以此次是点赞
                    redisTemplate.opsForSet().add(entityLikeKey, userId);
                    if (entityType == entitytype.ENTITY_TYPE_POST.getCode()) {
                        // 如果entityType的类型是帖子，postLikeByUserKey添加该帖子的id
                        redisTemplate.opsForSet().add(postLikeByUserKey, entityId);
                    }
                }
                return operations.exec();   // 提交事务
            }
        });
    }

    // 查询某实体的点赞数量
    public long findEntityLikeCount(int entityType, int entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().size(entityLikeKey);
    }

    // 查询某用户对某实体的点赞状态（使用int作为返回类型，为之后可能的未点赞、点赞、踩状态，提供扩展性）
    // 0：未点赞    1：点赞
    public int findEntityLikeStatus(int userId, int entityType, int entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().isMember(entityLikeKey, userId) ? 1 : 0;
    }

    // 查询某用户点赞的帖子
    public List<Integer> findPostLikeByUser(int userId) {
        String postLikeByUserKey = RedisKeyUtil.getPostLikeByUserKey(userId);
        Set<Integer> set = redisTemplate.opsForSet().members(postLikeByUserKey);
        List<Integer> list = new ArrayList<>();
        if (set.isEmpty())
            return null;
        else {
            for (Integer id : set)
                list.add(id);
            return list;
        }
    }

}
