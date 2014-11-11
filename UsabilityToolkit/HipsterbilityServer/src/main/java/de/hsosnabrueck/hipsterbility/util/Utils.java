package de.hsosnabrueck.hipsterbility.util;

import java.io.File;
import java.util.Random;

/**
 * Created by Albert on 28.09.2014.
 */
public class Utils {

    public static final String FILE_PATH = System.getProperty("user.dir") + File.separator
            + "hipsterbility" + File.separator + "files"; // TODO: make configurable

    private static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static Random rnd = new Random(System.currentTimeMillis());

    public static String randomString( int len ){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append(CHARS.charAt(rnd.nextInt(CHARS.length())));
        return sb.toString();
    }
}
