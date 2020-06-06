package controller;

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

import static service.UserService.deleteUser;
import static service.UserService.getAllAccs;

public class AdminUsersMenu implements Initializable {
    public TableView<User> client;
    public TableColumn<User, String> userLogin;
    public TableColumn<User, String> userPassword;
    public TableColumn<User, String> userRole;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<User> nAccs = null;
        try {
            nAccs = FXCollections.observableArrayList(getAllAccs());
        } catch (IOException e) {
            e.printStackTrace();
        }
        userLogin.setCellValueFactory(new PropertyValueFactory<User, String>("login"));
        userPassword.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        userRole.setCellValueFactory(new PropertyValueFactory<User, String>("role"));
        client.setItems(nAccs);
    }

    public void AddButton(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/AddUser.fxml"));
        Main.primaryStage.setScene(new Scene(root));
        Main.primaryStage.show();
    }

    public void RedactUserButton(ActionEvent event)throws IOException{
        User selectedAcc = client.getSelectionModel().getSelectedItem();
        if (selectedAcc == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка!");
            alert.setHeaderText(null);
            alert.setContentText("Нужно выбрать запись!");
            alert.showAndWait();
        }
        else {
            RedactUser.acc = selectedAcc;
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/Redact.fxml"));
            Main.primaryStage.setScene(new Scene(root));
            Main.primaryStage.show();
        }
    }

    public void DeleteUserButton(ActionEvent actionEvent) {
        User selectedAcc = client.getSelectionModel().getSelectedItem();
        if (selectedAcc == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка!");
            alert.setHeaderText(null);
            alert.setContentText("Нужно выбрать запись!");
            alert.showAndWait();
        }
        else {
            deleteUser(selectedAcc.getLogin());
            client.getItems().removeAll(selectedAcc);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Пользователь удалён!");
            alert.showAndWait();
        }
    }


    public void BackButton(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/AdminMenu.fxml"));
        Main.primaryStage.setScene(new Scene(root));
        Main.primaryStage.show();
    }
}
