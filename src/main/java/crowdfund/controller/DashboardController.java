package crowdfund.controller;

import crowdfund.dao.CampaignDAO;
import crowdfund.model.Campaign;
import crowdfund.service.ContributionService;
import crowdfund.util.Session;
import crowdfund.model.User;
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

    public void refreshCampaignList() {
        campaignList.getItems().clear();
        for (Campaign c : campaignDAO.getAllCampaigns()) {
            campaignList.getItems().add(c.getId() + " : " + c.getTitle() + " (Raised: " + c.getCurrentAmount() + ")");
        }
    }

    public void contribute() {
        String sel = campaignList.getSelectionModel().getSelectedItem();
        if (sel == null) {
            new Alert(Alert.AlertType.WARNING, "Select a campaign first").showAndWait();
            return;
        }

        int campaignId;
        try {
            campaignId = Integer.parseInt(sel.split(" : ")[0].trim());
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Invalid selection").showAndWait();
            return;
        }

        TextInputDialog dlg = new TextInputDialog();
        dlg.setHeaderText("Enter contribution amount");
        Optional<String> res = dlg.showAndWait();
        if (res.isEmpty()) return;

        double amount;
        try { amount = Double.parseDouble(res.get()); }
        catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid amount").showAndWait();
            return;
        }

        User current = Session.getInstance().getCurrentUser();
        if (current == null) {
            new Alert(Alert.AlertType.ERROR, "You must be logged in to contribute").showAndWait();
            return;
        }

        boolean ok = contributionService.contribute(campaignId, current.getId(), amount);
        if (ok) {
            new Alert(Alert.AlertType.INFORMATION, "Contribution successful!").showAndWait();
        } else {
            new Alert(Alert.AlertType.ERROR, "Contribution failed (validation or DB error).").showAndWait();
        }
        refreshCampaignList();
    }
}
