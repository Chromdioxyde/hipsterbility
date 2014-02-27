package de.hsosnabrueck.iui.informatik.vma.hipsterbility.helper;

import android.os.Environment;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.Hipsterbility;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.Session;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.User;

import java.io.File;

/**
 * Created by Albert Hoffmann on 25.02.14.
 * Class for various utility methods that are being user by several classes.
 */
public class Util {

    public static final String URL_SUFFIX_CAMERA = "videos/";
    public static final String URL_SUFFIX_CAPTURES = "captures/"; // for screenshots

    public static String createOutputDirPathName(long sessionId, String subdir) {
       String path =  createSessionDirPathName(sessionId)
                + subdir
                + File.separator;
        new File(path).mkdirs();
        return path;
    }

    public static String createSessionDirPathName(long sessionId){
        String path =  Environment.getExternalStorageDirectory()
                + File.separator
                + Hipsterbility.BASE_DIR
                + File.separator
                + sessionId
                + File.separator;
        new File(path).mkdirs();
        return path;
    }

    public static String createRelativeRoute(User u, Session s, String suffix){
        return u.getId() + "/" + s.getId() + "/" + suffix;
    }
}
