package crowdfund.service;

import crowdfund.dao.CampaignDAO;
import crowdfund.dao.ContributionDAO;
import crowdfund.model.Campaign;
import crowdfund.util.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * ContributionService orchestrates validation and atomic DB updates for contributions.
 */
public class ContributionService {

    private final CampaignDAO campaignDAO = new CampaignDAO();
    private final ContributionDAO contributionDAO = new ContributionDAO();

    /**
     * Process a contribution atomically: insert contribution row + update campaign amount.
     *
     * @param campaignId target campaign id
     * @param userId     contributor user id
     * @param amount     amount (>0)
     * @return true if committed successfully
     */
    public boolean contribute(int campaignId, int userId, double amount) {

        // 1. Basic validation
        if (amount <= 0) return false;

        // 2. Campaign existence
        Campaign campaign = campaignDAO.getCampaignById(campaignId);
        if (campaign == null) return false;

        // 3. Example rule: Do not exceed goal by business decision (optional)
        // double projected = campaign.getCurrentAmount() + amount;
        // if (projected > campaign.getGoalAmount()) return false;

        // 4. Transactional DB operations
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            if (conn == null) return false;
            conn.setAutoCommit(false);

            int inserted = contributionDAO.insertContribution(conn, campaignId, userId, amount);
            if (inserted != 1) {
                conn.rollback();
                conn.setAutoCommit(true);
                return false;
            }

            int updated = campaignDAO.updateCurrentAmount(conn, campaignId, amount);
            if (updated != 1) {
                conn.rollback();
                conn.setAutoCommit(true);
                return false;
            }

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
