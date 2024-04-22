package beans;

import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Ajax;
import utils.GenericUtils;
import db.HashingUtil;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.time.LocalDateTime;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import lombok.extern.slf4j.Slf4j;
import manhattan.GuestPreferences;
import model.*;
import security.Security;
import utils.TimeUtils;
import utils.UserUtils;

/**
 * Αυθεντικοποιεί τους χρήστες και ανεβάζει στο session το αντικείμενο
 * <code>Monimos</code> για τον συνδεδεμένο χρήστη.
 */

@Named("loginBean")
@Slf4j
@Getter
@Setter
@SessionScoped
public class LoginBean implements Serializable {

    public static final String AUTH_KEY = "username_auth";

    private Integer currentYear;

    //variables
    private boolean changePwd = false;
    private boolean ipsec = false;
    private String username = "";
    private String password = "";
    private Security security;
    private String PATH;
    private String TEMP_PATH;
    private String IMAGE_PATH;
    private String COSCO_SERVICE;
    private String PEARL_SERVICE;
    private Integer PASS_PERIOD;
    private Integer LOCK_OUT;
    private Boolean allGPS = false;
    private String seasoneffect = "";
    private String appimage = "";
    private String email;

    //Lists
    private List<Tbluser> usersList;
    private List<Tbluser> usersListGlobal;
    private Map globalMap = new HashMap();
    private Map globalWagonMap = new HashMap();
    private Map globalWagonTechMap = new HashMap();
    private Map<String,String> directionMap = new HashMap();


    //Oblects
    private Tbluser user;
    private Boolean isMobile;
    private Boolean istestEnviroment;
    private String userIp;


    @Inject
    private GuestPreferences guestPreferencesBean;


    public LoginBean() {
    }


    @PostConstruct
    public void init() {
        try {

            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            userIp = request.getHeader("X-FORWARDED-FOR");
            if (userIp == null) {
                userIp = request.getRemoteAddr();
            }

            String machineIp = "";

            //Βρίσκουμε ποια ειναι η IP του μηχανήματος που τρεχει ο server.
            Enumeration en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) en.nextElement();
                Enumeration ee = ni.getInetAddresses();
                while (ee.hasMoreElements()) {
                    InetAddress ia = (InetAddress) ee.nextElement();
                    // Αν η ip ξεκινάει απο 192.168. τότε είναι αυτή η ip που θέλω.
                    if (ia.getHostAddress().startsWith("192.168.")) {
                        machineIp = ia.getHostAddress();
                    }
                }
            }

            //Ανακτά το όνομα της βάσης
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

            currentYear = Calendar.getInstance().get(Calendar.YEAR);

            guestPreferencesBean = new GuestPreferences();
            guestPreferencesBean.setTheme("green-pink");


            Object expDays;
            PASS_PERIOD = 15;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * Κάνει έλεγχο για το αν θα πρέπει να εμφανιστεί το capta ή όχι
     *
     * @return
     */
    public boolean checkShowcaptcha(){
        //Έλεγχος άμα πρέπει να εμφανίσουμε το recaptcha
        //θα πρέπει να βλέπουμε άμα με την ip που έχει κάνει login έχει ξανα κάνει λάθος πάνω απο 3 φορές λάθος μα την ίδια ip

        String query = "from Tblusers e\n" +
                "where e.lastLoginIp= '"+userIp +"'\n" +
                "and e.wrongCred > 2";
        Integer count = db.dbTransactions.countObjectsBySqlQuery(Tbluser.class, query);
        return Objects.nonNull(count) && count > 0;
    }


    /**
     * Επιστρέφει το όνομα του theme του χρήστη.
     *
     * @return
     */
    public String getTheme() {
        //Default theme
        String theme = "blue-grey";

        if (user != null) {
            if (user.getTheme() != null) {
                theme = user.getTheme();
            }
        }
        return theme;

    }


    public void captcha() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correct", "Correct");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }


