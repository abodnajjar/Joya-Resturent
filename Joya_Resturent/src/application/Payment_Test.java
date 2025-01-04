package application;
import java.sql.*;
import java.util.*;
public class Payment_Test {
	private  Connection connection ;
	private  Statement statement ;
	
	public Payment_Test() {
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

    public Payment insertPayment(Payment p) {
        String sql = "INSERT INTO payment ( PaymentMethod) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Set parameters
 
            preparedStatement.setString(1,p.getMethod());
     

            // Execute the query
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {

                // Retrieve the generated key
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int paymentID = generatedKeys.getInt(1); // Get the generated PaymentID
                        p.setId(paymentID);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if the insertion fails
    }
    public boolean deletePayment(int paymentID) {
        String sql = "DELETE FROM payment WHERE PaymentID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Set the parameter
            preparedStatement.setInt(1, paymentID);

            // Execute the query
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("The payment with PaymentID " + paymentID + " was deleted successfully!");
                return true;
            } else {
                System.out.println("No payment found with PaymentID " + paymentID + ".");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updatePayment(int paymentID, Object newValue) {
 
        // Build the SQL query dynamically
        String sql = "UPDATE payment SET PaymentMethod = ? WHERE PaymentID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Set parameters dynamically
            preparedStatement.setObject(1, newValue); // Dynamically set the new value
            preparedStatement.setInt(2, paymentID);

            // Execute the query
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("The payment with PaymentID " + paymentID + " was updated successfully!");
                return true;
            } else {
                System.out.println("No payment found with PaymentID " + paymentID + ".");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public ArrayList<Payment> getAllPayments() {
        ArrayList<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payment";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            // Iterate through the result set and add each record to the ArrayList
            while (resultSet.next()) {
                int paymentID = resultSet.getInt("PaymentID");
                String paymentMethod = resultSet.getString("PaymentMethod");
     

                // Create a Payment object and add it to the ArrayList
                Payment payment = new Payment(paymentID, paymentMethod);
                payments.add(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return payments;
    }
	public static void main(String[] args) {
		 Payment_Test x=new  Payment_Test();
		 Payment p=new Payment("cash");
		 Payment p2=new Payment("Credit Card");
		 Payment p3=new Payment("Mobile Payment");
		 x.insertPayment(p3);
		 //System.out.println(x.getAllPayments());

	}

}
