package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "orders")
public class Orders implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "orderid")
    private String orderid;
    @Column(name = "userid")
    private String userid;
    @Column(name = "institutionid")
    private String institutionid;
    @Column(name = "type")
    private String type;
    @Column(name = "num")
    private int num;
    @Column(name = "time")
    private String time;
    @Column(name = "state")
    private String state;

    public Orders() {
    }

    public Orders(String orderid, String userid, String institutionid, String type, int num, String time, String state) {
        this.orderid = orderid;
        this.userid = userid;
        this.institutionid = institutionid;
        this.type = type;
        this.num = num;
        this.time = time;
        this.state = state;
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

    public String getInstitutionid() {
        return institutionid;
    }

    public void setInstitutionid(String institutionid) {
        this.institutionid = institutionid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
