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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 *
 * @author takis
 */
@Data
@Entity
@Table(name = "tblroles")
public class Tblroles implements Serializable {



    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false,fetch = FetchType.LAZY)
    @Column(name = "idRole")
    private Integer idRole;
    @Basic(optional = false,fetch = FetchType.LAZY)
    @NotNull
    @Size(min = 0, max = 45)
    @Column(name = "RoleDesc")
    private String roleDesc;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL, mappedBy = "tblroles")
    private List<Tblrolerights> tblrolerightsList;



    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL, mappedBy = "idEmployee")
    private List<Tbluser> userList;

}
