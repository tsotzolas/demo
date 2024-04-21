package utils;

import static com.google.common.math.IntMath.mod;

//import beans.customComponents.DatesObjects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Slf4j
public class GenericUtils {



    /**
     * Για να προσθέτουμε message στο FacesContec
     * FacesMessage.SEVERITY_INFO
     * FacesMessage.SEVERITY_WARN
     * FacesMessage.SEVERITY_ERROR
     *
     * @param severity
     * @param summary
     */
    public static void addMessage(FacesMessage.Severity severity, String summary, String details) {
        FacesMessage message = new FacesMessage(severity, summary, details);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }



}
