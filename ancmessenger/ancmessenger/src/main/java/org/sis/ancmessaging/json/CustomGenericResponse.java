package org.sis.ancmessaging.json;

import java.util.ArrayList;
import java.util.List;

/**
 * A custom POJO that will be automatically converted to JSON format. 
 * <p>We can use this to send generic messages to our JqGrid, whether a request is successful or not.
 * Of course, you will use plain JavaScript to parse the JSON response. 
 */
public class CustomGenericResponse {

	/**
	 * true if successful. 
	 */
	private Boolean success;
	
	/**
	 * Any custom message, i.e, 'Your request has been processed successfully!'
	 */
	private List<String> message;
	
	public CustomGenericResponse() {
		message = new ArrayList<String>();
	}
	
	public Boolean getSuccess() {
		return success;
	}
	
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	
	public List<String> getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message.add(message);
	}
	
	
}
