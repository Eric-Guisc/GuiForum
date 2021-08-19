package priv.gsc.guiforum.dao;

import org.apache.ibatis.annotations.Mapper;
import priv.gsc.guiforum.entity.User;

@Mapper
public interface UserMapper {

    // 新增用户
    int insertUser(User user);

    // 根据Id查询用户
    User selectUserById(int id);

    // 根据用户名查询用户
    User selectUserByName(String name);

    // 根据邮箱查询用户
    User selectUserByEmail(String email);

    // 修改用户的激活状态为已激活
    int updateActivationStatus(int id, int status);
}
