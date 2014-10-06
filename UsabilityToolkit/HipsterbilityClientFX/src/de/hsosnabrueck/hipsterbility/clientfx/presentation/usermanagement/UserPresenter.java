package de.hsosnabrueck.hipsterbility.clientfx.presentation.usermanagement;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import de.hsosnabrueck.hipsterbility.clientfx.rest.DataAccess;
import de.hsosnabrueck.hipsterbility.clientfx.rest.DataAccessException;
import de.hsosnabrueck.hipsterbility.entities.DeviceEntity;
import de.hsosnabrueck.hipsterbility.entities.GroupEntity;
import de.hsosnabrueck.hipsterbility.entities.UserEntity;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import javax.inject.Inject;
import java.net.URL;
import java.util.*;
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
    @FXML ComboBox<String> group;
    @FXML TableColumn<UserEntity, String> colUsername;
    @FXML TableColumn<UserEntity, String> colFirst;
    @FXML TableColumn<UserEntity, String> colLast;
    @FXML TableColumn<UserEntity, String> colEmail;
    @FXML TableColumn<UserEntity, Boolean> colActive;
    @FXML TableView<UserEntity> tblUsers;
    @FXML ListView<GroupEntity> lstGroups;
    @FXML ListView<DeviceEntity> lstDevices;




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
        UserEntity user = new UserEntity();
        user.setUsername(username.getText());
        String pwhash = Hashing.sha256()
                .hashString(password.getText(), Charsets.UTF_8).toString();
        user.setPassword(pwhash);
        user.setEmail(email.getText());
        user.setFirstname(firstname.getText());
        user.setLastname(lastname.getText());
        user.setActive(active.isSelected());
        user.setLocale(Locale.forLanguageTag(locale.getValue()));
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setName(group.getValue().toUpperCase());
        groupEntity.setUser(user);
        try {

            dataAccess.createUser(user, groupEntity);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> localeList = FXCollections.observableArrayList(Locale.getISOLanguages());
        locale.setItems(localeList);
        ObservableList<String> rolesList = FXCollections.observableArrayList();
        rolesList.add(GroupEntity.USER);
        rolesList.add(GroupEntity.ADMIN);
        group.setItems(rolesList);
        addProperties();
        addControlListener();
        addTableColFactories();
        tblUsers.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            //Check whether item is selected and set value of selected item to Label
            if (tblUsers.getSelectionModel().getSelectedItem() != null) {
                displayDetails(observableValue.getValue());
            }
        });
        showUsersIntable();
        lstDevices = new ListView<>();
        lstGroups = new ListView<>();
    }

    private void displayDetails(UserEntity value) {
        username.setText(value.getUsername());
        firstname.setText(value.getInviteCode());
        lastname.setText(value.getLastname());
        email.setText(value.getEmail());
        lastname.setText(value.getLastname());
        active.setSelected(value.isActive());
        if(null!= value.getLocale()) locale.setValue(value.getLocale().getISO3Language());
//        if(null!= value.getGroups()) lstGroups.setItems(FXCollections.observableList(value.getGroups()));
        if(null!= value.getDevices()) lstDevices.setItems(FXCollections.observableList(value.getDevices()));
    }

    private void addTableColFactories() {
        colUsername.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        colFirst.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstname()));
        colLast.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastname()));
        colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        colActive.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isActive()));
    }

    private void showUsersIntable() {
        List<UserEntity> users = dataAccess.getUsers();
        ObservableList<UserEntity> userList = FXCollections.observableList(users);
        tblUsers.setItems(userList);
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
