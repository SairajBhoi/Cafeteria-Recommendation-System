package client.requestgateway;

import client.model.MenuItem;
import client.util.JsonConverter;

public class MenuRequestGateway {
    private String basePath;

   
    public MenuRequestGateway(String role) {
        this.basePath = buildBasePath(role);
    }

    
    public MenuRequestGateway() {
        this.basePath = "";
    }

    
    private String buildBasePath(String role) {
        return (role != null && !role.isEmpty()) ? "/" + role : "";
    }

    
    private String generatePath(String endpoint) {
        return this.basePath + endpoint;
    }

  
    private String convertToJson(String path, Object data) {
        return JsonConverter.convertObjectToJson(path, data);
    }

   

    public String createAddMenuItemRequest(MenuItem menuItem) {
        String path = generatePath("/addMenuItem");
        return convertToJson(path, menuItem);
    }

    public String createUpdateMenuItemRequest(MenuItem menuItem) {
        String path = generatePath("/updateMenuItem");
        return convertToJson(path, menuItem);
    }

    public String createUpdateFoodAvailableStatusRequest(MenuItem menuItem) {
        String path = generatePath("/updateFoodAvailableStatus");
        return convertToJson(path, menuItem);
    }

    public String createDeleteMenuItemRequest(MenuItem menuItem) {
        String path = generatePath("/deleteMenuItem");
        return convertToJson(path, menuItem);
    }

    public String createViewAllMenuItemsRequest() {
        String path = generatePath("/viewAllMenuItems");
        return convertToJson(path, null);
    }
}
