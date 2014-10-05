package de.hsosnabrueck.hipsterbility.entities;

import javax.persistence.*;

/**
 * Created by Albert on 28.09.2014.
 */
@Entity(name = "Invite")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"CODE, EMAIL"}))
@NamedQuery(name = "InviteCode.findByCode", query = "SELECT c FROM Invite c WHERE c.code=:code")
public class InviteCodeEntity {

    public static final String TABLE_NAME = "Invite";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String email;

    @ManyToOne
    private UserEntity creator;

    private boolean valid;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserEntity getCreator() {
        return creator;
    }

    public void setCreator(UserEntity creator) {
        this.creator = creator;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
