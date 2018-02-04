package dao;

import model.Plans;

public interface PlanDao {

    public void save(Plans plans);

    /**
     * 得到当前最大的lessonid
     * @return
     */
    public String getMaxId();
}
