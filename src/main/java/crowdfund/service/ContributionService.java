package crowdfund.service;

import crowdfund.dao.CampaignDAO;
import crowdfund.dao.ContributionDAO;
import crowdfund.model.Campaign;
import crowdfund.util.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * ContributionService: business logic and orchestration for handling contributions.
 * - Validates input
 * - Loads campaign state
 * - Begins a JDBC transaction
 * - Inserts contribution and updates campaign amount atomically
 */
public class ContributionService {

    private final CampaignDAO campaignDAO = new CampaignDAO();
    private final ContributionDAO contributionDAO = new ContributionDAO();

    /**
     * Process a contribution atomically.
     *
     * @param campaignId campaign id
     * @param userId     contributor id
     * @param amount     amount to contribute (>0)
     * @return true if committed successfully, false otherwise
     */
    public boolean contribute(int campaignId, int userId, double amount) {
        // Basic validation
        if (amount <= 0) {
            return false; // invalid amount
        }

        // Check campaign exists and optionally business rules (e.g., cannot exceed goal)
        Campaign campaign = campaignDAO.getCampaignById(campaignId);
        if (campaign == null) {
            return false; // campaign not found
        }

        // Example business rule: don't allow contribution that makes total negative (safeguard)
        double projected = campaign.getCurrentAmount() + amount;
        if (projected < 0) return false;

        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            if (conn == null) return false;
            conn.setAutoCommit(false);

            // 1) Insert contribution row
            int inserted = contributionDAO.insertContribution(conn, campaignId, userId, amount);
            if (inserted != 1) {
                conn.rollback();
                conn.setAutoCommit(true);
                return false;
            }

            // 2) Update campaign amount
            int updated = campaignDAO.updateCurrentAmount(conn, campaignId, amount);
            if (updated != 1) {
                conn.rollback();
                conn.setAutoCommit(true);
                return false;
            }

            // 3) Commit
            conn.commit();
            conn.setAutoCommit(true);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
                try { conn.setAutoCommit(true); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            return false;
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (SQLException ignored) {}
            }
        }
    }
}
