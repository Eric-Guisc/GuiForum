package priv.gsc.guiforum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.gsc.guiforum.dao.UserMapper;
import priv.gsc.guiforum.entity.User;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User findUserById(int id) {
        User user = userMapper.selectUserById(id);
        return user;
    }
}
