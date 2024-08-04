package server.model;

import java.sql.Date;

public class UserDiscardedFeedback {
    private String userID;
    private int discardID;
    private String question1Answer;
    private String question2Answer;
    private String question3Answer;
    private Date feedbackDate;

    // Getters and Setters
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getDiscardID() {
        return discardID;
    }

    public void setDiscardID(int discardID) {
        this.discardID = discardID;
    }

    public String getQuestion1Answer() {
        return question1Answer;
    }

    public void setQuestion1Answer(String question1Answer) {
        this.question1Answer = question1Answer;
    }

    public String getQuestion2Answer() {
        return question2Answer;
    }

    public void setQuestion2Answer(String question2Answer) {
        this.question2Answer = question2Answer;
    }

    public String getQuestion3Answer() {
        return question3Answer;
    }

    public void setQuestion3Answer(String question3Answer) {
        this.question3Answer = question3Answer;
    }

    public Date getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

	
    
}
