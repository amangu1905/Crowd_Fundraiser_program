package crowdfund.controller;

import crowdfund.dao.CampaignDAO;
import crowdfund.model.Campaign;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CreateCampaignController {

    // Text fields from the FXML UI for campaign title and goal amount
    @FXML private TextField titleField, goalField;

    // Text area for campaign description
    @FXML private TextArea descField;

    // Method triggered when the user clicks "Create Campaign"
    public void createCampaign() {

        // DAO object used to interact with the database
        CampaignDAO dao = new CampaignDAO();

        // Create a new Campaign object using input fields
        // id = 0 (auto-generated), currentAmount = 0, userId = 1 (temporary fixed)
        Campaign c = new Campaign(
                0,
                titleField.getText(),
                descField.getText(),
                Double.parseDouble(goalField.getText()),
                0,
                1
        );

        // Insert the new campaign into the database
        dao.createCampaign(c);
    }
}
