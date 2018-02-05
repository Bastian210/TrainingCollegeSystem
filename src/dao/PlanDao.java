package dao;

import model.Plans;

import java.util.List;

public interface PlanDao {

    public void save(Plans plans);

    /**
     * 根据lessonid删除计划
     * @param lessonid
     */
    public void delete(String lessonid);

    /**
     * 得到当前最大的lessonid
     * @return
     */
    public String getMaxId();

    /**
     * 根据机构id得到所有计划
     * @param institutionid
     * @return
     */
    public List getPlanListByInstitutionId(String institutionid);

    /**
     * 根据lessonid得到相关计划
     * @param lessonid
     * @return
     */
    public List getPlanByLessonId(String lessonid);

    /**
     * 根据lessonid更新状态
     * @param lessonid
     * @param state
     */
    public void updateStateByLessonId(String lessonid, String state);

    /**
     * 根据课程名和类型得到课程列表
     * @param name
     * @param type
     * @return
     */
    public List getLessonByNameAndType(String name,String[] type);
}
