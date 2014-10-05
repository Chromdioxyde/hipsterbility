package de.hsosnabrueck.hipsterbility.entities.files;

import javax.persistence.*;

/**
 * Created by Albert on 25.09.2014.
 */

@Entity
@DiscriminatorValue("SCREENSHOT")
public class ScreenshotFileEntity extends FileEntity{

}
