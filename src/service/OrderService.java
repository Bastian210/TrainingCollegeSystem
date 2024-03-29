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
     * 自动取消订单
     * @param orderid
     */
    public void UnsubscribeOrder(String orderid);

    /**
     * 得到一个用户的所有订单
     * @param userid
     * @return
     */
    public JSONObject GetAllOrder(String userid);

    /**
     * 根据orderid得到学员情况
     * @param orderid
     * @return
     */
    public JSONObject GetOrderMessage(String orderid);

    /**
     * 取消订单
     * @param orderid
     * @param price
     */
    public void CancelOrder(String orderid,String price);

    /**
     * 删除订单
     * @param orderid
     */
    public void DeleteOrder(String orderid);

    /**
     * 根据机构id得到它内部所有的订单
     * @param institutionid
     * @return
     */
    public JSONObject GetAllInsOrder(String institutionid);
}
