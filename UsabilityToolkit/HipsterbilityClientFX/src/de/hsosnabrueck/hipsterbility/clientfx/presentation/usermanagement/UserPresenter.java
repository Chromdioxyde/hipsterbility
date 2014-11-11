package de.hsosnabrueck.hipsterbility.clientfx.presentation.usermanagement;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import de.hsosnabrueck.hipsterbility.clientfx.model.Device;
import de.hsosnabrueck.hipsterbility.clientfx.model.Group;
import de.hsosnabrueck.hipsterbility.clientfx.model.User;
import de.hsosnabrueck.hipsterbility.clientfx.rest.DataAccess;
import de.hsosnabrueck.hipsterbility.clientfx.rest.DataAccessException;
import de.hsosnabrueck.hipsterbility.clientfx.ui.lists.DeviceListCell;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

import javax.inject.Inject;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * Created by Albert on 01.10.2014.
 */
public class UserPresenter implements Initializable{

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    @Inject private DataAccess dataAccess;
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private TextField email;
    @FXML private TextField firstname;
    @FXML private TextField lastname;
    @FXML private ComboBox<String> locale;
    @FXML private CheckBox active;
    @FXML private Button create;
    @FXML private ComboBox<String> group;
    @FXML private TableColumn<User, String> colUsername;
    @FXML private TableColumn<User, String> colFirst;
    @FXML private TableColumn<User, String> colLast;
    @FXML private TableColumn<User, String> colEmail;
    @FXML private TableColumn<User, Boolean> colActive;
    @FXML private TableView<User> tblUsers;
    @FXML private ListView<String> lstGroups; // TODO: change to Group
    @FXML private ListView<Device> lstDevices;

    private ObservableList<Device> devicesForUserList;




    @FXML
    private void handleCreateButtonAction() {
        // Button was clicked, do something...
        preValidate();
        createUser();
    }

    private void preValidate() {
//        TODO: validate input
    }


    private void createUser() {
        User user = new User();
        user.setUsername(username.getText());
        String pwhash = Hashing.sha256()
                .hashString(password.getText(), Charsets.UTF_8).toString();
        user.setPassword(pwhash);
        user.setEmail(email.getText());
        user.setFirstname(firstname.getText());
        user.setLastname(lastname.getText());
        user.setActive(active.isSelected());
        user.setLocale(Locale.forLanguageTag(locale.getValue()));
        Group groupEntity = new Group();
        groupEntity.setName(group.getValue().toUpperCase());
        groupEntity.getUsers().add(user);
        try {

            dataAccess.createUser(user, groupEntity);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createListViews();
        ObservableList<String> localeList = FXCollections.observableArrayList(Locale.getISOLanguages());
        locale.setItems(localeList);
        ObservableList<String> rolesList = FXCollections.observableArrayList();
        rolesList.add(Group.USER);
        rolesList.add(Group.ADMIN);
        group.setItems(rolesList);
        addProperties();
        addControlListener();
        addTableColFactories();

        showUsersInTable();
        tblUsers.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            //Check whether item is selected and set value of selected item to Label
            if (tblUsers.getSelectionModel().getSelectedItem() != null) {
                displayDetails(observableValue.getValue());
            }
        });

    }

    private void createListViews(){
        Label placeholder = new Label("no content");
        devicesForUserList = FXCollections.observableArrayList();
//        lstDevices = new ListView<>(devicesForUserList);
        lstDevices.setPlaceholder(placeholder);
        lstDevices.setCellFactory(param -> new DeviceListCell());
        lstDevices.setItems(devicesForUserList);

        ObservableList<String> data = FXCollections.observableArrayList(
                "chocolate", "salmon", "gold", "coral", "darkorchid",
                "darkgoldenrod", "lightsalmon", "black", "rosybrown", "blue",
                "blueviolet", "brown");
//        lstGroups = new ListView<>(data);
        lstGroups.setItems(data);
//        lstGroups.setItems(data); //TODO: Replace by real groups
    }

    private void displayDetails(User user) {
        username.setText(user.getUsername());
        firstname.setText(user.getInviteCode());
        lastname.setText(user.getLastname());
        email.setText(user.getEmail());
        lastname.setText(user.getLastname());
        active.setSelected(user.getActive());
        if(null != user.getLocale()) locale.setValue(user.getLocale().getLanguage());
//        if(null!= user.getGroups()) lstGroups.setItems(FXCollections.observableList(user.getGroups()));
        // clear list
        devicesForUserList.removeAll(devicesForUserList);
        if(null != user.getDevices()){
            System.out.println("Number of Devices: " + user.getDevices().size()); // TODO remove
            devicesForUserList.addAll(user.getDevices());
//            lstDevices.getItems().addAll(user.getDevices());
        }
    }

    private void addTableColFactories() {
        colUsername.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        colFirst.setCellValueFactory(cellData -> cellData.getValue().firstnameProperty());
        colLast.setCellValueFactory(cellData -> cellData.getValue().lastnameProperty());
        colEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        colActive.setCellValueFactory(cellData -> cellData.getValue().activeProperty());
        colActive.setCellFactory(CheckBoxTableCell.forTableColumn(colActive));
    }

    private void showUsersInTable() {
        try {
            tblUsers.setItems(dataAccess.getUsers());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }


    private void addProperties() {
        BooleanProperty usernameEntered = new SimpleBooleanProperty();
        usernameEntered.bind(username.textProperty().isNotEmpty());
        BooleanProperty passwordEntered = new SimpleBooleanProperty();
        passwordEntered.bind(password.textProperty().isNotEmpty());
        BooleanProperty emailEntered = new SimpleBooleanProperty();
        emailEntered.bind(email.textProperty().isNotEmpty());
        create.disableProperty().bind(usernameEntered.and(passwordEntered.and(emailEntered)).not());
    }

    private void addControlListener() {
        DropShadow borderGlow = new DropShadow();
        borderGlow.setColor(Color.RED);
        borderGlow.setHeight(20);
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        username.textProperty().addListener((observable, oldvalue, newValue)->{
            if((newValue.length() < 6 || newValue.length() > 32)){
                username.setEffect(borderGlow);
            } else {
                username.setEffect(null);
            }
        });
        password.textProperty().addListener((observable, oldvalue, newValue)->{
            if((newValue.length() < 6 || newValue.length() > 32)) {
                password.setEffect(borderGlow);
            } else {
                password.setEffect(null);
            }
        });
        email.textProperty().addListener((observable, oldvalue, newValue)->{
            if(!pattern.matcher(newValue).matches()) {
                email.setEffect(borderGlow);
            } else {
                email.setEffect(null);
            }
        });
    }
}
