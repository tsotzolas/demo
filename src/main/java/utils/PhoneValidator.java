package utils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Pattern;

/**
* Created by Γεώργιος on 8/4/2019.
*/
@FacesValidator("phoneValidator")
public class PhoneValidator implements Validator {

    //Ελέγχουμε να ειναι 10 τα νούμερα και να ειναι μόνο αριθμοί
    private static final Pattern PATTERN = Pattern.compile("^([0-9]{10})$");


    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null || ((String) value).isEmpty()) {
            return; // Let required="true" handle.
        }

        if (!PATTERN.matcher((String) value).matches()) {
            String summary = context.getApplication().evaluateExpressionGet(context, "Phone Error", String.class);
            String detail = context.getApplication().evaluateExpressionGet(context, "Phone doesn't not have the appropriate format", String.class);

            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail));
        }
    }

}
