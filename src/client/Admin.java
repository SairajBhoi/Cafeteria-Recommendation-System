package client;

public class Admin extends User {
    private Menu menu;

    public Admin(String userId,String userName) {
        this.setUserRole("ADMIN");
        this.setUserName(userName);
        this.setUserId(userId);
        menu = new Menu(this.getUserRole());
    }

    public void addMenuItem() {
        menu.addMenuItem();
    }

    public void updateMenuItem() {
        menu.updateMenuItem();
    }

    public void deleteMenuItem() {
        menu.deleteMenuItem();
    }
    
    public void updateAvailabilityStatus() {
        menu.updateAvailabilityStatus();
    }
    
    public void viewAllMenuItems() {
        menu.viewAllMenuItems();
    }
}
