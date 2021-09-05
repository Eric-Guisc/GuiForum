package priv.gsc.guiforum.dao;

import org.apache.ibatis.annotations.Mapper;
import priv.gsc.guiforum.entity.Message;

@Mapper
public interface MessageMapper {

    // 新增消息
    int insertMessage(Message message);
}
