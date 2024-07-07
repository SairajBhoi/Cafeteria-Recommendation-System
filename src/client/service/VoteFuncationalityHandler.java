package client.service;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import client.Client;
import client.model.ChefMenuRollout;
import client.util.InputHandler;
import client.util.JsonConverter;
import client.util.JsonStringToObject;
import client.util.PrintOutToConsole;

import org.json.simple.JSONObject;

public class VoteFuncationalityHandler {
    private String requestPath;
    private String UserID;
    private String role;

    public VoteFuncationalityHandler(String role, String userID) {
        this.role = role;
        this.UserID = userID;
        this.requestPath = "/" + role;
    }

    public VoteFuncationalityHandler(String role) {
        this.role = role;
        this.requestPath = "/" + role;
    }

    public void viewChefRollout() {
        try {
            resetRequestPath();
            this.requestPath += "/viewChefRollout";
            String jsonRequest = JsonConverter.convertObjectToJson(this.requestPath, null);

            String jsonResponse = Client.requestServer(jsonRequest);
            PrintOutToConsole.printToConsole(jsonResponse);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            resetRequestPath();
        }
    }

    public void viewRecommendation() {
        try {
            resetRequestPath();
            this.requestPath += "/recommendation";
            String jsonRequest = JsonConverter.convertObjectToJson(this.requestPath, null);

            String jsonResponse = Client.requestServer(jsonRequest);
            PrintOutToConsole.printToConsole(jsonResponse);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            resetRequestPath();
        }
    }

    public void addVote() {
        try {
            resetRequestPath();
            this.requestPath += "/viewChefRollout";
            
            
            String jsonRequest = JsonConverter.convertObjectToJson(this.requestPath, null);

            String jsonResponse = Client.requestServer(jsonRequest);
            
            if (jsonResponse == null || jsonResponse.contains("Unknown request path")) {
                System.out.println("Invalid request path or no response received: " + this.requestPath);
                return;
            }
            else {
            	String status = JsonStringToObject.getValueFromData("status", jsonResponse);
            if ("info".equals(status)) {
                return ;
            }
            }
            
           
            

            ObjectMapper objectMapper = new ObjectMapper();
            List<ChefMenuRollout> rollouts = objectMapper.readValue(jsonResponse, new TypeReference<List<ChefMenuRollout>>() {});
            
            System.out.println("Recommendation");
            resetRequestPath();
//           this.requestPath += "/recommendationOnChefRooloout";
//            
//          
//            
//            
//            
//            String jsonRequestRecommendation = JsonConverter.convertObjectToJson(this.requestPath, null);
//            String jsonResponseRecommendation= Client.requestServer(jsonRequestRecommendation);
            
            
            
            
            
            this.requestPath += "/recommendationOnPreference";
            
            
            
            String jsonRequestRecommendation = JsonConverter.convertObjectToJson(this.requestPath,null);
            String jsonuserID= JsonConverter.createJson("UserID", this.UserID);
            
            
            System.out.println("jsonRequestRecommendation=============="+jsonRequestRecommendation);
            
            System.out.println("jsonuserID ==============="+jsonuserID);
            
            String jsonrequest=JsonConverter.addJsonObjectToDataField(jsonRequestRecommendation, jsonuserID);
            System.out.println("jsonuserID ==============="+jsonrequest);
            
            String jsonResponseRecommendation= Client.requestServer(jsonrequest);
            PrintOutToConsole.printToConsole(jsonResponseRecommendation);
            
            
            
            resetRequestPath();
            if (rollouts != null && !rollouts.isEmpty()) {
                for (ChefMenuRollout rollout : rollouts) {
                    boolean voteDecision = InputHandler.getBooleanInput("Enter whether you want to vote for " + rollout.getItemName() + " in category " + rollout.getCategoryName());

                    JSONObject voteJson = new JSONObject();
                    voteJson.put("userID", this.UserID);
                    voteJson.put("rolloutID", rollout.getRolloutID());
                    voteJson.put("categoryID", rollout.getCategoryID());
                    voteJson.put("voteDecision", voteDecision);

                    JSONObject requestJson = new JSONObject();
                    requestJson.put("path", "/" + this.role + "/addVote");
                    requestJson.put("data", voteJson);

                    String voteResponse = Client.requestServer(requestJson.toJSONString());
                    PrintOutToConsole.printToConsole(voteResponse);
                }
            } else {
                System.out.println("No rollout data available.");
            }
        } catch (IOException e) {
            System.out.println("Server connection failed.");
            e.printStackTrace();
        } finally {
            resetRequestPath();
        }
    }

    private void resetRequestPath() {
        this.requestPath = "/" + this.role;
    }
}
