package com.fable.weatherall.Controllers;

public class RedirectException extends Exception {
	private String redirectUrl;

    public RedirectException(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

}
