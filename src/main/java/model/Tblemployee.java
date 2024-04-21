package model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author takis
 */
@Data
@Entity
@Table(name = "tblemployee")
public class Tblemployee implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false, fetch = FetchType.LAZY)
    @Column(name = "idEmployee")
    private Integer idEmployee;
    @Size(max = 45)
    @Column(name = "EmployeeFname")
    private String employeeFname;
    @Size(max = 45)
    @Column(name = "EmployeeLname")
    private String employeeLname;
    @Size(max = 45)
    @Column(name = "EmployeeEmail")
    private String employeeEmail;
    @Basic(optional = false, fetch = FetchType.LAZY)
    @NotNull
    @Column(name = "gender_male")
    private int genderMale;
    @Column(name = "isactive")
    private Boolean isactive;
    @JoinColumn(name = "idcompany", referencedColumnName = "idcompany")
    @ManyToOne(fetch = FetchType.LAZY)
    private Tblcompany idcompany;


    @OneToOne(fetch = FetchType.LAZY, mappedBy = "idEmployee")
    private Tbluser tblusers;

}
