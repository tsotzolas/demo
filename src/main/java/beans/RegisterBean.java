package beans;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.omnifaces.util.Faces;
import utils.UserUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

/**
 * Κάνει register τον χρήστη.
 */

@Named("registerBean")
@Slf4j
@Getter
@Setter
@SessionScoped
public class RegisterBean implements Serializable {

    private String username;
    private String email;
    private String password;
    private String password1;



    @PostConstruct
    public void init() {
    }


    /**
     * Κάνει register τον χρήστη.
     * Στην περίπτωση που έχει companyid θα πρέπει να βάζει τον νέο χρήστη
     */
    public void registerUser(Integer companyId) throws IOException {

        UserUtils.registerUser(null,email,null,false);

        Faces.redirect("registerMessage.jsf",null);

    }

    public void redirect(){
        try {
            Faces.redirect("login.jsf",null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
