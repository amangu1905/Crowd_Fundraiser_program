package crowdfund.service;

import crowdfund.dao.CampaignDAO;
import crowdfund.dao.ContributionDAO;
import crowdfund.model.Campaign;

/**
 * ContributionService: validation and orchestration for contributions.
 * - Validates inputs
 * - Checks campaign existence and rules
 * - Calls DAO for atomic DB operation
 */
public class ContributionService {

    private final CampaignDAO campaignDAO = new CampaignDAO();
    private final ContributionDAO contributionDAO = new ContributionDAO();

    /**
     * Process a contribution to a campaign by a user.
     * Returns true if contribution succeeded (committed), false if validation failed or DB error.
     */
    public boolean contribute(int campaignId, int userId, double amount) {
        // Validation: amount must be positive
        if (amount <= 0) {
            return false;
        }

        // Validation: campaign must exist
        Campaign c = campaignDAO.getCampaignById(campaignId);
        if (c == null) {
            return false;
        }

        // Additional business rules can go here (e.g., cannot exceed goal, min amount, etc.)
        // For example, don't allow negative totals:
        double projected = c.getCurrentAmount() + amount;
        if (projected < 0) {
            return false;
        }

        // Delegate to DAO which runs a DB transaction
        return contributionDAO.contributeAtomic(campaignId, userId, amount);
    }
}
