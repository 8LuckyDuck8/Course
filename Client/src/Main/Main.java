package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import static service.UserService.getInstanceUser;

public class Main extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Main.fxml"));
        primaryStage.setScene(new Scene(root, 600, 400));
        runStage(primaryStage);
    }

    public static void setScene(Scene scene) {
        primaryStage.setScene(scene);
    }

    public static void runStage(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("Мясокомбинат");
        primaryStage.setMaxHeight(600);
        primaryStage.setMaxWidth(400);
        primaryStage.getIcons().add(new Image("/CSS/icon.png"));

        getInstanceUser();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

