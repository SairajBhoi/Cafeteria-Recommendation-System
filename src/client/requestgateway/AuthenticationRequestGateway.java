package client.requestgateway;


import client.model.User;
import client.util.JsonConverter;

public class AuthenticationRequestGateway {

    private final String basePath;

    public AuthenticationRequestGateway() {
        this.basePath = "/authenticate";
    }

    private String getBasePath() {
        return this.basePath;
    }


    private String convertToJson(String path, Object data) {
        return JsonConverter.convertObjectToJson(path, data);
    }

    public String createAuthenticateRequest(User user) {
        return convertToJson(this.getBasePath(), user);
    }
}
