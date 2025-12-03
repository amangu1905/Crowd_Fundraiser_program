package crowdfund.controller;

import crowdfund.dao.CampaignDAO;
import crowdfund.model.Campaign;
import crowdfund.service.ContributionService;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;

import java.util.Optional;

public class DashboardController {

    @FXML private ListView<String> campaignList;

    private final CampaignDAO campaignDAO = new CampaignDAO();
    private final ContributionService contributionService = new ContributionService();

    public void initialize() {
        refreshCampaignList();
    }

    private void refreshCampaignList() {
        campaignList.getItems().clear();
        for (Campaign c : campaignDAO.getAllCampaigns()) {
            // Show title and id so user can select; better UI would use custom cells with full object
            campaignList.getItems().add(c.getId() + " : " + c.getTitle() + " (Raised: " + c.getCurrentAmount() + ")");
        }
    }

    /**
     * Called by UI action to contribute to a selected campaign
     */
    public void contribute() {
        String sel = campaignList.getSelectionModel().getSelectedItem();
        if (sel == null) {
            Alert a = new Alert(Alert.AlertType.WARNING, "Select a campaign first");
            a.showAndWait();
            return;
        }

        // parse campaign id from string (we render as "id : title ...")
        int campaignId;
        try {
            campaignId = Integer.parseInt(sel.split(" : ")[0].trim());
        } catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Invalid campaign selection");
            a.showAndWait();
            return;
        }

        TextInputDialog dlg = new TextInputDialog();
        dlg.setHeaderText("Enter contribution amount");
        Optional<String> res = dlg.showAndWait();
        if (res.isEmpty()) return;

        double amount;
        try {
            amount = Double.parseDouble(res.get());
        } catch (NumberFormatException e) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Invalid amount");
            a.showAndWait();
            return;
        }

        // NOTE: in real app userId from session; using placeholder 1 for demo
        int userId = 1;

        boolean ok = contributionService.contribute(campaignId, userId, amount);

        if (ok) {
            Alert a = new Alert(Alert.AlertType.INFORMATION, "Contribution successful!");
            a.showAndWait();
            refreshCampaignList();
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Contribution failed (validation or DB error).");
            a.showAndWait();
            refreshCampaignList();
        }
    }
}
