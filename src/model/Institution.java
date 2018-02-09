package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "institution")
public class Institution implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "institutionid")
    private String institutionid;
    @Column(name = "institutionname")
    private String institutionname;
    @Column(name = "password")
    private  String password;
    @Column(name = "address")
    private String address;
    @Column(name = "phone")
    private String phone;
    @Column(name = "payid")
    private String payid;
    @Column(name = "state")
    private String state;
    @Column(name = "chanMess")
    private String chanMess;
    @Column(name = "consumption")
    private double consumption;

    public Institution(){}

    public Institution(String institutionid, String institutionname, String password, String address, String phone, String payid, String state, String chanMess, double consumption) {
        this.institutionid = institutionid;
        this.institutionname = institutionname;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.payid = payid;
        this.state = state;
        this.chanMess = chanMess;
        this.consumption = consumption;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getInstitutionid() {
        return institutionid;
    }

    public void setInstitutionid(String institutionid) {
        this.institutionid = institutionid;
    }

    public String getInstitutionname() {
        return institutionname;
    }

    public void setInstitutionname(String institutionname) {
        this.institutionname = institutionname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPayid() {
        return payid;
    }

    public void setPayid(String payid) {
        this.payid = payid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getChanMess() {
        return chanMess;
    }

    public void setChanMess(String chanMess) {
        this.chanMess = chanMess;
    }

    public double getConsumption() {
        return consumption;
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }
}
