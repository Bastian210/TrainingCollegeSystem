package service;

import org.json.JSONObject;

public interface LessonService {

    /**
     * 得到一个用户的所有课程
     * @param userid
     * @return
     */
    public JSONObject[] GetLessonByUserId(String userid);

    /**
     * 得到一个机构的所有课程
     * @param institutionid
     * @return
     */
    public JSONObject[] GetLessonByInstitutionId(String institutionid);

    /**
     * 退课
     * @param lessonid
     * @param userid
     */
    public void WithdrawLesson(String lessonid,String userid);

    /**
     * 现场缴费
     * @param lessonid
     * @param type
     * @param userid
     * @param classtype
     * @param actual
     * @param nameList
     * @param genderList
     * @param educationList
     */
    public void OnSiteBook(String lessonid, String type, String userid, String classtype, String actual, String[] nameList, String[] genderList, String[] educationList);
}
