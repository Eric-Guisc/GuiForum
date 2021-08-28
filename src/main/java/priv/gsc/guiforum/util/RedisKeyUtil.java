package priv.gsc.guiforum.util;

public class RedisKeyUtil {

    private static final String SPLIT = ":";
    private static final String PREFIX_ENTITY_LIKE = "like:entity";             // 点赞
    private static final String PREFIX_POST_LIKE_BY_USER = "like:post:by:user"; // 用户点赞的帖子
    private static final String PREFIX_FOLLOW = "follow";                       // 用户关注
    private static final String PREFIX_FOLLOWER = "follower";                   // 粉丝


    // 某个实体的赞
    // like:entity:entityType:entityId -> set(userId)   使用set集合来存储赞的原因：既可以通过set计算出点赞的数量，也可以通过set获取到每个点赞的人
    public static String getEntityLikeKey(int entityType, int entityId) {
        return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
    }

    // 用户点赞的帖子
    // like:post:by:user:userId -> set(postId)
    public static String getPostLikeByUserKey(int userId) {
        return PREFIX_POST_LIKE_BY_USER + SPLIT + userId;
    }

    // 某个用户关注的实体
    // follow:userId:entityType -> zset(entityId,now)
    public static String getFollowKey(int userId, int entityType) {
        return PREFIX_FOLLOW + SPLIT + userId + SPLIT + entityType;
    }

    // 某个实体拥有的粉丝
    // follower:entityType:entityId -> zset(userId,now)
    public static String getFollowerKey(int entityType, int entityId) {
        return PREFIX_FOLLOWER + SPLIT + entityType + SPLIT + entityId;
    }

}
