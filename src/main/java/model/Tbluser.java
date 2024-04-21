/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 *
 * @author takis
 */
@Data
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

}
