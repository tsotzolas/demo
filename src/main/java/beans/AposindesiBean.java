package beans;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author tsotzolas@gmail.com
 */
@Named("AposindesiBean")
@Slf4j
@ViewScoped
public class AposindesiBean implements Serializable {


    /**
     * Creates a new instance of aposindesiBean
     */
    public AposindesiBean() {
    }

    @PostConstruct
    public void init() {
    }

    public String  aposindesiUser() {

        // Ακύρωση του Session
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        request.getSession().setAttribute(LoginBean.AUTH_KEY, null);
        request.getSession().setAttribute("user", null);

        return "/login.jsf?faces-redirect=true";
    }

    public String changePassword() {
        return "/login/changePassword.jsf?faces-redirect=true";
    }

}
