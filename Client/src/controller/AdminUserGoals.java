package controller;

import Main.Main;
import dto.TaskDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static service.UserService.getAllMarksToUsers;

public class AdminUserGoals implements Initializable {
    public TableView<TaskDTO> goals;
    public TableColumn<TaskDTO, String> user;
    public TableColumn<TaskDTO, String> goalName;
    public TableColumn<TaskDTO, String> mark;

    @FXML
    public TextField searchField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<TaskDTO> allMarksToUsers = getAllMarksToUsers();
            ObservableList<TaskDTO> allMarks = FXCollections.observableArrayList(allMarksToUsers);

            user.setCellValueFactory(new PropertyValueFactory<TaskDTO, String>("userLogin"));
            goalName.setCellValueFactory(new PropertyValueFactory<TaskDTO, String>("goal"));
            mark.setCellValueFactory(new PropertyValueFactory<TaskDTO, String>("mark"));

            FilteredList<TaskDTO> filteredData = new FilteredList<>(allMarks, b -> true);
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(mark -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    return mark.getUserLogin().toLowerCase().contains(lowerCaseFilter) ||
                            mark.getGoal().getName().toLowerCase().contains(lowerCaseFilter) ||
                            String.valueOf(mark.getMark()).contains(lowerCaseFilter);
                });
            });
            SortedList<TaskDTO> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(goals.comparatorProperty());

            goals.setItems(sortedData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void BackButton(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/AdminMenu.fxml"));
        Main.primaryStage.setScene(new Scene(root));
        Main.primaryStage.show();
    }
}
