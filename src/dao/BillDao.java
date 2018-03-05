package dao;

import model.Bill;
import model.BillKey;

public interface BillDao {
    public void save(Bill bill);

    public void update(Bill bill);

    /**
     * 根据billkey查询bill
     * @param billKey
     * @return
     */
    public Bill getBillByBillKey(BillKey billKey);
}
