package de.hsosnabrueck.iui.informatik.vma.hipsterbility.helper;

import android.os.Environment;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.Hipsterbility;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.Session;

import java.io.File;

/**
 * Created by Albert Hoffmann on 25.02.14.
 * Class for various utility methods that are being user by several classes.
 */
public class Util {

    public static String createOutputDirPathName(Session session, String subdir) {
       return createSessionDirPathName(session)
                + subdir
                + File.separator;
    }

    public static String createSessionDirPathName(Session session){
        return Environment.getExternalStorageDirectory()
                + File.separator
                + Hipsterbility.BASE_DIR
                + File.separator
                + session.getId()
                + File.separator;
    }
}
