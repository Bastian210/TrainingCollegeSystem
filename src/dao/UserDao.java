package dao;

import model.User;

public interface UserDao {
    public void save(User user);

    public void delete(String userid);

    public User findUserByUserid(String userid);

    /**
     * 根据邮箱查询密码
     * @param email
     * @return
     */
    public String findPasswordByEmail(String email);

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
}
