/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author tsotzolas
 */
@Data
@Entity
@Table(name = "temp_users")
public class TempUsers implements Serializable {

    @NotNull
    @Column(name = "user_email")
    private String companyName;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtempusers")
    private Integer idtempusers;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "user_email")
    private String userEmail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "user_token")
    private String userToken;
    @Basic(optional = false)
    @NotNull
    @Column(name = "request_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestDate;
    @Column(name = "invitation_expiration")
    @Temporal(TemporalType.TIMESTAMP)
    private Date invitationExpiration;
    @Column(name = "is_reset_password")
    private Boolean resetPassword;

}
