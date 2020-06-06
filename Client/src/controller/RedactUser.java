package controller;


import Account.User;
import Main.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

import static service.UserService.editPassword;

public class RedactUser {
    public static User acc;
    public TextField newPassword;

    public void createNewPassword(ActionEvent actionEvent) throws IOException {
        String enter = newPassword.getText();
        if (enter.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка!");
            alert.setHeaderText(null);
            alert.setContentText("Пустое поле!");
            alert.showAndWait();
        } else {
            editPassword(enter, acc.getLogin());
            editSuccess();
            BackButton(new ActionEvent());
        }
    }

    public void BackButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load ( getClass ().getResource ("/FXML/AdminUsers.fxml") );
        Main.primaryStage.setScene ( new Scene ( root ) );
        Main.primaryStage.show ();
    }

    public void editSuccess() {
        Alert alert = new Alert ( Alert.AlertType.INFORMATION );
        alert.setTitle ( null );
        alert.setHeaderText ( null );
        alert.setContentText ( "Редактирование произведено!" );
        alert.showAndWait ();
    }

}
