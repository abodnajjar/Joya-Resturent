package application;

public class menu_item_ingredient {
int MenuID;
int IngredientId;

public menu_item_ingredient() {
	super();
}


public menu_item_ingredient(int menuID, int ingredientId) {

	this.MenuID = menuID;
	this.IngredientId = ingredientId;
}


public int getMenuID() {
	return MenuID;
}
public void setMenuID(int menuID) {
	MenuID = menuID;
}
public int getIngredientId() {
	return IngredientId;
}
public void setIngredientId(int ingredientId) {
	IngredientId = ingredientId;
}

}
