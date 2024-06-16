package server;

public class ClientRequestRouter {

    private final JsonStringToObject jsonStringToObject = new JsonStringToObject();
   

    public String route(String clientRequest) {
        String response = null;

        try {
            String path = jsonStringToObject.getPath(clientRequest);
            String data = jsonStringToObject.getData(clientRequest);

           
            } catch (Exception e) {
            System.err.println("Error processing client request: " + e.getMessage());
            response = "Error processing request";
        }

        return response;
    }
}