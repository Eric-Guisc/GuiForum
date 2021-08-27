package priv.gsc.guiforum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import priv.gsc.guiforum.util.RedisKeyUtil;

@Service
public class LikeService {

    @Autowired
    private RedisTemplate redisTemplate;

    // 点赞 取消赞
    public void like(int userId, int entityType, int entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        Boolean isMember = redisTemplate.opsForSet().isMember(entityLikeKey, userId);// 检查值userId是否在键entityLikeKey中
        if (isMember) {
            // 该用户已经点过赞了，所以此次是取消赞
            redisTemplate.opsForSet().remove(entityLikeKey, userId);
        } else {
            // 该用户之前没点过赞，所以此次是点赞
            redisTemplate.opsForSet().add(entityLikeKey, userId);
        }
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

}
