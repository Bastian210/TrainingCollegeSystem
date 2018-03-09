package model.key;

import java.io.Serializable;

public class BillKey implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String month;

    public BillKey() {
    }

    public BillKey(String id, String month) {
        this.id = id;
        this.month = month;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BillKey billKey = (BillKey) o;

        if (id != null ? !id.equals(billKey.id) : billKey.id != null) return false;
        return month != null ? month.equals(billKey.month) : billKey.month == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (month != null ? month.hashCode() : 0);
        return result;
    }
}
