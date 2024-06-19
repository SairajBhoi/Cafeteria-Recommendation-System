package client.model;

public class Vote {
    
    private String userID;
    private int rolloutID;
    private int categoryID;
    private boolean voteDecision;

    // Constructor
    public Vote(String userID, int rolloutID, int categoryID, boolean voteDecision) {
        this.userID = userID;
        this.rolloutID = rolloutID;
        this.categoryID = categoryID;
        this.voteDecision = voteDecision;
    }

    // Default Constructor
    public Vote() {}

    // Getter for userID
    public String getUserID() {
        return userID;
    }

    // Setter for userID
    public void setUserID(String userID) {
        this.userID = userID;
    }

    // Getter for rolloutID
    public int getRolloutID() {
        return rolloutID;
    }

    // Setter for rolloutID
    public void setRolloutID(int rolloutID) {
        this.rolloutID = rolloutID;
    }

    // Getter for categoryID
    public int getCategoryID() {
        return categoryID;
    }

    // Setter for categoryID
    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    // Getter for voteDecision
    public boolean isVoteDecision() {
        return voteDecision;
    }

    // Setter for voteDecision
    public void setVoteDecision(boolean voteDecision) {
        this.voteDecision = voteDecision;
    }
}
