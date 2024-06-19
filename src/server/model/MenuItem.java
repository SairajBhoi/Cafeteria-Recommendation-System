package server.model;

public class MenuItem {
    private String itemName;
    private float itemPrice;
    private boolean isItemAvailable;
    private String itemCategory;
   
    MenuItem(){}

    MenuItem(String itemName,String itemCategory)
    {
        this.itemName=itemName;
        this.itemCategory=itemCategory;
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
}
