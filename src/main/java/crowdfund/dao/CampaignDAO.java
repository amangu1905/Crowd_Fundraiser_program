package crowdfund.dao;

import crowdfund.model.Campaign;
import crowdfund.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CampaignDAO {

    public boolean createCampaign(Campaign c) {
        try (Connection con = DBUtil.getConnection()) {
            String sql = "INSERT INTO campaigns(title,description,goal_amount,current_amount,creator_id) VALUES(?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, c.getTitle());
            ps.setString(2, c.getDescription());
            ps.setDouble(3, c.getGoalAmount());
            ps.setDouble(4, c.getCurrentAmount());
            ps.setInt(5, c.getCreatorId());

            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public List<Campaign> getAllCampaigns() {
        List<Campaign> list = new ArrayList<>();
        try (Connection con = DBUtil.getConnection()) {
            String sql = "SELECT * FROM campaigns";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                list.add(new Campaign(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDouble("goal_amount"),
                        rs.getDouble("current_amount"),
                        rs.getInt("creator_id")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}
