package dao;

import model.Payment;

public interface PaymentDao {

    /**
     * 更新
     * @param payment
     */
    public void update(Payment payment);

    /**
     * 根据payid查找支付账号实体
     * @param payid
     * @return
     */
    public Payment findPaymentByPayId(String payid);

    /**
     * 更改支付密码
     * @param payid
     * @param password
     */
    public void updatePasswordByPayid(String payid,String password);

    public Payment getManagePayment();
}
