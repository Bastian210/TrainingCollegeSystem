package model;

import java.io.Serializable;

public class OrdermessageKey implements Serializable {
    private static final long serialVersionUID = 1L;

    private String orderid;
    private String userid;

    public OrdermessageKey(){}

    public OrdermessageKey(String orderid, String userid) {
        this.orderid = orderid;
        this.userid = userid;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrdermessageKey that = (OrdermessageKey) o;

        if (orderid != null ? !orderid.equals(that.orderid) : that.orderid != null) return false;
        return userid != null ? userid.equals(that.userid) : that.userid == null;
    }

    @Override
    public int hashCode() {
        int result = orderid != null ? orderid.hashCode() : 0;
        result = 31 * result + (userid != null ? userid.hashCode() : 0);
        return result;
    }
}
