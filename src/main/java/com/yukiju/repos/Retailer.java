package com.yukiju.repos;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "retailers")
public class Retailer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank(message = "Must provide a retailer name")
	@Column(unique = true, nullable = false)
	private String retailer;
	
	@ManyToMany
	@JoinTable(name="retailers_products", joinColumns = { @JoinColumn(name =
		"retailer_id") }, inverseJoinColumns = {
	@JoinColumn(name = "product_id") })
	private List<Product> products;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRetailer() {
		return retailer;
	}

	public void setRetailer(String retailer) {
		this.retailer = retailer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((retailer == null) ? 0 : retailer.hashCode());
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
		Retailer other = (Retailer) obj;
		if (id != other.id)
			return false;
		if (retailer == null) {
			if (other.retailer != null)
				return false;
		} else if (!retailer.equals(other.retailer))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Retailer [id=" + id + ", retailer=" + retailer + "]";
	}

	public Retailer(int id, @NotBlank(message = "Must provide a retailer name") String retailer) {
		super();
		this.id = id;
		this.retailer = retailer;
	}

	public Retailer() {
		super();
	}

}
