package crowdfund.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * ContributionDAO: low-level DB insertion method.
 * Accepts a Connection so transaction control remains with the service.
 */
public class ContributionDAO {

    private static final String INSERT_SQL = "INSERT INTO contributions (campaign_id, user_id, amount) VALUES (?, ?, ?)";

    public int insertContribution(Connection conn, int campaignId, int userId, double amount) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
            ps.setInt(1, campaignId);
            ps.setInt(2, userId);
            ps.setDouble(3, amount);
            return ps.executeUpdate();
        }
    }
}
