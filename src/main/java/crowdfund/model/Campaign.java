package crowdfund.model;

public class Campaign {
    private int id;
    private String title;
    private String description;
    private double goalAmount;
    private double currentAmount;
    private int creatorId;
    private String status; // PENDING, APPROVED, REJECTED

    public Campaign() {}

    public Campaign(int id, String title, String description, double goalAmount, double currentAmount, int creatorId, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.goalAmount = goalAmount;
        this.currentAmount = currentAmount;
        this.creatorId = creatorId;
        this.status = status;
    }

    public Campaign(int id, String title, String description, double goalAmount, double currentAmount, int creatorId) {
        this(id, title, description, goalAmount, currentAmount, creatorId, "PENDING");
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public double getGoalAmount() { return goalAmount; }
    public double getCurrentAmount() { return currentAmount; }
    public int getCreatorId() { return creatorId; }
    public String getStatus() { return status; }

    public void setCurrentAmount(double currentAmount) { this.currentAmount = currentAmount; }
    public void setStatus(String status) { this.status = status; }
}
