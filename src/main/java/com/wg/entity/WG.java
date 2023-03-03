package com.wg.entity;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;

import javax.enterprise.event.Observes;
import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;

/**
 * @author majaesch
 * Mit dieser Klasse wird die Zugriffssteuerung umgesetzt
 * */
@Entity
@UserDefinition
public class WG extends PanacheEntityBase {
    @Id
    @SequenceGenerator(
            name = "WGSequence",
            sequenceName = "WG_id_seq",
            allocationSize = 1,
            initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WGSequence")
    @Basic(optional = false)
    private long wgId;
    @Username
    @Column(unique = true)
    @NotBlank(message = "wgname needs to be present")
    private String wgname;

    @Password
    @NotBlank(message = "password needs to be present")
    private String password;

    @Roles
    private String role; //hier keine validation, da

    public long getWgId() {
        return wgId;
    }
    public String getWgname() {
        return wgname;
    }

    public void setWgname(String wgname) {
        this.wgname = wgname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
