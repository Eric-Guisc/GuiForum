package priv.gsc.guiforum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import priv.gsc.guiforum.util.RedisKeyUtil;

@Service
public class ViewService {

    @Autowired
    private RedisTemplate redisTemplate;

    // 某实体的浏览量 + 1
    public void addViewCount(int entityType, int entityId) {
        String viewKey = RedisKeyUtil.getViewKey(entityType, entityId);
        if (redisTemplate.hasKey(viewKey)) {
            redisTemplate.opsForValue().increment(viewKey);
        } else {
            redisTemplate.opsForValue().set(viewKey, 1);
        }
    }

    // 查询某实体的浏览量
    public long findViewCount(int entityType, int entityId) {
        String viewKey = RedisKeyUtil.getViewKey(entityType, entityId);
        if (redisTemplate.hasKey(viewKey)) {
            Integer viewCount = (Integer) redisTemplate.opsForValue().get(viewKey);
            return viewCount;
        } else
            return 0;
    }
}
