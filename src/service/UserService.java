package service;

import org.json.JSONObject;

import java.util.Map;

public interface UserService {

    /**
     * 登录
     * @param email
     * @param password
     * @return
     */
    public String Login(String email, String password);

    /**
     * 经理登录
     * @param id
     * @param password
     * @return
     */
    public String ManagerLogin(String id, String password);

    /**
     * 发送验证码
     * @param email
     */
    public void SendCode(String email);

    /**
     * 注册
     * @param username
     * @param email
     * @param password
     * @param code 验证码
     * @return
     */
    public String Register(String username, String email, String password,String code);

    /**
     * 根据userid得到个人信息（姓名、性别、教育程度）
     * @param userid
     * @return
     */
    public Map GetUserMessage(String userid);

    /**
     * 根据userid修改个人信息（姓名、性别、教育程度）
     * @param userid
     * @param username
     * @param gender
     * @param education
     * @return
     */
    public void ChangeUserMessage(String userid, String username, String gender, String education);

    /**
     * 绑定支付账号
     * @param userid
     * @param payid
     * @param password
     * @return
     */
    public String BindAccount(String userid, String payid, String password);

    /**
     * 解绑支付账号
     * @param userid
     * @param payid
     * @return
     */
    public void UnbindAccount(String userid, String payid);

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
     * @param userid
     * @param oldPassword
     * @param newPassword
     * @return
     */
    public String ChangePassword(String userid, String oldPassword, String newPassword);

    /**
     * 注销账号
     * @param userid
     * @return
     */
    public void WriteOff(String userid);

    /**
     * 得到用户支付信息（会员等级，积分,有无支付账号）
     * @param userid
     * @return
     */
    public JSONObject GetUserPayMessage(String userid);

    /**
     * 用户支付
     * @param userid
     * @param password
     * @param price
     * @param orderid
     * @param checkbox
     * @return
     */
    public String Pay(String userid,String password,String price,String orderid,String checkbox);

    /**
     * 检测userid是不是本系统会员
     * @param userid
     * @return
     */
    public String TestVip(String userid);

    /**
     * 得到所有用户
     * @return
     */
    public JSONObject[] GetAllUser();

    /**
     * 得到系统利润
     * @return
     */
    public double getProfit();

    /**
     * 获取用户的每月账单
     * @param userid
     * @return
     */
    public JSONObject GetMonthBill(String userid);
}
