package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; 
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Customer_Test {

    private Connection connection;
    private Statement statement;

    public Customer_Test() {
    	try {
            // Initialize the connection and statement inside the constructor
            connection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/project_DataBasse?useSSL=false", 
                "root", 
                "computer2005"
            );
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addNewCustomer(Customer c) {
        String query = "INSERT INTO customer (customerName, customerPoints) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, c.getName());
            preparedStatement.setDouble(2, c.getPointer());    
        	int rowsInserted =  preparedStatement.executeUpdate();
			if (rowsInserted > 0) {
				//System.out.println("A new payment was inserted successfully!");
				try (ResultSet generatedKeys =  preparedStatement.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						int paymentID = generatedKeys.getInt(1); // Get the generated PaymentID
					c.setId(paymentID);
					}
				}
			}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCustomerName(int customerId, String newName) {
        updateField("customerName", newName, customerId);
    }

    public void updateCustomerPoints(int customerId, double newPoints) {
        updateField("customerPoints", newPoints, customerId);
    }

    private void updateField(String fieldName, Object newValue, int customerId) {
        String query = "UPDATE customer SET " + fieldName + " = ? WHERE customerId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, newValue);
            preparedStatement.setInt(2, customerId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomer(int customerId) {
        String query = "DELETE FROM customer WHERE customerId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customerId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Customer> getAllCustomers() {
        List<Customer> customerList = new ArrayList<>();
        String query = "SELECT * FROM customer";
        try (ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("customerId");
                String name = resultSet.getString("customerName");
                double points = resultSet.getDouble("customerPoints");
                customerList.add(new Customer(id, name, points));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }

    public void close() {
        try {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}