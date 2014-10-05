package de.hsosnabrueck.hipsterbility;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Albert on 20.09.2014.
 */
public class PasswordTest {

    @Test
    public void testPasswords(){
        String pw1 = "dummy";
        String pwhash1 = generateTestPasswords(pw1);
        String pwhash2 = generateHash(pw1);
        System.out.println(pw1+"|"+pwhash1+"|"+pwhash2);
    }

    public String generateTestPasswords(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
//            String text = "admin";
            md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String output = bigInt.toString(16);

            System.out.println(output);
            return output;

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(PasswordTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String generateHash(String password) {
        // This is one way of generating a SHA-256 hash. I uses classes/methods
        // from the Google Guava project. See the Maven pom.xml file which
        // I've modified to include the Guava libraries. See the imports
        // above which show what is being used.
        String hash
                = Hashing.sha256()
                .hashString(password, Charsets.UTF_8).toString();

        String output = MessageFormat.format("{0} hashed to: {1}", password, hash);

        System.out.println(output);
        return hash;
    }


}
