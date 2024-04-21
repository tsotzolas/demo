package security;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import model.*;

import java.io.Serializable;
import java.util.*;

/**
 * @author tsotzolas@gmail.com
 */
@Slf4j
@Getter
@Setter
public class Security implements Serializable {


    private Tblroles role;
    private Map<String, Tblroles> rolesMap;

    private List<Tblpages> pagesList;
    private Map<String, Tblpages> pagesMap;

    private HashMap<String, List<Tblelements>> elements = new HashMap<String, List<Tblelements>>();
    private HashMap<String, Integer> pages = new HashMap<String, Integer>();

    private List<Tblelements> elementsList;
    private Map<String, Tblelements> elementsMap = new HashMap<>();


    private boolean admin = false;


    /**
     * Αρχικοποιεί ένα αντικείμενο Security
     *
     * @param user
     */
    public Security(Tbluser user) {
        //Άμα ο χρήστης ειναι ενεργός
        if (user.getUserActive() == 1) {

            role = new Tblroles();
            role = user.getIdRole();


            admin =  user.getIdRole().getRoleDesc().equals("Administrator");

            elements = new HashMap<String, List<Tblelements>>();
            pages = new HashMap<String, Integer>();

//            fillPageElement(user);

            pagesList = new ArrayList<>();

            elementsList = new ArrayList<>();

        }
    }


    /**
     * Γεμίζει τις σελίδες και τα elemeνnts
     * @param user
     */
    private void fillPageElement(Tbluser user) {

        //Βρίσκουμε τις σελίδες όπου ο χρήστης δεν έχει δικαίομα να δεί.
        String queryPages = "from (select p.* from page p , rolerights rr\n" +
                "                where rr.idpage = p.idpage\n" +
                "                and rr.idRole =" + user.getIdRole().getRoleDesc() + ") e";

        pagesList = (List<Tblpages>) (List<?>) db.dbTransactions.getObjectsBySqlQueryDistinct(Tblpages.class, queryPages, null, null, null);

        //Γεμίζουμε τα pages για να ξέρουμε αν ο χρήστης δεν έχει πρόσβαση σε αυτες τις σελίδες.
        for (Tblpages p : pagesList) {
            pages.put(p.getPageName(), p.getIdpage());
        }


        //Βρίσκουμε τα elements των σελίδων όπου ο χρήστης δεν έχει δικαίομα να δεί.
        String queryElements = "from tblelements e where e.element_name!='default'";

        elementsList = (List<Tblelements>) (List<?>) db.dbTransactions.getObjectsBySqlQueryDistinct(Tblelements.class, queryElements, null, null, null);

    }

    /**
     * Ελέγχει αν ο χρήστης έχει τη συγκεκριμένη σελίδα
     *
     * @param pageName
     */
    public boolean foundPageName(String pageName) {

        return isAdmin() || pages.containsKey(pageName);
    }

    /**
     * Ελέγχει άμα ο χρήστης έχει την κατάλληλη σελίδα . Χρησιμοποίται για τα menus
     * Άμα ο χρήστης έχει την συγκεκριμένη σελίδα για το menu θα βλέπει το menu o χρήστης
     * @param pagePath
     * @return
     */
    public Boolean foundPageAccess(String pagePath) {
        return isAdmin() || pages.containsKey(pagePath);
    }

    /**
     * Ελέγχει άμα ο χρήστης μπορεί να δει το menu.
     * Για να το κάνουμε αυτό κάνουμε το εξείς.
     * Ελέγχουμε αν έχει πρόσβαση σε τουλάχιστον μία σελίδα που έχει μέσα το mεnu.
     * Άμα σε κάποια έχει τότε αυξάνουμε τον counter
     * Άμα ο counter ειναι μεγαλύτερος απο το μηδεν τότε ο χρήστης θα πρέπει να δει το menu.
     * @param pagesString
     * @return
     */
    public Boolean hasMenuAccess(String pagesString) {
        Boolean hasAccess = false;
        List<String> pageList = Arrays.asList(pagesString.split(","));
        List<String> tempList = new ArrayList<>();
        for(String s : pageList){
            tempList.add(s.trim());
        }

        int counter = 0;
        for(String a : tempList){
            if(pages.containsKey(a)){
               counter++;
            }
        }
        if(counter >0){
            hasAccess = true;
        }

        return isAdmin() || hasAccess;
    }






    public String getRolesString() {
        String s = "";
//        for (Tblroles roles : getTblrolesList()) {
//            s += roles.getRoleDesc() + " ";
//        }
        return s;
    }


}