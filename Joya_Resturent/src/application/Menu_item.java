package application;

public class Menu_item {
	 private int id;
	 private String name; 
	 private String descirption;
	 private double price;
//	 private int ingredient_id;  
	 private int count=0;
	 
	 
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

	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	public void Increment_count() {
		this.count=this.count+1;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "Menu_item [id=" + id + ", name=" + name + ", descirption=" + descirption + ", price=" + price
				+ ", ingredient_id=" +  "]";
	}
	 
	 
}
