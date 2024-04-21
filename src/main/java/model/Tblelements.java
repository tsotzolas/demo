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

/**
 *
 * @author tsotzo
 */
@Data
@Entity
@Table(name = "tblelements")
public class Tblelements implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false,fetch = FetchType.LAZY)
    @Column(name = "idelement")
    private Integer idelement;
    @Basic(optional = false,fetch = FetchType.LAZY)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "element_name")
    private String elementName;
    @Size(max = 100)
    @Column(name = "element_desc")
    private String elementDesc;
    @Size(max = 100)
    @Column(name = "element_id_desc")
    private String elementIdDesc;
    @Basic(optional = false,fetch = FetchType.LAZY)
    @NotNull
    @Column(name = "visible")
    private int visible;
    @JoinColumn(name = "ref_page_id", referencedColumnName = "idpage")
    @ManyToOne(fetch = FetchType.LAZY)
    private Tblpages refPageId;
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL, mappedBy = "tblelements")
    private List<Tblrolerights> tblrolerightsList;

}
