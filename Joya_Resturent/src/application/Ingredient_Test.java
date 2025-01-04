package application;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
public class Ingredient_Test {
	private  Connection conn ;
	private  Statement statement ;

	public Ingredient_Test() {
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

	public Ingredient addIngredientToDatabase(Ingredient g) {
		try (PreparedStatement pstmt = conn.prepareStatement(
				"INSERT INTO ingredient (IngredientName, IngredientSupplier, quantity_in_stock) VALUES (?, ?, ?)",Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, g.getName());
			pstmt.setString(2, g.getSupplier());
			pstmt.setDouble(3, g.getQuantity_in_stock());	
			int rowsInserted =  pstmt.executeUpdate();
			if (rowsInserted > 0) {
				//System.out.println("A new payment was inserted successfully!");
				try (ResultSet generatedKeys =  pstmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						int paymentID = generatedKeys.getInt(1); // Get the generated PaymentID
						g.setId(paymentID);
					}
				}
			}
		} catch (SQLException e) {
			showErrorDialog("Error adding ingredient to database.", e.getMessage());
		}
		return g;
	}

	public void deleteSelectedIngredient(int id ) {
		try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ingredient WHERE IngredientId = ?")) {
			pstmt.setInt(1, id);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			showErrorDialog("Error deleting ingredient from database.", e.getMessage());
		}
	}

	public void updateIngredientName(int id, String newName) {
		updateField("IngredientName", newName, id);
	}
	public void updateIngredientSupplier(int id, String newName) {
		updateField("IngredientSupplier", newName, id);
	}

	public void updateIngredientQuantity(int id, double quantity) {
		updateField("quantity_in_stock",quantity, id);
	}

	private void updateField(String fieldName, Object newValue, int customerId) {
		String query = "UPDATE ingredient SET " + fieldName + " = ? WHERE IngredientId = ?";
		try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
			preparedStatement.setObject(1, newValue);
			preparedStatement.setInt(2, customerId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void showErrorDialog(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public List<Ingredient> loadIngredientsFromDatabase() {
		List<Ingredient> ingredientList = new ArrayList<>();
		String query = "SELECT * FROM ingredient";
		try (ResultSet resultSet = statement.executeQuery(query)) {
			while (resultSet.next()) {
				int ingredientId = resultSet.getInt("IngredientId");
				String name = resultSet.getString("IngredientName");
				String ingredientSupplier = resultSet.getString("IngredientSupplier");
				Double ingredientQuantity = resultSet.getDouble("quantity_in_stock");
				ingredientList.add(new Ingredient(ingredientId, name, ingredientSupplier,ingredientQuantity));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ingredientList;
	}


}