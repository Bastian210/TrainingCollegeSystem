package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "lesson")
@IdClass(LessonKey.class)
public class Lesson implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "lessonid")
    private String lessonid;
    @Id
    @Column(name = "classtype")
    private String classtype;
    @Id
    @Column(name = "userid")
    private String userid;
    @Column(name = "grade")
    private double grade;
    @Column(name = "state")
    private String state;
    @Column(name = "classid")
    private String classid;

    public Lesson() {
    }

    public Lesson(String lessonid, String classtype, String userid, double grade, String state, String classid) {
        this.lessonid = lessonid;
        this.classtype = classtype;
        this.userid = userid;
        this.grade = grade;
        this.state = state;
        this.classid = classid;
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

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
