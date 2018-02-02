package service;

import dao.InstitutionDao;
import dao.InstitutionDaoImpl;
import model.Institution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstitutionServiceImpl implements InstitutionService {
    @Autowired
    private InstitutionDao institutionDao;

    @Override
    public String Register(String name, String address, String phone, String password) {
        Institution old = institutionDao.findInstitutionByPhone(phone);
        if(old!=null){
            return "has register";
        }
        int num = institutionDao.getMaxId();
        String institutionid = String.valueOf(num+1);
        Institution institution = new Institution(institutionid,name,password,address,phone,null,"not checked");
        institutionDao.save(institution);
        return institutionid;
    }
}
