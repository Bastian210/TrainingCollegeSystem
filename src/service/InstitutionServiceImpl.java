package service;

import dao.InstitutionDao;
import dao.InstitutionDaoImpl;
import dao.PaymentDao;
import model.Institution;
import model.Payment;
import model.Teachers;
import model.TeachersKey;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InstitutionServiceImpl implements InstitutionService {

    @Autowired
    private InstitutionDao institutionDao;

    @Autowired
    private PaymentDao paymentDao;

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
        Institution institution = new Institution(institutionid,name,password,address,phone,null,"not checked",null,0);
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
        String payid = institution.getPayid();
        if(payid==null){
            map.put("payid","");
            map.put("balance","");
        }else{
            map.put("payid",payid);
            map.put("balance", String.valueOf(paymentDao.findPaymentByPayId(payid).getBalance()));
        }
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

    @Override
    public String BindAccount(String id, String payid, String password) {
        Payment payment = paymentDao.findPaymentByPayId(payid);
        String cor_password = payment.getPassword();
        if(payment==null){
            return "wrong id";
        }else if(!cor_password.equals(password)){
            return "wrong password";
        }else{
            institutionDao.updatePayIdById(id, payid);
            return "success";
        }
    }

    @Override
    public void UnbindAccount(String id, String payid) {
        institutionDao.updatePayIdById(id,"");
    }

    @Override
    public String ChangePaymentPassword(String payid, String oldPassword, String newPassword) {
        Payment payment = paymentDao.findPaymentByPayId(payid);
        if(!payment.getPassword().equals(oldPassword)){
            return "wrong password";
        }
        paymentDao.updatePasswordByPayid(payid,newPassword);
        return "success";
    }

    @Override
    public String ChangePassword(String id, String oldPassword, String newPassword) {
        Institution institution = institutionDao.findInstitutionById(id);
        if(!institution.getPassword().equals(oldPassword)){
            return "wrong password";
        }
        institutionDao.updatePasswordById(id,newPassword);
        return "success";
    }

    @Override
    public void AddTeacher(String id, String name, String gender, String type) {
        Teachers teachers = new Teachers(id,name,gender,type);
        institutionDao.saveTeacher(teachers);
    }

    @Override
    public Map GetTeacher(String id) {
        List list = institutionDao.findTeachersById(id);
        Map<String,String[]> map = new HashMap<>();
        int length = list.size();
        String[] indexlist = new String[length];
        String[] namelist = new String[length];
        String[] genderlist = new String[length];
        String[] typelist = new String[length];
        for(int i=0;i<length;i++){
            Teachers teachers = (Teachers) list.get(i);
            indexlist[i] = String.valueOf(i+1);
            namelist[i] = teachers.getName();
            genderlist[i] = teachers.getGender();
            typelist[i] = teachers.getType();
        }
        map.put("index",indexlist);
        map.put("name",namelist);
        map.put("gender",genderlist);
        map.put("type",typelist);
        return map;
    }

    @Override
    public void ChangeTeacherMessage(String id, String name, String gender, String type) {
        institutionDao.updateTeacherMessageByTeachersKey(new TeachersKey(id,name),gender,type);
    }

    @Override
    public void DeleteTeacher(String id, String name) {
        institutionDao.deleteTeacher(new TeachersKey(id,name));
    }
}
