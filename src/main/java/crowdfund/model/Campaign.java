package crowdfund.model;

public class Campaign {
    private int id, creatorId; // id of the campaign and id of the user who created the campaign
    private String title, description; // title and description of the campaign
    private double goalAmount, currentAmount; // goal amount and current amount of the campaign

    public Campaign() {} // default constructor

    public Campaign(int id, String title, String description,
                    double goalAmount, double currentAmount, int creatorId) {
        this.id = id; this.title = title; this.description = description;
        this.goalAmount = goalAmount; this.currentAmount = currentAmount;
        this.creatorId = creatorId;
    }

    public int getId() { return id; } // getter for id
    public void setId(int id) { this.id = id; } // setter for id

    public String getTitle() { return title; } // getter for title
    public void setTitle(String title) { this.title = title; } // setter for title

    public String getDescription() { return description; } // getter for description
    public void setDescription(String description) { this.description = description; } // setter for description

    public double getGoalAmount() { return goalAmount; } // getter for goalAmount
    public void setGoalAmount(double goalAmount) { this.goalAmount = goalAmount; } // setter for goalAmount

    public double getCurrentAmount() { return currentAmount; } // getter for currentAmount
    public void setCurrentAmount(double currentAmount) { this.currentAmount = currentAmount; } // setter for currentAmount

    public int getCreatorId() { return creatorId; } // getter for creatorId
    public void setCreatorId(int creatorId) { this.creatorId = creatorId; } // setter for creatorId
}
