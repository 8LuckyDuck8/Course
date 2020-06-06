package controller;

import Main.Main;
import dto.TaskDTO;
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
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static service.UserService.*;

public class Task implements Initializable {

    public TableView<TaskDTO> task;
    public TableColumn<TaskDTO, String> goal;
    public TableColumn<TaskDTO, String> mark;

    private Integer marksCount;

    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<TaskDTO> taskDTOS = null;
        try {
            taskDTOS = FXCollections.observableArrayList(getAllMarksByUser());
            marksCount = getMarksCount();
        } catch (IOException e) {
            e.printStackTrace();
        }
        goal.setCellValueFactory(new PropertyValueFactory<TaskDTO, String>("goal"));
        mark.setCellValueFactory(new PropertyValueFactory<TaskDTO, String>("mark"));
        task.setItems(taskDTOS);

        task.setEditable(true);
        mark.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    public void changeMark(TableColumn.CellEditEvent event) throws IOException {
        TaskDTO selectedTask = task.getSelectionModel().getSelectedItem();
        String newValue = event.getNewValue().toString();
        if (newValue.matches("(10|[0-" + marksCount + "]?)")) {
            selectedTask.setMark(newValue);
        } else {
            showAlert("Нужно ввести оценку от 0 до " + marksCount + "!");
            selectedTask.setMark(event.getOldValue().toString());
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/Task.fxml"));
            Main.primaryStage.setScene(new Scene(root));
            Main.primaryStage.show();
        }
    }

    public void SaveButton(ActionEvent event) throws IOException {
        ObservableList<TaskDTO> taskDTOS = task.getItems();
        if (checkEmptyMarks(taskDTOS)) {
            showAlert("Все поля должны быть заполнены!");
        } else {
            saveAllMarks(taskDTOS);
            task.refresh();
            saveSuccess();
        }
    }

    private void saveSuccess() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Успешное выполнение!");
        alert.setHeaderText(null);
        alert.setContentText("Данные сохранены");

        alert.showAndWait();
    }

    private boolean checkEmptyMarks(ObservableList<TaskDTO> taskDTOS) {
        for (TaskDTO taskDTO : taskDTOS) {
            if (taskDTO.getMark() == null) {
                return true;
            }
        }
        return false;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ошибка!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void BackButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/UserMenu.fxml"));
        Main.primaryStage.setScene(new Scene(root));
        Main.primaryStage.show();
    }
}
