package priv.gsc.guiforum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;
import priv.gsc.guiforum.dao.MessageMapper;
import priv.gsc.guiforum.entity.Message;
import priv.gsc.guiforum.util.SensitiveFilter;

@Service
public class MessageService {

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Autowired
    private MessageMapper messageMapper;

    public int addMessage(Message message) {
        message.setContent(HtmlUtils.htmlEscape(message.getContent()));
        message.setContent(sensitiveFilter.filter(message.getContent()));
        return messageMapper.insertMessage(message);
    }
}
