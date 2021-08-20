package priv.gsc.guiforum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.gsc.guiforum.dao.LoginTicketMapper;
import priv.gsc.guiforum.entity.LoginTicket;

@Service
public class LoginTicketService {

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    public LoginTicket findLoginTicketByTicket(String ticket) {
        LoginTicket loginTicket = loginTicketMapper.selectLoginTicketByTicket(ticket);
        return loginTicket;
    }
}
