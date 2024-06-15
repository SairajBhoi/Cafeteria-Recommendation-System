package client;

public class Admin extends User {
    private Menu menu= null;


    public   Admin(){
        menu= new Menu(this.getUserRole());
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


    public void viewAllMenuItems() {
    menu.viewAllMenuItems();
    }

    
}
