/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author takis
 */
@Getter
@Setter
@Entity
@Table(name = "Tblusers")
public class Tbluser implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false, fetch = FetchType.LAZY)
    @Column(name = "idUser")
    private Integer idUser;
    @Basic(optional = false, fetch = FetchType.LAZY)
    @NotNull
    @Size(min = 0, max = 30)
    @Column(name = "username")
    private String username;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "email")
    private String email;
    @Basic(optional = false, fetch = FetchType.LAZY)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "password")
    private String password;
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "lastLogin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;

    @Size(min = 1, max = 150)
    @Column(name = "altpass")
    private String altpass;
    @Column(name = "expDate")
    private Date expDate;

    @Column(name = "locked_until")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lockedUntil;


    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idEmployee", referencedColumnName = "idEmployee")
    private Tblemployee idEmployee;

    @JoinColumn(name = "idRole", referencedColumnName = "idRole")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Tblroles idRole;

    @Size(min = 1, max = 45)
    @Column(name = "theme")
    private String theme;

    @Size(min = 1, max = 45)
    @Column(name = "layoutMode")
    private String layoutMode;

    @Column(name = "lightMenu")
    private Boolean lightMenu;

    @Column(name = "specialEffect")
    private Boolean specialEffect;

    @Lob
    @Size(max = 65535)
    @Column(name = "avatar")
    private String avatar;

    @Size(max = 50)
    @Column(name = "lastLoginIp")
    private String lastLoginIp;

    @Basic(optional = false, fetch = FetchType.LAZY)
    @NotNull
    @Column(name = "userActive")
    private int userActive;

    @Size(max = 150)
    @Column(name = "saltedpassword")
    private String saltedpassword;
    @Size(max = 100)
    @Column(name = "salt")
    private String salt;
    @Size(max = 100)
    @Column(name = "newsalt")
    private String newsalt;
    @Column(name = "wrongCred")
    private Integer wrongCred;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "idUser")
    private List<Tblpasshistory> passhistoryList;

    @Override
    public String toString() {
        return "Tbluser{" +
                "idUser=" + idUser +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", createTime=" + createTime +
                ", lastLogin=" + lastLogin +
                ", altpass='" + altpass + '\'' +
                ", expDate=" + expDate +
                ", lockedUntil=" + lockedUntil +
                ", idEmployee=" + idEmployee +
                ", idRole=" + idRole +
                ", theme='" + theme + '\'' +
                ", layoutMode='" + layoutMode + '\'' +
                ", lightMenu=" + lightMenu +
                ", specialEffect=" + specialEffect +
                ", avatar='" + avatar + '\'' +
                ", lastLoginIp='" + lastLoginIp + '\'' +
                ", userActive=" + userActive +
                ", saltedpassword='" + saltedpassword + '\'' +
                ", salt='" + salt + '\'' +
                ", newsalt='" + newsalt + '\'' +
                ", wrongCred=" + wrongCred +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tbluser tbluser = (Tbluser) o;

        if (userActive != tbluser.userActive) return false;
        if (!Objects.equals(idUser, tbluser.idUser)) return false;
        if (!Objects.equals(username, tbluser.username)) return false;
        if (!Objects.equals(email, tbluser.email)) return false;
        if (!Objects.equals(password, tbluser.password)) return false;
        if (!Objects.equals(createTime, tbluser.createTime)) return false;
        if (!Objects.equals(lastLogin, tbluser.lastLogin)) return false;
        if (!Objects.equals(altpass, tbluser.altpass)) return false;
        if (!Objects.equals(expDate, tbluser.expDate)) return false;
        if (!Objects.equals(lockedUntil, tbluser.lockedUntil)) return false;
        if (!Objects.equals(idEmployee, tbluser.idEmployee)) return false;
        if (!Objects.equals(idRole, tbluser.idRole)) return false;
        if (!Objects.equals(theme, tbluser.theme)) return false;
        if (!Objects.equals(layoutMode, tbluser.layoutMode)) return false;
        if (!Objects.equals(lightMenu, tbluser.lightMenu)) return false;
        if (!Objects.equals(specialEffect, tbluser.specialEffect))
            return false;
        if (!Objects.equals(avatar, tbluser.avatar)) return false;
        if (!Objects.equals(lastLoginIp, tbluser.lastLoginIp)) return false;
        if (!Objects.equals(saltedpassword, tbluser.saltedpassword))
            return false;
        if (!Objects.equals(salt, tbluser.salt)) return false;
        if (!Objects.equals(newsalt, tbluser.newsalt)) return false;
        if (!Objects.equals(wrongCred, tbluser.wrongCred)) return false;
        return Objects.equals(passhistoryList, tbluser.passhistoryList);
    }

    @Override
    public int hashCode() {
        int result = idUser != null ? idUser.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (lastLogin != null ? lastLogin.hashCode() : 0);
        result = 31 * result + (altpass != null ? altpass.hashCode() : 0);
        result = 31 * result + (expDate != null ? expDate.hashCode() : 0);
        result = 31 * result + (lockedUntil != null ? lockedUntil.hashCode() : 0);
        result = 31 * result + (idEmployee != null ? idEmployee.hashCode() : 0);
        result = 31 * result + (idRole != null ? idRole.hashCode() : 0);
        result = 31 * result + (theme != null ? theme.hashCode() : 0);
        result = 31 * result + (layoutMode != null ? layoutMode.hashCode() : 0);
        result = 31 * result + (lightMenu != null ? lightMenu.hashCode() : 0);
        result = 31 * result + (specialEffect != null ? specialEffect.hashCode() : 0);
        result = 31 * result + (avatar != null ? avatar.hashCode() : 0);
        result = 31 * result + (lastLoginIp != null ? lastLoginIp.hashCode() : 0);
        result = 31 * result + userActive;
        result = 31 * result + (saltedpassword != null ? saltedpassword.hashCode() : 0);
        result = 31 * result + (salt != null ? salt.hashCode() : 0);
        result = 31 * result + (newsalt != null ? newsalt.hashCode() : 0);
        result = 31 * result + (wrongCred != null ? wrongCred.hashCode() : 0);
        result = 31 * result + (passhistoryList != null ? passhistoryList.hashCode() : 0);
        return result;
    }
}
