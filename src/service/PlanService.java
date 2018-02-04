package service;

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
}
