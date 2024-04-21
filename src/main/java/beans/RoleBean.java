/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import lombok.Getter;
import lombok.Setter;
import model.Tblroles;
import utils.GenericUtils;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import db.dbTransactions;
import org.omnifaces.util.Ajax;

/**
 * @author takis
 */

@Named("rolesBean")
@Getter
@Setter
@ViewScoped
public class RoleBean implements Serializable {

    private List<Tblroles> rolesList;
    private String infomsg;
    private Tblroles role = new Tblroles();
    private Tblroles roleCopy = new Tblroles();


    //Login Bean
    @Inject
    private LoginBean loginBean;


    @PostConstruct
    public void init() {
        readAll();
    }


    /**
     * Διαβάζει όλες τις εγγραφες.
     */
    public void readAll(){
        rolesList = (List<Tblroles>) (List<?>) db.dbTransactions.getAllObjectsSorted(Tblroles.class.getCanonicalName(), "roleDesc", 0);
    }

    public void openNew() {
        this.role = new Tblroles();
    }


    /**
     * Αντιγράφει το Object για να μπορέσουμε να κάνουμε το Update.
     */
    public void copyObject() {
        roleCopy = role;
    }

    /**
     * Κάνει Εισαγωγή.
     */
    public void insert() {

        if (Objects.nonNull(role)) {
            db.dbTransactions.storeObject(role);
        }
        if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {

            readAll();
            GenericUtils.addMessage(FacesMessage.SEVERITY_INFO, "Successful Insert", (role.getRoleDesc()));

             }
    }


    public void update() {

        if (Objects.nonNull(role)) {
            db.dbTransactions.updateObject(role);
        }
        GenericUtils.addMessage(FacesMessage.SEVERITY_INFO, "Successful Insert", (role.getRoleDesc()));
         }


    public void delete() {

//        String queryDelete = "Role c WHERE c.idrole =" + role.getIdRole();
//        dbTransactions.deleteObjectsBySqlQuery(null, queryDelete);

        db.dbTransactions.deleteObject(role);

        if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {

            GenericUtils.addMessage(FacesMessage.SEVERITY_INFO, "Successful Delete", (role.getRoleDesc()));

            readAll();
            Ajax.update("form");

           }
    }
}

