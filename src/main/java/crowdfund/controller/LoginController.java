package crowdfund.controller;

import crowdfund.dao.UserDAO;
import crowdfund.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    public void login() {
        UserDAO dao = new UserDAO();
        User u = dao.login(emailField.getText(), passwordField.getText());

        if (u != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/crowdfund/fxml/dashboard.fxml"));
                Stage stage = (Stage) emailField.getScene().getWindow();
                stage.setScene(new Scene(loader.load()));
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    public void openRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/crowdfund/fxml/register.fxml"));
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
        } catch (Exception e) { e.printStackTrace(); }
    }
}
