// createCampaign method
public boolean createCampaign(Campaign c) {
    String sql = "INSERT INTO campaigns(title,description,goal_amount,current_amount,creator_id) VALUES(?,?,?,?,?)";
    try (Connection con = DBUtil.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) { // try-with-resources
        ps.setString(1, c.getTitle());
        ps.setString(2, c.getDescription());
        ps.setDouble(3, c.getGoalAmount());
        ps.setDouble(4, c.getCurrentAmount());
        ps.setInt(5, c.getCreatorId());

        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        // Use a logging framework instead of printStackTrace
        System.err.println("Error creating campaign: " + e.getMessage()); // Replace with proper logging
        return false;
    }
}

// getAllCampaigns method
public List<Campaign> getAllCampaigns() {
    List<Campaign> list = new ArrayList<>();
    String sql = "SELECT * FROM campaigns";
    try (Connection con = DBUtil.getConnection();
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery(sql)) { // try-with-resources
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
    } catch (SQLException e) {
        System.err.println("Error getting all campaigns: " + e.getMessage()); // Replace with proper logging
    }
    return list;
}
