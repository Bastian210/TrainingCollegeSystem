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
}
