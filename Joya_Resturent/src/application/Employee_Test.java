package application;
import java.sql.*;
public class Employee_Test {
	private static Connection connection = null;
	private static Statement statement = null;
	public static Employee AddEmp(Employee employee)  {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/project_DataBasse?useSSL=false", "root", "computer2005");

			statement = connection.createStatement();
			String line = "INSERT INTO Employee ( Name, ContactInfo, Position, Password, Salary, Address) VALUES (  ?, ?, ?, ?, ?,?);";
	        PreparedStatement statement = connection.prepareStatement(line, Statement.RETURN_GENERATED_KEYS);
	        statement.setString(1, employee.getName());
	        statement.setString(2, employee.getContactInfo());
	        statement.setString(3, employee.getPostion());
	        statement.setString(4, employee.getPassword());
	        statement.setDouble(5, employee.getSalary());
	        statement.setString(6, employee.getAddress());

	        // تنفيذ الإدخال
	        int rowsInserted = statement.executeUpdate();

	        if (rowsInserted > 0) {
	            // استرداد المفتاح التلقائي
	            ResultSet generatedKeys = statement.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                int generatedId = generatedKeys.getInt(1);
	                employee.setId(generatedId); // تعيين الـ ID في الكائن
	            }
	        }

	        statement.close();
	        return employee;
		} 
		catch (SQLIntegrityConstraintViolationException e) {
			// Handle duplicate entry
			System.out.println("Error: Duplicate entry for ID " + employee.getId());
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return employee;
	}

	public static void deleteEmp(int id)  {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/project_DataBasse?useSSL=false", "root", "computer2005");
			statement = connection.createStatement();
			String line = "DELETE FROM Employee WHERE EmployeeID = ? ;";
			PreparedStatement preparedStatement = connection.prepareStatement(line);
			preparedStatement.setInt(1,id);
			preparedStatement.executeUpdate(); 
			System.out.println("Deleted");
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String selEmp(String choose) throws SQLException {
		String sql = "SELECT " + choose + " FROM Employee;";
		ResultSet resultSet = statement.executeQuery(sql);
		String result = " ";

		while (resultSet.next()) {
			result += resultSet.getString(choose) + "\n"; 
			System.out.println(resultSet.getString(choose));
		}

		return result;
	}
	public String UpdateEmp(String choose) throws SQLException {
		String sql = "Update " + choose + " FROM Employee;";
		ResultSet resultSet = statement.executeQuery(sql);
		String result = " ";
		while (resultSet.next()) {
			result += resultSet.getString(choose) + "\n"; 
			System.out.println(resultSet.getString(choose));
		}

		return result;
	}
	public static void main(String[] args) {
		//		Employee x=new Employee(5,2500,"saeed","0566","leder","123","Ramallah");
		//		String s="EmployeeID=1";

		deleteEmp(10);

	}

}
