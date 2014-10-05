package de.hsosnabrueck.hipsterbility.clientfx;

import de.hsosnabrueck.hipsterbility.clientfx.presentation.login.LoginView;
import de.hsosnabrueck.hipsterbility.clientfx.presentation.main.HipsterbilityView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Albert on 01.10.2014.
 */
public class HipsterbilityClientApp extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        LoginView loginView = new LoginView();
        Scene scene = new Scene(loginView.getView());
        primaryStage.setTitle("Hipsterbility Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void launchMainView(){
        //TODO:  improve by not using static methods and fields
        stage.close();
        Stage newStage = new Stage();
        HipsterbilityView hipsterbilityView = new HipsterbilityView();
        Scene scene = new Scene(hipsterbilityView.getView());
        newStage.setTitle("Hipsterbility");
        newStage.setScene(scene);
        newStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
