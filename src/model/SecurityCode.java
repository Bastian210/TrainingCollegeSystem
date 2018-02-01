package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "securitycode")
public class SecurityCode implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "email")
    private String email;
    @Column(name = "code")
    private String code;

    public SecurityCode() {
    }

    public SecurityCode(String email, String code) {
        this.email = email;
        this.code = code;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
