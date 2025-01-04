package application;

public class Menu_item {
	 private int id;
	 private String name; 
	 private String descirption;
	 private double price;
	 private int ingredient_id;  
	 
	 
	public Menu_item() {
		super();
	}
	public Menu_item( String name, String descirption,double price) {
		super();
		this.name = name;
		this.descirption = descirption;
		this.price = price;
	}
	public Menu_item(int id, String name, String descirption,double price ) {
		super();
		this.id = id;
		this.name = name;
		this.descirption = descirption;
		this.price = price;
	}
	public Menu_item(int id, String name,double price ) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
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
	public String getDescirption() {
		return descirption;
	}
	public void setDescirption(String descirption) {
		this.descirption = descirption;
	}
	public int getIngredient_id() {
		return ingredient_id;
	}
	public void setIngredient_id(int ingredient_id) {
		this.ingredient_id = ingredient_id;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "Menu_item [id=" + id + ", name=" + name + ", descirption=" + descirption + ", price=" + price
				+ ", ingredient_id=" + ingredient_id + "]";
	}
	 
	 
}
