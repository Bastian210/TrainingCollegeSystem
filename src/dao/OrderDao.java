package dao;

import model.Ordermessage;
import model.Orders;

public interface OrderDao {
    public void save(Orders orders);

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
