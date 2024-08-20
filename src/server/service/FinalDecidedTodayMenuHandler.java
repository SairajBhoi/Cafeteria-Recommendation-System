package server.service;

import java.time.LocalDate;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.model.TodayMenu;
import server.databaseoperation.FinalDecidedTodaysMenuDatabaseOperator;
import server.util.JsonConverter;
import server.util.JsonStringToObject;

public class FinalDecidedTodayMenuHandler {
    private static final Logger logger = Logger.getLogger(FinalDecidedTodayMenuHandler.class.getName());

    public String addFinalResultMenu(String data) {
        String jsonResponse;
        try {
            TodayMenu todayMenu = JsonStringToObject.fromJsonToObject(data, TodayMenu.class);
            FinalDecidedTodaysMenuDatabaseOperator finalResultMenuDAO = new FinalDecidedTodaysMenuDatabaseOperator();
            String addStatusMessage = finalResultMenuDAO.addFinalDecidedMenu(todayMenu);
            boolean status = addStatusMessage.contains("Successfully");
            String message = status ? "Success" : "Failed";

            jsonResponse = JsonConverter.convertStatusAndMessageToJson(message, addStatusMessage);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurred during addFinalResultMenu: {0}", e.getMessage());
            jsonResponse = JsonConverter.convertStatusAndMessageToJson("error", e.getMessage());
        }
        return jsonResponse;
    }

    public String viewFinalResultMenu() {
        String jsonResponse;
        try {
            LocalDate nextDay = LocalDate.now().plusDays(1);
            Date nextDayDate = Date.valueOf(nextDay);
            String date = nextDayDate.toString();

            FinalDecidedTodaysMenuDatabaseOperator finalResultMenuDAO = new FinalDecidedTodaysMenuDatabaseOperator();
            List<TodayMenu> menuItems = finalResultMenuDAO.getMenuItems(date);

            if (menuItems == null || menuItems.isEmpty()) {
                jsonResponse = JsonConverter.convertStatusAndMessageToJson("info", "Not yet prepared");
            } else {
                jsonResponse = JsonConverter.convertObjectToJson(menuItems);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurred during viewFinalResultMenu: {0}", e.getMessage());
            jsonResponse = JsonConverter.convertStatusAndMessageToJson("error", e.getMessage());
        }
        return jsonResponse;
    }

    public String viewTodaysMenu() {
        String jsonResponse;
        try {
            LocalDate currentDate = LocalDate.now();
            String date = currentDate.toString();

            FinalDecidedTodaysMenuDatabaseOperator finalResultMenuDAO = new FinalDecidedTodaysMenuDatabaseOperator();
            List<TodayMenu> menuItems = finalResultMenuDAO.getMenuItems(date);

            if (menuItems == null || menuItems.isEmpty()) {
                jsonResponse = JsonConverter.convertStatusAndMessageToJson("info", "Not yet prepared");
            } else {
                jsonResponse = JsonConverter.convertObjectToJson(menuItems);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurred during viewTodaysMenu: {0}", e.getMessage());
            jsonResponse = JsonConverter.convertStatusAndMessageToJson("error", e.getMessage());
        }
        return jsonResponse;
    }
}
