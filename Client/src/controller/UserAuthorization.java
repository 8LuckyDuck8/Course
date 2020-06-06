package controller;

import Main.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import service.UserService;

import java.io.IOException;

import static service.UserService.authorization;
import static service.UserService.userService;

public class UserAuthorization {
    @FXML
    PasswordField passwordfield;
    @FXML
    TextField loginfield;

    public void AuthorisationButton(ActionEvent event) throws IOException {
        String login = loginfield.getText();
        if (login.isEmpty() || passwordfield.getText().isEmpty()) {
            showAlert("Поля должны быть заполнены!");
            loginfield.clear();
            passwordfield.clear();
        } else {
            String answer = authorization(login, passwordfield.getText());
            UserService.setUserLogin(login);
            if("admin".equals(answer)) {
                Parent root = FXMLLoader.load(getClass().getResource("/FXML/AdminMenu.fxml"));
                Main.primaryStage.setScene(new Scene(root));
                Main.primaryStage.show();
            } else if ("user".equals(answer)) {
                Parent root = FXMLLoader.load(getClass().getResource("/FXML/UserMenu.fxml"));
                Main.primaryStage.setScene(new Scene(root));
                Main.primaryStage.show();
            } else {
                showAlert("Неверно введены логин или пароль!");
                loginfield.clear();
                passwordfield.clear();
            }
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Ошибка!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void BackButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Main.fxml"));
        Main.primaryStage.setScene(new Scene(root));
        Main.primaryStage.show();
    }
}
