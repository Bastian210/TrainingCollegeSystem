package dao;

import model.Institution;

public interface InstitutionDao {
    public void save(Institution institution);

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
}
