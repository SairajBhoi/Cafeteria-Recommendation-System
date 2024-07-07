package client.model;

public class UserPreference {
    private String userID;
    private String dietaryPreference;  
    private String preferredSpiceLevel;  
    private String preferredCuisine;  
    private boolean hasSweetTooth;
 
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDietaryPreference() {
        return dietaryPreference;
    }

    public void setDietaryPreference(String dietaryPreference) {
        this.dietaryPreference = dietaryPreference;
    }

    public String getPreferredSpiceLevel() {
        return preferredSpiceLevel;
    }

    public void setPreferredSpiceLevel(String preferredSpiceLevel) {
        this.preferredSpiceLevel = preferredSpiceLevel;
    }

    public String getPreferredCuisine() {
        return preferredCuisine;
    }

    public void setPreferredCuisine(String preferredCuisine) {
        this.preferredCuisine = preferredCuisine;
    }

    public boolean isHasSweetTooth() {
        return hasSweetTooth;
    }

    public void setHasSweetTooth(boolean hasSweetTooth) {
        this.hasSweetTooth = hasSweetTooth;
    }

    
}
