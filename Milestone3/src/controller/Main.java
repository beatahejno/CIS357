package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Library library = new Library();


        //pass the generated data to the main menu page
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenuPage.fxml"));
        Parent root = loader.load();
        MainMenuPage controller = loader.getController();
        controller.library = library;
        primaryStage.setTitle("Library Management System");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
