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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Tsotzolas George
 */
@Getter
@Setter
@Named("rolePageElementBean")
@ViewScoped
public class RolePageElementBean implements Serializable {

    private List<Tblroles> rolesList;
    private Tblroles role = new Tblroles();
    private String infomsg;
    private List<Tblpages> targetPagesList;
    private List<Tblpages> sourcePagesList;

    private List<Tblelements> targetElementsList;
    private List<Tblelements> sourceElementsList;
    private Tblpages selectedPage;
    private Tblelements selectedElement;

    //Login Bean
    @Inject
    private LoginBean loginBean;


    @PostConstruct
    public void init() {

        String rolesQuery = "from role e where e.role_desc != 'Administrator' order by e.role_desc asc";
        rolesList = (List<Tblroles>) (List<?>) dbTransactions.getObjectsBySqlQuery(Tblroles.class, rolesQuery, null, null, null);
        clear1();
    }


    public void onRoleSelect() {
        String q1 = "from (select p.*\n" +
                "from page p\n" +
                "where p.idpage not in (select distinct p.idpage\n" +
                "                     from rolerights rr ,page p\n" +
                "                     where p.idpage = rr.idpage\n" +
                "                     and rr.idrole = " + role.getIdRole() +
                " ) order by p.page_name) e";

        sourcePagesList = (List<Tblpages>) (List<?>) dbTransactions.getObjectsBySqlQuery(Tblpages.class, q1, null, null, null);


        String q = "from (select distinct p.* \n" +
                "from rolerights rr ,page p\n" +
                "where p.idpage = rr.idpage " +
                "and rr.idrole = " + role.getIdRole() +
                ") e";
        targetPagesList = (List<Tblpages>) (List<?>) dbTransactions.getObjectsBySqlQuery(Tblpages.class, q, null, null, null);
    }


    public void onSelection(Tblpages page) {
        selectedPage = page;

        sourceElementsList = page.getTblelementsList();

        //Αφαιρούμε απο την λίστα τα default Elements
        sourceElementsList = sourceElementsList.stream().filter(i -> !i.getElementName().equals("default")).collect(Collectors.toList());


        String q = "from (select el.*\n" +
                "from rolerights rr,\n" +
                "     page p,\n" +
                "     element el\n" +
                "where p.idpage = rr.idpage\n" +
                "  and el.idelement = rr.idelement\n" +
                "  and rr.idPage = " + selectedPage.getIdpage() + "\n" +
                "  and rr.idRole = " + role.getIdRole() + "\n" +
                "  and el.element_name <> 'default'" +
                ") e";
        targetElementsList = (List<Tblelements>) (List<?>) dbTransactions.getObjectsBySqlQuery(Tblelements.class, q, null, null, null);

        //Αφαιρούμε απο την λίστα τοy source αυτά τα οποία υπάρχουν στο target άμα έχει δη κάποια elements
        if (targetElementsList.size() > 0) {
            sourceElementsList.removeAll(targetElementsList);
        }
    }


    public void onDBSelection(Tblpages selectedPage, List<Tblpages> listToAdd, List<Tblpages> listToRemove) {

        listToAdd.add(selectedPage);

        listToRemove.remove(selectedPage);

        //Κάνουμε και την αποθήκευση
        addElementsToModel();

        Ajax.update("sourceTable , targetTable");

    }


    public void onDBElementsSelection(Tblelements selElememt, List<Tblelements> listElementToAdd, List<Tblelements> listElementToRemove) {

        listElementToAdd.add(selElememt);

        listElementToRemove.remove(selElememt);

        //Κάνουμε και την αποθήκευση
        addElementsToModel();
    }


    public void addElementsToModel() {
        //Έχουμε την τριπλέτα
        //  Role    Page   Element
        //    1       1       2
        // Ο ρόλος ειναι κάτι τον οποίο έχει επιλέξει ο χρήστης απο την αρχή. Οπότε ειναι κάτι το οποίο ειναι σταθερό.
        // Αυτό το οποίο αλλάζει ειναι οι σελίδες και τα elements


        //Παίρνουμε όλα τα δεδομενα τα οποία είδη έχει μέσα ο χρηστης στην βάση,
        List<Tblrolerights> oldRolesRightsList = new ArrayList<>();

        String q = "from rolerights e " +
                "where e.idrole =" + role.getIdRole();
//                "and e.idPage =" + selectedPage.getIdpage();


        oldRolesRightsList = (List<Tblrolerights>) (List<?>) dbTransactions.getObjectsBySqlQuery(Tblrolerights.class, q, null, null, null);


        //Στη συνέχεια θα πρέπει να σβήσουμε αυτά τα παλαιά δεδομένα απο την βαση δεδομένων.
        if (oldRolesRightsList.size() > 0) {

            //Σβήνουμε όλα τα διακαιώματα εκτός απο τα default elements
            String queryDelete = "Rolerights  where idrole = " + role.getIdRole()  + "\n" +
                    "and idpage = " + selectedPage.getIdpage() ;

            dbTransactions.deleteObjectsBySqlQuery(null, queryDelete);
        }

        if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {

            for (Tblpages page : targetPagesList) {
                List<Tblelements> pageElementList = new ArrayList<>();

                //Βρίσκουμε τα elements τα οποία έχουν αυτές οι σελίδες.
                pageElementList = page.getTblelementsList();

                //Απο αυτά τα elements θα πρέπει να βρούμε αυτα τα οποιά υπάρχουν και μέσα στο targetElementsList και να βάλκουμε και τα default
                List<Tblelements> sameElements = new ArrayList<>();
                for (Tblelements e : pageElementList) {
                    if (targetElementsList != null && targetElementsList.contains(e)) {
                        sameElements.add(e);

                    }
                    //Bάζουμε και το default element της σελίδας.
                    sameElements.add(page.getTblelementsList().stream().filter(i -> i.getElementName().equals("default")).collect(Collectors.toList()).get(0));
                }

                // Και τώρα πάμε να κάνουμε το insert
                // Στην περίπτωση που η σελίδα έχει elements
                if (sameElements.size() > 0) {
                    Tblrolerights roleright = new Tblrolerights();
                    for (Tblelements element : sameElements) {

                        TblrolerightsPK pk = new TblrolerightsPK();
                        pk.setIdElement(element.getIdelement());
                        pk.setIdPage(element.getRefPageId().getIdpage());
                        pk.setIdRole(role.getIdRole());

                        roleright.setTblpages(element.getRefPageId());
                        roleright.setTblroles(role);
                        roleright.setTblelements(element);
                        roleright.setTblrolerightsPK(pk);


                        List<Tblrolerights> inTheDBList = new ArrayList<>();

                        String q1 = "from rolerights e where e.idrole =" + role.getIdRole() + " and e.idpage = " + page.getIdpage();

                        inTheDBList = (List<Tblrolerights>) (List<?>) dbTransactions.getObjectsBySqlQuery(Tblrolerights.class, q1, null, null, null);


                        if (!inTheDBList.contains(roleright)) {

                            dbTransactions.storeObject(roleright);
                        }
                    }

                    if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
                        GenericUtils.addMessage(FacesMessage.SEVERITY_INFO, "Successful Insert", "Pages in the role " + role.getRoleDesc());
                    }
                }

            }
        }
    }

    public void clear1() {
        role = new Tblroles();
        targetElementsList = new ArrayList<>();
        sourceElementsList = new ArrayList<>();
        selectedElement = new Tblelements();
        selectedPage = new Tblpages();
        targetPagesList = new ArrayList<>();
        sourcePagesList = new ArrayList<>();
    }



}

