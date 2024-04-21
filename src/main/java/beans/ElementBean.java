/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import db.dbTransactions;
import lombok.Getter;
import lombok.Setter;
import model.*;
import org.omnifaces.util.Ajax;
import utils.GenericUtils;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * @author Tsotzolas George
 */

@Named("elementBean")
@Getter
@Setter
@ViewScoped
public class ElementBean implements Serializable {

    private List<Tblelements> elementsList;
    private List<Tblpages> pagesList;
    private String infomsg;
    private Tblelements element = new Tblelements();
    private Tblelements elementCopy = new Tblelements();

    //Login Bean
    @Inject
    private LoginBean loginBean;


    @PostConstruct
    public void init() {

        readAll();
        clear1();
    }

    public void openNew() {
        this.element= new Tblelements();
    }

    public void readAll() {
        String queryElements = "from element e where e.element_name <>'default' order by e.element_name asc";
        elementsList = (List<Tblelements>) (List<?>) dbTransactions.getObjectsBySqlQuery(Tblelements.class, queryElements, null, null, null);
        pagesList = (List<Tblpages>) (List<?>) dbTransactions.getAllObjectsSorted(Tblpages.class.getCanonicalName(), "pageName", 0);
        Ajax.update("form");
    }

    /**
     * Αντιγράφει το Object για να μπορέσουμε να κάνουμε τυο Update.
     */
    public void copyObject() {
        elementCopy = element;
    }

    public void insert() {

        if (element != null) {
            dbTransactions.storeObject(element);
        }

        if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {

            readAll();

            GenericUtils.addMessage(FacesMessage.SEVERITY_INFO, "Successful Insert", element.getElementName());


        }
    }


    public void update() {
        if (element != null) {
            dbTransactions.updateObject(element);
        }
        if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {

            readAll();
            GenericUtils.addMessage(FacesMessage.SEVERITY_INFO, "Successful Update", element.getElementName());

        }
    }


    public void delete() {

        //Σβήνουμε όλα τα διακαιώματα εκτός απο τα default elements
//        String queryDelete = "Element where idelement = " + element.getIdelement();
//        dbTransactions.deleteObjectsBySqlQuery(null, queryDelete);
        db.dbTransactions.deleteObject(element);

        if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
            FacesMessage msg = new FacesMessage("Successful Delete");
            FacesContext.getCurrentInstance().addMessage(null, msg);

            readAll();


        }
    }


    public void clear1() {
        element = new Tblelements();
    }
}

