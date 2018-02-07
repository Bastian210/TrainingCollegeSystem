package dao;

import model.Ordermessage;
import model.Orders;

public interface OrderDao {
    public void save(Orders orders);

    public void update(Orders orders);

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
     * 保存ordermessage
     * @param ordermessage
     */
    public void saveOrderMessage(Ordermessage ordermessage);
}
