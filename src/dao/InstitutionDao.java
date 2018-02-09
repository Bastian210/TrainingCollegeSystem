package dao;

import model.Institution;
import model.Teachers;
import model.TeachersKey;

import java.util.List;

public interface InstitutionDao {
    public void save(Institution institution);

    public void update(Institution institution);

    /**
     * 根据识别码查找机构
     * @param id
     * @return
     */
    public Institution findInstitutionById(String id);

    /**
     * 得到当前最大的机构识别码
     * @return
     */
    public int getMaxId();

    /**
     * 根据联系方式来查找教育机构
     * @param phone
     * @return
     */
    public Institution findInstitutionByPhone(String phone);

    /**
     * 根据识别码更新修改的信息
     * @param id
     * @param chanMess
     */
    public void updateChanMessById(String id, String chanMess);

    /**
     * 根据识别码更新支付账号
     * @param id
     * @param payid
     */
    public void updatePayIdById(String id,String payid);

    /**
     * 根据识别码更新登录密码
     * @param id
     * @param password
     */
    public void updatePasswordById(String id,String password);

    /**
     * 保存教师
     * @param teachers
     */
    public void saveTeacher(Teachers teachers);

    /**
     * 根据识别码得到教师列表
     * @param id
     * @return
     */
    public List findTeachersById(String id);

    /**
     * 根据教师主键更新教师信息
     * @param key
     * @param gender
     * @param type
     */
    public void updateTeacherMessageByTeachersKey(TeachersKey key, String gender, String type);

    /**
     * 根据教师主键删除教师
     * @param key
     */
    public void deleteTeacher(TeachersKey key);

    /**
     * 根据状态查找机构
     * @param state
     * @return
     */
    public List findInstitutionByState(String state);

    /**
     * 根据修改信息查找机构
     * @return
     */
    public List findInstitutionByChanMess();
}
