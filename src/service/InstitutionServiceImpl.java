package service;

import dao.InstitutionDao;
import dao.InstitutionDaoImpl;
import model.Institution;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.Param;

import java.util.HashMap;
import java.util.Map;

@Service
public class InstitutionServiceImpl implements InstitutionService {
    @Autowired
    private InstitutionDao institutionDao;

    @Override
    public String Login(String id, String password) {
        Institution institution = institutionDao.findInstitutionById(id);
        if(institution==null){
            return "wrong id";
        }
        if(!institution.getPassword().equals(password)){
            return "wrong password";
        }
        if(institution.getState().equals("not checked")){
            return "still in check";
        }
        Param.setInstitutionid(id);
        return "success";
    }

    @Override
    public String Register(String name, String address, String phone, String password) {
        Institution old = institutionDao.findInstitutionByPhone(phone);
        if(old!=null){
            return "has register";
        }
        int num = institutionDao.getMaxId();
        String institutionid = String.valueOf(num+1);
        Institution institution = new Institution(institutionid,name,password,address,phone,null,"not checked",null);
        institutionDao.save(institution);
        return institutionid;
    }

    @Override
    public Map GetInsMess(String id) {
        Map<String,String> map = new HashMap<>();
        Institution institution = institutionDao.findInstitutionById(id);
        map.put("id",id);
        map.put("name",institution.getInstitutionname());
        map.put("address",institution.getAddress());
        map.put("phone",institution.getPhone());
        return map;
    }

    @Override
    public void ChangeInsMess(String id, String name, String address, String phone) {
        JSONObject json = new JSONObject();
        json.put("name",name);
        json.put("address",address);
        json.put("phone",phone);
        String chanMess = json.toString();
        institutionDao.updateChanMessById(id,chanMess);
    }
}
