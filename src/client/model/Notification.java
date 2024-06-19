package client.model;


public class Notification {
    private String message;
    private String createdDate;
    private String type;



    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }


    public String getCreatedDate() {
        return createdDate;
    }

  
    public String setCreatedDate(String createdDate) {
       return this.createdDate=createdDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
