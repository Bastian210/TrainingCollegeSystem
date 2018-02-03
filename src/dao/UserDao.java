package dao;

import model.Payment;
import model.User;

public interface UserDao {
    public void save(User user);

    public void delete(String userid);

    /**
     * 根据id查找用户
     * @param userid
     * @return
     */
    public User findUserByUserid(String userid);

    /**
     * 根据邮箱查找用户
     * @param email
     * @return
     */
    public User findUserByEmail(String email);

    /**
     * 得到数据库中最大的userid
     * @return
     */
    public String getMaxUserid();

    /**
     * 保存生成的验证码
     * @param email
     * @param code
     * @return
     */
    public void saveCode(String email, String code);

    /**
     * 取出验证码
     * @param email
     */
    public String getCode(String email);

    /**
     * 根据userid修改个人信息（姓名、性别、教育程度）
     * @param userid
     * @param username
     * @param gender
     * @param education
     */
    public void updateMessageByUserId(String userid, String username, String gender, String education);

    /**
     * 绑定支付账号
     * @param userid
     * @param payid
     */
    public void updatePayIdByUserId(String userid, String payid);

    /**
     * 根据payid查找支付账号实体
     * @param payid
     * @return
     */
    public Payment findPaymentByPayId(String payid);

    /**
     * 更改支付密码
     * @param payid
     * @param password
     */
    public void updatePasswordByPayid(String payid,String password);
}
