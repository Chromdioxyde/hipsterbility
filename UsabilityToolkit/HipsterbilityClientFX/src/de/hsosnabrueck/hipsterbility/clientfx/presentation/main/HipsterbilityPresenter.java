package de.hsosnabrueck.hipsterbility.clientfx.presentation.main;

import de.hsosnabrueck.hipsterbility.clientfx.presentation.usermanagement.UserView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Albert on 04.10.2014.
 */
public class HipsterbilityPresenter implements Initializable{

    @FXML
    BorderPane mainpane;

    @FXML
    Button btnUsers;

    @FXML
    public void openUsers(){
        UserView userCreationView = new UserView();
        Parent view = userCreationView.getView();
        mainpane.setCenter(view);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        LoginView loginView = new LoginView();
//        Parent view = loginView.getView();
//        mainpane.setCenter(view);
    }
}
