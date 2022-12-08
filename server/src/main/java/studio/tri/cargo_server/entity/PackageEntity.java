package studio.tri.cargo_server.entity;

import java.util.Date;

public class PackageEntity {
    private int id;
    private String orderid;
    private String startplace;
    private String endplace;
    private String status;
    private String sender;
    private String receiver;
    private Date time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getStartplace() {
        return startplace;
    }

    public void setStartplace(String startplace) {
        this.startplace = startplace;
    }

    public String getEndplace() {
        return endplace;
    }

    public void setEndplace(String endplace) {
        this.endplace = endplace;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
