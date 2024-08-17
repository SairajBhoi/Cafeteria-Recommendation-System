package test;



import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import server.DatabaseOperation.UserProfileDatabaseOperator;
import server.model.UserPreference;
import server.util.JsonConverter;
import server.util.JsonStringToObject;
import server.service.UserProfile;

public class UserProfileTest {

    @Mock
    private UserProfileDatabaseOperator userProfileDatabaseOperator;

    @InjectMocks
    private UserProfile userProfile;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUserProfile_Success() {
        // Test data
        String inputJson = "{\"userID\":\"EMP001\"}";
        UserPreference userPreference = new UserPreference();
        userPreference.setUserID("EMP001");
        userPreference.setDietaryPreference("Vegetarian");
        userPreference.setPreferredSpiceLevel("High");
        userPreference.setPreferredCuisine("Other");
        userPreference.setHasSweetTooth(false);

        // Mock behavior
        when(JsonStringToObject.fromJsonToObject(inputJson, UserPreference.class)).thenReturn(userPreference);
        when(userProfileDatabaseOperator.getUserPreference("EMP001")).thenReturn(userPreference);

        // Call the method
        String result = userProfile.getUserProfile(inputJson);
        String expected = JsonConverter.convertObjectToJson(userPreference);

        // Verify
        assertEquals(expected, result);
    }

    @Test
    public void testGetUserProfile_ProfileNotFound() {
        // Test data
        String inputJson = "{\"userID\":\"EMP004\"}";
        UserPreference userPreference = new UserPreference();
        userPreference.setUserID("EMP004");
        userPreference.setDietaryPreference("Vegetarian");
        userPreference.setPreferredSpiceLevel("Medium");
        userPreference.setPreferredCuisine("North Indian");
        userPreference.setHasSweetTooth(true);

        // Mock behavior
        when(JsonStringToObject.fromJsonToObject(inputJson, UserPreference.class)).thenReturn(userPreference);
        when(userProfileDatabaseOperator.getUserPreference("EMP004")).thenReturn(null);

        // Call the method
        String result = userProfile.getUserProfile(inputJson);
        String expected = JsonConverter.convertStatusAndMessageToJson("error", "Profile not set");

        // Verify
        assertEquals(expected, result);
    }

    @Test
    public void testGetUserProfile_Exception() {
        // Test data
        String inputJson = "{\"userID\":\"EMP001\"}";
        UserPreference userPreference = new UserPreference();
        userPreference.setUserID("EMP001");
        userPreference.setDietaryPreference("Vegetarian");
        userPreference.setPreferredSpiceLevel("High");
        userPreference.setPreferredCuisine("Other");
        userPreference.setHasSweetTooth(false);

        // Mock behavior
        when(JsonStringToObject.fromJsonToObject(inputJson, UserPreference.class)).thenReturn(userPreference);
        when(userProfileDatabaseOperator.getUserPreference("EMP001")).thenThrow(new RuntimeException("Database error"));

        // Call the method
        String result = userProfile.getUserProfile(inputJson);
        String expected = JsonConverter.convertStatusAndMessageToJson("error", "Database error");

        // Verify
        assertEquals(expected, result);
    }

    @Test
    public void testUpdateUserProfile_Success() {
        // Test data
        String inputJson = "{\"userID\":\"EMP002\",\"dietaryPreference\":\"Vegetarian\",\"preferredSpiceLevel\":\"Medium\",\"preferredCuisine\":\"North Indian\",\"hasSweetTooth\":true}";
        UserPreference userPreference = new UserPreference();
        userPreference.setUserID("EMP002");
        userPreference.setDietaryPreference("Vegetarian");
        userPreference.setPreferredSpiceLevel("Medium");
        userPreference.setPreferredCuisine("North Indian");
        userPreference.setHasSweetTooth(true);
        String responseMessage = "Profile updated successfully";

        // Mock behavior
        when(JsonStringToObject.fromJsonToObject(inputJson, UserPreference.class)).thenReturn(userPreference);
        when(userProfileDatabaseOperator.updateOrAddUserPreference(userPreference)).thenReturn(responseMessage);

        // Call the method
        String result = userProfile.updateUserProfile(inputJson);
        String expected = JsonConverter.convertStatusAndMessageToJson("success", responseMessage);

        // Verify
        assertEquals(expected, result);
    }

    @Test
    public void testUpdateUserProfile_Exception() {
        // Test data
        String inputJson = "{\"userID\":\"EMP002\",\"dietaryPreference\":\"Vegetarian\",\"preferredSpiceLevel\":\"Medium\",\"preferredCuisine\":\"North Indian\",\"hasSweetTooth\":true}";
        UserPreference userPreference = new UserPreference();
        userPreference.setUserID("EMP002");
        userPreference.setDietaryPreference("Vegetarian");
        userPreference.setPreferredSpiceLevel("Medium");
        userPreference.setPreferredCuisine("North Indian");
        userPreference.setHasSweetTooth(true);

        // Mock behavior
        when(JsonStringToObject.fromJsonToObject(inputJson, UserPreference.class)).thenReturn(userPreference);
        when(userProfileDatabaseOperator.updateOrAddUserPreference(userPreference)).thenThrow(new RuntimeException("Update error"));

        // Call the method
        String result = userProfile.updateUserProfile(inputJson);
        String expected = JsonConverter.convertStatusAndMessageToJson("error", "Update error");

        // Verify
        assertEquals(expected, result);
    }

    @Test
    public void testDeleteUserProfile_Success() {
        // Test data
        String userID = "EMP003";
        String responseMessage = "Successfully deleted the profile";

        // Mock behavior
        doNothing().when(userProfileDatabaseOperator).deleteUserPreference(userID);

        // Call the method
        String result = userProfile.deleteUserProfile(userID);
        String expected = JsonConverter.convertStatusAndMessageToJson("success", responseMessage);

        // Verify
        assertEquals(expected, result);
    }

    @Test
    public void testDeleteUserProfile_Exception() {
        // Test data
        String userID = "EMP003";

        // Mock behavior
        doThrow(new RuntimeException("Deletion error")).when(userProfileDatabaseOperator).deleteUserPreference(userID);

        // Call the method
        String result = userProfile.deleteUserProfile(userID);
        String expected = JsonConverter.convertStatusAndMessageToJson("error", "Deletion error");

        // Verify
        assertEquals(expected, result);
    }
}
