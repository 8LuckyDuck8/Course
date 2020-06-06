package controller;

import Account.Goal;
import Main.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

import static service.UserService.addGoal;

public class AddGoal {
    @FXML
    TextField goalfield;


    public void BackButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/AdminGoals.fxml"));
        Main.primaryStage.setScene(new Scene(root));
        Main.primaryStage.show();
    }

    public void EnterButton(ActionEvent event) throws IOException {
        String empty = "";
        if (empty.equals(goalfield.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка!");
            alert.setHeaderText(null);
            alert.setContentText("Поле должны быть заполнены!");

            alert.showAndWait();
            goalfield.clear();
        } else {
            addGoal(new Goal(goalfield.getText()));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Успешное выполнение!");
            alert.setHeaderText(null);
            alert.setContentText("Цель добавлена!");

            alert.showAndWait();

            Parent root = FXMLLoader.load(getClass().getResource("/FXML/AdminGoals.fxml"));
            Main.primaryStage.setScene(new Scene(root));
            Main.primaryStage.show();

        }
    }

}
