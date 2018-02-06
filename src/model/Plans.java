package model;

import javax.naming.Name;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "plans")
@IdClass(PlansKey.class)
public class Plans implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "lessonid")
    private String lessonid;
    @Id
    @Column(name = "classtype")
    private String classtype;
    @Column(name = "institutionid")
    private String institutionid;
    @Column(name = "begin")
    private String begin;
    @Column(name = "end")
    private String end;
    @Column(name = "classhours")
    private int classhours;
    @Column(name = "type")
    private String type;
    @Column(name = "lesson")
    private String lesson;
    @Column(name = "description")
    private String description;
    @Column(name = "teacher")
    private String teacher;
    @Column(name = "classnum")
    private int classnum;
    @Column(name = "studentnum")
    private int studentnum;
    @Column(name = "price")
    private double price;
    @Column(name = "sold")
    private int sold;
    @Column(name = "state")
    private String state;   //"undetermined","selling","soldout","start","end"

    public Plans() {
    }

    public Plans(String lessonid, String classtype, String institutionid, String begin, String end, int classhours, String type, String lesson, String description, String teacher, int classnum, int studentnum, double price, int sold, String state) {
        this.lessonid = lessonid;
        this.classtype = classtype;
        this.institutionid = institutionid;
        this.begin = begin;
        this.end = end;
        this.classhours = classhours;
        this.type = type;
        this.lesson = lesson;
        this.description = description;
        this.teacher = teacher;
        this.classnum = classnum;
        this.studentnum = studentnum;
        this.price = price;
        this.sold = sold;
        this.state = state;
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

    public String getInstitutionid() {
        return institutionid;
    }

    public void setInstitutionid(String institutionid) {
        this.institutionid = institutionid;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getClasshours() {
        return classhours;
    }

    public void setClasshours(int classhours) {
        this.classhours = classhours;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getClasstype() {
        return classtype;
    }

    public void setClasstype(String classtype) {
        this.classtype = classtype;
    }

    public int getClassnum() {
        return classnum;
    }

    public void setClassnum(int classnum) {
        this.classnum = classnum;
    }

    public int getStudentnum() {
        return studentnum;
    }

    public void setStudentnum(int studentnum) {
        this.studentnum = studentnum;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
