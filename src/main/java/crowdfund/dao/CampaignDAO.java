package crowdfund.dao;

import crowdfund.model.Campaign;
import crowdfund.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CampaignDAO {

    private static final String SELECT_ALL = "SELECT * FROM campaigns";
    private static final String SELECT_BY_ID = "SELECT * FROM campaigns WHERE id = ?";
    private static final String INSERT_SQL = "INSERT INTO campaigns (title, description, goal_amount, current_amount, creator_id, status) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_AMOUNT_SQL = "UPDATE campaigns SET current_amount = current_amount + ? WHERE id = ?";

    public List<Campaign> getAllCampaigns() {
        List<Campaign> list = new ArrayList<>();
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public Campaign getCampaignById(int id) {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean createCampaign(Campaign c) {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, c.getTitle());
            ps.setString(2, c.getDescription());
            ps.setDouble(3, c.getGoalAmount());
            ps.setDouble(4, c.getCurrentAmount());
            ps.setInt(5, c.getCreatorId());
            // Validate status
            String st = c.getStatus();
            if (st == null) st = "PENDING";
            ps.setString(6, st);

            int affected = ps.executeUpdate();
            if (affected == 0) return false;

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) c.setStatus(st); // keep status; ID not required here
            }
            return true;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    /**
     * NOTE: This method is designed to be used in a transaction (caller manages Connection).
     */
    public int updateCurrentAmount(Connection conn, int campaignId, double amount) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(UPDATE_AMOUNT_SQL)) {
            ps.setDouble(1, amount);
            ps.setInt(2, campaignId);
            return ps.executeUpdate();
        }
    }

    private Campaign mapRow(ResultSet rs) throws SQLException {
        return new Campaign(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getDouble("goal_amount"),
                rs.getDouble("current_amount"),
                rs.getInt("creator_id"),
                rs.getString("status")
        );
    }
}
