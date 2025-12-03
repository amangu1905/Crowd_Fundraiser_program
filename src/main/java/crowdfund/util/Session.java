package crowdfund.util;

import crowdfund.model.User;

/**
 * Tiny session holder to keep the logged-in user across controllers.
 * Not thread-safe; suitable for single-user desktop app.
 */
public class Session {
    private static Session instance;
    private User currentUser;

    private Session() {}

    public static Session getInstance() {
        if (instance == null) instance = new Session();
        return instance;
    }

    public void setCurrentUser(User u) { this.currentUser = u; }
    public User getCurrentUser() { return currentUser; }
    public void clear() { currentUser = null; }
}
