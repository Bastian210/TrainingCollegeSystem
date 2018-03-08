package service;

import org.json.JSONObject;

import java.util.Map;

public interface InstitutionService {
    /**
     * 教育机构登录
     * @param id
     * @param password
     * @return
     */
    public String Login(String id, String password);

    /**
     * 教育机构注册
     * @param name
     * @param address
     * @param phone
     * @param password
     * @return
     */
    public String Register(String name,String address,String phone, String password);

    /**
     * 更具识别码得到机构信息（机构名称、地址、联系方式）
     * @param id
     * @return
     */
    public Map GetInsMess(String id);

    /**
     * 根据识别码修改机构信息（机构名称、地址、联系方式）(需要审核)
     * @param id
     * @param name
     * @param address
     * @param phone
     * @return
     */
    public void ChangeInsMess(String id, String name, String address, String phone);

    /**
     * 机构绑定支付账号
     * @param id
     * @param payid
     * @param password
     * @return
     */
    public String BindAccount(String id, String payid, String password);

    /**
     * 解绑支付账号
     * @param id
     * @param payid
     * @return
     */
    public void UnbindAccount(String id, String payid);

    /**
     * 更改支付密码
     * @param payid
     * @param oldPassword
     * @param newPassword
     * @return
     */
    public String ChangePaymentPassword(String payid,String oldPassword,String newPassword);

    /**
     * 更改登录密码
     * @param id
     * @param oldPassword
     * @param newPassword
     * @return
     */
    public String ChangePassword(String id,String oldPassword,String newPassword);

    /**
     * 教育机构添加教师
     * @param id
     * @param name
     * @param gender
     * @param type
     * @return
     */
    public String AddTeacher(String id,String name,String gender,String type);

    /**
     * 得到教育机构的所有老师
     * @param id
     * @return
     */
    public Map GetTeacher(String id);

    /**
     * 修改教师信息
     * @param id
     * @param name
     * @param gender
     * @param type
     */
    public void ChangeTeacherMessage(String id,String name,String gender,String type);

    /**
     * 删除教师
     * @param id
     * @param name
     */
    public void DeleteTeacher(String id, String name);

    /**
     * 得到一个机构内主教某门课的教师列表
     * @param institutionid
     * @param type
     * @return
     */
    public String[] GetTeacherName(String institutionid,String type);

    /**
     * 得到机构申请列表
     * @return
     */
    public JSONObject[] GetRegisterApply();

    /**
     * 得到机构修改信息申请列表
     * @return
     */
    public JSONObject[] GetChangeMessApply();

    /**
     * 拒绝注册申请
     * @param id
     */
    public void RefuseRegister(String id);

    /**
     * 批准注册申请
     * @param id
     */
    public void AgreeRegister(String id);

    /**
     * 拒绝修改信息
     * @param id
     */
    public void RefuseChange(String id);

    /**
     * 批准修改信息
     * @param id
     */
    public void AgreeChange(String id);

    /**
     * 得到所有机构的信息
     * @return
     */
    public JSONObject[] GetAllInstitution();

    /**
     * 得到机构的月利润
     * @param institutionid
     * @return
     */
    public JSONObject GetMonthBill(String institutionid);
}
