package client.model; 

import java.math.BigDecimal;
import java.sql.Date;

public class DiscardedFoodItem {
    private int discardID;
    private int itemID;
    private String itemName;
    private BigDecimal averageRating;
    private BigDecimal positivePercentage;
    private BigDecimal negativePercentage;
    private Date discardedDate;



    public int getDiscardID() {
        return discardID;
    }

    public void setDiscardID(int discardID) {
        this.discardID = discardID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(BigDecimal averageRating) {
        this.averageRating = averageRating;
    }

    public BigDecimal getPositivePercentage() {
        return positivePercentage;
    }

    public void setPositivePercentage(BigDecimal positivePercentage) {
        this.positivePercentage = positivePercentage;
    }

    public BigDecimal getNegativePercentage() {
        return negativePercentage;
    }

    public void setNegativePercentage(BigDecimal negativePercentage) {
        this.negativePercentage = negativePercentage;
    }

    public Date getDiscardedDate() {
        return discardedDate;
    }

    public void setDiscardedDate(Date discardedDate) {
        this.discardedDate = discardedDate;
    }


}
