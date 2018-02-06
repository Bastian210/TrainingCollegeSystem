package model;

import java.io.Serializable;

public class CheckinKey implements Serializable {
    private static final long serialVersionUID = 1L;

    private String lessonid;
    private String classtype;
    private String name;
    private int classhour;

    public CheckinKey(){

    }

    public CheckinKey(String lessonid, String classtype, String name, int classhour) {
        this.lessonid = lessonid;
        this.classtype = classtype;
        this.name = name;
        this.classhour = classhour;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CheckinKey that = (CheckinKey) o;

        if (classhour != that.classhour) return false;
        if (lessonid != null ? !lessonid.equals(that.lessonid) : that.lessonid != null) return false;
        if (classtype != null ? !classtype.equals(that.classtype) : that.classtype != null) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = lessonid != null ? lessonid.hashCode() : 0;
        result = 31 * result + (classtype != null ? classtype.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + classhour;
        return result;
    }
}
