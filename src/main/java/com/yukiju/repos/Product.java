package com.yukiju.repos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	// @Column(unique = true, nullable = false)
	private String upc;

	@Column(name = "quantity_purchased")
	private int quantityPurchased;

	@NotBlank(message = "Must provide a product name")
	@Column(unique = true, nullable = false)
	private String product;

	private String notes;

	@Column(name = "measure_unit")
	private String measureUnit;

	@Column(name = "date_purchased")
	private LocalDateTime datePurchased;

	@Column(name = "expiration_date")
	private LocalDateTime expirationDate;

	// number of days until product is no longer good for consumption
	@Column(name = "shelf_life")
	private int shelfLife;

	// price from the store
	@PositiveOrZero
	private double price;

	@PositiveOrZero
	private double weight;

	@ManyToOne
	@JoinColumn(name = "foodType_id")
	private FoodType foodType;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;

	@ManyToOne
	@JoinColumn(name = "storage_id")
	private Storage storagePlace;

	@Transient
	private Retailer retailer;

	@ManyToMany
	@JoinTable(name = "retailers_products", joinColumns = { @JoinColumn(name = "product_id") }, inverseJoinColumns = {
			@JoinColumn(name = "retailer_id") })
	private List<Retailer> retailers;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUpc() {
		return upc;
	}

	public void setUpc(String upc) {
		this.upc = upc;
	}

	public int getQuantityPurchased() {
		return quantityPurchased;
	}

	public void setQuantityPurchased(int quantityPurchased) {
		this.quantityPurchased = quantityPurchased;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public FoodType getFoodType() {
		return foodType;
	}

	public void setFoodType(FoodType type) {
		this.foodType = type;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getMeasureUnit() {
		return measureUnit;
	}

	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}

	public LocalDateTime getDatePurchased() {
		return datePurchased;
	}

	public void setDatePurchased(LocalDateTime datePurchased) {
		this.datePurchased = datePurchased;
	}

	public LocalDateTime getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDateTime expirationDate) {
		this.expirationDate = expirationDate;
	}

	public int getShelfLife() {
		return shelfLife;
	}

	public void setShelfLife(int shelfLife) {
		this.shelfLife = shelfLife;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<Retailer> getRetailers() {
		return retailers;
	}

	public void setRetailers(List<Retailer> retailers) {
		this.retailers = retailers;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Storage getStoragePlace() {
		return storagePlace;
	}

	public void setStoragePlace(Storage storagePlace) {
		this.storagePlace = storagePlace;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Retailer getRetailer() {
		return retailer;
	}

	public void setRetailer(Retailer retailer) {
		List<Retailer> retailers = new ArrayList<Retailer>();
		retailers.add(retailer);
		setRetailers(retailers);
		this.retailer = retailer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((datePurchased == null) ? 0 : datePurchased.hashCode());
		result = prime * result + ((expirationDate == null) ? 0 : expirationDate.hashCode());
		result = prime * result + id;
		result = prime * result + ((measureUnit == null) ? 0 : measureUnit.hashCode());
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + quantityPurchased;
		result = prime * result + shelfLife;
		result = prime * result + ((foodType == null) ? 0 : foodType.hashCode());
		result = prime * result + ((upc == null) ? 0 : upc.hashCode());
		temp = Double.doubleToLongBits(weight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Product other = (Product) obj;
		if (datePurchased == null) {
			if (other.datePurchased != null)
				return false;
		} else if (!datePurchased.equals(other.datePurchased))
			return false;
		if (expirationDate == null) {
			if (other.expirationDate != null)
				return false;
		} else if (!expirationDate.equals(other.expirationDate))
			return false;
		if (id != other.id)
			return false;
		if (measureUnit == null) {
			if (other.measureUnit != null)
				return false;
		} else if (!measureUnit.equals(other.measureUnit))
			return false;
		if (notes == null) {
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (quantityPurchased != other.quantityPurchased)
			return false;
		if (shelfLife != other.shelfLife)
			return false;
		if (foodType == null) {
			if (other.foodType != null)
				return false;
		} else if (!foodType.equals(other.foodType))
			return false;
		if (upc == null) {
			if (other.upc != null)
				return false;
		} else if (!upc.equals(other.upc))
			return false;
		if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", upc=" + upc + ", quantityPurchased=" + quantityPurchased + ", product="
				+ product + ", foodType=" + foodType + ", notes=" + notes + ", measureUnit=" + measureUnit
				+ ", datePurchased=" + datePurchased + ", expirationDate=" + expirationDate + ", shelfLife=" + shelfLife
				+ ", price=" + price + ", weight=" + weight + "]";
	}

	public Product(int id, @NotBlank(message = "Must provide a product name") String product,
			LocalDateTime datePurchased) {
		super();
		this.id = id;
		this.product = product;
		this.datePurchased = datePurchased;
	}

	public Product(int id, String upc, int quantityPurchased,
			@NotBlank(message = "Must provide a product name") String product, String notes, String measureUnit,
			LocalDateTime datePurchased, LocalDateTime expirationDate, int shelfLife, @PositiveOrZero double price,
			@PositiveOrZero double weight) {
		super();
		this.id = id;
		this.upc = upc;
		this.quantityPurchased = quantityPurchased;
		this.product = product;
		this.notes = notes;
		this.measureUnit = measureUnit;
		this.datePurchased = datePurchased;
		this.expirationDate = expirationDate;
		this.shelfLife = shelfLife;
		this.price = price;
		this.weight = weight;
	}

	public Product(int id, @NotBlank(message = "Must provide a product name") String product,
			LocalDateTime datePurchased, FoodType foodType) {
		super();
		this.id = id;
		this.product = product;
		this.datePurchased = datePurchased;
		this.foodType = foodType;
	}

	public Product(int id, String upc, int quantityPurchased,
			@NotBlank(message = "Must provide a product name") String product, FoodType type, String notes,
			String measureUnit, LocalDateTime datePurchased, LocalDateTime expirationDate, int shelfLife,
			@PositiveOrZero double price, @PositiveOrZero double weight) {
		super();
		this.id = id;
		this.upc = upc;
		this.quantityPurchased = quantityPurchased;
		this.product = product;
		this.foodType = type;
		this.notes = notes;
		this.measureUnit = measureUnit;
		this.datePurchased = datePurchased;
		this.expirationDate = expirationDate;
		this.shelfLife = shelfLife;
		this.price = price;
		this.weight = weight;
	}

	public Product(int id, String upc, int quantityPurchased,
			@NotBlank(message = "Must provide a product name") String product, FoodType type, String notes,
			String measureUnit, LocalDateTime datePurchased, LocalDateTime expirationDate, int shelfLife,
			@PositiveOrZero double price, @PositiveOrZero double weight, Retailer retailer) {
		super();
		this.id = id;
		this.upc = upc;
		this.quantityPurchased = quantityPurchased;
		this.product = product;
		this.foodType = type;
		this.notes = notes;
		this.measureUnit = measureUnit;
		this.datePurchased = datePurchased;
		this.expirationDate = expirationDate;
		this.shelfLife = shelfLife;
		this.price = price;
		this.weight = weight;
		this.retailer = retailer;
	}

	public Product(int id, String upc, int quantityPurchased,
			@NotBlank(message = "Must provide a product name") String product, FoodType type, String notes,
			String measureUnit, LocalDateTime datePurchased, LocalDateTime expirationDate, int shelfLife,
			@PositiveOrZero double price, @PositiveOrZero double weight, List<Retailer> retailers) {
		super();
		this.id = id;
		this.upc = upc;
		this.quantityPurchased = quantityPurchased;
		this.product = product;
		this.foodType = type;
		this.notes = notes;
		this.measureUnit = measureUnit;
		this.datePurchased = datePurchased;
		this.expirationDate = expirationDate;
		this.shelfLife = shelfLife;
		this.price = price;
		this.weight = weight;
		this.retailers = retailers;
	}

	public Product() {
		super();
	}

}
