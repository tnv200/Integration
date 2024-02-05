package com.fable.weatherall.Responses;

public class Feedback {
	
	private String suggestion;

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	@Override
	public String toString() {
		return "Feedback [suggestion=" + suggestion + "]";
	}


}
