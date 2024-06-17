package server;

public class FeedbackService {
	private Feedback feedback;
	
	 public String addFeedback(String  data) {
		 String jsonResponse;
			try {
				feedback = JsonStringToObject.fromJsonToObject(data, Feedback.class);
				EmployeeFeedback empFeedback = new EmployeeFeedback();
				jsonResponse=empFeedback.addFeedback(feedback);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				jsonResponse = "{\"error\": \"" + e.getMessage() + "\"}";
			}
			return jsonResponse;
		    }

}
