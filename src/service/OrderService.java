package service;

import org.json.JSONObject;

public interface OrderService {
    /**
     * 添加订单
     * @param userid
     * @param lessonid
     * @param institutionid
     * @param type
     * @param price
     * @param actualpay
     * @param classtype
     * @param nameList
     * @param genderList
     * @param educationList
     * @return
     */
    public JSONObject AddOrder(String userid, String lessonid,String institutionid, String type, String price, String actualpay,
                               String classtype, String[] nameList, String[] genderList, String[] educationList);

    /**
     * 得到一个用户的所有订单
     * @param userid
     * @return
     */
    public JSONObject[] GetAllOrder(String userid);

    /**
     * 取消订单
     * @param orderid
     */
    public void CancelOrder(String orderid);

    /**
     * 删除订单
     * @param orderid
     */
    public void DeleteOrder(String orderid);
}
