package de.hsosnabrueck.iui.informatik.vma.hipsterbility.persistence;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.model.FileEntity;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.model.TaskEntity;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.model.TestEntity;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.model.TestSessionEntity;

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
                FileEntity.class,
        };
        public static void main(String[] args) throws Exception {
            writeConfigFile(new File(PATH_TO_CONFIG_FILE + "ormlite_config.txt"), classes);
        }
}