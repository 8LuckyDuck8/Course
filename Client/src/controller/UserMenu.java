package controller;

import Main.Main;
import dto.TaskDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static service.UserService.getAllMarksToUsers;
import static service.UserService.getMainGoal;

public class UserMenu {

    public void TaskButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Task.fxml"));
        Main.primaryStage.setScene(new Scene(root));
        Main.primaryStage.show();
    }

    public void GraphButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Graph.fxml"));
        Main.primaryStage.setScene(new Scene(root));
        Main.primaryStage.show();
    }

    //TODO: сделать создание отчета (сервер присылает данные, клиент формирует из них отчет)
    public void ReportButton(ActionEvent event) throws IOException {
        List<TaskDTO> taskDTOS = getAllMarksToUsers();
        String mainGoal = getMainGoal();

        XWPFDocument document = new XWPFDocument();

        XWPFParagraph reportParagraph = document.createParagraph();
        XWPFRun par = reportParagraph.createRun();
        par.setBold(true);
        par.setTextPosition(10);
        par.addBreak();
        par.setText("Текстовый отчет оценок пользователей");

        XWPFTable tableOne = document.createTable();
        XWPFTableRow tableOneRowOne = tableOne.getRow(0);
        createTableHeaders(document, tableOneRowOne);

        for (TaskDTO dto : taskDTOS) {
            XWPFTableRow newRow = tableOne.createRow();
            newRow.getCell(0).setText(dto.getUserLogin());
            newRow.getCell(1).setText(dto.getGoal().getName());
            newRow.getCell(2).setText(dto.getMark());
        }

        createAnswer(mainGoal, document);

        String reportName = "report" + java.time.LocalDate.now() + ".doc";
        FileOutputStream outStream = new FileOutputStream("..\\Files\\" + reportName);
        document.write(outStream);
        outStream.close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText("Отчет (" + reportName + ") был создан!");
        alert.showAndWait();
    }

    private void createTableHeaders(XWPFDocument document, XWPFTableRow tableOneRowOne) {
        tableOneRowOne.getCell(0);
        XWPFParagraph userPar = tableOneRowOne.getCell(0).addParagraph();
        XWPFRun userParOneRunOne = userPar.createRun();
        userParOneRunOne.setBold(true);
        userParOneRunOne.setText("Пользователь");

        XWPFParagraph goalPar = tableOneRowOne.addNewTableCell().addParagraph();
        XWPFRun goalParOneRunOne = goalPar.createRun();
        goalParOneRunOne.setBold(true);
        goalParOneRunOne.setText("Цель");

        XWPFParagraph markPar = tableOneRowOne.addNewTableCell().addParagraph();
        XWPFRun markParOneRunOne = markPar.createRun();
        markParOneRunOne.setBold(true);
        markParOneRunOne.setText("Оценка");
    }

    private void createAnswer(String mainGoal, XWPFDocument document) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun paragraphOneRunOne = paragraph.createRun();
        paragraphOneRunOne.setBold(true);
        paragraphOneRunOne.setItalic(true);
        paragraphOneRunOne.addBreak();
        paragraphOneRunOne.setText("Приоритетная цель: " + mainGoal);
    }

    public void ExitButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Main.fxml"));
        Main.primaryStage.setScene(new Scene(root));
        Main.primaryStage.show();
    }
}
