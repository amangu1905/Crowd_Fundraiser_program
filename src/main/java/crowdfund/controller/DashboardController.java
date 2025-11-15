package crowdfund.controller;

import crowdfund.dao.CampaignDAO;
import crowdfund.model.Campaign;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class DashboardController {

    @FXML private ListView<String> campaignList;

    public void initialize() {
        CampaignDAO dao = new CampaignDAO();
        for (Campaign c : dao.getAllCampaigns()) {
            campaignList.getItems().add(c.getTitle());
        }
    }
}
