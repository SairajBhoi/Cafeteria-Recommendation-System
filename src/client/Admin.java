public class Admin extends User {
    private Menu() menu= null;

    public   Admin(){
        menu= new Menu(String this.role);
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


    public viewAllMenuItems() {
    menu.getAllMenuItems();
    }

    
}
