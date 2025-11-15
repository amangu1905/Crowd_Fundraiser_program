package crowdfund.controller;

import crowdfund.dao.UserDAO;
import crowdfund.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {

    @FXML private TextField nameField, emailField, roleField;
    @FXML private PasswordField passwordField;

    public void register() {
        UserDAO dao = new UserDAO();
        User u = new User(0, nameField.getText(), emailField.getText(),
                passwordField.getText(), roleField.getText());
        dao.register(u);
        openLogin();
    }

    public void openLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/crowdfund/fxml/login.fxml"));
            Stage s = (Stage) nameField.getScene().getWindow();
            s.setScene(new Scene(loader.load()));
        } catch (Exception e) { e.printStackTrace(); }
    }
}
