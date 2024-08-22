package test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import server.DatabaseOperation.MenuDatabaseOperator;
import server.model.MenuItem;
import server.service.MenuItemHandler;
import server.service.NotificationService;
import server.util.JsonConverter;

public class MenuItemHandlerTest {

    @Mock
    private MenuDatabaseOperator menuDatabaseOperator;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private MenuItemHandler menuItemHandler;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddMenuItem_Success() throws Exception {
        MenuItem menuItem = new MenuItem();
        menuItem.setItemName("Pasta");
        menuItem.setItemCategory("lunch");
        menuItem.setItemPrice(10.99f);
        menuItem.setItemAvailable(true);
        menuItem.setCuisineType("Other");
        menuItem.setFoodType("Vegetarian");
        menuItem.setSpiceLevel("Low");
        menuItem.setSweet(false);
        String data = JsonConverter.convertObjectToJson(menuItem);

        when(menuDatabaseOperator.isItemInCategory(anyString(), anyString())).thenReturn(false);
        when(menuDatabaseOperator.addMenuItem(any(MenuItem.class))).thenReturn(true);

        String result = menuItemHandler.addMenuItem(data);
        assertEquals("{\"status\":\"success\",\"message\":\"Added item to menu.\"}", result);

        verify(menuDatabaseOperator, times(1)).addMenuItem(any(MenuItem.class));
        verify(notificationService, times(1)).addNotification("AddedPasta to Main Menu");
    }

    @Test
    public void testAddMenuItem_ItemAlreadyExists() throws Exception {
        MenuItem menuItem = new MenuItem();
        menuItem.setItemName("Pasta");
        menuItem.setItemCategory("lunch");
        menuItem.setItemPrice(10.99f);
        menuItem.setItemAvailable(true);
        menuItem.setCuisineType("Other");
        menuItem.setFoodType("Vegetarian");
        menuItem.setSpiceLevel("Low");
        menuItem.setSweet(false);
        String data = JsonConverter.convertObjectToJson(menuItem);

        when(menuDatabaseOperator.isItemInCategory(anyString(), anyString())).thenReturn(true);

        String result = menuItemHandler.addMenuItem(data);
        assertEquals("{\"status\":\"info\",\"message\":\"Item already present in menu\"}", result);

        verify(menuDatabaseOperator, never()).addMenuItem(any(MenuItem.class));
        verify(notificationService, never()).addNotification(anyString());
    }

    @Test
    public void testUpdateMenuItem_Success() throws Exception {
        MenuItem menuItem = new MenuItem();
        menuItem.setItemName("Pasta");
        menuItem.setItemCategory("lunch");
        menuItem.setItemPrice(10.99f);
        menuItem.setItemAvailable(true);
        menuItem.setCuisineType("Other");
        menuItem.setFoodType("Vegetarian");
        menuItem.setSpiceLevel("Low");
        menuItem.setSweet(false);
        String data = JsonConverter.convertObjectToJson(menuItem);

        when(menuDatabaseOperator.updateMenuItem(any(MenuItem.class))).thenReturn(true);

        String result = menuItemHandler.updateMenuItem(data);
        assertEquals("{\"status\":\"success\",\"message\":\"Updated menu item.\"}", result);

        verify(menuDatabaseOperator, times(1)).updateMenuItem(any(MenuItem.class));
    }

    @Test
    public void testDeleteMenuItem_Success() throws Exception {
        MenuItem menuItem = new MenuItem();
        menuItem.setItemName("Pasta");
        menuItem.setItemCategory("lunch");
        menuItem.setItemPrice(10.99f);
        menuItem.setItemAvailable(true);
        menuItem.setCuisineType("Other");
        menuItem.setFoodType("Vegetarian");
        menuItem.setSpiceLevel("Low");
        menuItem.setSweet(false);
        String data = JsonConverter.convertObjectToJson(menuItem);

        when(menuDatabaseOperator.deleteMenuItem(anyString(), anyString())).thenReturn(true);

        String result = menuItemHandler.deleteMenuItem(data);
        assertEquals("{\"status\":\"success\",\"message\":\"Deleted menu item.\"}", result);

        verify(menuDatabaseOperator, times(1)).deleteMenuItem(anyString(), anyString());
        verify(notificationService, times(1)).addNotification("DeletedPasta Food Item from Main Menu");
    }

    @Test
    public void testUpdateFoodAvailableStatus_Success() throws Exception {
        String data = "{\"itemName\":\"Pasta\",\"itemAvailable\":true}";

        when(menuDatabaseOperator.updateAvailability(anyString(), anyBoolean())).thenReturn(true);

        String result = menuItemHandler.updateFoodAvailableStatus(data);
        assertEquals("{\"status\":\"success\",\"message\":\"updated Availability Status in menu.\"}", result);

        verify(menuDatabaseOperator, times(1)).updateAvailability(anyString(), eq(true));
        verify(notificationService, times(1)).addNotification("updated Availability Status of Pasta");
    }

    @Test
    public void testViewAllMenuItems_NoItems() throws Exception {
        when(menuDatabaseOperator.viewMenuItems()).thenReturn(null);

        String result = menuItemHandler.viewAllMenuItems();
        assertEquals("{\"status\":\"info\",\"message\":\"No menu items found\"}", result);
    }

    @Test
    public void testViewAllMenuItems_Exception() throws Exception {
        when(menuDatabaseOperator.viewMenuItems()).thenThrow(new RuntimeException("Database error"));

        String result = menuItemHandler.viewAllMenuItems();
        assertEquals("{\"status\":\"error\",\"message\":\"Database error\"}", result);
    }
}
