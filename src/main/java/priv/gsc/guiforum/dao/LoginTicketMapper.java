package priv.gsc.guiforum.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import priv.gsc.guiforum.entity.LoginTicket;

@Mapper
@Deprecated     // 不推荐使用的注解
public interface LoginTicketMapper {

    // 新增登录凭证
    @Insert({
            "insert into login_ticket(user_id, ticket, valid_status, expired) ",
            "values(#{userId}, #{ticket}, #{validStatus}, #{expired})"
    })
    int insertLoginTicket(LoginTicket loginTicket);

    // 根据ticket查询登录凭证
    @Select({
            "select * ",
            "from login_ticket where ticket = #{ticket}"
    })
    LoginTicket selectLoginTicketByTicket(String ticket);

    // 修改有效状态
    @Update({
            "update login_ticket set valid_status = #{validStatus} where ticket = #{ticket}"
    })
    int updateValidStatus(String ticket, int validStatus);
}
