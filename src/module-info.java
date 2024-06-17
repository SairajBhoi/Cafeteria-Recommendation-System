module CafeteriaRecommendationSystem {
	exports server;
	exports client;

	requires java.sql;
	requires com.fasterxml.jackson.databind;
	opens server to com.fasterxml.jackson.databind;
	opens client to com.fasterxml.jackson.databind;
}