package de.hsosnabrueck.iui.informatik.vma.hipsterbility.rest;

import de.hsosnabrueck.iui.informatik.vma.hipsterbility.Hipsterbility;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.helper.Util;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.Session;

import java.io.File;

/**
 * Created by Albert Hoffmann on 25.02.14.
 */
public class UploadManager {

    private static UploadManager instance = new UploadManager();
    //TODO: set parameter names
    private final static String PARAM_NAME_SCREENSHOT = "screenshot";
    private final static String PARAM_NAME_CAMERA = "";
    private final static String PARAM_NAME_SCREEN = "";
    private final static String PARAM_NAME_TOUCH_LOG = "";

    private static UploadManager getInstance() {
        return instance;
    }

    public boolean uploadSessionData(Session session) {
        File sessionDir = new File(Util.createSessionDirPathName(session));
        if (!sessionDir.exists()) {
            return false;
        }
        //TODO: check and upload data

        /*
        File myFile = new File("/path/to/file.png");
        RequestParams params = new RequestParams();
        try {
            params.put("profile_picture", myFile);
        } catch(FileNotFoundException e) {}
         */
        return true;
    }
}
