package RequestGateway;

import client.model.MenuItem;
import client.util.JsonConverter;

public class MenuRequestGateway {
    private String basePath;

    public MenuRequestGateway(String role) {
        this.basePath = "/" + role;
    }

    private String generatePath(String endpoint) {
        return this.basePath + endpoint;
    }

    public String createJsonRequest(String endpoint, Object data) {
        String path = generatePath(endpoint);
        return JsonConverter.convertObjectToJson(path, data);
    }

    public String createAddMenuItemRequest(MenuItem menuItem) {
        return createJsonRequest("/addMenuItem", menuItem);
    }

    public String createUpdateMenuItemRequest(MenuItem menuItem) {
        return createJsonRequest("/updateMenuItem", menuItem);
    }

    public String createUpdateFoodAvailableStatusRequest(MenuItem menuItem) {
        return createJsonRequest("/updateFoodAvailableStatus", menuItem);
    }

    public String createDeleteMenuItemRequest(MenuItem menuItem) {
        return createJsonRequest("/deleteMenuItem", menuItem);
    }

    public String createViewAllMenuItemsRequest() {
        return createJsonRequest("/viewAllMenuItems", null);
    }
}
