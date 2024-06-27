package client.service;

import java.io.IOException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import client.Client;
import client.model.ChefMenuRollout;
import client.util.InputHandler;
import client.util.JsonConverter;
import client.util.PrintOutToConsole;
import server.util.JsonStringToObject;

public class VoteFuncationalityHandler {
  private  String requestPath;
  private  String UserID;
  private String role;
	public VoteFuncationalityHandler(String role,String userID){
		this.requestPath="/"+role;
		System.out.print(this.requestPath);
		this.role=role;
		this.UserID=userID;
	}
	
	
	VoteFuncationalityHandler(String role){
		this.requestPath="/"+role;
		this.role=role;
	}
	
	
  public void viewChefRollout(){
	  String jsonResponse = null;
	  this.requestPath="/"+this.role;
		this.requestPath=this.requestPath+"/viewChefRollout";
		System.out.print("=================");
		System.out.print(this.requestPath);
		String jsonRequest =JsonConverter.convertObjectToJson(this.requestPath, null);
		try {
		 jsonResponse= Client.requestServer(jsonRequest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		System.out.print(jsonResponse);
		 PrintOutToConsole.printToConsole(jsonResponse);
	     
		
		
	}
  
  
  
  public void viewRecommendation() {
	  this.requestPath="/"+this.role;
	  
	  
	  System.out.println("Recommendation  by system");
	  
	  String jsonrequest =JsonConverter.convertObjectToJson(requestPath, null);
	  this.requestPath=this.requestPath+"/recommendation";

	  String jsonresponse = null;
	  
	  
	  jsonrequest =JsonConverter.convertObjectToJson(requestPath, null);
	  try {
			 jsonresponse= Client.requestServer(jsonrequest);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  
	  System.out.print(jsonresponse);
	  PrintOutToConsole.printToConsole(jsonresponse);
	  this.requestPath="/"+this.role;
	  
  }
  
  public void addVote() {
	    this.requestPath =  "/" + this.role + "/viewChefRollout";   // client

	    // Fetch the rollout list
	    String jsonrequest = JsonConverter.convertObjectToJson(this.requestPath, null);
	    System.out.println("Request: " + jsonrequest);

	    String jsonresponse = null;
	    try {
	        jsonresponse = Client.requestServer(jsonrequest);
	    } catch (IOException e) {
	        System.out.println("Server connection failed.");
	        e.printStackTrace();
	        return;
	    }

	    System.out.println("Response: " + jsonresponse);

	    if (jsonresponse == null || jsonresponse.contains("Unknown request path")) {
	        System.out.println("Invalid request path or no response received: " + this.requestPath);
	        return;
	    }

	    // Clean up the response string
	    if (jsonresponse.endsWith("Array:")) {
	        jsonresponse = jsonresponse.substring(0, jsonresponse.length() - 6).trim();
	    }

	    ObjectMapper objectMapper = new ObjectMapper();
	    List<ChefMenuRollout> rollouts = null;
	    try {
	        rollouts = objectMapper.readValue(jsonresponse, new TypeReference<List<ChefMenuRollout>>() {});
	    } catch (IOException e) {
	        System.out.println("Failed to parse JSON response.");
	        e.printStackTrace();
	        return;
	    }

	    if (rollouts != null && !rollouts.isEmpty()) {
	        System.out.println("Available Rollouts:");
	        for (ChefMenuRollout rollout : rollouts) {
	            System.out.println("Rollout ID: " + rollout.getRolloutID());
	            System.out.println("Category ID: " + rollout.getCategoryID());
	            System.out.println("Category Name: " + rollout.getCategoryName());
	            System.out.println("Name of Food: " + rollout.getItemName());
	            System.out.println();
	        }

	        for (ChefMenuRollout rollout : rollouts) {
	            boolean voteDecision = false;
	            try {
	                voteDecision = InputHandler.getBooleanInput("Enter whether you want to vote for " + rollout.getItemName() + " in category " + rollout.getCategoryName());
	            } catch (IOException e) {
	                System.out.println("Failed to get user input.");
	                e.printStackTrace();
	                continue;
	            }

	            JSONObject voteJson = new JSONObject();
	            voteJson.put("userID", this.UserID);
	            voteJson.put("rolloutID", rollout.getRolloutID());
	            voteJson.put("categoryID", rollout.getCategoryID());
	            voteJson.put("voteDecision", voteDecision);

	            JSONObject requestJson = new JSONObject();
	            requestJson.put("path", "/"+this.role + "/addVote");
	            requestJson.put("data", voteJson);

	            String voteResponse = null;
	            try {
	                voteResponse = Client.requestServer(requestJson.toJSONString());
	            } catch (IOException e) {
	                System.out.println("Server connection failed.");
	                e.printStackTrace();
	                continue;
	            }

	            System.out.println("Vote Response: " + voteResponse);
	            PrintOutToConsole.printToConsole(voteResponse);
	        }
	    } else {
	        System.out.println("No rollout data available.");
	    }

	    this.requestPath = "/" + this.role;
	}

}