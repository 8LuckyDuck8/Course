package controller;

import Main.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class AdminMenu {

    public void usersMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/AdminUsers.fxml"));
        Main.primaryStage.setScene(new Scene(root));
        Main.primaryStage.show();
    }

    public void goalsMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/AdminGoals.fxml"));
        Main.primaryStage.setScene(new Scene(root));
        Main.primaryStage.show();
    }

    public void marksMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/AdminUserGoals.fxml"));
        Main.primaryStage.setScene(new Scene(root));
        Main.primaryStage.show();
    }

    public void BackButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Main.fxml"));
        Main.primaryStage.setScene(new Scene(root));
        Main.primaryStage.show();
    }


}
