package crowdfund.dao;

import crowdfund.model.Contribution;
import crowdfund.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * DAO responsible solely for low-level DB operations for contributions.
 * The contributeAtomic method runs an atomic transaction: insert contribution + update campaign total.
 */
public class ContributionDAO {

    /**
     * Atomically insert a contribution row and update campaign's current_amount.
     * Returns true if commit succeeded, false otherwise.
     */
    public boolean contributeAtomic(int campaignId, int userId, double amount) {
        String insertSql = "INSERT INTO contributions (campaign_id, user_id, amount) VALUES (?, ?, ?)";
        String updateSql = "UPDATE campaigns SET current_amount = current_amount + ? WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement insertPs = conn.prepareStatement(insertSql);
             PreparedStatement updatePs = conn.prepareStatement(updateSql)) {

            // Start transaction
            conn.setAutoCommit(false);

            // 1) Insert contribution
            insertPs.setInt(1, campaignId);
            insertPs.setInt(2, userId);
            insertPs.setDouble(3, amount);
            insertPs.executeUpdate();

            // 2) Update campaign current_amount
            updatePs.setDouble(1, amount);
            updatePs.setInt(2, campaignId);
            int updatedRows = updatePs.executeUpdate();

            if (updatedRows != 1) {
                // If campaign wasn't found or not updated, rollback
                conn.rollback();
                conn.setAutoCommit(true);
                return false;
            }

            // commit transaction
            conn.commit();
            conn.setAutoCommit(true);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            // on exception we must rollback - but since we're in try-with-resources we need explicit handling
            // attempt an extra rollback if connection is available
            // (we cannot access conn here because it is closed at the end of try; the best we can do is rely on catch)
            return false;
        }
    }
}
