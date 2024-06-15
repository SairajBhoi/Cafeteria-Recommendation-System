package client;

public class MenuItem {
    private String itemName;
    private float itemPrice;
    private boolean isItemAvailable;
    private char itemCategory;
   
    MenuItem(){}

    MenuItem(String itemName,char itemCategory)
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

    public char getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(char itemCategory) {
        this.itemCategory = itemCategory;
    }
}
