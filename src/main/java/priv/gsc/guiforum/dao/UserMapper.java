package priv.gsc.guiforum.dao;

import org.apache.ibatis.annotations.Mapper;
import priv.gsc.guiforum.entity.User;

@Mapper
public interface UserMapper {

    // 新增用户
    int insertUser(User user);

    // 根据Id查询用户
    User selectUserById(int id);
}
