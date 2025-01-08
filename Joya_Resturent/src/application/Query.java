package application;

	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.sql.Statement;
	import java.util.ArrayList;
	import java.util.List;

	public class Query {
	    private Connection connection;
	    private Statement statement;

	  
	    public Query() {
	        try {
	            connection = DriverManager.getConnection(
						"jdbc:mysql://127.0.0.1:3306/project_DataBasse?useSSL=false", 
						"root", 
						"computer2005"
						);
				statement = connection.createStatement();
	        } catch (SQLException e) {
	            System.out.println("Error Connecting to Database!");
	            e.printStackTrace();
	        }
	    }

	   
	    public int getTotalEmployees() {
	        String query = "SELECT COUNT(*) AS totalEmployees FROM employee;";
	        try (PreparedStatement stmt = connection.prepareStatement(query);
	             ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt("totalEmployees");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return 0;
	    }


	    public int getTotalCustomers() {
	        String query = "SELECT COUNT(*) AS totalCustomers FROM customer;";
	        try (PreparedStatement stmt = connection.prepareStatement(query);
	             ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt("totalCustomers");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return 0;
	    }

	   
	    public int getTotalMenuItems() {
	        String query = "SELECT COUNT(*) AS totalMenuItems FROM menu_item;";
	        try (PreparedStatement stmt = connection.prepareStatement(query);
	             ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt("totalMenuItems");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return 0;
	    }

	  
	    public int getTotalOrders() {
	        String query = "SELECT COUNT(*) AS totalOrders FROM orders;";
	        try (PreparedStatement stmt = connection.prepareStatement(query);
	             ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt("totalOrders");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return 0;
	    }
	    
	    
	    public int getTotalIngredient() {
	        String query = "SELECT COUNT(*) AS TotalIngredient FROM ingredient;";
	        try (PreparedStatement stmt = connection.prepareStatement(query);
	             ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt("TotalIngredient");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return 0;
	    }

	   

	   
	    public double getTotalMoneyForDate(String date) {
	        String query = "SELECT SUM(TotalPrice) AS totalMoney FROM orders WHERE OrderDate = ?;";
	        try (PreparedStatement stmt = connection.prepareStatement(query)) {
	            stmt.setString(1, date); 
	            try (ResultSet rs = stmt.executeQuery()) {
	                if (rs.next()) {
	                    return rs.getDouble("totalMoney");
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return 0.0;
	    }
	    
	    
	    
	    public double getTotalSalesForDate(String date) {
	        String query = "SELECT SUM(TotalPrice) AS totalSales FROM orders WHERE OrderDate = ?;";
	        try (PreparedStatement stmt = connection.prepareStatement(query)) {
	            stmt.setString(1, date); 
	            try (ResultSet rs = stmt.executeQuery()) {
	                if (rs.next()) {
	                    return rs.getDouble("totalSales");
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return 0.0;
	    }
	    
	    
	    public List<Customer> getCustomersByPointsDesc() {
	        List<Customer> customers = new ArrayList<>();
	        String query = "SELECT customerId, customerName, customerPoints FROM customer ORDER BY customerPoints DESC";
	        
	        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
	             ResultSet resultSet = preparedStatement.executeQuery()) {
	            
	            while (resultSet.next()) {
	                int id = resultSet.getInt("customerId");
	                String name = resultSet.getString("customerName");
	                double points = resultSet.getDouble("customerPoints");
	                
	                customers.add(new Customer(id, name, points));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        
	        return customers;
	    }
	    
	    public List<Ingredient> loadIngredientsBelow100FromDatabase() {
	        List<Ingredient> ingredientList = new ArrayList<>();
	        String query = "SELECT * FROM ingredient WHERE quantity_in_stock < 100 ORDER BY quantity_in_stock ASC";
	        try (ResultSet resultSet = statement.executeQuery(query)) {
	            while (resultSet.next()) {
	                int ingredientId = resultSet.getInt("IngredientId");
	                String name = resultSet.getString("IngredientName");
	                String ingredientSupplier = resultSet.getString("IngredientSupplier");
	                Double ingredientQuantity = resultSet.getDouble("quantity_in_stock");
	                ingredientList.add(new Ingredient(ingredientId, name, ingredientSupplier, ingredientQuantity));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return ingredientList;
	    }
	    
	


}
