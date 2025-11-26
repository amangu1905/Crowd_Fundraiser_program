package crowdfund.model;

public class User {
    private int id; // Unique identifier for the user
    private String name, email, password, role; // User attributes: name, email, password, and role

    public User() {} // Default constructor

    public User(int id, String name, String email, String password, String role) {
        this.id = id; this.name = name; this.email = email;
        this.password = password; this.role = role;
    } // Constructor to initialize all user attributes

    public int getId() { return id; } // Getter for user id
    public void setId(int id) { this.id = id; } // Setter for user id

    public String getName() { return name; } // Getter for user name
    public void setName(String name) { this.name = name; } // Setter for user name

    public String getEmail() { return email; } // Getter for user email
    public void setEmail(String email) { this.email = email; } // Setter for user email

    public String getPassword() { return password; } // Getter for user password
    public void setPassword(String password) { this.password = password; } // Setter for user password

    public String getRole() { return role; } // Getter for user role
    public void setRole(String role) { this.role = role; } // Setter for user role
}
