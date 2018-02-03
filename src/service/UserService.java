package service;

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
    public Map getUserMessage(String userid);

    /**
     * 根据userid修改个人信息（姓名、性别、教育程度）
     * @param userid
     * @param username
     * @param gender
     * @param education
     * @return
     */
    public void ChangeUserMessage(String userid, String username, String gender, String education);
}
