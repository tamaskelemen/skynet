package com.skynet.dto;

import java.util.Date;

public class ContractDTO {
	private String[] description;
	private Double price;

	public ContractDTO() {
	}

	public String[] getDescription() {
		return description;
	}

	public void setDescription(String[] description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
