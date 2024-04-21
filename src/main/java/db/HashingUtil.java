package db;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Παράγει τα hashes γνωστών συναρτήσεων βάσει του String που δίνεται ως είσοδος.
 * 
 * @author Meletiadis Vasilis <kurtz.pentagon@gmail.com>
 */
@Slf4j
public class HashingUtil {
    

    public static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    buf.append((char) ('0' + halfbyte));
                } else {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }
    
    public static String MD5(String input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes("iso-8859-1"), 0, input.length());
            byte[] md5hash = md.digest();
            return convertToHex(md5hash);
        } catch (UnsupportedEncodingException ex) {
            log.error("MD5 UnsupportedEncodingException, " + ex);
        } catch (NoSuchAlgorithmException ex) {
            log.error("MD5 NoSuchAlgorithmException, " + ex);
        }
        return null;
    }
    
    public static String SHA1(String input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-1");
            md.update(input.getBytes("iso-8859-1"), 0, input.length()); //εδω το κάνουμε salted.
            byte[] sha1hash = md.digest();
            return convertToHex(sha1hash);
        } catch (UnsupportedEncodingException ex) {
            log.error("SHA-1 UnsupportedEncodingException, " + ex);
        } catch (NoSuchAlgorithmException ex) {
            log.error("SHA-1 NoSuchAlgorithmException, " + ex);
        }
        return null;
    }

//    public String get_SHA_512_SecurePassword(String passwordToHash, String salt){
//        String generatedPassword = null;
//        try {
//            MessageDigest md = MessageDigest.getInstance("SHA-512");
//            md.update(salt.getBytes(StandardCharsets.UTF_8));
//            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
//            StringBuilder sb = new StringBuilder();
//            for(int i=0; i< bytes.length ;i++){
//                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
//            }
//            generatedPassword = sb.toString();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return generatedPassword;
//    }

}
