package crowdfund.controller;

import crowdfund.dao.UserDAO; // Imports the UserDAO class, which handles database interactions for User objects.
import crowdfund.model.User; // Imports the User class, representing a user in the application.
import javafx.fxml.FXML; // Imports FXML annotations for injecting UI elements.
import javafx.fxml.FXMLLoader; // Imports FXMLLoader for loading FXML files.
import javafx.scene.Scene; // Imports Scene for creating a scene in the JavaFX application.
import javafx.scene.control.PasswordField; // Imports PasswordField for handling password input.
import javafx.scene.control.TextField; // Imports TextField for handling text input.
import javafx.stage.Stage; // Imports Stage for managing the application window.

public class LoginController {

    @FXML private TextField emailField; // Injects the email TextField from the FXML file.
    @FXML private PasswordField passwordField; // Injects the password PasswordField from the FXML file.

    public void login() {
        UserDAO dao = new UserDAO(); // Creates a new UserDAO object to interact with the user database.
        User u = dao.login(emailField.getText(), passwordField.getText()); // Attempts to log in the user using the provided email and password.

        if (u != null) { // Checks if the login was successful (i.e., a User object was returned).
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/crowdfund/fxml/dashboard.fxml")); // Creates an FXMLLoader to load the dashboard FXML file.
                Stage stage = (Stage) emailField.getScene().getWindow(); // Gets the current stage from the email field.
                stage.setScene(new Scene(loader.load())); // Sets the scene of the stage to the dashboard scene.
            } catch (Exception e) { e.printStackTrace(); } // Catches any exceptions that occur during loading the dashboard and prints the stack trace.
        }
    }

    public void openRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/crowdfund/fxml/register.fxml")); // Creates an FXMLLoader to load the register FXML file.
            Stage stage = (Stage) emailField.getScene().getWindow(); // Gets the current stage from the email field.
            stage.setScene(new Scene(loader.load())); // Sets the scene of the stage to the register scene.
        } catch (Exception e) { e.printStackTrace(); } // Catches any exceptions that occur during loading the register page and prints the stack trace.
    }
}
