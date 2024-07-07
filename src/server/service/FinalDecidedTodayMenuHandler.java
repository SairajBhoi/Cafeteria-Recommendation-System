package server.service;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

import server.model.TodayMenu;
import server.DatabaseConnection;
import server.DatabaseOperation.FinalDecidedTodaysMenuDatabaseOperator;
import server.DatabaseOperation.MenuDatabaseOperator;
import server.util.JsonConverter;
import server.util.JsonStringToObject;

public class FinalDecidedTodayMenuHandler {
    
    private Connection connection;
    
   
    public FinalDecidedTodayMenuHandler() {
        DatabaseConnection dbInstance = DatabaseConnection.getInstance();
        this.connection = dbInstance.getConnection();
    }

 
    public String addFinalResultMenu(String data) throws Exception {
    	TodayMenu todayMenu= new TodayMenu();
    	 todayMenu =JsonStringToObject.fromJsonToObject(data, TodayMenu.class);
    	
        FinalDecidedTodaysMenuDatabaseOperator finalResultMenu = new FinalDecidedTodaysMenuDatabaseOperator();

        String addStatusMessage = finalResultMenu.addFinalDecidedMenu(todayMenu);

        boolean status = addStatusMessage.contains("Successfully");
    String message=null;    
if(status) {
	message="Success";
}else {
	message="failed to ";
}

        return JsonConverter.convertStatusAndMessageToJson(message, addStatusMessage);
    }
    
    
    
    
    public String viewFinalResultMenu() throws Exception {
        FinalDecidedTodaysMenuDatabaseOperator finalResultMenu = new FinalDecidedTodaysMenuDatabaseOperator();
        List<TodayMenu> menuItems = (List<TodayMenu>) finalResultMenu.getTodaysMenuItems();

        String response;
        if (menuItems == null || menuItems.isEmpty()) {
            response = JsonConverter.convertStatusAndMessageToJson("info", "Not yet prepared");
        } else {
            response = JsonConverter.convertObjectToJson(menuItems);
        }

        return response;
    }

 }





    



    
    
    
    
    