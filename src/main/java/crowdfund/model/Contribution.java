package crowdfund.model;

import java.time.LocalDateTime;

public class Contribution {
    private int id;
    private int campaignId;
    private int userId;
    private double amount;
    private LocalDateTime date;

    public Contribution() {}

    public Contribution(int id, int campaignId, int userId, double amount) {
        this.id = id;
        this.campaignId = campaignId;
        this.userId = userId;
        this.amount = amount;
        this.date = LocalDateTime.now();
    }

    public int getId() { return id; }
    public int getCampaignId() { return campaignId; }
    public int getUserId() { return userId; }
    public double getAmount() { return amount; }
    public LocalDateTime getDate() { return date; }
}
