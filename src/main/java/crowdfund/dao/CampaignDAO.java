package crowdfund.dao;

import crowdfund.model.Campaign;
import crowdfund.util.DBUtil;

import java.sql.*;

/**
 * CampaignDAO: DB operations for campaigns.
 * - getCampaignById: read-only
 * - updateCurrentAmount: update using provided Connection (for transactional updates)
 */
public class CampaignDAO {

    private static final String SELECT_BY_ID = "SELECT * FROM campaigns WHERE id = ?";
    private static final String UPDATE_AMOUNT_SQL = "UPDATE campaigns SET current_amount = current_amount + ? WHERE id = ?";

    public Campaign getCampaignById(int campaignId) {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_ID)) {

            ps.setInt(1, campaignId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Campaign(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getDouble("goal_amount"),
                            rs.getDouble("current_amount"),
                            rs.getInt("creator_id")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Update a campaign's current_amount using the supplied connection.
     * NOTE: This method does NOT commit/rollback the connection; caller should manage transaction.
     *
     * @param conn       active JDBC connection
     * @param campaignId id of campaign
     * @param amount     amount to add (can be negative if needed)
     * @return number of rows updated
     * @throws SQLException on SQL error
     */
    public int updateCurrentAmount(Connection conn, int campaignId, double amount) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(UPDATE_AMOUNT_SQL)) {
            ps.setDouble(1, amount);
            ps.setInt(2, campaignId);
            return ps.executeUpdate();
        }
    }
}
