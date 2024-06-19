module CafeteriaRecommendationSystem {
	exports server;
	exports client;

	requires java.sql;
	requires com.fasterxml.jackson.databind;
	requires json.simple;
	requires com.fasterxml.jackson.core;
	opens server to com.fasterxml.jackson.databind;
	opens client to com.fasterxml.jackson.databind;
}