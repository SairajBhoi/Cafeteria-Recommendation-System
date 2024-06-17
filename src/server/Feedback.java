package server;

public class Feedback {
    private String employeeName;
    private String itemName;
    private int tasteRating;
    private int qualityRating;
    private int freshnessRating;
    private int valueForMoneyRating;
    private String feedbackMessage;
    private String feedbackMessageSentiment; 
    
    
    public String getEmployeeName() {
        return this.employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getTasteRating() {
        return tasteRating;
    }

    public void setTasteRating(int tasteRating) {
        this.tasteRating = tasteRating;
    }

    public int getQualityRating() {
        return qualityRating;
    }

    public void setQualityRating(int qualityRating) {
        this.qualityRating = qualityRating;
    }

    public int getFreshnessRating() {
        return freshnessRating;
    }

    public void setFreshnessRating(int freshnessRating) {
        this.freshnessRating = freshnessRating;
    }

    public int getValueForMoneyRating() {
        return valueForMoneyRating;
    }

    public void setValueForMoneyRating(int valueForMoneyRating) {
        this.valueForMoneyRating = valueForMoneyRating;
    }

    public String getFeedbackMessage() {
        return feedbackMessage;
    }

    public void setFeedbackMessage(String feedbackMessage) {
        this.feedbackMessage = feedbackMessage;
    }

    public String getFeedbackMessageSentiment() {
        return feedbackMessageSentiment;
    }

    public void setFeedbackMessageSentiment(String feedbackMessageSentiment) {
        this.feedbackMessageSentiment = feedbackMessageSentiment;
    }
}
