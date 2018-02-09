package dao;

import model.Checkin;
import model.CheckinKey;
import model.Lesson;
import model.LessonKey;

import java.util.List;

public interface LessonDao {
    public void save(Lesson lesson);

    public void delete(Lesson lesson);

    public void update(Lesson lesson);

    /**
     * 根据lessonkey查找lesson
     * @param lessonKey
     * @return
     */
    public Lesson findLessonByLessonKey(LessonKey lessonKey);

    /**
     * 根据lessonid和学生名查找lesson
     * @param lessonid
     * @param name
     */
    public Lesson findLessonByLessonidAndName(String lessonid, String name);

    /**
     * 根据姓名查找课程
     * @param name
     * @return
     */
    public List findLessonListByName(String name);

    /**
     * 自动更新state字段
     * @param lessonid
     * @param state
     */
    public void updateStateByLessonid(String lessonid, String state);

    /**
     * 根据lessonid、班级类型和班级id来查找lesson
     * @param lessonid
     * @param classtype
     * @param classid
     * @return
     */
    public List findLessonByLessonidAndClassid(String lessonid,String classtype,String classid);

    /**
     * 根据主键查找登记信息
     * @param checkinKey
     * @return
     */
    public Checkin findCheckinByKey(CheckinKey checkinKey);

    /**
     * 保存登记信息
     * @param checkin
     */
    public void saveCheckIn(Checkin checkin);
}
