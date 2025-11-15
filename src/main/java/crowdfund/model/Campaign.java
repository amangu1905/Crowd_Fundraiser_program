package crowdfund.model;

public class Campaign {
    private int id, creatorId;
    private String title, description;
    private double goalAmount, currentAmount;

    public Campaign() {}

    public Campaign(int id, String title, String description,
                    double goalAmount, double currentAmount, int creatorId) {
        this.id = id; this.title = title; this.description = description;
        this.goalAmount = goalAmount; this.currentAmount = currentAmount;
        this.creatorId = creatorId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getGoalAmount() { return goalAmount; }
    public void setGoalAmount(double goalAmount) { this.goalAmount = goalAmount; }

    public double getCurrentAmount() { return currentAmount; }
    public void setCurrentAmount(double currentAmount) { this.currentAmount = currentAmount; }

    public int getCreatorId() { return creatorId; }
    public void setCreatorId(int creatorId) { this.creatorId = creatorId; }
}
