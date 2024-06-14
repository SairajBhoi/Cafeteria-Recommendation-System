public class Admin extends User {
    private Menu() menu;

   
    public void addMenuItem(MenuItem menuItem) {
        menu.addMenuItem(menuItem);
    }

    public void updateMenuItem(MenuItem menuItem) {
        menu.updateMenuItem(menuItem);
    }

    public void deleteMenuItem(String itemId) {
        menu.deleteMenuItem(itemId);
    }


    public List<MenuItem> getAllMenuItems() {
        return menu.getAllMenuItems();
    }

    
}
