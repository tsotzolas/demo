package beans;

import db.HashingUtil;
import lombok.extern.slf4j.Slf4j;
import model.Tblemployee;
import model.Tblroles;
import model.TempUsers;
import model.Tbluser;
import org.omnifaces.util.Ajax;
import org.omnifaces.util.Faces;
import utils.GenericUtils;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static java.lang.Thread.sleep;

/**
 * Κάνει register τον χρήστη.
 */

@Named("registrationRequestBean")
@Slf4j
@ViewScoped
public class RegistrationRequestBean implements Serializable {

    private String token;
    private String username;
    private String email;
    private String password;
    private String password1;

    private TempUsers tempUser = new TempUsers();

    private Tblemployee employee = new Tblemployee();


    @PostConstruct
    public void init(){

//        person.setNickname("");
//        person.setPersonFname("");
//        person.setPersonLname("");

        checkToken();
    }


    /**
     * Ελέγχει το token του χρήστη.
     * Στην περίπτωση που υπάρχει θα πρέπει να τον κάνουμε redirect για να συμπληρώσει και τα υπόλοιπα στοιχεία.
     * @return
     */
    public void checkToken() {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        token = request.getParameter("token");

        String query = "from temp_users e where e.user_token ='" + token+"'";
        List<TempUsers> tempUsersList = ((List<TempUsers>) (List<?>) db.dbTransactions.getObjectsBySqlQuery(TempUsers.class, query, null, null, null));

        tempUser = tempUsersList.stream().findFirst().orElse(null);

        String queryFindUser="from\n" +
                "(Select p.*\n" +
                " from temp_users tu,\n" +
                "      user u,\n" +
                "      person p\n" +
                " where tu.user_email = u.username\n" +
                "   and u.idperson = p.idperson\n" +
                "   and u.username = '"+tempUser.getUserEmail()+"'\n" +
                ") e";
        List<Tblemployee> personList = ((List<Tblemployee>) (List<?>) db.dbTransactions.getObjectsBySqlQuery(Tblemployee.class, queryFindUser, null, null, null));
        employee = personList.stream().findFirst().orElse(new Tblemployee());
        Ajax.update("form");
    }



    /**
     * Κάνει register τον χρήστη.
     */
    public void registerUser(Boolean isResetPass) throws IOException, InterruptedException {

        //Θα πρέπει να φτιάξουμε μια εγγραφή στην πίνακα temp_user


//        Person person = new Person();
        employee.setEmployeeEmail(tempUser.getUserEmail());
//        employee.setToken(HashingUtil.SHA1(tempUser.getUserEmail()+(new Date().toString())));
        employee.setIsactive(true);

        employee = (Tblemployee) db.dbTransactions.storeWithMergeObject(employee);

        //TODO αυτό πρέπει να το αλλάξω!
        Tblroles role =(Tblroles) db.dbTransactions.getObjectById(Tblroles.class.getCanonicalName(),13);

        // Στην περίπτωση που δεν ειναι resetPassword
        if(!isResetPass) {
            Tbluser user = new Tbluser();
            user.setIdRole(role);
            user.setCreateTime(new Date());
            user.setPassword(HashingUtil.SHA1(password));
            user.setIdEmployee(employee);
            user.setUsername(tempUser.getUserEmail());
            user.setUserActive(1);    //Ενεργός Χρήστης

            //Θα πρέπει να κάνουμε έλεγχο άμα υπάρχει ο χρήστης.
            String query = "from user e where username ='" + tempUser.getUserEmail() + "'";
            List<Tbluser> tempUsersList = ((List<Tbluser>) (List<?>) db.dbTransactions.getObjectsBySqlQuery(Tbluser.class, query, null, null, null));

            if (Objects.nonNull(tempUsersList) && tempUsersList.size() > 0) {
                GenericUtils.addMessage(FacesMessage.SEVERITY_WARN, "User Already Exists.", "If you don't remember the password you can reset it.");
            } else {
                db.dbTransactions.storeWithMergeObject(user);
                GenericUtils.addMessage(FacesMessage.SEVERITY_INFO, "Success Registration", tempUser.getUserEmail());

            }
        }else{
////            Tbluser user = employee.getTblusers();
//            user.setPassword(HashingUtil.SHA1(password));
//
//            db.dbTransactions.storeWithMergeObject(user);
//            GenericUtils.addMessage(FacesMessage.SEVERITY_INFO, "Success reset password", "For user " + tempUser.getUserEmail()+ ". You will redirect automatically.");
        }

        sleep(5);
        Faces.redirect("login.jsf",null);
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public TempUsers getTempUser() {
        return tempUser;
    }

    public void setTempUser(TempUsers tempUser) {
        this.tempUser = tempUser;
    }


}
