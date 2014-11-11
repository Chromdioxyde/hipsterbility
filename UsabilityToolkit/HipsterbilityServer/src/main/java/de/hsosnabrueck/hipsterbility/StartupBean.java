package de.hsosnabrueck.hipsterbility;

import de.hsosnabrueck.hipsterbility.entities.GroupEntity;
import de.hsosnabrueck.hipsterbility.exceptions.DataAccessException;
import de.hsosnabrueck.hipsterbility.model.enums.FileType;
import de.hsosnabrueck.hipsterbility.rest.service.GroupService;
import de.hsosnabrueck.hipsterbility.rest.service.UserService;
import de.hsosnabrueck.hipsterbility.util.Utils;
import org.apache.commons.io.FileUtils;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Albert on 10.11.2014.
 *
 * Should be run after deployment by the container and perform checks for necessary data.
 */
@Singleton
@Startup
public class StartupBean {

    private GroupService groupService;
    private UserService userService;

    @Inject
    private transient Logger log;

    @Inject
    public StartupBean(GroupService groupService, UserService userService) {
        this.groupService = groupService;
        this.userService = userService;
    }

    @PostConstruct
    private void init(){
        log.log(Level.INFO, "PostConstruct");
        try {
            checkGroupsExist();
            checkAdministratorExists();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        createDirectories();
    }

    private void createDirectories() {
        log.log(Level.INFO, "Create directories");
        // Create empty directories for all file types, if they don't already exist.
        for(FileType fileType:FileType.values()){
            try {
                File file = new File(Utils.FILE_PATH + File.separator + fileType.getName() + File.separator);
                if(!(file.exists() && file.isDirectory())) {
                    log.log(Level.INFO, "Creating directory: {0}, Path: {1}", new Object[]{fileType.getName(), file.getAbsolutePath()});
                    FileUtils.forceMkdir(file);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkGroupsExist() throws DataAccessException {
        // double check for existing security roles (and trigger DDL if it didn't run already)
        if (null == groupService.read(GroupEntity.ADMIN)) {
            log.log(Level.WARNING, "Group {0} does not exist, attempting to create.", GroupEntity.ADMIN);
            groupService.create(new GroupEntity(GroupEntity.ADMIN));
        }
        if (null == groupService.read(GroupEntity.USER)) {
            log.log(Level.WARNING, "Group {0} does not exist, attempting to create.", GroupEntity.USER);
            groupService.create(new GroupEntity(GroupEntity.USER));
        }
    }

    private void checkAdministratorExists() throws DataAccessException {
        if(null == userService.findByName("admin")){
            log.log(Level.WARNING, "User {0} does not exist! Create an administrator in database.", "admin");
        }
    }

}
