package client.model;

public class Vote {
    
    private String userID;
    private int rolloutID;
    private int categoryID;
    private boolean voteDecision;

   
    public Vote(String userID, int rolloutID, int categoryID, boolean voteDecision) {
        this.userID = userID;
        this.rolloutID = rolloutID;
        this.categoryID = categoryID;
        this.voteDecision = voteDecision;
    }

 
    public Vote() {}

    
    public String getUserID() {
        return userID;
    }

    
    public void setUserID(String userID) {
        this.userID = userID;
    }

 
    public int getRolloutID() {
        return rolloutID;
    }

    // Setter for rolloutID
    public void setRolloutID(int rolloutID) {
        this.rolloutID = rolloutID;
    }

    
    public int getCategoryID() {
        return categoryID;
    }

   
    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

   
    public boolean isVoteDecision() {
        return voteDecision;
    }

    
    public void setVoteDecision(boolean voteDecision) {
        this.voteDecision = voteDecision;
    }
}
