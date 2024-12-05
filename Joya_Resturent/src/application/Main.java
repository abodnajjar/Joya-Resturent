package application;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
public class Main extends Application {
	@Override
	public void start(Stage sr) {
		firstStage().show();
	}
	public Stage firstStage() {
		Stage primaryStage = new Stage();
		primaryStage.setTitle("Employee Operation :- ");
		try {
			Employee_Test et=new Employee_Test();
			TableView tableView = new TableView<>();
			ObservableList<Employee> employeeList = FXCollections.observableArrayList();
			// Create columns for Employee properties using getter methods
			TableColumn<Employee, Integer> employeeIDColumn = new TableColumn<>("EmployeeID");
			employeeIDColumn.setCellValueFactory(cellData -> 
			new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getId()));

			TableColumn<Employee, String> nameColumn = new TableColumn<>("Name");
			nameColumn.setCellValueFactory(cellData -> 
			new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getName()));

			TableColumn<Employee, String> contactInfoColumn = new TableColumn<>("Contact Info");
			contactInfoColumn.setCellValueFactory(cellData -> 
			new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getContactInfo()));

			TableColumn<Employee, String> positionColumn = new TableColumn<>("Position");
			positionColumn.setCellValueFactory(cellData -> 
			new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getPostion()));

			TableColumn<Employee, String> passwordColumn = new TableColumn<>("Password");
			passwordColumn.setCellValueFactory(cellData -> 
			new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getPassword()));

			TableColumn<Employee, String> addressColumn = new TableColumn<>("Address");
			addressColumn.setCellValueFactory(cellData -> 
			new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getAddress()));

			TableColumn<Employee, Double> salaryColumn = new TableColumn<>("Salary");
			salaryColumn.setCellValueFactory(cellData -> 
			new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getSalary()));

			// Add the columns to the TableView
			tableView.getColumns().addAll(employeeIDColumn, nameColumn, contactInfoColumn, positionColumn, passwordColumn, addressColumn, salaryColumn);

			// Create an ObservableList of Employee objects
			employeeList = FXCollections.observableArrayList();

			// Fill the list with Employee data (you can load this from a database or other source)
			employeeList.addAll(getAllEmployees()); // Assuming getEmployeeList() returns an ArrayList of Employee objects

			// Set the ObservableList to the TableView
			tableView.setItems(employeeList);

			BorderPane root = new BorderPane();
			root.setTop(tableView);
			Button insert =new Button("Insert");

			insert.setPrefWidth(150);
			insert.setPrefHeight(30);
			Button Delete =new Button("Delete");
			Delete.setPrefWidth(150);
			Delete.setPrefHeight(30);
			HBox hb=new HBox(30,insert,Delete);
			hb.setAlignment(Pos.CENTER);
			root.setCenter(hb);
			Scene scene = new Scene(root,490,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			insert.setOnAction(e->{
				primaryStage.close();
				Stage s=getstage();
				s.show();
			});
			Delete.setOnAction(e->{
				primaryStage.close();
				deleteStage().show();
			});
			return primaryStage;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return primaryStage;
	}
	public Stage deleteStage() {
		Stage primaryStage = new Stage();
		primaryStage.setTitle("Delete Employee :-");
		try {
			Label lb=new Label("Select the employee and clike delete");
			lb.setAlignment(Pos.CENTER);
			lb.setFont(Font.font(20));
			Employee_Test et=new Employee_Test();
			TableView tableView = new TableView<>();
			ObservableList<Employee> employeeList = FXCollections.observableArrayList();
			// Create columns for Employee properties using getter methods
			TableColumn<Employee, Integer> employeeIDColumn = new TableColumn<>("EmployeeID");
			employeeIDColumn.setCellValueFactory(cellData -> 
			new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getId()));

			TableColumn<Employee, String> nameColumn = new TableColumn<>("Name");
			nameColumn.setCellValueFactory(cellData -> 
			new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getName()));

			TableColumn<Employee, String> contactInfoColumn = new TableColumn<>("Contact Info");
			contactInfoColumn.setCellValueFactory(cellData -> 
			new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getContactInfo()));

			TableColumn<Employee, String> positionColumn = new TableColumn<>("Position");
			positionColumn.setCellValueFactory(cellData -> 
			new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getPostion()));

			TableColumn<Employee, String> passwordColumn = new TableColumn<>("Password");
			passwordColumn.setCellValueFactory(cellData -> 
			new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getPassword()));

			TableColumn<Employee, String> addressColumn = new TableColumn<>("Address");
			addressColumn.setCellValueFactory(cellData -> 
			new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getAddress()));

			TableColumn<Employee, Double> salaryColumn = new TableColumn<>("Salary");
			salaryColumn.setCellValueFactory(cellData -> 
			new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getSalary()));

			// Add the columns to the TableView
			tableView.getColumns().addAll(employeeIDColumn, nameColumn, contactInfoColumn, positionColumn, passwordColumn, addressColumn, salaryColumn);

			// Create an ObservableList of Employee objects
			employeeList = FXCollections.observableArrayList();

			// Fill the list with Employee data (you can load this from a database or other source)
			employeeList.addAll(getAllEmployees()); // Assuming getEmployeeList() returns an ArrayList of Employee objects

			// Set the ObservableList to the TableView
			tableView.setItems(employeeList);
			VBox vb=new VBox(15,lb,tableView);
			vb.setAlignment(Pos.CENTER);
			BorderPane root = new BorderPane();
			root.setTop(vb);
			Button go =new Button("Go Back ");
			go.setOnAction(e->{
				primaryStage.close();
				firstStage().show();
			});
			go.setPrefWidth(150);
			go.setPrefHeight(30);
			Button Delete =new Button("Delete");
			Delete.setOnAction(e->{
				Employee selectedEmployee = (Employee) tableView.getSelectionModel().getSelectedItem();

				if (selectedEmployee != null) {
					int employeeID = selectedEmployee.getId();  // Get the Employee ID

					// Call the delete method
					et.deleteEmp(employeeID);

					// Remove the employee from the TableView
					tableView.getItems().remove(selectedEmployee);

					lb.setText("Employee deleted successfully.");
					lb.setStyle("-fx-text-fill: green;");
				} else {
					lb.setText("Please select an employee to delete.");
					lb.setStyle("-fx-text-fill: red;");
				}
			});
			Delete.setPrefWidth(150);
			Delete.setPrefHeight(30);
			HBox hb=new HBox(30,go,Delete);
			hb.setAlignment(Pos.CENTER);
			root.setCenter(hb);
			Scene scene = new Scene(root,500,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			return primaryStage;
		} catch(Exception e) {
			e.printStackTrace();
		}

		return primaryStage;
	}

	public Stage getstage() {
		Stage formStage = new Stage();
		formStage.setTitle("Insert Employee :-");

		// Create Labels and TextFields for each attribute

		Label lblName = new Label("Name: ");
		TextField txtName = new TextField();

		Label lblSalary = new Label("Salary: ");
		TextField txtSalary = new TextField();

		Label lblContactInfo = new Label("Contact Info: ");
		TextField txtContactInfo = new TextField();

		Label lblPosition = new Label("Position: ");
		TextField txtPosition = new TextField();

		Label lblPassword = new Label("Password: ");
		TextField txtPassword = new TextField();

		Label lblAddress = new Label("Address: ");
		TextField txtAddress = new TextField();

		GridPane grid = new GridPane();
		grid.setVgap(20);
		grid.setHgap(10);

		// Add the labels and textfields to the grid
		grid.add(lblName, 1, 1);
		grid.add(txtName, 2, 1);

		grid.add(lblSalary, 1, 2);
		grid.add(txtSalary, 2, 2);

		grid.add(lblContactInfo, 1, 3);
		grid.add(txtContactInfo, 2, 3);

		grid.add(lblPosition, 1, 4);
		grid.add(txtPosition, 2, 4);

		grid.add(lblPassword, 1, 5);
		grid.add(txtPassword, 2, 5);

		grid.add(lblAddress, 1, 6);
		grid.add(txtAddress, 2, 6);
		Button btnInsert = new Button("Insert");
		btnInsert.setPrefWidth(100);
		btnInsert.setPrefHeight(35);
		grid.add(btnInsert, 2, 7);
		Label lb=new Label();
		grid.add(lb, 2, 8);
		Button Go_Back = new Button("Go Back");
		Go_Back.setPrefWidth(100);
		Go_Back.setPrefHeight(35);
		grid.add(Go_Back, 4, 7);

		btnInsert.setOnAction(e->{
			try {
				// Validate EmployeeID and Salary
				String salaryText = txtSalary.getText();

				if ( salaryText.isEmpty() || txtName.getText().isEmpty() || txtContactInfo.getText().isEmpty() 
						|| txtPosition.getText().isEmpty() || txtPassword.getText().isEmpty() || txtAddress.getText().isEmpty()) {
					lb.setText("All fields must be filled.");
					lb.setStyle("-fx-text-fill: red;");
					return; // Stop further execution if fields are empty
				}
				lb.setStyle("");
			
				double salary;
				try {
					salary = Double.parseDouble(salaryText);
				} catch (NumberFormatException k) {
					lb.setText("Salary must be a valid number.");
					lb.setStyle("-fx-text-fill: red;");
					return; // Stop execution if Salary is invalid
				}

				// Create Employee object with the data from the text fields
				String name = txtName.getText();
				String contactInfo = txtContactInfo.getText();
				String position = txtPosition.getText();
				String password = txtPassword.getText();
				String address = txtAddress.getText();

				Employee employee = new Employee( salary, name, contactInfo, position, password, address);
				 employee=Employee_Test.AddEmp(employee);
				lb.setText("Employee Added with id :"+employee.getId());


			} catch (NumberFormatException r) {
				// If EmployeeID is not an integer or Salary is not a double
				lb.setText("All fields must be filled.");
				lb.setStyle("-fx-text-fill: red;");
			}
		});

		// Create a Scene for the new stage
		Scene formScene = new Scene(grid, 400, 500);
		Go_Back.setOnAction(e->{
			formStage.close();
			firstStage().show();
		});
		formStage.setScene(formScene);
		return formStage;
	}
	public static Connection connect() throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/project_DataBasse?useSSL=false", "root", "computer2005");
	}

	public static ArrayList<Employee> getAllEmployees() {
		ArrayList<Employee> employeeList = new ArrayList<>();
		String query = "SELECT * FROM employee";

		try (Connection conn = connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			while (rs.next()) {
				employeeList.add(new Employee(
						rs.getInt("EmployeeID"),
						rs.getDouble("Salary"),
						rs.getString("Name"),
						rs.getString("ContactInfo"),
						rs.getString("Position"),
						rs.getString("Password"),
						rs.getString("Address")
						));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return employeeList;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
