package service;

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
}
