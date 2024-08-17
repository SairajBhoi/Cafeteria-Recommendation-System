module CafeteriaRecommendationSystem {
	  exports server;
	    exports client;

	    
	    exports client.model; 
	    exports client.controller;
	    exports client.service;
	    exports client.util;
	    exports client.auth;
	    
	    
	    exports server.model; 
	    exports server.DatabaseOperation;
	    exports server.auth;
	    exports server.util;
	    exports server.service;
	    
	    
	    opens server.util to com.fasterxml.jackson.databind;
	   
	    opens server.model to com.fasterxml.jackson.databind; 
	    opens server.controller to com.fasterxml.jackson.databind; 
	    
	    opens server.DatabaseOperation to com.fasterxml.jackson.databind; 
	    opens server.service to com.fasterxml.jackson.databind; 
	    opens server.auth to com.fasterxml.jackson.databind; 
	    opens server.resources to com.fasterxml.jackson.databind; 
	    
	
	 

	    requires java.sql;
	    requires com.fasterxml.jackson.databind;
	    requires json.simple;
	    
	   
	    requires com.fasterxml.jackson.core;
		

		
		
	    
	    
	    opens server to com.fasterxml.jackson.databind;
	    opens client to com.fasterxml.jackson.databind;
	    opens client.util to com.fasterxml.jackson.databind;
	
}