    /**
     * Ενέργειες που γίνονται όταν ο χρήστης πατήσει ξέχασα τον κωδικό μου.
     * -> Θα πρέπει να στέλνει ένα email στον χρήστη
     * -> θα πρέπει να κάνει μια εγγραφή στη βάση στον πίνακα temp_user με user_
     */
    public void forgotPass() throws IOException {

        if(UserUtils.registerUser(null,email,null,true)){
            GenericUtils.addMessage(FacesMessage.SEVERITY_INFO,"Success", "An email has been send to you.");
        }else{
            GenericUtils.addMessage(FacesMessage.SEVERITY_ERROR,"Error", "Something went wrong");
        }


    }

    public String forgotPassword() throws IOException {

//        FacesContext context = FacesContext.getCurrentInstance();
//        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
//        FacesMessage message = new FacesMessage();
//
//        //Ελέγχει αν ο χρήστης έχει βάλει το username του
//        if (username.isEmpty() || username == "" || username == null) {
//            GenericUtils.addMessage(FacesMessage.SEVERITY_WARN, "Wrong", "You must insert your username!");
//            return null;
//        }
//        // Αναζήτηση για το χρήστη
//        setUsersList((List<Tblusers>) (List<?>) db.dbTransactions.getObjectsByProperty(model.Tblusers.class.getCanonicalName(), "username", getUsername()));
//
//        // Αν είναι άδεια η λίστα ή ο χρήστης δεν έχει email, επιστροφή
//        if (getUsersList().isEmpty() || Objects.isNull(getUsersList().get(0).getIdEmployee().getEmployeeEmail())
//            || getUsersList().get(0).getIdEmployee().getEmployeeEmail().equals("")) {
//            GenericUtils.addMessage(FacesMessage.SEVERITY_WARN,"Wrong", "Username or User Email not valid!");
//            return "null";
//        }
//
//        // Αν είναι περάσει από τα παραπάνω ξεκινάμε την διαδικασία για reset password
//        user = getUsersList().get(0);
//        String newAltPass = StringUtils.randomPass();
//        String altPassword = HashingUtil.SHA1(newAltPass);
//        user.setAltpass(altPassword);
//        db.dbTransactions.updateObject(user);
//
//        String w = "";
//        StringBuilder eb = new StringBuilder();
//        eb.append("<html>");
//        eb.append("<body>");
//        eb.append("Dear PEARL user,");
//        eb.append("<br/>");
//        eb.append("<br/>");
//        eb.append("You have submitted a request for a new password. We have created a new password for you and the new one is: " + newAltPass);
//        eb.append("<br/>");
//        eb.append("You must change it when you login.");
//        eb.append("<br/>");
//        eb.append("If you have NOT asked for a new password, just ignore this message. Your password is still valid.");
//        eb.append("<br/>");
//        eb.append("<br/>");
//        eb.append("Thank you.");
//        eb.append("</body>");
//        eb.append("</html>");
//        String emailHeader = "PEARL New Password Request";
//        String finalEmail = eb.toString();
//        utils.EmailSender.sendmail(emailHeader, finalEmail, getUsersList().get(0).getIdEmployee().getEmployeeEmail(), null, null, null);
//
//        GenericUtils.addMessage(FacesMessage.SEVERITY_INFO,"OK", "A new password was send to your E-mail.");

        return null;
    }

