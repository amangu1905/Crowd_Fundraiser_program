package crowdfund.controller;

import crowdfund.dao.UserDAO;
import crowdfund.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

public class RegisterController {

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private TextField roleField; // enter ADMIN/CREATOR/CONTRIBUTOR

    public void register() {
        String name = nameField.getText();
        String email = emailField.getText();
        String pass = passwordField.getText();
        String role = roleField.getText() == null ? "CONTRIBUTOR" : roleField.getText().toUpperCase();

        if (!(role.equals("ADMIN") || role.equals("CREATOR") || role.equals("CONTRIBUTOR"))) {
            new Alert(Alert.AlertType.ERROR, "Role must be ADMIN, CREATOR, or CONTRIBUTOR").showAndWait();
            return;
        }

        User user = new User(name, email, pass, role);
        UserDAO dao = new UserDAO();
        boolean ok = dao.create(user);
        if (!ok) {
            new Alert(Alert.AlertType.ERROR, "Registration failed (maybe duplicate email)").showAndWait();
            return;
        }

        new Alert(Alert.AlertType.INFORMATION, "Account created. Please login.").showAndWait();
        openLogin();
    }

    public void openLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/crowdfund/fxml/login.fxml"));
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
        } catch (Exception e) { e.printStackTrace(); }
    }
}
