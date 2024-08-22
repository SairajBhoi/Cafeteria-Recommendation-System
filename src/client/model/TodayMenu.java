package client.model;

import java.sql.Date;

import java.time.LocalDate;

public class TodayMenu {
    private Date menuDate;
    private int itemID;
    private int categoryID;
    private int numberOfVotes;
    private String itemName;
    private String categoryName;
    private double itemPrice;

   
    public Date getMenuDate() {
        return menuDate;
    }

    public void setMenuDate(Date menuDate) {
        this.menuDate = menuDate;
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

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }
}
