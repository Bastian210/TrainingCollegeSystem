package service;

public interface InstitutionService {
    /**
     * 教育机构注册
     * @param name
     * @param address
     * @param phone
     * @param password
     * @return
     */
    public String Register(String name,String address,String phone, String password);
}
