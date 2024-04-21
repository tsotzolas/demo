/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;


import db.dbTransactions;
import lombok.Getter;
import lombok.Setter;
import model.Tblemployee;
import model.Tblroles;
import model.Tbluser;
import org.omnifaces.util.Ajax;
import utils.GenericUtils;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author tsotzolas@gmail.com
 */

@Named("userBean")
@Getter
@Setter
@ViewScoped
public class UserBean implements Serializable {

    private List<Tbluser> userList;
    private List<Tblemployee> employeeList;
    private List<Tblroles> roleList;
    private String infomsg;
    private Tbluser user = new Tbluser();
    private Tbluser userCopy = new Tbluser();
    private Map<Integer, String> userStatusMap = new HashMap();


    //Login Bean
    @Inject
    private LoginBean loginBean;


    @PostConstruct
    public void init() {
        userStatusMap.put(0, "Deleted");
        userStatusMap.put(1, "Disable");
        userStatusMap.put(2, "Active");


        readAll();
    }


    /**
     * Διαβάζει όλες τις εγγραφες.
     */
    public void readAll() {
        userList = (List<Tbluser>) (List<?>) dbTransactions.getAllObjectsSorted(Tbluser.class.getCanonicalName(), "username", 0);

        roleList = (List<Tblroles>) (List<?>) dbTransactions.getAllObjectsSorted(Tblroles.class.getCanonicalName(), "roleDesc", 0);

        //Φέρνουμε τα ενεργά person μονο
        String qrySQL = "FROM Tblemployee e WHERE e.isactive=1";
        employeeList = (List<Tblemployee>) (List<?>) dbTransactions.getObjectsBySqlQuery(Tblemployee.class, qrySQL, null, null, null);

    }

    public void openNew() {
        this.user = new Tbluser();
    }


    /**
     * Αντιγράφει το Object για να μπορέσουμε να κάνουμε το Update.
     */
    public void copyObject() {
        userCopy = user;
    }


    public void update() {

        if (Objects.nonNull(user)) {
            if(user.getUserActive() == 1){
                user.setWrongCred(0);
            }
            dbTransactions.updateObject(user);
        }
        GenericUtils.addMessage(FacesMessage.SEVERITY_INFO, "Successful Update", (user.getUsername()));
         }


    public void delete() {

//        String queryDelete = "User c WHERE c.idUser =" + user.getIdluser();
//        dbTransactions.deleteObjectsBySqlQuery(null, queryDelete);
        userCopy = user;
        dbTransactions.deleteObject(user);

        if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {

            GenericUtils.addMessage(FacesMessage.SEVERITY_INFO, "Successful Delete", (userCopy.getUsername()));

            readAll();
            Ajax.update("form");

           }
    }
}

