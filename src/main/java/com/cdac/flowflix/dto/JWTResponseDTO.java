package com.cdac.flowflix.dto;

public class JWTResponseDTO {

	public String token;

	public JWTResponseDTO() {

	}

// © FlowFlix OTT Platform | Author: Mahesh Ganesh Kajale <maheshkajale2112@gmail.com> | Unauthorized use prohibited.


	public JWTResponseDTO(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
