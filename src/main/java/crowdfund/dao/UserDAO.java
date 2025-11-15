package crowdfund.dao;

import crowdfund.model.User;
import crowdfund.util.DBUtil;

import java.sql.*;

public class UserDAO {

    public User login(String email, String password) {
        try (Connection con = DBUtil.getConnection()) {
            String sql = "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public boolean register(User u) {
        try (Connection con = DBUtil.getConnection()) {
            String sql = "INSERT INTO users(name,email,password,role) VALUES(?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, u.getName());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getRole());

            return ps.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
}
