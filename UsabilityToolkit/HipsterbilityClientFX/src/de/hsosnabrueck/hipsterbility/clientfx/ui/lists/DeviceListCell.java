package de.hsosnabrueck.hipsterbility.clientfx.ui.lists;

import de.hsosnabrueck.hipsterbility.clientfx.helper.IconHelper;
import de.hsosnabrueck.hipsterbility.clientfx.model.Device;
import de.hsosnabrueck.hipsterbility.model.enums.DeviceClass;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;


/**
 * Created by Albert on 06.11.2014.
 */
public class DeviceListCell extends ListCell<Device> {
//    private static final String CACHE_LIST_FOUND_CLASS = "cache-list-found";
//    private static final String CACHE_LIST_NOT_FOUND_CLASS = "cache-list-not-found";
//    private static final String CACHE_LIST_NAME_CLASS = "cache-list-name";
//    private static final String CACHE_LIST_DT_CLASS = "cache-list-dt";
//    private static final String CACHE_LIST_ICON_CLASS = "cache-list-icon";
    private static final String FONT_AWESOME = "FontAwesome";
    private static final int LOGO_WIDTH = 28;
    private static final int DEVICE_ICON_FONT_SIZE = 48;

    GlyphFont fontAwesome = GlyphFontRegistry.font("FontAwesome");

    private GridPane grid = new GridPane();
    private ImageView icon = new ImageView();
    private Label deviceClass = new Label();
    private Label name = new Label();
    private Label customName = new Label();
    private Label uuid = new Label();
    private Label version = new Label();

    public DeviceListCell() {
        configureGrid();
        configureIcons();
//        configureName();
//        configureDifficultyTerrain();
        addControlsToGrid();
        System.out.println(this.getClass().getName()); // TODO remove
    }

    private void configureGrid() {
        grid.setHgap(10);
        grid.setVgap(4);
        grid.setPadding(new Insets(0, 10, 0, 10));
    }

    private void configureIcons() {
        icon.setFitWidth(LOGO_WIDTH);
        icon.setPreserveRatio(true);
//        deviceClass.getStyleClass().add(CACHE_LIST_ICON_CLASS);

    }

//    private void configureName() {
//        name.getStyleClass().add(CACHE_LIST_NAME_CLASS);
//    }

//    private void configureDifficultyTerrain() {
//        dt.getStyleClass().add(CACHE_LIST_DT_CLASS);
//    }

    private void addControlsToGrid() {
        grid.add(icon, 0, 0, 1, 2);
        grid.add(version, 0, 3);
        grid.add(deviceClass, 1, 0, 1, 2);
        grid.add(name, 2, 0);
        grid.add(customName, 2, 1);
        grid.add(uuid, 1, 3, 3, 1);
    }

    @Override
    public void updateItem(Device device, boolean empty) {
        super.updateItem(device, empty);
        if (empty) {
            clearContent();
        }
        if(null != device) {
            addContent(device);
        }
    }

    private void clearContent() {
        setText(null);
        setGraphic(null);
    }

    private void addContent(Device device) {
        setText(null);
        switch (device.getPlatform()){
            case ANDROID: icon.setImage(IconHelper.getAndroidLogo()); break;
        }
        deviceClass.setGraphic(createDeviceIcon(device.getDeviceClass()));
        name.setText(device.getName());
        customName.setText(device.getCustomName());
        uuid.setText(device.getUuid());
        version.setText(device.getOsVersion());
        setGraphic(grid);
    }

    private Glyph createDeviceIcon(DeviceClass devClass) {
        FontAwesome.Glyph icon;
        switch (devClass) {
            case TABLET:
                icon = FontAwesome.Glyph.TABLET;
                break;
            case PHONE:
                icon = FontAwesome.Glyph.MOBILE_PHONE;
                break;
            case PHABLET:
                icon = FontAwesome.Glyph.MOBILE;
                break;
            default:
                icon = FontAwesome.Glyph.QUESTION;
        }
        Glyph deviceIcon = fontAwesome.create(icon);
        deviceIcon.setFontSize(DEVICE_ICON_FONT_SIZE);
        return deviceIcon;
    }

//    private void setStyleClassDependingOnFoundState(Cache cache) {
//        if (CacheUtils.hasUserFoundCache(cache, new Long(3906456))) {
//            addClasses(this, CACHE_LIST_FOUND_CLASS);
//            removeClasses(this, CACHE_LIST_NOT_FOUND_CLASS);
//        } else {
//            addClasses(this, CACHE_LIST_NOT_FOUND_CLASS);
//            removeClasses(this, CACHE_LIST_FOUND_CLASS);
//        }
//    }
}
