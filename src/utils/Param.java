package utils;

public class Param {
    private static String userid;

    private static String institutionid;

    private static String lessonid;

    public static String getUserid() {
        return userid;
    }

    public static void setUserid(String userid) {
        Param.userid = userid;
    }

    public static String getInstitutionid() {
        return institutionid;
    }

    public static void setInstitutionid(String institutionid) {
        Param.institutionid = institutionid;
    }

    public static String getLessonid() {
        return lessonid;
    }

    public static void setLessonid(String lessonid) {
        Param.lessonid = lessonid;
    }
}
