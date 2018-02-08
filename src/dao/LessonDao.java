package dao;

import model.Lesson;
import model.LessonKey;

public interface LessonDao {
    public void save(Lesson lesson);

    public void delete(Lesson lesson);

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
}
