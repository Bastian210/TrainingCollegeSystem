package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "teachers")
@IdClass(TeachersKey.class)
public class Teachers implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "institutionid")
    private String institution;
    @Id
    @Column(name = "name")
    private String name;
    @Column(name = "gender")
    private String gender;
    @Column(name = "type")
    private String type;

    public Teachers() {
    }

    public Teachers(String institution, String name, String gender, String type) {
        this.institution = institution;
        this.name = name;
        this.gender = gender;
        this.type = type;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
