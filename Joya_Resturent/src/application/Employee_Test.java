package application;
import java.sql.*;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; 
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
public class Employee_Test {
	private static Connection connection = null;
	private static Statement statement = null;
	public Employee_Test() {
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

   
    public void addNewEmployee(String name, String contact,String position, String password, double salary, String address) {
        String query = "INSERT INTO employee (Name, ContactInfo, Position, Password, salary, address) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, contact);
            preparedStatement.setString(3, position);
            preparedStatement.setString(4, password);
            preparedStatement.setDouble(5, salary);
            preparedStatement.setString(6, address);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateField(int employeeId, String fieldName, Object newValue) {
        String query = "UPDATE employee SET " + fieldName + " = ? WHERE EmployeeID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, newValue);
            preparedStatement.setInt(2, employeeId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEmployee(int employeeId) {
        String query = "DELETE FROM employee WHERE EmployeeID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, employeeId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM employee";
        try (ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("EmployeeID");
                double salary = rs.getDouble("salary");
                String name = rs.getString("Name");
                String contact = rs.getString("ContactInfo");
                String position = rs.getString("Position");
                String password = rs.getString("Password");
                String address = rs.getString("address");
                employees.add(new Employee(id, salary, name, contact, position, password, address));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

}
