package com.rest.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
public class CheckoutResponse {
	private String total;
	private String cart;	
	private String offers;

	public CheckoutResponse() {
	}

	public CheckoutResponse(String total, String cart, String offers) {
		this.total = total;
		this.cart = cart;
		this.offers = offers;
	}

	/**
	 * @return the total
	 */
	@XmlElement(name = "total")
	public String getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}

	/**
	 * @return the cart
	 */
	@XmlElement(name = "cartItems")
	public String getCart() {
		return cart;
	}

	/**
	 * @param cart
	 *            the cart to set
	 */
	public void setCart(String cart) {
		this.cart = cart;
	}

	/**
	 * @return the offers
	 */
	@XmlElement(name = "appliedOffers")
	public String getOffers() {
		return offers;
	}

	/**
	 * @param offers
	 *            the offers to set
	 */
	public void setOffers(String offers) {
		this.offers = offers;
	}

}
