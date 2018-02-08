package dao;

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
}
