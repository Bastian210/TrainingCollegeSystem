package dao;

import model.Lesson;
import model.LessonKey;

public interface LessonDao {
    public void save(Lesson lesson);

    /**
     * 根据lessonkey查找lesson
     * @param lessonKey
     * @return
     */
    public Lesson findLessonByLessonKey(LessonKey lessonKey);

    /**
     * 根据lessonid和学生名删除lesson
     * @param lessonid
     * @param name
     * @return
     */
    public Lesson deleteLessonByLessonidAndName(String lessonid, String name);
}
