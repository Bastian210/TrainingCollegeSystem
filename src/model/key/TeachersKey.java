package model.key;

import java.io.Serializable;

public class TeachersKey implements Serializable {
    private static final long serialVersionUID = 1L;

    private String institutionid;
    private String name;

    public TeachersKey(){}

    public TeachersKey(String institutionid, String name) {
        this.institutionid = institutionid;
        this.name = name;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getInstitutionid() {
        return institutionid;
    }

    public void setInstitutionid(String institutionid) {
        this.institutionid = institutionid;
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

        TeachersKey that = (TeachersKey) o;

        if (institutionid != null ? !institutionid.equals(that.institutionid) : that.institutionid != null)
            return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = institutionid != null ? institutionid.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
