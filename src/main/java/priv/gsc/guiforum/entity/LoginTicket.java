package priv.gsc.guiforum.entity;

import java.util.Date;

public class LoginTicket {
    private int id;
    private int userId;
    private String ticket;
    private int validStatus;        // 0：有效  1：失效
    private Date expired;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(int validStatus) {
        this.validStatus = validStatus;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    @Override
    public String toString() {
        return "LoginTicket{" +
                "id=" + id +
                ", userId=" + userId +
                ", ticket='" + ticket + '\'' +
                ", validStatus=" + validStatus +
                ", expired=" + expired +
                '}';
    }
}
