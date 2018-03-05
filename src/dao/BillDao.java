package dao;

import model.Bill;
import model.BillKey;

import java.util.List;

public interface BillDao {
    public void save(Bill bill);

    public void update(Bill bill);

    /**
     * 根据billkey查询bill
     * @param billKey
     * @return
     */
    public Bill getBillByBillKey(BillKey billKey);

    /**
     * 根据userid得到bill列表
     * @param userid
     * @return
     */
    public List getBillListByUserid(String userid);
}
