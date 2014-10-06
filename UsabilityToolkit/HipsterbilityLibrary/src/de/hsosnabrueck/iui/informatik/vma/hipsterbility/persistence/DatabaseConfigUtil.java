package de.hsosnabrueck.iui.informatik.vma.hipsterbility.persistence;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;
import de.hsosnabrueck.hipsterbility.entities.TaskEntity;
import de.hsosnabrueck.hipsterbility.entities.TestEntity;
import de.hsosnabrueck.hipsterbility.entities.TestSessionEntity;
import de.hsosnabrueck.hipsterbility.entities.files.AudioFileEntity;
import de.hsosnabrueck.hipsterbility.entities.files.LogFileEntity;
import de.hsosnabrueck.hipsterbility.entities.files.ScreenshotFileEntity;
import de.hsosnabrueck.hipsterbility.entities.files.VideoFileEntity;

import java.io.File;


/**
 * Can be run on dev-machine to create the config file for annotated classes.
 *
 */
public class DatabaseConfigUtil extends OrmLiteConfigUtil {
    private static final String PATH_TO_CONFIG_FILE = System.getProperty("user.dir") + File.separator
            + "HipsterbilityLibrary" + File.separator
            + "res" + File.separator
            + "raw" + File.separator;
        private static final Class<?>[] classes = new Class[] {
                TestEntity.class,
                TaskEntity.class,
                TestSessionEntity.class,
                AudioFileEntity.class,
                VideoFileEntity.class,
                ScreenshotFileEntity.class,
                LogFileEntity.class
        };
        public static void main(String[] args) throws Exception {
            writeConfigFile(new File(PATH_TO_CONFIG_FILE + "ormlite_config.txt"), classes);
        }
}