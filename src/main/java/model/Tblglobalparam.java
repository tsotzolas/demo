/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author takis
 */
@Data
@Entity
@Table(name = "tblglobalparam")
public class Tblglobalparam implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false,fetch = FetchType.LAZY)
    @Column(name = "globalparamID")
    private Integer globalparamID;
    @Size(max = 45)
    @Column(name = "globalparamDesc")
    private String globalparamDesc;
    @Size(max = 100)
    @Column(name = "globalparamValue")
    private String globalparamValue;

    @Size(max = 1000)
    @Column(name = "comments")
    private String comments;

}
