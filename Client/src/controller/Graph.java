package controller;

import Main.Main;
import dto.GoalToWeightDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static service.UserService.getGoalsWeight;
import static service.UserService.getMainGoal;

public class Graph implements Initializable {
    public BarChart chart;
    public CategoryAxis x;
    public NumberAxis y;
    public Label mainGoal;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            List<GoalToWeightDTO> goalsWeight = getGoalsWeight();
            XYChart.Series setOfData = new XYChart.Series<>();
            for (GoalToWeightDTO entry : goalsWeight) {
                setOfData.getData().add(new XYChart.Data(entry.getGoal().getName(), entry.getWeight()));
            }
            chart.getData().addAll(setOfData);
            mainGoal.setText(getMainGoal());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ExitButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/UserMenu.fxml"));
        Main.primaryStage.setScene(new Scene(root));
        Main.primaryStage.show();
    }
}
