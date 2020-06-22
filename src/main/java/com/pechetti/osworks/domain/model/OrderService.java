package com.pechetti.osworks.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.pechetti.osworks.domain.ValidationGroups;
import com.pechetti.osworks.domain.exception.DomainException;
import com.sun.istack.NotNull;

@Entity
public class OrderService {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Valid
	@ConvertGroup(from = Default.class, to = ValidationGroups.ClientId.class)
	@NotNull
	@ManyToOne
	private Client client;
	
	@NotBlank
	private String description;
	
	@NotNull
	private BigDecimal price;
	
	@JsonProperty(access = Access.READ_ONLY)
	@Enumerated(EnumType.STRING)
	private OrderServiceStatus status;

	@JsonProperty(access = Access.READ_ONLY)
	private OffsetDateTime openDate;
	
	@JsonProperty(access = Access.READ_ONLY)
	private OffsetDateTime endDate;
	
	@OneToMany(mappedBy = "orderService")
	private List<Note> notes = new ArrayList<>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
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
	public List<Note> getNotes() {
		return notes;
	}
	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderService other = (OrderService) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public boolean isOpen() {
		return OrderServiceStatus.OPEN.equals(getStatus());
	}
	
	/*
	public boolean isCanceled() {
		return OrderServiceStatus.CANCELED.equals(getStatus());
	}
	
	public boolean isFinished() {
		return OrderServiceStatus.FINISHED.equals(getStatus());
	}
	*/
	
	public void cancel() {
		if (!isOpen()) {
			throw new DomainException("The service order cannot be canceled");
		}
		
		setStatus(OrderServiceStatus.CANCELED);
		setEndDate(OffsetDateTime.now());
	}	
	
	public void finish() {
		if (!isOpen()) {
			throw new DomainException("The service order cannot be finalized");
		}
		
		setStatus(OrderServiceStatus.FINISHED);
		setEndDate(OffsetDateTime.now());
	}	
}
