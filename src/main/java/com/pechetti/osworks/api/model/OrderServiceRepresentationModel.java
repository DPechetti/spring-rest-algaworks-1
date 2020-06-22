package com.pechetti.osworks.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.pechetti.osworks.domain.model.OrderServiceStatus;

public class OrderServiceRepresentationModel {

	private Long id;
	private ClientResumeModel client;
	private String description;
	private BigDecimal price;
	private OrderServiceStatus status;
	private OffsetDateTime openDate;
	private OffsetDateTime endDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ClientResumeModel getClient() {
		return client;
	}
	public void setClient(ClientResumeModel client) {
		this.client = client;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public OrderServiceStatus getStatus() {
		return status;
	}
	public void setStatus(OrderServiceStatus status) {
		this.status = status;
	}
	public OffsetDateTime getOpenDate() {
		return openDate;
	}
	public void setOpenDate(OffsetDateTime openDate) {
		this.openDate = openDate;
	}
	public OffsetDateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(OffsetDateTime endDate) {
		this.endDate = endDate;
	}
	
}
