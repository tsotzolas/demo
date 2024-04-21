/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *
 * @author tsotzo
 */
@Data
@Embeddable
public class TblrolerightsPK implements Serializable {

    @Basic(optional = false,fetch = FetchType.LAZY)
    @NotNull
    @Column(name = "idRole")
    private int idRole;
    @Basic(optional = false,fetch = FetchType.LAZY)
    @NotNull
    @Column(name = "idElement")
    private int idElement;
    @Basic(optional = false,fetch = FetchType.LAZY)
    @NotNull
    @Column(name = "idPage")
    private int idPage;

}
