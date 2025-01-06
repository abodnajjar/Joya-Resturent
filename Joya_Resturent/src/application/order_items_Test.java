package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class order_items_Test {

	private  Connection connection ;
	private  Statement statement ;
	
	public order_items_Test() {
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
	
	
	public void addNewOrderItem(int o_id, int i_id, int quantity, double price) {
	    String query = "INSERT INTO order_items (OrderID, MenuID, Quantity, OrderPrice) " +
	                   "VALUES (?, ?, ?, ?) " +
	                   "ON DUPLICATE KEY UPDATE " +
	                   "Quantity = Quantity + VALUES(Quantity), " +
	                   "OrderPrice = OrderPrice + VALUES(OrderPrice)";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setInt(1, o_id);
	        preparedStatement.setInt(2, i_id);
	        preparedStatement.setInt(3, quantity);
	        preparedStatement.setDouble(4, price);
	        preparedStatement.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	 public void deleteOrder_iteam(int o_id,int i_id) {
	        String query = "DELETE FROM order_items WHERE OrderID= ? and MenuID=? ";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            preparedStatement.setInt(1,o_id);
	            preparedStatement.setInt(2,i_id);
	            preparedStatement.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	

	    public void update_Quatity_Price(int o_id,int i_id,int q,double price ) {
	        String query = "UPDATE order_items SET Quantity = ?,OrderPrice= ? WHERE OrderID= ? and MenuID=?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        	 preparedStatement.setInt(1,q);
	            preparedStatement.setDouble(2, price);
	            preparedStatement.setInt(3,o_id);
	            preparedStatement.setInt(4,i_id);
	            preparedStatement.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    public List<order_items> getIngredientsForMenuItem(int order_id) {
		    List<order_items> order_items = new ArrayList<>();
		    String query = "SELECT m.`OrderID` , m.`MenuID` ,m.`Quantity` ,m.`OrderPrice`" +
		                   "FROM menu_item AS i " +
		                   "JOIN order_items AS m ON i.`MenuID`= m.`MenuID`" +
		                   "WHERE m.`OrderID` = ?";

		    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
		        // Set the MenuID parameter
		        preparedStatement.setInt(1,order_id);

		        // Execute the query and retrieve the result set
		        try (ResultSet resultSet = preparedStatement.executeQuery()) {
		            // Loop through the result set
		            while (resultSet.next()) {
		                int ingredientID = resultSet.getInt("OrderID");
		                int menua_id = resultSet.getInt("MenuID");
		                int qantity= resultSet.getInt("Quantity");
		                double cost = resultSet.getInt("OrderPrice");
		                order_items.add(new order_items(ingredientID, menua_id,qantity,cost));
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return order_items;
		}
}
