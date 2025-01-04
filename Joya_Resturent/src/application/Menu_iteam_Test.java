package application;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
public class Menu_iteam_Test {
	private  Connection conn ;
	private  Statement statement ;

	public Menu_iteam_Test() {
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

	public Menu_item addIngre_Iteam_ToDatabase(Menu_item  g) {
		try (PreparedStatement pstmt = conn.prepareStatement(
				"INSERT INTO menu_item (MenuName,MenuDescription,price) VALUES (?, ?, ?)",Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, g.getName());
			pstmt.setString(2, g.getDescirption());
			pstmt.setDouble(3, g.getPrice());	
			int rowsInserted =  pstmt.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("A new payment was inserted successfully!");
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
	public void deleteSelected_iteam(int id ) {
		try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM menu_item  WHERE MenuID= ?")) {
			pstmt.setInt(1, id);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			showErrorDialog("Error deleting ingredient from database.", e.getMessage());
		}
	}

	public void updateMenuName(int id, String newName) {
		updateField("MenuName", newName, id);
	}
	public void updateMenuDescription(int id, String newName) {
		updateField("MenuDescription", newName, id);
	}

	public void updateMenuprice(int id, double price) {
		updateField("price",price, id);
	}

	private void updateField(String fieldName, Object newValue, int customerId) {
		String query = "UPDATE menu_item SET " + fieldName + " = ? WHERE MenuID = ?";
		try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
			preparedStatement.setObject(1, newValue);
			preparedStatement.setInt(2, customerId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Menu_item> load_menu_item_FromDatabase() {
		List<Menu_item> Menu_item_List = new ArrayList<>();
		String query = "SELECT * FROM menu_item";
		try (ResultSet resultSet = statement.executeQuery(query)) {
			while (resultSet.next()) {
				int ingredientId = resultSet.getInt("MenuID");
				String name = resultSet.getString("MenuName");
				String MenuDescription= resultSet.getString("MenuDescription");
				Double price = resultSet.getDouble("price");
				Menu_item_List .add(new Menu_item(ingredientId, name,MenuDescription,price));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Menu_item_List ;
	}

	public void showErrorDialog(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
