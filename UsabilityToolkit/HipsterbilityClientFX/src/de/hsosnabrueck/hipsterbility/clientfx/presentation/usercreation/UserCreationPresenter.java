package de.hsosnabrueck.hipsterbility.clientfx.presentation.usercreation;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import de.hsosnabrueck.hipsterbility.clientfx.rest.DataAccess;
import de.hsosnabrueck.hipsterbility.clientfx.rest.DataAccessException;
import de.hsosnabrueck.hipsterbility.entities.UserEntity;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
public class UserCreationPresenter implements Initializable{

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    @Inject
    private DataAccess dataAccess;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private TextField email;

    @FXML
    private TextField firstname;

    @FXML
    private TextField lastname;

    @FXML
    private ComboBox<String> locale;

    @FXML
    private CheckBox active;

    @FXML
    private Button create;


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
        try {
            dataAccess.createUser(user);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> localeList = FXCollections.observableArrayList(Locale.getISOLanguages());
        locale.setItems(localeList);
        addProperties();
        addControlListener();
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
