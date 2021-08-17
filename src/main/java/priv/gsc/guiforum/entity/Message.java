package priv.gsc.guiforum.entity;

import java.util.Date;

public class Message {
    private int id;
    private int fromId;             // fromId = 1 为系统发的通知，不是私信
    private int toId;
    private String conversationId;  // 私信时为fromId和toId拼接的，系统通知时为通知的类型
    private String content;
    private int status;             // 0：未读  1：已读  2：删除
    private Date createTime;
}
