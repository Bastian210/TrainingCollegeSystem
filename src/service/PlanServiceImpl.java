package service;

import dao.PlanDao;
import model.Plans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanServiceImpl implements PlanService {

    @Autowired
    private PlanDao planDao;

    @Override
    public void AddPlan(String id, String name, String type, String begin, String end, String classhour, String description,
                        String[] teacherList, String[] typeList, String[] classNumList, String[] stuNumList, String[] priceList) {
        String max = planDao.getMaxId();
        int max_id = Integer.valueOf(max);
        for(int i=0;i<teacherList.length;i++){
            max_id++;
            planDao.save(new Plans(String.valueOf(max_id),id,begin,end,Integer.valueOf(classhour),type,name,description,teacherList[i],
                    typeList[i],Integer.valueOf(classNumList[i]),Integer.valueOf(stuNumList[i]),Double.valueOf(priceList[i])));
        }
    }
}
