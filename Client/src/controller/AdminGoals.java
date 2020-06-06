package controller;

import Account.Goal;
import Account.User;
import Main.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static service.UserService.*;

public class AdminGoals implements Initializable {
    public TableView<Goal> goal;
    public TableColumn<Goal, String> goalName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Goal> nAccs = null;
        try {
            nAccs = FXCollections.observableArrayList(getAllGoals());
        } catch (IOException e) {
            e.printStackTrace();
        }
        goalName.setCellValueFactory(new PropertyValueFactory<Goal, String>("name"));
        goal.setItems(nAccs);
    }

    public void AddButton(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/AddGoal.fxml"));
        Main.primaryStage.setScene(new Scene(root));
        Main.primaryStage.show();
    }


    public void DeleteGoalButton(ActionEvent actionEvent) {
        Goal selectedAcc = goal.getSelectionModel().getSelectedItem();
        if (selectedAcc == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка!");
            alert.setHeaderText(null);
            alert.setContentText("Нужно выбрать запись!");
            alert.showAndWait();
        }
        else {
            deleteGoal(selectedAcc.getName());
            goal.getItems().removeAll(selectedAcc);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Цель удалена!");
            alert.showAndWait();
        }
    }


    public void BackButton(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/AdminMenu.fxml"));
        Main.primaryStage.setScene(new Scene(root));
        Main.primaryStage.show();
    }
}