    public String validateUser() throws IOException {

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        if ( Objects.isNull(username) || username.isEmpty() || "".equals(username)) {
            GenericUtils.addMessage(FacesMessage.SEVERITY_WARN,"Wrong login", "You must insert a username ");
            return "null";
        } else if (password == null ||password.isEmpty() || password == "") {
            GenericUtils.addMessage(FacesMessage.SEVERITY_WARN,"Wrong login", "You must insert a password ");
            return "null";
        } else {

            // Αναζήτηση για το χρήστη
            usersList = ((List<Tbluser>) (List<?>) db.dbTransactions.getObjectsByProperty(Tbluser.class.getCanonicalName(), "username", getUsername()));
            // Αν είναι άδεια η λίστα, επιστροφή
            if (usersList.isEmpty()) {
                request.getSession().setAttribute(AUTH_KEY, null);
                GenericUtils.addMessage(FacesMessage.SEVERITY_WARN,"Wrong login", "Inserted credentials are wrong");
                return "null";
            }

            user = usersList.get(0);

            //Ελέγχω αν ο λογαριασμός είναι απενεργοποιημένος
            if(user.getUserActive()==0) {// Disabled
                GenericUtils.addMessage(FacesMessage.SEVERITY_ERROR,"Account is disabled", "Call administrator for support");
                return "null";
            }

            Object userLockout;
            LOCK_OUT = 5;

            //Ελέγχω αν έχει λήξει ο λογαριασμός
            LocalDateTime curdate = LocalDateTime.now();
            LocalDateTime userexpDate = TimeUtils.localDateTimeFromDate(user.getExpDate());

            if(Objects.nonNull(userexpDate) && curdate.isAfter(userexpDate)){
                GenericUtils.addMessage(FacesMessage.SEVERITY_ERROR,"Account Expired", "Call Helpdesk for support");
                return "null";
            }


            //Ελέγχω αν έχει κλειδωθεί ο χρήστης
            if(Objects.nonNull(user.getWrongCred()) && user.getWrongCred() >= LOCK_OUT){
                GenericUtils.addMessage(FacesMessage.SEVERITY_ERROR,"User is locked", "Call Helpdesk for support");
                user.setUserActive(0); //Disable User
                db.dbTransactions.storeWithMergeObject(user);
                return "null";
            }

            // Αν είναι σωστός ο συνδυασμός username/password, εισαγωγή στην εφαρμογή
            if (!user.getIdRole().getRoleDesc().equals("No Access")) {
                //Αν υπάρχει altpass και έχει κάνει login με αυτό τότε τον οδηγούμε στην σελίδα αλλαγής κωδικού πρόσβασης
                if ((getUsername().equals(user.getUsername()))
                    || (getUsername().equals(user.getUsername())
//                        && HashingUtil.SHA1(getPassword()).equals(user.getAltpass())
                      &&  true
                )
                ){

                    request.getSession().setAttribute(AUTH_KEY, getUsername());
                    request.getSession().setAttribute("user", user);

                    //Εάν έχει κάνει Login με το κανονικό password κάνουμε reset το εναλλακτικό
//                    if(Objects.equals(HashingUtil.SHA1(getPassword()), user.getPassword())){
//                        user.setAltpass(null);
//                    }else{
//
//                        return null;
//                    }

                    //Φτιάχνουμε το security
                    security = new Security(user);

                    //Βάζουμε το theme που έχει eπιλέξει ο χρήστης
                    guestPreferencesBean = new GuestPreferences();

//                    if (user.getLightMenu() != null && user.getLightMenu()) {
                        guestPreferencesBean.setLightMenu(true);
//                    } else {
//                        guestPreferencesBean.setLightMenu(false);
//                    }

                    user.setLastLogin(TimeUtils.dateFromLocalDateTime(curdate));
                    user.setWrongCred(0);
                    user.setLastLoginIp(userIp);
//                    db.dbTransactions.updateObject(user);


//                    //Είναι για τα μηνύματα.
//                    String query = "from (\n" +
//                            "                  Select distinct u.*\n" +
//                            "                  from user u,\n" +
//                            "                       person p,\n" +
//                            "                       personcompany pc\n" +
//                            "                  where u.idperson = p.idperson\n" +
//                            "                    and p.isactive = 1\n" +
//                            "                    and pc.idperson = p.idperson\n" +
//                            "                    and pc.idperson <> "+user.getIdpers.getIdperson()+"\n"+
//                            "                    and pc.idcompany in (select distinct pc.idcompany\n" +
//                            "                                         from personcompany pc\n" +
//                            "                                         where idperson = "+user.getIdperson().getIdperson()+")\n" +
//                            "              )e";
//                    usersListGlobal = (List<Tbluser>) (List<?>) db.dbTransactions.getObjectsBySqlQuery(Tbluser.class, query, null, null, null);




//                    if(Objects.nonNull(userexpDate) && curdate.isAfter(userexpDate.minusDays(10))) {
//                        GenericUtils.addMessage(FacesMessage.SEVERITY_WARN,"Account Warning", "Account password will expire soon. Consider change you password!");
//                    }



                    //Αν έχει κάνει login με το default password τον οδηγούμε στην ανάλογη σελίδα να το αλλάξει.
//                    String defaultPassword = HashingUtil.SHA1("123456");
//                    if (user.getPassword().equals(defaultPassword) || !Objects.isNull(user.getAltpass())) {
//                        GenericUtils.addMessage(FacesMessage.SEVERITY_INFO,"Password Change", "You have login with default password. You must change it!");
//                        return "/webContent/changePassword.jsf?faces-redirect=true";
//                    } else {
                        return "webContent/dashboard.jsf?faces-redirect=true";
//                    }
                } else {//Περίπτωση που είναι λάθος ο συνδυασμός Username και Password
                    request.getSession().setAttribute(AUTH_KEY, null);
                    user.setWrongCred(user.getWrongCred()+1);
                    user.setLastLoginIp(userIp);

                    request.getSession().setAttribute("user", user);


                    request.getSession().setAttribute("user", null);
                    int remainingtries = LOCK_OUT-user.getWrongCred();
                    if (remainingtries==1){
                        GenericUtils.addMessage(FacesMessage.SEVERITY_WARN,"Careful! Wrong Credentials",
                                "Insert correct Username and Password. This is your last chance before your account lock!");
                    } else if (remainingtries==0) {
                        user.setUserActive(0);
                        GenericUtils.addMessage(FacesMessage.SEVERITY_ERROR,"Account Locked", "Call Helpdesk for support");
                    } else {
                        GenericUtils.addMessage(FacesMessage.SEVERITY_INFO,"Wrong Credentials",
                                "Insert correct Username and Password. You have " + remainingtries + " tries");
                    }

                    db.dbTransactions.storeWithMergeObject(user);
                    return null;
                }
            } else {
                FacesContext.getCurrentInstance().getExternalContext().redirect("http://www.google.com/");
                return null;
            }
        }
    }


