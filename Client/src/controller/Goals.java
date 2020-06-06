package controller;

import Account.Goal;
import Main.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static service.UserService.getAllGoals;

public class Goals implements Initializable {
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

    public void ExitButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Task.fxml"));
        Main.primaryStage.setScene(new Scene(root));
        Main.primaryStage.show();
    }
}
