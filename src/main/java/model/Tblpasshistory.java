/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * @author tsotzolas
 */
@Data
@Entity
@Table(name = "tblpasshistory")
public class Tblpasshistory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false, fetch = FetchType.LAZY)
    @Column(name = "id")
    private Integer id;
    @Size(max = 150)
    @Column(name = "old_password")
    private String oldPassword;
    @Column(name = "change_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date changeDate;

    @Size(max = 150)
    @Column(name = "salt")
    private String salt;

    @JoinColumn(name = "idUser", referencedColumnName = "idUser")
    @ManyToOne
    private Tbluser idUser;


}
