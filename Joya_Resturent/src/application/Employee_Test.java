package application;
import java.sql.*;
public class Employee_Test {
	private static Connection connection = null;
	private static Statement statement = null;
	public static void AddEmp(int id,double salary,String name,String postion,String conection,String password ,String address) throws SQLException {
		connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/project_DataBasse", "root", "computer2005");
		statement = connection.createStatement();
		String line = "INSERT INTO Employee (EmployeeID, Name, ContactInfo, Position, Password, Salary, Address) VALUES (?, ?, ?, ?, ?, ?, ?);";

		PreparedStatement preparedStatement = connection.prepareStatement(line);
		//System.out.println(preparedStatement);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, conection);
        preparedStatement.setString(4, postion);
        preparedStatement.setString(5, password);
        preparedStatement.setDouble(6, salary);
        preparedStatement.setString(7, address);
        preparedStatement.executeUpdate();
        System.out.println(preparedStatement);
	}
	public static void main(String[] args) {

		//Employee x=new Employee(2,2500,"anas","0569","leder","123","Ramallah");
		try {
			AddEmp(4,2500,"alias","0569","manegerr","123","Ramallah");
		} catch (SQLException e) {
			System.out.println("eroor");
			e.printStackTrace();
		}
	}

}
