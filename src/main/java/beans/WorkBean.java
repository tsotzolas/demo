///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package beans;
//
//
//import db.dbTransactions;
//import lombok.Getter;
//import lombok.Setter;
//import model.Work;
//import org.omnifaces.util.Ajax;
//import utils.GenericUtils;
//import utils.StringUtils;
//
//import javax.annotation.PostConstruct;
//import javax.faces.application.FacesMessage;
//import javax.faces.context.FacesContext;
//import javax.faces.view.ViewScoped;
//import javax.inject.Inject;
//import javax.inject.Named;
//import java.io.Serializable;
//import java.util.List;
//import java.util.Objects;
//
///**
// * @author tsotzolas@gmail.com
// */
//
//@Named("workBean")
//@Getter
//@Setter
//@ViewScoped
//public class WorkBean implements Serializable {
//
//    private List<Work> workList;
//    private String infomsg;
//    private Work work = new Work();
//    private Work workCopy = new Work();
//
//
//    //Login Bean
//    @Inject
//    private LoginBean loginBean;
//
//
//    @PostConstruct
//    public void init() {
//        readAll();
//    }
//
//
//    /**
//     * Διαβάζει όλες τις εγγραφες.
//     */
//    public void readAll(){
//        workList = (List<Work>) (List<?>) dbTransactions.getAllObjectsSorted(Work.class.getCanonicalName(), "workDesc", 0);
//    }
//
//    public void openNew() {
//        this.work = new Work();
//    }
//
//
//    /**
//     * Αντιγράφει το Object για να μπορέσουμε να κάνουμε το Update.
//     */
//    public void copyObject() {
//        workCopy = work;
//    }
//
//    /**
//     * Κάνει Εισαγωγή.
//     */
//    public void insert() {
//
//        if (Objects.nonNull(work)) {
//            dbTransactions.storeObject(work);
//        }
//        if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
//
//            readAll();
//            GenericUtils.addMessage(FacesMessage.SEVERITY_INFO, "Successful Insert", (work.getWorkDesc()));
//
//            //Auditing
//            Auditing.getInstance().store(Auditing.INSERT, Work.class.getCanonicalName(), work.toString());
//        }
//    }
//
//
//    public void update() {
//
//        if (Objects.nonNull(work)) {
//            dbTransactions.updateObject(work);
//        }
//        GenericUtils.addMessage(FacesMessage.SEVERITY_INFO, "Successful Insert", (work.getWorkDesc()));
//        //Auditing
//        Auditing.getInstance().store(Auditing.UPDATE, Work.class.getCanonicalName(), work.toString());
//    }
//
//
//    public void delete() {
//
////        String queryDelete = "work c WHERE c.idwork =" + work.getIdlwork();
////        dbTransactions.deleteObjectsBySqlQuery(null, queryDelete);
//
//        dbTransactions.deleteObject(work);
//
//        if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
//
//            GenericUtils.addMessage(FacesMessage.SEVERITY_INFO, "Successful Delete", (work.getWorkDesc()));
//
//            readAll();
//            Ajax.update("form");
//
//            //Auditing
//            Auditing.getInstance().store(Auditing.DELETE, Work.class.getCanonicalName(), work.toString());
//        }
//    }
//
//    /**
//     * Κόβει τα μεγάλα string.
//     * @param inputString
//     * @return
//     */
//    public  String cutLongStrings(String inputString){
//        return StringUtils.cutLongStrings(inputString);
//    }
//}
//
