package server.model;

public class ChefMenuItemScore {
	 private int rolloutID;
	 private String itemName;
	    private float itemPrice;
	    private boolean isItemAvailable;
	    private String itemCategory;
	    private String cuisineType;  
	    private String foodType;
	    private String spiceLevel;
	    private boolean isSweet;  
	    private double score;
	    
	    public ChefMenuItemScore() {
	    }

	    public ChefMenuItemScore(String itemName, String itemCategory) {
	        this.itemName = itemName;
	        this.itemCategory = itemCategory;
	    }

	    public int getRolloutID() {
	        return rolloutID;
	    }

	    public void setRolloutID(int rolloutID) {
	        this.rolloutID = rolloutID;
	    }
	    public String getItemName() {
	        return itemName;
	    }

	    public void setItemName(String itemName) {
	        this.itemName = itemName;
	    }

	    public float getItemPrice() {
	        return itemPrice;
	    }

	    public void setItemPrice(float itemPrice) {
	        this.itemPrice = itemPrice;
	    }

	    public boolean isItemAvailable() {
	        return isItemAvailable;
	    }

	    public void setItemAvailable(boolean itemAvailable) {
	        isItemAvailable = itemAvailable;
	    }

	    public String getItemCategory() {
	        return itemCategory;
	    }

	    public void setItemCategory(String itemCategory) {
	        this.itemCategory = itemCategory;
	    }

	    public String getCuisineType() {
	        return cuisineType;
	    }

	    public void setCuisineType(String cuisineType) {
	        this.cuisineType = cuisineType;
	    }

	    public String getFoodType() {
	        return foodType;
	    }

	    public void setFoodType(String foodType) {
	        this.foodType = foodType;
	    }

	    public String getSpiceLevel() {
	        return spiceLevel;
	    }

	    public void setSpiceLevel(String spiceLevel) {
	        this.spiceLevel = spiceLevel;
	    }

	    public boolean isSweet() {
	        return isSweet;
	    }

	    public void setSweet(boolean sweet) {
	        isSweet = sweet;
	    }
	    
	    public double getScore() {
	        return this.score;
	    }

	    public void setScore(double score) {
	        this.score=score;
	    }
	
	
	
	
}
