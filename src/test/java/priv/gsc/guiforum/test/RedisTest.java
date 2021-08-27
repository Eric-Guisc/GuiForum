package priv.gsc.guiforum.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import priv.gsc.guiforum.GuiForumApplication;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = GuiForumApplication.class)
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testTransactional() {
        Object obj = redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String redisKey = "test:tx";
                operations.multi();	// 开启事务
                operations.opsForSet().add(redisKey, "zhangsan");
                operations.opsForSet().add(redisKey, "lisi");
                operations.opsForSet().add(redisKey, "wangwu");
                System.out.println(operations.opsForSet().members(redisKey));	// 在事务中做查询，此时数据还没有在事务中提交，所以为空
                return operations.exec();	// 提交事务
            }
        });

        System.out.println(obj);
        System.out.println(redisTemplate.opsForSet().members("test:tx"));
    }
}
