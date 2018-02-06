package model;

import java.io.Serializable;

public class OrdermessageKey implements Serializable {
    private static final long serialVersionUID = 1L;

    private String orderid;
    private String name;

    public OrdermessageKey(){}

    public OrdermessageKey(String orderid, String name) {
        this.orderid = orderid;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrdermessageKey that = (OrdermessageKey) o;

        if (orderid != null ? !orderid.equals(that.orderid) : that.orderid != null) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = orderid != null ? orderid.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
