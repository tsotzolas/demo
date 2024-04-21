package beans;

import db.dbTransactions;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.omnifaces.cdi.Eager;

import javax.annotation.PostConstruct;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
@Slf4j
@Getter
@Setter
@Eager
public class ApplicationBean implements Serializable {

    public static Integer expirationRegistrationDay = 0;

    @PostConstruct
    public void initialize() {

        // To max toy damageCost που έχουν τα wagons.
        String queryExpirationDays = "from globalparam e where e.globalparam_desc = 'expirationRegistrationDays'";

        expirationRegistrationDay = (Integer) dbTransactions.getObjectsBySqlQueryObject(queryExpirationDays).get(0);

    }

}

