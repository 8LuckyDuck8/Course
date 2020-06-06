package Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class MainC {

    public void AdminButton(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/UserAuthorization.fxml"));
        Main.primaryStage.setScene(new Scene(root));
        Main.primaryStage.show();
    }
}
