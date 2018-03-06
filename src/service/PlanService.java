package service;

import org.json.JSONObject;

public interface PlanService {

    /**
     * 添加计划
     * @param id
     * @param name
     * @param type
     * @param begin
     * @param end
     * @param classhour
     * @param description
     * @param teacherList
     * @param typeList
     * @param classNumList
     * @param stuNumList
     * @param priceList
     */
    public void AddPlan(String id,String name,String type,String begin,String end,String classhour,String description,
                        String[] teacherList, String[] typeList,String[] classNumList,String[] stuNumList,String[] priceList);

    /**
     * 删除计划
     * @param lessonid
     */
    public void DeletePlan(String lessonid);

    /**
     * 保存lessonid到静态变量
     * @param lessonid
     */
    public void SaveLessonId(String lessonid);

    /**
     * 根据lessonid得到计划/课程
     * @param lessonid
     */
    public JSONObject GetPlan(String lessonid);

    /**
     * 得到一个机构的所有计划
     * @param institutionid
     */
    public JSONObject[] GetPlanList(String institutionid);

    /**
     * 发布计划（开始销售）
     * @param lessonid
     */
    public void ReleasePlan(String lessonid);

    /**
     * 更新计划
     * @param lessonid
     * @param institutionid
     * @param name
     * @param type
     * @param begin
     * @param end
     * @param classhour
     * @param description
     * @param teacherList
     * @param typeList
     * @param classNumList
     * @param stuNumList
     * @param priceList
     */
    public void EditPlan(String lessonid, String institutionid, String name, String type, String begin, String end, String classhour, String description, String[] teacherList, String[] typeList, String[] classNumList, String[] stuNumList, String[] priceList);

    /**
     * 得到推荐课程列表
     * @param userid
     * @return
     */
    public JSONObject[] GetLessonList(String userid);

    /**
     * 搜索课程
     * @param lessonName
     * @param school
     * @param subject
     * @return
     */
    public JSONObject[] SearchLessonList(String lessonName,String school, String[] subject);

    /**
     * 经理检查计划/课程
     */
    public void CheckPlan();
}
