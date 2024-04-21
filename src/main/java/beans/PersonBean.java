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
//import model.*;
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
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
///**
// * @author tsotzolas@gmail.com
// */
//
//@Named("personBean")
//@Getter
//@Setter
//@ViewScoped
//public class PersonBean implements Serializable {
//
//    private List<Person> personList;
//    private String infomsg;
//    private Person person = new Person();
//    private Person personCopy = new Person();
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
//        personList = (List<Person>) (List<?>) dbTransactions.getAllObjectsSorted(Person.class.getCanonicalName(), "personLname", 0);
//    }
//
//    public void openNew() {
//        this.person = new Person();
//    }
//
//
//    /**
//     * Αντιγράφει το Object για να μπορέσουμε να κάνουμε το Update.
//     */
//    public void copyObject() {
//        personCopy = person;
//    }
//
//    /**
//     * Κάνει Εισαγωγή.
//     */
//    public void insert() {
//
//        if (Objects.nonNull(person)) {
//            person.setToken(StringUtils.getMd5(new Date().toString()));
//            dbTransactions.storeObject(person);
//        }
//        if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
//
//            readAll();
//            GenericUtils.addMessage(FacesMessage.SEVERITY_INFO, "Successful Insert", (person.getPersonLname()+ " "+ person.getPersonFname()));
//
//            //Auditing
//            Auditing.getInstance().store(Auditing.INSERT, Person.class.getCanonicalName(), person.toString());
//        }
//    }
//
//
//    public void update() {
//
//        if (Objects.nonNull(person)) {
//            dbTransactions.updateObject(person);
//        }
//        GenericUtils.addMessage(FacesMessage.SEVERITY_INFO, "Successful Insert", (person.getPersonLname()+ " "+ person.getPersonFname()));
//        //Auditing
//        Auditing.getInstance().store(Auditing.UPDATE, Person.class.getCanonicalName(), person.toString());
//    }
//
//
//    public void delete() {
//
////        String queryDelete = "Person c WHERE c.idPerson =" + person.getIdlperson();
////        dbTransactions.deleteObjectsBySqlQuery(null, queryDelete);
//
//        personCopy = person;
//        dbTransactions.deleteObject(person);
//
//        if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
//
//            GenericUtils.addMessage(FacesMessage.SEVERITY_INFO, "Successful Delete", (personCopy.getPersonLname()+ " "+ personCopy.getPersonFname()));
//
//            readAll();
//            Ajax.update("form");
//
//            //Auditing
//            Auditing.getInstance().store(Auditing.DELETE, Person.class.getCanonicalName(), person.toString());
//        }
//    }
//
//
//    /**
//     * Βρίσκει απο το person τα wallets.
//     * @param person
//     */
//    public List<Wallet> walletsFromPerson(Person person){
//        return person.getWalletpersonList().stream().map(Walletperson::getIdlwallet).collect(Collectors.toList());
//    }
//
//
//    /**
//     * Βρίσκει ta coin σύμφωνα με αυτό που έχει γράψει ο χρήστης.
//     *
//     * @param coinString
//     * @return
//     */
//    public List<Coin> findCoins(String coinString) {
//        String query = "from coins e where e.coin_name like'%" + coinString + "%' order by e.coin_name";
//        return (List<Coin>) (List<?>) db.dbTransactions.getObjectsBySqlQuery(Coin.class, query, null, null, null);
//
//    }
//
//}
//
