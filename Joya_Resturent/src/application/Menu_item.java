package application;

public class Menu_item {
	 private int id;
	 private String name;
	 private String descirption;
	 private int ingredient_id;
	 
	public Menu_item() {
		super();
	}
	public Menu_item(int id, String name, String descirption, int ingredient_id) {
		super();
		this.id = id;
		this.name = name;
		this.descirption = descirption;
		this.ingredient_id = ingredient_id;
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
	 
	 
}
