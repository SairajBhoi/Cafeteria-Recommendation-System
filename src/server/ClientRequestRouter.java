package server;

public class ClientRequestRouter {

   
   

    public String route(String clientRequest) {
        String response = null;

        try {
            String path = JsonStringToObject.getPath(clientRequest);
            String data = JsonStringToObject.getData(clientRequest);

            switch(path) {
            case  "/authenticate" :
            	
            	AuthenticationService authserv = new AuthenticationService();
            	response=authserv.authenticate(data);
            	break;
            }
            } catch (Exception e) {
            System.err.println("Error processing client request: " + e.getMessage());
            response = "Error processing request";
        }

        return response;
    }
}