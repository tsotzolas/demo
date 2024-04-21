/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import lombok.Data;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author tsotzo
 */
@Data
@Entity
@Table(name = "tblrolerights")
public class Tblrolerights implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TblrolerightsPK tblrolerightsPK;
    @JoinColumn(name = "idElement", referencedColumnName = "idelement", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private Tblelements tblelements;
    @JoinColumn(name = "idPage", referencedColumnName = "idpage", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private Tblpages tblpages;
    @JoinColumn(name = "idRole", referencedColumnName = "idRole", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private Tblroles tblroles;


}