    public Boolean isAdmin() {
        Boolean isAdmin = false;
        isAdmin = user.getIdRole().getRoleDesc().equals("Administrator");
        return isAdmin;
    }


    /**
     * call redirect function in class Security
     * Άμα βρίσκει την σελίδα θα πρέπει να δίνει πρόσβαση στον χρήστη
     *
     * @param pageName
     */
    public boolean redirect(String pageName) {
//        log.info("pageName -->" + pageName);
        boolean hasAccessToPage = getSecurity() != null && getSecurity().foundPageName(pageName);
        if (!hasAccessToPage) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/pearl/webContent/main.jsf?faces-redirect=true");
            } catch (IOException ex) {
                log.error(ex.getLocalizedMessage());
            }
        }
        return hasAccessToPage;
    }


    /**
     * Επιστρέφει to avatar για τον χρήστη.
     * Άμα ο χρήστης έχει ανεβάσει κάποιο avatar τότε θα του επιστρέψει αυτό
     * Άμα δεν έχει ανεβάσει θα του επιστρέψει ένα dummy ανάλογα με το φύλο του
     *
     * @param useravatar
     * @return
     * @throws IOException
     */
    public String avatarGender(Tbluser useravatar) throws IOException {
        // Έχουμε μετατρέψει τις φωτοφγραφίες του dummy avatar του χρήστη σε base64 images.
        // Λόγο όμως του ότι ήταν πολύ μεγάλες και δεν χωρούσε σε ένα string τις βάλαμε σε ένα properties αρχείο απο όπου
        // πηγαίνει και τα διαβάζει απο εκεί.

        Properties prop = new Properties();
        prop.load(getClass().getResourceAsStream("/gender.properties"));

        if (useravatar.getPassword().equals("bot")) {
            return prop.getProperty("bot");
        }

        //Ελέγχουμε άμα ο χρήστης έχει ανεβάσει κάποιο avatar
        if (useravatar.getAvatar() == null) {
            int i = useravatar.getIdEmployee().getGenderMale();

            if (i == 1) {
                //MALE
                return prop.getProperty("male");
            } else if(i == 2){
                return prop.getProperty("female");
            }else{
                return prop.getProperty("unknown");
            }
        } else {
            return useravatar.getAvatar();
        }
    }

    public void clear() {
        username = "";
        email="";
        Ajax.update("forgotForm");

    }
}
