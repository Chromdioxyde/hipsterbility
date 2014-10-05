package de.hsosnabrueck.hipsterbility.clientfx.presentation.login;

import de.hsosnabrueck.hipsterbility.clientfx.HipsterbilityClientApp;
import de.hsosnabrueck.hipsterbility.clientfx.Settings;
import de.hsosnabrueck.hipsterbility.clientfx.rest.RestClientHelper;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Albert on 03.10.2014.
 */
public class LoginPresenter implements Initializable {

    @Inject
    Settings settings;
    @Inject
    RestClientHelper client;
    @FXML
    TextField server;
    @FXML
    TextField port;
    IntegerProperty portNumber;
    @FXML
    CheckBox usessl;
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    CheckBox savepassword;
    @FXML
    TitledPane tpLogin;
    @FXML
    TitledPane tpServer;
    @FXML
    Accordion container;
    @FXML
    Label status;
    @FXML
    Label serverStatus;

    private Stage stage;

    @FXML
    public void ping(){
        try {
            if(client.pingServer()){
                printSuccess(serverStatus, "Server Online");
            }
        } catch (Exception e) {
            printError(serverStatus, "Error: " + e.getMessage());
        }
    }

    @FXML
    public void login(){
        try {
            if(client.checkLogin()){
                printSuccess(status, "Login sucessfull");
                HipsterbilityClientApp.launchMainView();
            } else {
                printError(status, "Wrong username and/or password");
            }
        } catch (Exception e) {
            printError(status,e.getMessage());
        }
    }

    private void printSuccess(Label lbl, String msg){
        lbl.setTextFill(Color.GREEN);
        lbl.setText(msg);
    }

    private void printError(Label lbl, String msg){
        lbl.setTextFill(Color.RED);
        lbl.setText(msg);
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: improve binding for getting values from stored settings
        container.setExpandedPane(tpLogin); // Expand login pane

        // Converter for converting strings to integer
        NumberStringConverter converter = new NumberStringConverter();
        // Property to store the converted integer
        portNumber = new SimpleIntegerProperty();

        // Set control values to stored settings from preferences
        username.setText(settings.getUser());
        password.setText(settings.getPassword());
        server.setText(settings.getServer());
        savepassword.setSelected(settings.getSavePassword());
        usessl.setSelected(settings.getSsl());
        port.setText(String.valueOf(settings.getPort()));
        portNumber.setValue(settings.getPort());

        /*
        Bind control properties to settings to register changes.
        One way binding is sufficient, because settings don't change in background.
         */
        settings.userProperty().bind(username.textProperty());
        settings.passwordProperty().bind(password.textProperty());
        settings.serverProperty().bind(server.textProperty());
        settings.savePasswordProperty().bind(savepassword.selectedProperty());
        settings.sslProperty().bind(usessl.selectedProperty());
        settings.portProperty().bind(portNumber);

        // Convert string values to integer without throwing exceptions
        port.textProperty().addListener(e -> {
            try {
                Number n = converter.fromString(port.getText());
                // if textfield is changed to empty, n becomes null
                if (n != null) {
                    portNumber.setValue(n);
                }
                printSuccess(serverStatus,"");
            } catch (Exception ex) {
                printError(serverStatus, "Please input a numeric value for port");
            }
        });
    }

}
