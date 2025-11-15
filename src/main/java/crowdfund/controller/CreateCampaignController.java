package crowdfund.controller;

import crowdfund.dao.CampaignDAO;
import crowdfund.model.Campaign;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CreateCampaignController {

    @FXML private TextField titleField, goalField;
    @FXML private TextArea descField;

    public void createCampaign() {
        CampaignDAO dao = new CampaignDAO();
        Campaign c = new Campaign(0, titleField.getText(), descField.getText(),
                Double.parseDouble(goalField.getText()), 0, 1);
        dao.createCampaign(c);
    }
}
