package model;

import java.io.Serializable;

public class PlansKey implements Serializable {
    private static final long serialVersionUID = 1L;

    private String lessonid;
    private String classtype;

    public PlansKey() {
    }

    public PlansKey(String lessonid, String classtype) {
        this.lessonid = lessonid;
        this.classtype = classtype;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlansKey plansKey = (PlansKey) o;

        if (lessonid != null ? !lessonid.equals(plansKey.lessonid) : plansKey.lessonid != null) return false;
        return classtype != null ? classtype.equals(plansKey.classtype) : plansKey.classtype == null;
    }

    @Override
    public int hashCode() {
        int result = lessonid != null ? lessonid.hashCode() : 0;
        result = 31 * result + (classtype != null ? classtype.hashCode() : 0);
        return result;
    }
}
