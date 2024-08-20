package client.model;

import java.sql.Date;

public class ChefMenuRollout {

    private int rolloutID;
    private Date rolloutDate;
    private int itemID;
    private int categoryID;
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
		this.rolloutDate=rolloutDate;
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

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getNumberOfVotes() {
        return numberOfVotes;
    }

    public void setNumberOfVotes(int numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


   
   
}
