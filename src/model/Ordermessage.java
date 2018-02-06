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
    @Column(name = "name")
    private String name;
    @Column(name = "gender")
    private String gender;
    @Column(name = "education")
    private String education;

    public Ordermessage(){

    }

    public Ordermessage(String orderid, String name, String gender, String education) {
        this.orderid = orderid;
        this.name = name;
        this.gender = gender;
        this.education = education;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }
}
