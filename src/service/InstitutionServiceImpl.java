package service;

import dao.*;
import model.Institution;
import model.Payment;
import model.Teachers;
import model.TeachersKey;
import org.json.JSONArray;
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

    @Autowired
    private PlanDao planDao;

    @Autowired
    private OrderDao orderDao;

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
        if(institution.getState().equals("fail check")){
            return "fail check";
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
        map.put("profit", String.valueOf(institution.getConsumption()));
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

    @Override
    public JSONObject[] GetRegisterApply() {
        List list = institutionDao.findInstitutionByState("not checked");
        JSONObject[] jsonObjects = new JSONObject[list.size()];
        for(int i=0;i<list.size();i++){
            Institution institution = (Institution) list.get(i);
            JSONObject json = new JSONObject();
            json.put("name",institution.getInstitutionname());
            json.put("address",institution.getAddress());
            json.put("phone",institution.getPhone());
            json.put("id",institution.getInstitutionid());
            jsonObjects[i] = json;
        }
        return jsonObjects;
    }

    @Override
    public JSONObject[] GetChangeMessApply() {
        List list = institutionDao.findInstitutionByChanMess();

        JSONObject[] jsonObjects = new JSONObject[list.size()];
        for(int i=0;i<list.size();i++){
            Institution institution = (Institution) list.get(i);
            String chanMess = institution.getChanMess();
            JSONObject change = new JSONObject(chanMess);

            JSONObject json = new JSONObject();
            if(change.get("name").equals(institution.getInstitutionname())){
                json.put("name",institution.getInstitutionname());
            }else{
                json.put("name",institution.getInstitutionname()+"→"+change.get("name"));
            }

            if(change.get("address").equals(institution.getAddress())){
                json.put("address",institution.getAddress());
            }else{
                json.put("address",institution.getAddress()+"→"+change.get("address"));
            }

            if(change.get("phone").equals(institution.getPhone())){
                json.put("phone",institution.getPhone());
            }else{
                json.put("phone",institution.getPhone()+"→"+change.get("phone"));
            }

            json.put("id",institution.getInstitutionid());
            jsonObjects[i] = json;
        }
        return jsonObjects;
    }

    @Override
    public void RefuseRegister(String id) {
        Institution institution = institutionDao.findInstitutionById(id);
        institution.setState("fail check");
        institutionDao.update(institution);
    }

    @Override
    public void AgreeRegister(String id) {
        Institution institution = institutionDao.findInstitutionById(id);
        institution.setState("checked");
        institutionDao.update(institution);
    }

    @Override
    public void RefuseChange(String id) {
        Institution institution = institutionDao.findInstitutionById(id);
        institution.setChanMess(null);
        institutionDao.update(institution);
    }

    @Override
    public void AgreeChange(String id) {
        Institution institution = institutionDao.findInstitutionById(id);
        JSONObject json = new JSONObject(institution.getChanMess());
        institution.setInstitutionname((String) json.get("name"));
        institution.setAddress((String) json.get("address"));
        institution.setPhone((String) json.get("phone"));
        institution.setChanMess(null);
        institutionDao.update(institution);
    }

    @Override
    public JSONObject[] GetAllInstitution() {
        List list = institutionDao.findInstitutionByState("checked");
        JSONObject[] jsonObjects = new JSONObject[list.size()];
        for(int i=0;i<list.size();i++){
            Institution institution = (Institution) list.get(i);
            JSONObject json = new JSONObject();
            json.put("id",institution.getInstitutionid());
            json.put("name",institution.getInstitutionname());
            json.put("address",institution.getAddress());
            json.put("phone",institution.getPhone());
            json.put("consumption",institution.getConsumption());

            List plansList = planDao.getPlanListByInstitutionId(institution.getInstitutionid());
            json.put("plans",plansList.size());
            List ordersList = orderDao.findOrderListByInstitutionId(institution.getInstitutionid());
            json.put("orders",ordersList.size());

            jsonObjects[i] = json;
        }
        return jsonObjects;
    }
}
