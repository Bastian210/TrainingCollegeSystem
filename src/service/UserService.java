package service;

public interface UserService {

    /**
     * 登录
     * @param email
     * @param password
     * @return
     */
    public String Login(String email, String password);

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
}
