package de.hsosnabrueck.hipsterbility.util;

import java.util.Random;

/**
 * Created by Albert on 28.09.2014.
 */
public class Utils {

    private static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static Random rnd = new Random(System.currentTimeMillis());

    public static String randomString( int len ){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( CHARS.charAt( rnd.nextInt(CHARS.length()) ) );
        return sb.toString();
    }
}
