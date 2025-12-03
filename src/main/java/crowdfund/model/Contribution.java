package crowdfund.model;

public class Contribution {
    private int id;
    private int campaignId;
    private int userId;
    private double amount;

    public Contribution() {}

    public Contribution(int id, int campaignId, int userId, double amount) {
        this.id = id; this.campaignId = campaignId; this.userId = userId; this.amount = amount;
    }

    public int getId() { return id; }
    public int getCampaignId() { return campaignId; }
    public int getUserId() { return userId; }
    public double getAmount() { return amount; }
}
