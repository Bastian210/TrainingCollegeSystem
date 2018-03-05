package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "bill")
@IdClass(BillKey.class)
public class Bill implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private String id;  //包含userid和institutionid
    @Id
    @Column(name = "month")
    private String month;
    @Column(name = "income")
    private double income;

    public Bill() {
    }

    public Bill(String id, String month, double income) {
        this.id = id;
        this.month = month;
        this.income = income;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }
}
