package application;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;

public class menu_item_ingredient_Test {
	private  Connection conn ;
	private  Statement statement ;

	public menu_item_ingredient_Test() {
		try {
			// Initialize the connection and statement inside the constructor
			conn = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/project_DataBasse?useSSL=false", 
					"root", 
					"computer2005"
					);
			statement = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public menu_item_ingredient insert_menu_item_ingredient(menu_item_ingredient g) {
		try (PreparedStatement pstmt = conn.prepareStatement(
				"INSERT INTO menu_item_ingredient(MenuID,IngredientId) VALUES (?, ?)",Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setInt(1, g.getMenuID());
			pstmt.setInt(2, g.getIngredientId());
			int rowsInserted =  pstmt.executeUpdate();

		} catch (SQLException e) {
			showErrorDialog("Error adding ingredient to database.", e.getMessage());
		}
		return g;
	}

	public void deleteSelected_iteam(int MenuID  ,int IngredientId) {
		try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM menu_item_ingredient WHERE MenuID= ? and IngredientId =? ")) {
			pstmt.setInt(1, MenuID );
			pstmt.setInt(2,IngredientId);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			showErrorDialog("Error deleting ingredient from database.", e.getMessage());
		}
	}

	private void updateField( int MenuID , int IngredientId) {
		String query = "UPDATE menu_item_ingredient set IngredientId  = ? WHERE MenuID = ?";
		try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
			preparedStatement.setInt(1,IngredientId);
			preparedStatement.setInt(2,MenuID);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<menu_item_ingredient> load_menu_item_FromDatabase(int id) {
		List<menu_item_ingredient> Menu_item_List = new ArrayList<>();
		String query = "SELECT * FROM menu_item_ingredient WHERE MenuID = ?";

		try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
			// Set the value for the MenuID parameter
			preparedStatement.setInt(1,id);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				// Loop through the result set
				while (resultSet.next()) {
					int mid = resultSet.getInt("MenuID");
					int gid = resultSet.getInt("IngredientId");
					// Add a new menu_item_ingredient object to the list
					Menu_item_List.add(new menu_item_ingredient(mid, gid));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Menu_item_List;
	}

	public List<Menu_item> getIngredientsForMenuItem(int menuID) {
		List<Menu_item> ingredients = new ArrayList<>();
		String query = "SELECT i.IngredientId, i.IngredientName,i.price" +
				"FROM ingredient AS i " +
				"JOIN menu_item_ingredient AS m ON i.IngredientId = m.IngredientId " +
				"WHERE m.MenuID = ?";

		try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
			// Set the MenuID parameter
			preparedStatement.setInt(1, menuID);

			// Execute the query and retrieve the result set
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				// Loop through the result set
				while (resultSet.next()) {
					int MenuID= resultSet.getInt("MenuID");
					String ingredientName = resultSet.getString("MenuName");
					int price= resultSet.getInt("price");
					// Add the ingredient to the list
					ingredients.add(new Menu_item(MenuID, ingredientName,price));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ingredients;
	}
	public void showErrorDialog(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
