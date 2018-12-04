package com.placepass.product.domain.product;

public class Policy {
	
	private boolean childAllowed;
	
	private Integer minChildAge;
	
	private Integer maxChildAge;
	
	private String childPolicyMsg;
	
	
	public boolean isChildAllowed() {
		return childAllowed;
	}

	public void setChildAllowed(boolean childAllowed) {
		this.childAllowed = childAllowed;
	}

	public Integer getMinChildAge() {
		return minChildAge;
	}

	public void setMinChildAge(Integer minChildAge) {
		this.minChildAge = minChildAge;
	}

	public Integer getMaxChildAge() {
		return maxChildAge;
	}

	public void setMaxChildAge(Integer maxChildAge) {
		this.maxChildAge = maxChildAge;
	}

	public String getChildPolicyMsg() {
		return childPolicyMsg;
	}

	public void setChildPolicyMsg(String childPolicyMsg) {
		this.childPolicyMsg = childPolicyMsg;
	}

}
