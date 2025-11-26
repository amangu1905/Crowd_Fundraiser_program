package crowdfund.controller;

import crowdfund.dao.CampaignDAO;
import crowdfund.model.Campaign;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class DashboardController {

    @FXML private ListView<String> campaignList;

    public void initialize() {
        // Create a CampaignDAO object to interact with the campaign data.
        CampaignDAO dao = new CampaignDAO();
        // Iterate through all campaigns retrieved from the database.
        for (Campaign c : dao.getAllCampaigns()) {
            // Add the title of each campaign to the campaignList ListView.
            campaignList.getItems().add(c.getTitle());
        }
    }
}
