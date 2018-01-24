package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "checkin")
@IdClass(LessonKey.class)
public class Checkin implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "lessonid")
    private String lessonid;
    @Id
    @Column(name = "userid")
    private String userid;
    @Column(name = "time")
    private String time;
    @Column(name = "classhour")
    private int classhour;
    @Column(name = "isattend")
    private int isattend;

    public Checkin() {
    }

    public Checkin(String lessonid, String userid, String time, int classhour, int isattend) {
        this.lessonid = lessonid;
        this.userid = userid;
        this.time = time;
        this.classhour = classhour;
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getClasshour() {
        return classhour;
    }

    public void setClasshour(int classhour) {
        this.classhour = classhour;
    }

    public int getIsattend() {
        return isattend;
    }

    public void setIsattend(int isattend) {
        this.isattend = isattend;
    }
}
