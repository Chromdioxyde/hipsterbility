package de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules;

import java.io.IOException;

/**
 * Created by Albert Hoffmann on 25.02.14.
 */
public class ScreenRecorder {

    private static ScreenRecorder instance = new ScreenRecorder();

    private boolean recording = false;

    private ScreenRecorder(){

    }

    public static ScreenRecorder getInstance(){
        return instance;
    }

    public void startRecording(){
        try {
            Process suProcess = Runtime.getRuntime().exec("su");
            Process process = Runtime.getRuntime().exec("screenrecord /sdcard/Folder/Example.mp4");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void stopRecording(){


    }

}
