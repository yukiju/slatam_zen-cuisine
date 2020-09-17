package com.yukiju.repos;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "foodtypes")
public class FoodType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank(message = "Must provide a type name")
	@Column(unique = true, nullable = false)
	private String foodType;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "foodType")
	private List<Product> products;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFoodType() {
		return foodType;
	}

	public void setFoodType(String type) {
		this.foodType = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((foodType == null) ? 0 : foodType.hashCode());
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
		FoodType other = (FoodType) obj;
		if (id != other.id)
			return false;
		if (foodType == null) {
			if (other.foodType != null)
				return false;
		} else if (!foodType.equals(other.foodType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FoodTypes [id=" + id + ", foodType=" + foodType + "]";
	}

	public FoodType(int id, @NotBlank(message = "Must provide a type name") String type) {
		super();
		this.id = id;
		this.foodType = type;
	}

	public FoodType() {
		super();
	}

	
}
