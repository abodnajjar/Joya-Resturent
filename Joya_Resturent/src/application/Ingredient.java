package application;

public class Ingredient {
	 private int id;
	 private String name;
	 private String supplier;
	 private double quantity_in_stock;
	 
	public Ingredient() {
		super();
	}
	public Ingredient(int id, String name, String supplier, double quantity_in_stock) {
		super();
		this.id = id;
		this.name = name;
		this.supplier = supplier;
		this.quantity_in_stock = quantity_in_stock;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public double getQuantity_in_stock() {
		return quantity_in_stock;
	}
	public void setQuantity_in_stock(double quantity_in_stock) {
		this.quantity_in_stock = quantity_in_stock;
	}
	 
	 
	 
}
