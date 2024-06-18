package client;



import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ChefMenuRollout {

    private int rolloutID;
    private Date rolloutDate;
    private int itemID;
    private int categoryID;
    private Timestamp votingEndTime;
    private int numberOfVotes;
    private String itemName;
    private String categoryName;
    
    
    public ChefMenuRollout() {}

  
    public int getRolloutID() {
        return rolloutID;
    }

    public void setRolloutID(int rolloutID) {
        this.rolloutID = rolloutID;
    }

    public Date getRolloutDate() {
        return rolloutDate;
    }

    public void setRolloutDate(Date rolloutDate) {
        this.rolloutDate = rolloutDate;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    
    
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public Timestamp getVotingEndTime() {
        return votingEndTime;
    }

    public void setVotingEndTime(Timestamp votingEndTime) {
        this.votingEndTime = votingEndTime;
    }

    public int getNumberOfVotes() {
        return numberOfVotes;
    }

    public void setNumberOfVotes(int numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }

    public String getFormattedRolloutDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(rolloutDate);
    }

    // Method to format votingEndTime to a String
    public String getFormattedVotingEndTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(votingEndTime);
    }

    // Method to set rolloutDate from String
    public void setRolloutDateFromString(String dateStr) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.rolloutDate = new Date(sdf.parse(dateStr).getTime());
    }

    // Method to set votingEndTime from String
    public void setVotingEndTimeFromString(String dateStr) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.votingEndTime = new Timestamp(sdf.parse(dateStr).getTime());
    }
}








































