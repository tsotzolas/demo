/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

//import org.apache.commons.codec.binary.Base64;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SystemUtils;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Meletiadis Vasilis <kurtz.pentagon@gmail.com>
 */
public class StringUtils {

    public static String getLatinFromGreek(String input) {
        String result;
        input = input.toLowerCase();


        String[] REGEXdouble = {"αι", "αυ([θκξπσςτφχψ]|\\s|$)", "αυ", "οι", "ου", "ει",
                "ευ([θκξπσςτφχψ]|\\s|$)", "ευ", "(^|\\s)μπ", "μπ(\\s|$)", "μπ", "ντ", "τσ",
                "τζ", "γγ", "γκ", "ηυ([θκξπσςτφχψ]|\\s|$)", "ηυ", "θ", "χ", "ψ"
        };
        String[] REPLACEdouble = {"ai", "af$1", "av", "oi", "ou", "ei", "ef$1", "ev", "$1b",
                "b$1", "mp", "nt", "ts", "tz", "ng", "gk", "if$1", "iy", "th", "ch", "ps"
        };

        Pattern p = Pattern.compile(REGEXdouble[0]);
        Matcher m = p.matcher(input);
        result = m.replaceAll(REPLACEdouble[0]);


        for (int i = 1; i < REGEXdouble.length; i++) {
            p = Pattern.compile(REGEXdouble[i]);
            m = p.matcher(result);
            result = m.replaceAll(REPLACEdouble[i]);

        }


        String REGEX = "αάβγδεέζηήιίΐϊκλμνξοόπρστυύϋΰφωώς";
        String REPLACE = "aabgdeeziiiiiiklmnxooprstyyiifoos";

        p = Pattern.compile(REGEX.substring(0, 1));
        m = p.matcher(result);
        result = m.replaceAll(REPLACE.substring(0, 1));


        for (int i = 1; i < REGEX.length(); i++) {
            p = Pattern.compile(REGEX.substring(i, i + 1));
            m = p.matcher(result);
            result = m.replaceAll(REPLACE.substring(i, i + 1));

        }


        return result;
    }


    /**
     * Παίρνει μια λίστα απο String και την κάνει ένα string με ','
     *
     * @param list
     * @return
     */
    public static String listToArray(List<String> list) {

        String r = "";
        if (list.size() > 0) {
            r = org.apache.commons.lang3.StringUtils.join(list, ",");
        }


        return r;
    }

    /**
     * Παίρνει μια λίστα απο String και την κάνει ένα string με ','
     *
     * @param list
     * @return
     */
    public static String listToArrayInteger(List<Integer> list) {

        String r = "";
        if (list.size() > 0) {
            r = org.apache.commons.lang3.StringUtils.join(list, ",");
        }


        return r;
    }

    /**
     * Παίρνει μια λίστα απο String και την κάνει ένα string με ','
     * αλλά για να το χρησιμοποιήσουμε σαν string μέσα σε ένα ερώτημα/
     *
     * @param list
     * @return
     */
    public static String listToArrayForString(List<String> list) {

        String r = " '";
        if (list.size() > 0) {
            r = r + org.apache.commons.lang3.StringUtils.join(list, "','");
        }
        r = r + "'";


        return r;
    }


    /**
     * Μέθοδος που μετατρέπει ένα inputstream σε BASE64 string
     *
     * @param inputStream
     * @return base64 String
     */
    public static String inputStreamToBase64String(InputStream inputStream) {

        String encodedString = "";
        try {
//            InputStream is = inputStream;
            byte[] fileContent = IOUtils.toByteArray(inputStream);
////            fileContent = IOUtils.toByteArray(is);
//            encodedString = Base64.encodeBase64String(fileContent);
            encodedString = Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encodedString;
    }


    /**
     * Μέθοδος που μετατρέπει ένα byte[] σε BASE64 string
     *
     * @param
     * @return base64 String
     */
    public static String bytesToBase64String(byte[] input) {

        String encodedString = "";
        try {
//            InputStream is = inputStream;
            encodedString = Base64.getEncoder().encodeToString(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodedString;
    }


    /**
     * Επιστρέφει το md5 απο ένα string.
     *
     * @param input
     * @return
     */
    public static String getMd5(String input) {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String randomPass() {

        String rPass = "";
        for (int i = 0; i < 8; i++) {
            rPass += randomCharacter();
        }
        return rPass;
    }

    //Create a function that randomly generates a letter (lowercase or uppercase) or number (0-9) using ASCII
    //'0' - '9' => 48-57 in ASCII
    //'A' - 'Z' => 65-90 in ASCII
    //'a' - 'z' => 97-122 in ASCII
    public static char randomCharacter() {

        int rand = (int) (Math.random() * 62);
        if (rand <= 9) {
            //Number (48-57 in ASCII)
            //To convert from 0-9 to 48-57, we can add 48 to rand because 48-0 = 48
            int number = rand + 48;
            //This way, rand = 0 => number = 48 => '0'
            //Remember to cast our int value to a char!
            return (char) (number);
        } else if (rand <= 35) {
            //Uppercase letter (65-90 in ASCII)
            //To convert from 10-35 to 65-90, we can add 55 to rand because 65-10 = 55
            int uppercase = rand + 55;
            //This way, rand = 10 => uppercase = 65 => 'A'
            //Remember to cast our int value to a char!
            return (char) (uppercase);
        } else {
            //Lowercase letter (97-122 in ASCII)
            //To convert from 36-61 to 97-122, we can add 61 to rand because 97-36 = 61
            int lowercase = rand + 61;
            //This way, rand = 36 => lowercase = 97 => 'a'
            //Remember to cast our int value to a char!
            return (char) (lowercase);
        }
    }

    /**
     * Μέθοδος που κάνει sanitize ένα string και τοαυ αφαιρεί τον html κώδικα που έχει.
     *
     * @param untrustedHTML
     * @return
     */
    public static String sanitizeHTML(String untrustedHTML) {
        PolicyFactory policy = new HtmlPolicyBuilder()
//                .allowAttributes("src").onElements("img")
//                .allowAttributes("href").onElements("a")
//                .allowStandardUrlProtocols()
//                .allowElements(
//                        "a", "img")
                .toFactory();

        return policy.sanitize(untrustedHTML);
    }


    /**
     * Κόβει τα μεγάλα strings ώστε να μποορούμε να τα εμφανίσουμε στον χρήστη.
     *
     * @return
     */
    public static String cutLongStrings(String inputString) {

        if (inputString.length() > 50) {
            return inputString.substring(0, 50) + "....";
        } else {
            return inputString;
        }
    }


}
