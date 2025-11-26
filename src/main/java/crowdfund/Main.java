package crowdfund;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Load the login.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/crowdfund/fxml/login.fxml"));
        // Create a scene with the loaded FXML
        stage.setScene(new Scene(loader.load()));
        // Set the title of the stage
        stage.setTitle("CrowdFunding System");
        // Show the stage
        stage.show();
    }
    public static void main(String[] args) {
        // Launch the JavaFX application
        launch();
    }
}
