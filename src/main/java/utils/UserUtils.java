/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import db.HashingUtil;
import db.dbTransactions;
import model.Tblcompany;
import model.TempUsers;
import model.Tbluser;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Meletiadis Vasilis <kurtz.pentagon@gmail.com>
 */
public class UserUtils {

    /**
     * Κάνει register τον χρήστη.
     * Στην περίπτωση που έχει companyid θα πρέπει να βάζει τον νέο χρήστη
     */
    public static Boolean registerUser(Tblcompany company, String email, Tbluser user, Boolean isResetPass) throws IOException {

        Boolean allOk = true;
        try {
            //Θα πρέπει να φτιάξουμε μια εγγραφή στην πίνακα temp_user
            String token = HashingUtil.SHA1(new Date().toString());

            //θα πρέπει να δούμε άμα υπάρχει με το ίδιο email άλλο αίτημα για τον χρήστη.
            String queryEmails = "from temp_users e where e.user_email = '" + email + "'";
            List<TempUsers> tempUserList = (List<TempUsers>) (List<?>) db.dbTransactions.getObjectsBySqlQueryDistinct(TempUsers.class, queryEmails, null, null, null);

            //Σβήνουμε προηγούμενα registration σε περίπτωση που έχει κάνει πολλές προσπάθειες να συνδεθεί.
            if (Objects.nonNull(tempUserList) && tempUserList.size() > 0) {
                for (TempUsers tempUser : tempUserList) {
                    db.dbTransactions.deleteObject(tempUser);
                }
            }


            TempUsers tempUser = new TempUsers();
            tempUser.setUserEmail(email);
            tempUser.setUserToken(token);
            tempUser.setRequestDate(new Date());
            tempUser.setCompanyName(company.getCompanyname());
            if(isResetPass)
                tempUser.setResetPassword(true);

            //Προσθέτουμε τον χρόνο που θα είναι valid το registration
            String queryExpirationDays = "from (select e1.globalparam_value from tblglobalparam e1 where e1.globalparam_desc = 'expirationRegistrationDays') e";
            Object expirationRegistrationDay = (Object) dbTransactions.getObjectsBySqlQueryObject(queryExpirationDays).get(0);

            tempUser.setInvitationExpiration(TimeUtils.addSubtractDaysToDate(new Date(), Integer.valueOf(expirationRegistrationDay.toString())));

            db.dbTransactions.storeWithMergeObject(tempUser);

            String page = "";

            String queryIp = "from (select e1.globalparam_value from tblglobalparam e1 where e1.globalparam_desc = 'ip') e";
            Object ipObj = (Object) dbTransactions.getObjectsBySqlQueryObject(queryIp).get(0);

            String ip = String.valueOf(ipObj.toString());

            if(isResetPass){
                page = "resetPass";
            }else{
                page = "registrationRequest";
            }

            String body = "https://"+ip+"/ergani/"+page+".jsf?token=" + token;
            String emailTitle = "";
            if(isResetPass){
                emailTitle= "Reset Password";
            }else{
                emailTitle = "Verification Email";
            }
            EmailSender.sendmail(emailTitle, body, email, null, null, null);

        }catch(Exception ex){
            ex.printStackTrace();
            allOk=false;
        }
        return allOk;
    }

}
