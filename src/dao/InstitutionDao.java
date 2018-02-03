package dao;

import model.Institution;

public interface InstitutionDao {
    public void save(Institution institution);

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
    public void updatePayIdByUserId(String id,String payid);
}
