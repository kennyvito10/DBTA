package sample;

//All the necessary imports

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


//main class
public class Main extends Application {
    //declaration for stage and anchorpane
    private Stage stage;
    private AnchorPane layout2;



    //the table view function  for showing notes data

    public void tableview() {
        try {
            // Load person overview.
            FXMLLoader run = new FXMLLoader();
            run.setLocation(Main.class.getResource("menu.fxml"));
            layout2 = (AnchorPane) run.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(layout2);
            stage.setScene(scene);
            stage.show();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //start function of the layout
    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        this.stage.setTitle("Pho Street");



        tableview();
    }


    //calling the main class and return stage class
    public Stage getPrimaryStage() {
        return stage;
    }

    public static void main(String[] args) {
        launch(args);


    }
}