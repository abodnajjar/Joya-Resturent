package application;

import java.util.List;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Employee_Tabe  {

	private TableView<Employee> tableView;
	private ObservableList<Employee> employeeData;
	private Employee_Test employeeTest;


	public VBox get_main_stage() {
		Stage primaryStage=new Stage();
		employeeTest = new Employee_Test();

		primaryStage.setTitle("Employee Management System");

		tableView = new TableView<>();
		TableColumn<Employee, Number> idColumn = new TableColumn<>("ID");
		idColumn.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getId()));
		idColumn.setPrefWidth(100);

		TableColumn<Employee, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
		nameColumn.setPrefWidth(200);

		TableColumn<Employee, String> contactColumn = new TableColumn<>("Contact");
		contactColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getContactInfo()));
		nameColumn.setPrefWidth(100);

		TableColumn<Employee, String> positionColumn = new TableColumn<>("Position");
		positionColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPostion()));
		positionColumn.setPrefWidth(100);


		TableColumn<Employee, String> addressColumn = new TableColumn<>("Address");
		addressColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAddress()));
		addressColumn.setPrefWidth(100);

		TableColumn<Employee, Number> salaryColumn = new TableColumn<>("Salary");
		salaryColumn.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getSalary()));
		salaryColumn.setPrefWidth(100);
		
		TableColumn<Employee, String> password = new TableColumn<>("password");
		 password.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPassword()));
		 password.setPrefWidth(100);

		tableView.getColumns().addAll( idColumn, nameColumn, contactColumn, positionColumn, addressColumn, salaryColumn,password );

		employeeData = FXCollections.observableArrayList();
		loadEmployeeData();  


		FilteredList<Employee> filteredData = new FilteredList<>(employeeData, p -> true);
		tableView.setItems(filteredData);


		TextField searchField = new TextField();
		searchField.setPromptText("Search...");


		searchField.textProperty().addListener((obs, oldValue, newValue) -> {
			String lowerCaseFilter = (newValue == null) ? "" : newValue.toLowerCase();
			filteredData.setPredicate(emp -> {
				if (lowerCaseFilter.isEmpty()) {
					return true;
				}
				String idStr = String.valueOf(emp.getId());
				String nameStr = emp.getName().toLowerCase();
				String contactStr = emp.getContactInfo().toLowerCase();
				String positionStr = emp.getPostion().toLowerCase();
				String addressStr = emp.getAddress().toLowerCase();
				String salaryStr = String.valueOf(emp.getSalary());
				return idStr.contains(lowerCaseFilter)
						|| nameStr.contains(lowerCaseFilter)
						|| contactStr.contains(lowerCaseFilter)
						|| positionStr.contains(lowerCaseFilter)
						|| addressStr.contains(lowerCaseFilter)
						|| salaryStr.contains(lowerCaseFilter);
			});
		});

		Button addButton = new Button("Add");
		addButton.setPrefWidth(150);
		addButton.setPrefHeight(30);
		Button updateButton = new Button("Update");
		Button deleteButton = new Button("Delete");
		deleteButton.setPrefWidth(150);
		deleteButton.setPrefHeight(30);
		updateButton.setPrefWidth(150);
		updateButton.setPrefHeight(30);

		HBox buttonBox = new HBox(15, addButton, updateButton, deleteButton);
		buttonBox.setPadding(new Insets(10));
		buttonBox.setAlignment(Pos.CENTER);

	
		VBox rightBox = new VBox();
		rightBox.setPadding(new Insets(10));
		rightBox.setPrefWidth(150);

		VBox searchBox = new VBox(10, searchField);
		searchBox.setPadding(new Insets(10));
		searchBox.setAlignment(Pos.CENTER);
		
		VBox vb = new VBox(30,searchBox,tableView,buttonBox);
	


		addButton.setOnAction(e -> showAddEmployeeScene());

		deleteButton.setOnAction(e -> {
			Employee selectedEmployee = tableView.getSelectionModel().getSelectedItem();
			if (selectedEmployee != null) {
				Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
				confirmationAlert.setTitle("Delete Confirmation");
				confirmationAlert.setHeaderText("Are you sure you want to delete this employee?");
				confirmationAlert.setContentText("Employee: " + selectedEmployee.getName());
				confirmationAlert.showAndWait().ifPresent(response -> {
					if (response == ButtonType.OK) {
						employeeTest.deleteEmployee(selectedEmployee.getId());
						employeeData.remove(selectedEmployee);
						tableView.getSelectionModel().clearSelection();
						showInfoAlert("Employee deleted successfully!");
					}
				});
			} else {
				showErrorAlert("No Selection", "Please select an employee to delete.");
			}
		});

		updateButton.setOnAction(e -> {
			Employee selectedEmployee = tableView.getSelectionModel().getSelectedItem();
			TablePosition<?, ?> selectedCell = tableView.getSelectionModel().getSelectedCells().isEmpty()
					? null
							: tableView.getSelectionModel().getSelectedCells().get(0);

			if (selectedEmployee != null && selectedCell != null) {
				TableColumn<?, ?> selectedColumn = selectedCell.getTableColumn();
				String columnName = selectedColumn.getText();

				if ("ID".equals(columnName)) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Update Error");
					alert.setHeaderText("Employee ID cannot be updated");
					alert.setContentText("This is an auto-increment column.");
					alert.showAndWait();
					return;
				}

				TextInputDialog dialog = new TextInputDialog();
				dialog.setTitle("Update Employee Data");
				dialog.setHeaderText("Update " + columnName);
				dialog.setContentText("Enter new value:");

				dialog.showAndWait().ifPresent(newValue -> {
					try {
						switch (columnName) {
						case "Name":
							selectedEmployee.setName(newValue);
							employeeTest.updateField(selectedEmployee.getId(), "Name", newValue);
							break;
						case "Contact":
							selectedEmployee.setContactInfo(newValue);
							employeeTest.updateField(selectedEmployee.getId(), "ContactInfo", newValue);
							break;
						case "Position":
							selectedEmployee.setPostion(newValue);
							employeeTest.updateField(selectedEmployee.getId(), "Position", newValue);
							break;
						case "Address":
							selectedEmployee.setAddress(newValue);
							employeeTest.updateField(selectedEmployee.getId(), "address", newValue);
							break;
						case "Salary":
							double newSalary = Double.parseDouble(newValue);
							selectedEmployee.setSalary(newSalary);
							employeeTest.updateField(selectedEmployee.getId(), "salary", newSalary);
							break;
						case "password":
							selectedEmployee.setPassword(newValue);
							employeeTest.updateField(selectedEmployee.getId(), "Password",newValue);
							break;
						default:
							showErrorAlert("Update Error", "Invalid column selected for update!");
							return;
						}
						tableView.refresh();
						tableView.getSelectionModel().clearSelection();
						showInfoAlert(columnName + " updated successfully!");
					} catch (NumberFormatException ex) {
						showErrorAlert("Input Error", "Please enter a valid number for Salary.");
					}
				});
			} else {
				showErrorAlert("No Selection", "Please select an employee and a specific cell to update.");
			}
		});
		return vb;
	}

	private void loadEmployeeData() {
		employeeData.clear();
		List<Employee> employees = employeeTest.getAllEmployees();
		employeeData.addAll(employees);
	}

	private void showAddEmployeeScene() {
		Stage addStage = new Stage();
		addStage.setTitle("Add New Employee");

		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(20));
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setAlignment(Pos.CENTER);

		TextField nameField = new TextField();
		nameField.setPromptText("Name");
		TextField contactField = new TextField();
		contactField.setPromptText("Contact");
		TextField positionField = new TextField();
		positionField.setPromptText("Position");
		TextField addressField = new TextField();
		addressField.setPromptText("Address");
		TextField salaryField = new TextField();
		salaryField.setPromptText("Salary");
		TextField passField = new TextField();
		passField.setPromptText("password");
		Button saveButton = new Button("Save");
		Button cancelButton = new Button("Cancel");

		saveButton.setOnAction(e -> {
			if (!nameField.getText().isEmpty()&&!contactField .getText().isEmpty()&&!positionField.getText().isEmpty()&&!addressField .getText().isEmpty()
					&&!salaryField.getText().isEmpty()&&!passField.getText().isEmpty()) {
				String name = nameField.getText();
				String contact = contactField.getText();
				String position = positionField.getText();
				String address = addressField.getText();
				try {
					double salary = Double.parseDouble(salaryField.getText());
					employeeTest.addNewEmployee(name, contact, position,passField.getText(), salary, address);
					loadEmployeeData();
					addStage.close();
					showInfoAlert("New employee added successfully!");
				} catch (NumberFormatException ex) {
					showErrorAlert("Input Error", "Please enter a valid number for Salary.");
				}
			}
			else {
				showErrorAlert("Input Error", "Please enter all value .");
			}

		});

		cancelButton.setOnAction(e -> addStage.close());

		gridPane.add(new Label("Name:"), 0, 0);
		gridPane.add(nameField, 1, 0);
		gridPane.add(new Label("Contact:"), 0, 1);
		gridPane.add(contactField, 1, 1);
		gridPane.add(new Label("Position:"), 0, 2);
		gridPane.add(positionField, 1, 2);
		gridPane.add(new Label("Address:"), 0, 3);
		gridPane.add(addressField, 1, 3);
		gridPane.add(new Label("Salary:"), 0, 4);
		gridPane.add(salaryField, 1, 4);
		gridPane.add(new Label("password:"), 0, 5);
		gridPane.add(passField, 1, 5);
		gridPane.add(saveButton, 0, 6);
		gridPane.add(cancelButton, 1, 6);

		Scene addScene = new Scene(gridPane, 500, 400);
		addStage.setScene(addScene);
		addStage.showAndWait();
	}

	private void showInfoAlert(String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private void showErrorAlert(String header, String content) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}


}
