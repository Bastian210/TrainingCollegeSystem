package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ordermessage")
@IdClass(OrdermessageKey.class)
public class Ordermessage implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "orderid")
    private String orderid;
    @Id
    @Column(name = "userid")
    private String userid;
    @Column(name = "classtype")
    private String classtype;
    @Column(name = "classid")
    private String classid;

    public Ordermessage(){

    }

    public Ordermessage(String orderid, String userid, String classtype, String classid) {
        this.orderid = orderid;
        this.userid = userid;
        this.classtype = classtype;
        this.classid = classid;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getClasstype() {
        return classtype;
    }

    public void setClasstype(String classtype) {
        this.classtype = classtype;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }
}
