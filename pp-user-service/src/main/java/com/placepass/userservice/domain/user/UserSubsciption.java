package com.placepass.userservice.domain.user;

/**
 * The Class UserSubsciption.
 */
public class UserSubsciption {

	/** The subscribed to newsletter. */
	private boolean subscribedToNewsletter;

	/**
	 * Instantiates a new user subsciption.
	 *
	 * @param subscribedToNewsletter the subscribed to newsletter
	 */
	public UserSubsciption(boolean subscribedToNewsletter) {
		super();
		this.subscribedToNewsletter = subscribedToNewsletter;
	}

	/**
	 * Checks if is subscribed to newsletter.
	 *
	 * @return true, if is subscribed to newsletter
	 */
	public boolean isSubscribedToNewsletter() {
		return subscribedToNewsletter;
	}

	/**
	 * Sets the subscribed to newsletter.
	 *
	 * @param subscribedToNewsletter the new subscribed to newsletter
	 */
	public void setSubscribedToNewsletter(boolean subscribedToNewsletter) {
		this.subscribedToNewsletter = subscribedToNewsletter;
	}
	
	
	
	
	
}
