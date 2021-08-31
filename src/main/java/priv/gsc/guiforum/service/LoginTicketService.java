package priv.gsc.guiforum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import priv.gsc.guiforum.dao.LoginTicketMapper;
import priv.gsc.guiforum.entity.LoginTicket;
import priv.gsc.guiforum.util.RedisKeyUtil;

@Service
public class LoginTicketService {

    @Autowired
//    private LoginTicketMapper loginTicketMapper;
    private RedisTemplate redisTemplate;

    public LoginTicket findLoginTicketByTicket(String ticket) {
//        LoginTicket loginTicket = loginTicketMapper.selectLoginTicketByTicket(ticket);
//        return loginTicket;
        String ticketKey = RedisKeyUtil.getTicketKey(ticket);
        return (LoginTicket) redisTemplate.opsForValue().get(ticketKey);
    }
}
