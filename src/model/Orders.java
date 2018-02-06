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
    @Column(name = "lessonid")
    private String lessonid;
    @Column(name = "type")
    private String type;
    @Column(name = "num")
    private int num;
    @Column(name = "ordertime")
    private String ordertime;
    @Column(name = "price")
    private double price;
    @Column(name = "actualpay")
    private double actualpay;
    @Column(name = "classtype")
    private String classtype;
    @Column(name = "deadline")
    private String deadline;
    @Column(name = "state")
    private String state;

    public Orders() {
    }

    public Orders(String orderid, String userid, String institutionid, String lessonid, String type, int num, String ordertime, double price, double actualpay, String classtype, String deadline, String state) {
        this.orderid = orderid;
        this.userid = userid;
        this.institutionid = institutionid;
        this.lessonid = lessonid;
        this.type = type;
        this.num = num;
        this.ordertime = ordertime;
        this.price = price;
        this.actualpay = actualpay;
        this.classtype = classtype;
        this.deadline = deadline;
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

    public String getLessonid() {
        return lessonid;
    }

    public void setLessonid(String lessonid) {
        this.lessonid = lessonid;
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

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getActualpay() {
        return actualpay;
    }

    public void setActualpay(double actualpay) {
        this.actualpay = actualpay;
    }

    public String getClasstype() {
        return classtype;
    }

    public void setClasstype(String classtype) {
        this.classtype = classtype;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
