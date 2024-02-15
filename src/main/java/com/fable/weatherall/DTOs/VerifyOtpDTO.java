package com.fable.weatherall.DTOs;

public class VerifyOtpDTO {
	
	private String email;
    private String otp;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public VerifyOtpDTO(String email, String otp) {
		this.email = email;
		this.otp = otp;
	}
	public VerifyOtpDTO() {
		super();
	}

}
