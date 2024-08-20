package server.service;

import java.time.LocalDate;
import java.sql.Date;


import java.util.List;

import server.model.TodayMenu;
import server.databaseoperation.FinalDecidedTodaysMenuDatabaseOperator;

import server.util.JsonConverter;
import server.util.JsonStringToObject;

public class FinalDecidedTodayMenuHandler {
    
     
   
   

 
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
    	LocalDate nextDay = LocalDate.now().plusDays(1);
        Date nextDayDate = Date.valueOf(nextDay);
        String date =  nextDayDate.toString();
        
        FinalDecidedTodaysMenuDatabaseOperator finalResultMenu = new FinalDecidedTodaysMenuDatabaseOperator();
        List<TodayMenu> menuItems = (List<TodayMenu>) finalResultMenu.getMenuItems(date);
        
        String response;
        if (menuItems == null || menuItems.isEmpty()) {
            response = JsonConverter.convertStatusAndMessageToJson("info", "Not yet prepared");
        } else {
            response = JsonConverter.convertObjectToJson(menuItems);
        }

        return response;
    }

 





public String viewTodaysMenu() throws Exception {
    FinalDecidedTodaysMenuDatabaseOperator finalResultMenu = new FinalDecidedTodaysMenuDatabaseOperator();
    
    LocalDate currentDate = LocalDate.now();
    String date = currentDate.toString();
    List<TodayMenu> menuItems = (List<TodayMenu>) finalResultMenu.getMenuItems(date);

    String response;
    if (menuItems == null || menuItems.isEmpty()) {
        response = JsonConverter.convertStatusAndMessageToJson("info", "Not yet prepared");
    } else {
        response = JsonConverter.convertObjectToJson(menuItems);
    }

    return response;
}

}



    



    
    
    
    
    