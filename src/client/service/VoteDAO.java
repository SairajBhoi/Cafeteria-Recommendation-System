package client.service;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import client.Client;
import client.util.InputHandler;
import client.util.JsonConverter;

public class VoteDAO {
  private  String requestPath;
  private  String UserID;
  private String role;
	public VoteDAO(String role,String userID){
		this.requestPath="/"+role;
		System.out.print(this.requestPath);
		this.role=role;
	}
	
	
	VoteDAO(String role){
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
	  this.requestPath="/"+this.role;
	  
  }
  
      public void addVote() throws IOException, ParseException {
    	  this.requestPath=""+this.role;
    	  
    	  System.out.println("Engine    =================");
    	  this.viewRecommendation();
    	  

    	  System.out.println("Vote");
    	  this.requestPath=""+this.role;
    	  this.requestPath=this.requestPath+"/addVote";
    	  
         
         


String jsonrequest = JsonConverter.convertObjectToJson(requestPath, null);
System.out.println(jsonrequest);

JSONParser parser = new JSONParser();

try {
    JSONObject jsonObject = (JSONObject) parser.parse(jsonrequest);
    JSONArray rolloutList = (JSONArray) jsonObject.get("data");

    if (rolloutList != null) {
        System.out.println(rolloutList);

        for (Object obj : rolloutList) {
            JSONObject rollout = (JSONObject) obj;
            int rolloutID = ((Long) rollout.get("rolloutID")).intValue();
            String nameOfFood = (String) rollout.get("nameOfFood");
            String categoryName = (String) rollout.get("categoryName");
            int categoryID = ((Long) rollout.get("categoryID")).intValue();

            // Simulate user voting (in a real application, this would involve user interaction)
            boolean voteDecision = InputHandler.getBooleanInput("Enter whether you want to vote");

            // Send the vote to the server
            JSONObject voteJson = new JSONObject();
            voteJson.put("userID", this.UserID);
            voteJson.put("rolloutID", rolloutID);
            voteJson.put("categoryID", categoryID);
            voteJson.put("voteDecision", voteDecision);

            JSONObject requestJson = new JSONObject();
            requestJson.put("path", this.requestPath);
            requestJson.put("data", voteJson);

            String jsonresponse = null;
            try {
                jsonresponse = Client.requestServer(requestJson.toJSONString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(jsonresponse);
        }
    } else {
        System.out.println("No rollout data available.");
    }
} catch (ParseException e) {
    e.printStackTrace();
}

this.requestPath = "/" + this.role;
}
}

      
//      String jsonrequest =JsonConverter.convertObjectToJson(requestPath, null);
//      
//      System.out.println(jsonrequest);
//      
// 	 JSONParser parser = new JSONParser();
//      JSONArray rolloutList = (JSONArray) parser.parse(jsonrequest);


//         for (Object obj : rolloutList) {
//             JSONObject rollout = (JSONObject) obj;
//             int rolloutID = ((Long) rollout.get("rolloutID")).intValue();
//             String nameOfFood = (String) rollout.get("nameOfFood");
//             String categoryName = (String) rollout.get("categoryName");
//             int categoryID = ((Long) rollout.get("categoryID")).intValue();
//
//             // Simulate user voting (in a real application, this would involve user interaction)
//             boolean voteDecision =InputHandler.getBooleanInput("Enter the whether you want vote ");
//
//             // Send the vote to the server
//             JSONObject voteJson = new JSONObject();
//			voteJson.put("userID", this.UserID);
//             voteJson.put("rolloutID", rolloutID);
//             voteJson.put("categoryID", categoryID);
//             voteJson.put("voteDecision", voteDecision);
//             JSONObject requestJson = new JSONObject();
//             requestJson.put("path", this.requestPath);
//             requestJson.put("data", voteJson);
//             
//          
//    	String  jsonresponse = null;
//		try {
//			jsonresponse = Client.requestServer(requestJson.toJSONString());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	  
//    	  System.out.println(jsonresponse);
//    	  
//
//    	  
//	  
//      }
//
//    	
//         this.requestPath="/"+this.role;
//         }
//      
//}



	
	
	
	
	
	
	
	

