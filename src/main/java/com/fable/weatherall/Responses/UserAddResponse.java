package com.fable.weatherall.Responses;

public class UserAddResponse {
	
	String message;
	Boolean status;
	public UserAddResponse(String message, Boolean status) {

		this.message = message;
		this.status = status;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "UserAddResponse [message=" + message + ", status=" + status + "]";
	}
	

}
