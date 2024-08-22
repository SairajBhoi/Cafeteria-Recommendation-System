package client.util;

	
	
		import com.fasterxml.jackson.databind.JsonNode;
		import com.fasterxml.jackson.databind.ObjectMapper;
		import java.io.IOException;

		public class PrintOutToConsole {
		    public static void printToConsole(String jsonString) {
		        try {
		            // Initialize ObjectMapper to parse JSON
		            ObjectMapper mapper = new ObjectMapper();
		            
		            // Read JSON from the input string
		            JsonNode root = mapper.readTree(jsonString);
		            
		            // Print JSON tree recursively
		            printJsonNode(root);
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		    
		    // Method to recursively print JSON nodes
		    private static void printJsonNode(JsonNode node) {
		        if (node.isObject()) {
		            // If node is an object, print each field
		            System.out.println("\n\n");
		            node.fields().forEachRemaining(entry -> {
		                System.out.println(entry.getKey() + ": " + entry.getValue());
		            });
		        } else if (node.isArray()) {
		           
		            System.out.println("Array:");
		            for (JsonNode element : node) {
		                printJsonNode(element); 
		            }
		        } else {
		            // Otherwise, print as primitive value
		            System.out.println("Primitive Value: " + node.asText());
		        }
		    }
	
	
		    public static void print(String jsonString) {
		        // Remove leading and trailing whitespace from the JSON string
		        jsonString = jsonString.trim();
		        
		        // Remove outermost square brackets [] if present
		        if (jsonString.startsWith("[") && jsonString.endsWith("]")) {
		            jsonString = jsonString.substring(1, jsonString.length() - 1).trim();
		        }
		        
		        // Split the JSON array into individual JSON objects
		        String[] objects = jsonString.split("\\},\\s*\\{");

		        // Iterate over each JSON object
		        for (int i = 0; i < objects.length; i++) {
		            String object = objects[i].trim();
		            
		            // Ensure each object has curly braces
		            if (!object.startsWith("{")) {
		                object = "{" + object;
		            }
		            if (!object.endsWith("}")) {
		                object = object + "}";
		            }
		            
		            // Split each object into individual key-value pairs based on commas
		            String[] keyValuePairs = object.split(",\\s*");

		            // Iterate over each key-value pair and print them
		            for (String pair : keyValuePairs) {
		                // Split each pair into key and value based on colon
		                String[] parts = pair.split(":\\s*", 2); // Limit split to 2 parts to handle values with colons
		                
		                if (parts.length == 2) {
		                    // Trim any leading or trailing quotes from keys and values
		                    String key = parts[0].trim().replaceAll("^\"|\"$", "");
		                    String value = parts[1].trim().replaceAll("^\"|\"$", "");
		                    
		                    // Print key and value
		                    System.out.println(key + ": " + value);
		                }
		            }
		            
		            // Print separator between objects, except after the last object
		            if (i < objects.length - 1) {
		                System.out.println("-------------------------------------");
		            }
		        }
		    }




	
}


