package application;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
public class Order_Test {

	private  Connection connection ;
	private  Statement statement ;

	public Order_Test() {
		try {
			// Initialize the connection and statement inside the constructor
			connection = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/project_DataBasse?useSSL=false", 
					"root", 
					"computer2005"
					);
			statement = connection.createStatement();
			System.out.println("Connection successful!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Order insertOrder(Order o) {

		String sql = "INSERT INTO orders (OrderDate,OrderTime,CustomerID,EmployeeID,PaymentID) "
				+ "VALUES (? , ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			// Set parameters

			preparedStatement.setString(1,o.getDate());
			preparedStatement.setString(2,o.getTime());
			preparedStatement.setInt(3,o.getCustomer_id());
			preparedStatement.setInt(4,o.getEmployee_id());
			preparedStatement.setInt(5,o.getPayment_id());
			// Execute the query
			int rowsInserted = preparedStatement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("A new order was inserted successfully!");

				// Retrieve the generated key
				try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						int orderID = generatedKeys.getInt(1); // Get the generated PaymentID
						o.setId(orderID);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; // Return null if the insertion fails
	}
	
	public Order insertOrderT(Order o) {

		String sql = "INSERT INTO orders (OrderDate,OrderTime,EmployeeID) "
				+ "VALUES (? , ?, ?)";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			// Set parameters

			preparedStatement.setString(1,o.getDate());
			preparedStatement.setString(2,o.getTime());
			preparedStatement.setInt(3,o.getEmployee_id());
		
			// Execute the query
			int rowsInserted = preparedStatement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("A new order was inserted successfully!");

				// Retrieve the generated key
				try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						int orderID = generatedKeys.getInt(1); // Get the generated PaymentID
						o.setId(orderID);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; // Return null if the insertion fails
	}

	public void deleteSelected_iteam(int order_id) {
		try (PreparedStatement pstmt =connection.prepareStatement("DELETE FROM orders WHERE OrderID= ? ")) {
			pstmt.setInt(1,  order_id );
			pstmt.executeUpdate();

		} catch (SQLException e) {
			showErrorDialog("Error deleting ingredient from database.", e.getMessage());
		}
	}

	public void updateCustomer_id(int order_id, int new_customer_id) {
		updateField("CustomerID", new_customer_id,order_id);
	}

	public void updatepayment_id(int order_id,int payment_id) {
		updateField("PaymentID", payment_id, order_id);
	}
	public void updateorder_Date(int order_id,String date) {
		updateField("PaymentID",date, order_id);
	}
	public void updateorder_time(int order_id,String time) {
		updateField("OrderTime",time, order_id);
	}

	private void updateField(String fieldName, Object newValue, int order_Id) {
		String query = "UPDATE orders SET " + fieldName + " = ? WHERE OrderID = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setObject(1, newValue);
			preparedStatement.setInt(2, order_Id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public List<Order> getAllCustomers() {
		List<Order> order_list = new ArrayList<>();
		String query = "SELECT * FROM orders";
		try (ResultSet resultSet = statement.executeQuery(query)) {
			while (resultSet.next()) {
				int order_id = resultSet.getInt("OrderID");
				String date = resultSet.getString("OrderDate");
				String time = resultSet.getString("OrderTime");
				int customer_id = resultSet.getInt("CustomerID");
				int employee_id = resultSet.getInt("EmployeeID");
				int payment_id = resultSet.getInt("PaymentID");

				order_list.add(new Order(order_id, date,time,customer_id,employee_id, payment_id ));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return order_list;
	}
	public void showErrorDialog(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
	}
	public static void main(String[] args) {

	}
}
