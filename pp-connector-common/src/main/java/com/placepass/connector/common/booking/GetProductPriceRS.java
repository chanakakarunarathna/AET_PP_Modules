package com.placepass.connector.common.booking;

import com.placepass.connector.common.common.BaseRS;

public class GetProductPriceRS extends BaseRS {
	
	private Total total;

	public Total getTotal() {
		return total;
	}

	public void setTotal(Total total) {
		this.total = total;
	}

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "GetProductPriceRS [" + (total != null ? "total=" + total : "") + "]";
    }

}
