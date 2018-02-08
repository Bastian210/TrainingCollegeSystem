package dao;

import model.Ordermessage;
import model.Orders;

import java.util.List;

public interface OrderDao {
    public void save(Orders orders);

    public void update(Orders orders);

    public void delete(String orderid);

    /**
     * 根据orderis查询订单
     * @param orderid
     * @return
     */
    public Orders findOrderByOrderId(String orderid);

    /**
     * 得到最大的orderid
     */
    public String getMaxId();

    /**
     * 根据userid查询订单列表
     * @param userid
     * @return
     */
    public List findOrderListByUserId(String userid);

    /**
     * 保存ordermessage
     * @param ordermessage
     */
    public void saveOrderMessage(Ordermessage ordermessage);

    /**
     * 根据orderid查询ordermessage
     * @param orderid
     * @return
     */
    public List getOrderMessageListByOrderId(String orderid);

    /**
     * 根据orderid删除ordermessage
     * @param orderid
     */
    public void deleteOrderMessageByOrderId(String orderid);

    /**
     * 根据状态搜索订单
     * @param state
     * @return
     */
    public List findOrderListByState(String state);
}
