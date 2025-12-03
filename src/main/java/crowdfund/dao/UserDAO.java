package crowdfund.dao;

import crowdfund.model.User;
import crowdfund.util.DBUtil;

import java.sql.*;

/**
 * UserDAO: handles users CRUD. Enforces role insertion (ENUM in DB).
 */
public class UserDAO {

    public User findByEmail(String email) {
        String sql = "SELECT id, name, email, password, role FROM users WHERE email = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("role")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean create(User user) {
        String sql = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Validate role
            String role = user.getRole();
            if (!("ADMIN".equals(role) || "CREATOR".equals(role) || "CONTRIBUTOR".equals(role))) {
                throw new IllegalArgumentException("Invalid role: " + role);
            }

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, role);

            int affected = ps.executeUpdate();
            if (affected == 0) return false;

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    user.setId(keys.getInt(1));
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
