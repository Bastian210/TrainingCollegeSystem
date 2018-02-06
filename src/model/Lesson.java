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
    @Column(name = "name")
    private String name;
    @Column(name = "grade")
    private double grade;
    @Column(name = "state") //未开课、开课中、已结课、已退课
    private String state;
    @Column(name = "classid")
    private String classid;

    public Lesson() {
    }

    public Lesson(String lessonid, String classtype, String name, double grade, String state, String classid) {
        this.lessonid = lessonid;
        this.classtype = classtype;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
