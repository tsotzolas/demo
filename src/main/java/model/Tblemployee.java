package model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
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


    @Override
    public String toString() {
        return "Tblemployee{" +
                "idEmployee=" + idEmployee +
                ", employeeFname='" + employeeFname + '\'' +
                ", employeeLname='" + employeeLname + '\'' +
                ", employeeEmail='" + employeeEmail + '\'' +
                ", genderMale=" + genderMale +
                ", isactive=" + isactive +
                ", idcompany=" + idcompany +
                ", tblusers=" + tblusers +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tblemployee that = (Tblemployee) o;

        if (genderMale != that.genderMale) return false;
        if (!Objects.equals(idEmployee, that.idEmployee)) return false;
        if (!Objects.equals(employeeFname, that.employeeFname))
            return false;
        if (!Objects.equals(employeeLname, that.employeeLname))
            return false;
        if (!Objects.equals(employeeEmail, that.employeeEmail))
            return false;
        if (!Objects.equals(isactive, that.isactive)) return false;
        if (!Objects.equals(idcompany, that.idcompany)) return false;
        return Objects.equals(tblusers, that.tblusers);
    }

    @Override
    public int hashCode() {
        int result = idEmployee != null ? idEmployee.hashCode() : 0;
        result = 31 * result + (employeeFname != null ? employeeFname.hashCode() : 0);
        result = 31 * result + (employeeLname != null ? employeeLname.hashCode() : 0);
        result = 31 * result + (employeeEmail != null ? employeeEmail.hashCode() : 0);
        result = 31 * result + genderMale;
        result = 31 * result + (isactive != null ? isactive.hashCode() : 0);
        result = 31 * result + (idcompany != null ? idcompany.hashCode() : 0);
        result = 31 * result + (tblusers != null ? tblusers.hashCode() : 0);
        return result;
    }
}
