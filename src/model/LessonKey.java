package model;

import java.io.Serializable;

public class LessonKey implements Serializable {
    private static final long serialVersionUID = 1L;

    private String lessonid;
    private String classtype;
    private String userid;

    public LessonKey(){

    }

    public LessonKey(String lessonid, String classtype, String userid) {
        this.lessonid = lessonid;
        this.classtype = classtype;
        this.userid = userid;
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LessonKey lessonKey = (LessonKey) o;

        if (lessonid != null ? !lessonid.equals(lessonKey.lessonid) : lessonKey.lessonid != null) return false;
        if (classtype != null ? !classtype.equals(lessonKey.classtype) : lessonKey.classtype != null) return false;
        return userid != null ? userid.equals(lessonKey.userid) : lessonKey.userid == null;
    }

    @Override
    public int hashCode() {
        int result = lessonid != null ? lessonid.hashCode() : 0;
        result = 31 * result + (classtype != null ? classtype.hashCode() : 0);
        result = 31 * result + (userid != null ? userid.hashCode() : 0);
        return result;
    }
}
