package crowdfund.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * ContributionDAO: low-level DB operations for contributions.
 * Methods are written to accept an active Connection so they can be
 * part of a larger transaction controlled by the service layer.
 */
public class ContributionDAO {

    private static final String INSERT_SQL =
            "INSERT INTO contributions (campaign_id, user_id, amount) VALUES (?, ?, ?)";

    /**
     * Insert a contribution row using the provided Connection.
     * This method DOES NOT commit/rollback the connection — the caller controls the transaction.
     *
     * @param conn       active JDBC Connection (non-null)
     * @param campaignId campaign id
     * @param userId     user id
     * @param amount     contribution amount
     * @return number of rows inserted (should be 1 on success)
     * @throws SQLException if SQL error occurs
     */
    public int insertContribution(Connection conn, int campaignId, int userId, double amount) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
            ps.setInt(1, campaignId);
            ps.setInt(2, userId);
            ps.setDouble(3, amount);
            return ps.executeUpdate();
        }
    }
}
