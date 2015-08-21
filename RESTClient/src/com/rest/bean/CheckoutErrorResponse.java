package com.rest.bean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="error")
public class CheckoutErrorResponse {
	private String message;

	public CheckoutErrorResponse() {
	}

	public CheckoutErrorResponse(String message) {
		this.message = message;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
