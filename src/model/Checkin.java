package model;

import org.springframework.stereotype.Controller;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "checkin")
@IdClass(CheckinKey.class)
public class Checkin implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "lessonid")
    private String lessonid;
    @Id
    @Column(name = "classtype")
    private String classtype;
    @Id
    @Column(name = "name")
    private String name;
    @Id
    @Column(name = "classhour")
    private int classhour;
    @Column(name = "time")
    private String time;
    @Column(name = "isattend")
    private int isattend;

    public Checkin() {
    }

    public Checkin(String lessonid, String classtype, String name, int classhour, String time, int isattend) {
        this.lessonid = lessonid;
        this.classtype = classtype;
        this.name = name;
        this.classhour = classhour;
        this.time = time;
        this.isattend = isattend;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getLessonid() {
        return lessonid;
    }

    public void setLessonid(String lessonid) {
        this.lessonid = lessonid;
    }

    public String getClasstype() {
        return classtype;
    }

    public void setClasstype(String classtype) {
        this.classtype = classtype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getClasshour() {
        return classhour;
    }

    public void setClasshour(int classhour) {
        this.classhour = classhour;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getIsattend() {
        return isattend;
    }

    public void setIsattend(int isattend) {
        this.isattend = isattend;
    }
}
