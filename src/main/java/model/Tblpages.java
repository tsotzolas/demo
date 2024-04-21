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
@Table(name = "tblpages")
public class Tblpages implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false,fetch = FetchType.LAZY)
    @Column(name = "idpage")
    private Integer idpage;
    @Basic(optional = false,fetch = FetchType.LAZY)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "page_name")
    private String pageName;
    @Size(max = 100)
    @Column(name = "page_desc")
    private String pageDesc;
    @Size(max = 255)
    @Column(name = "page_path")
    private String pagePath;
    @Size(max = 255)
    @Column(name = "menu_label")
    private String menuLabel;
    @Size(max = 100)
    @Column(name = "menu_icon")
    private String menuIcon;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "refPageId",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tblelements> tblelementsList;
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL, mappedBy = "tblpages")
    private List<Tblrolerights> tblrolerightsList;

}
