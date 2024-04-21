/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import lombok.Getter;
import lombok.Setter;
import model.Tblelements;
import model.Tblpages;
import utils.GenericUtils;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author takis
 */

@Named("pagesBean")
@Getter
@Setter
@ViewScoped
public class PagesBean implements Serializable {

    private List<Tblpages> pagesList;
    private String infomsg;
    private Tblpages page = new Tblpages();
    private Tblpages pageCopy = new Tblpages();

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
        pagesList = (List<Tblpages>) (List<?>) db.dbTransactions.getAllObjectsSorted(Tblpages.class.getCanonicalName(), "pageName", 0);
    }


    public void openNew() {
        this.page = new Tblpages();
    }


    /**
     * Αντιγράφει το Object για να μπορέσουμε να κάνουμε το Update.
     */
    public void copyObject() {
        pageCopy = page;
    }

    /**
     * Κάνει Εισαγωγή.
     */
    public void insert() {
        FacesMessage message = new FacesMessage();

        if (page != null) {
            db.dbTransactions.storeObject(page);

            //Φτιάνουμε και ένα default element για κάθε σελίδα
            Tblelements element = new Tblelements();
            element.setElementName("default");
            element.setElementDesc("default");
            element.setRefPageId(page);

            db.dbTransactions.storeObject(element);

        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Fault Insert", page.getPageName());
        }
        if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {

            pagesList.add(page);

            FacesMessage msg = new FacesMessage("Successful Insert", page.getPageName());
            FacesContext.getCurrentInstance().addMessage(null, msg);

           }
    }


    public void update() {

        if (Objects.nonNull(page)) {
            db.dbTransactions.updateObject(page);
        }
        GenericUtils.addMessage(FacesMessage.SEVERITY_INFO, "Successful Insert", (page.getPageName()));
        }


//    public void delete() {
//
//        db.dbTransactions.deleteObject(page);
//
//        if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
//            FacesMessage msg = new FacesMessage("Successful Delete");
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//
//            pagesList.remove(page);
//            Ajax.update("form");
//
//            //Auditing
//            Auditing.getInstance().store(Auditing.DELETE, Tblpages.class.getCanonicalName(), page.toString());
//        }
//    }

}

