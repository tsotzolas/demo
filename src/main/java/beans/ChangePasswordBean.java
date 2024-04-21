/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;


import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import lombok.Getter;
import lombok.Setter;
import model.Tbluser;
import utils.GenericUtils;

import db.HashingUtil;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author George Tsotzolas <tsotzolas@gmail.com>
 */
@Named
@Slf4j
@Setter
@Getter
@ViewScoped
public class ChangePasswordBean implements Serializable {

    //variables
    private String username;
    private String oldPassword;
    private String newPassword1;
    private String newPassword2;
    private String message;
    //placeholder obj
    private Tbluser user = new Tbluser();
    @Inject
    private LoginBean loginBean;
    private Boolean isFailed = false;
    FacesMessage msg = new FacesMessage();

    /**
     * Creates a new instance of ChangePasswordBean
     */
    public ChangePasswordBean() {
        log.debug("---->ChangePasswordBean constructor called");
    }

    @PostConstruct
    public void init() {
        log.info("---->PostConstructor init() called");
        HttpServletRequest hsr = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        log.debug("init() completed");
        //msg = new FacesMessage("Επιτυχής Εισαγωγή", (promitheftis.getEponymo()));
        username = loginBean.getUser().getUsername();
    }

    /**
     *
     */
    public void changePWD() {
        log.debug("---->changePWD() called");

        user = loginBean.getUser();



        // Σωστή καταχώρηση κωδικού
        if ((user.getPassword().equals(HashingUtil.SHA1(getOldPassword())))
                && newPassword1.equals(newPassword2)
                && newPassword1.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$/+])(?=\\S+$).{8,}$")
                && !(newPassword1.equals(getOldPassword())) && newPassword1.length()>7 ){


            user.setPassword(HashingUtil.SHA1(newPassword1));
            user.setAltpass(null);
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.DATE, loginBean.getPASS_PERIOD());
            user.setExpDate(cal.getTime());
            db.dbTransactions.updateObject(user);

            GenericUtils.addMessage(FacesMessage.SEVERITY_INFO,"Success ","Password has changed" );


            isFailed = true;

            // Λανθασμένη εισαγωγή παλαιού κωδικού
        } else if (!user.getPassword().equals(HashingUtil.SHA1(getOldPassword()))) {
            GenericUtils.addMessage(FacesMessage.SEVERITY_WARN,"Error","Old Password is wrong" );
            isFailed = false;
            // Δεν ταιριάζουν οι νέοι κωδικοί πρόσβασης
        } else if (!newPassword1.equals(newPassword2)) {
            GenericUtils.addMessage(FacesMessage.SEVERITY_WARN,"Error","New passwords don't match" );
            isFailed = false;
       } else if (!newPassword1.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$/+])(?=\\S+$).{8,}$")) {
            GenericUtils.addMessage(FacesMessage.SEVERITY_WARN,"Error","New password must be complex using letters, numbers and symbols" );
            isFailed = false;
        } else if (newPassword1.equals(getOldPassword())) {
            GenericUtils.addMessage(FacesMessage.SEVERITY_WARN,"Error","New password must be different from the old" );
            isFailed = false;
        } else if (newPassword1.length()<8){
            GenericUtils.addMessage(FacesMessage.SEVERITY_WARN,"Error","New Password length must be at least 8 characters long" );
            isFailed = false;
        }

        if(isFailed) {
            redirectLoginPage();
        }
    }

    public String redirectLoginPage() {
        log.debug("---->Method redirectLoginPage() called");
        try {
        // Ακύρωση του Session
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        request.getSession().setAttribute(LoginBean.AUTH_KEY, null);


            FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath()+"/login.jsf?faces-redirect=true");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "/login.jsf?faces-redirect=true";
    }

     public String redirectChangePassword() throws IOException {
        HttpServletRequest hsr = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

            return "/login/changePassword.jsf?faces-redirect=true";
    }
}