package de.hsosnabrueck.hipsterbility.model.enums;

/**
 * Created by Albert on 10.11.2014.
 */
public enum FileType {

    IMAGE_SCREENSHOT("screenthot"),
    AUDIO_MICROPHONE("audio_microphone"),
    LOG_TOUCH("log_touch"),
    LOG_SESSION("log_session"),
    VIDEO_CAMERA_FRONT("video_camera_front"),
    VIDEO_SCREEN("video_screen");

    private String name;

    FileType(String name) {
        this.name=name;
    }

    public String getName() {
        return name;
    }
}
