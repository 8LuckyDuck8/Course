package controller;

import Account.User;
import Main.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static service.UserService.addUser;

public class AddUser implements Initializable {

    @FXML
    TextField passwordfield;
    @FXML
    TextField loginfield;
    @FXML
    ComboBox userRole;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> positions = FXCollections.observableArrayList("admin", "user");
        userRole.setItems(positions);
    }

    public void BackButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/AdminUsers.fxml"));
        Main.primaryStage.setScene(new Scene(root));
        Main.primaryStage.show();
    }

    public void EnterButton(ActionEvent event) throws IOException {
        String empty = "";
        if (empty.equals(loginfield.getText()) || empty.equals(passwordfield.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка!");
            alert.setHeaderText(null);
            alert.setContentText("Поля должны быть заполнены!");

            alert.showAndWait();
            loginfield.clear();
            passwordfield.clear();
        } else {
            addUser(new User(loginfield.getText(), passwordfield.getText(), userRole.getSelectionModel().getSelectedItem().toString()));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Успешное выполнение!");
            alert.setHeaderText(null);
            alert.setContentText("Пользователь добавлен!");

            alert.showAndWait();

            Parent root = FXMLLoader.load(getClass().getResource("/FXML/AdminUsers.fxml"));
            Main.primaryStage.setScene(new Scene(root));
            Main.primaryStage.show();

        }
    }
}
