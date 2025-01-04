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

public class Customer_Tab{

	private TableView<Customer> tableView;
	private ObservableList<Customer> customerData;
	private Customer_Test customerTest;


	public VBox get_main_Stage() {
		Stage primaryStage =new Stage();
		customerTest = new Customer_Test();
		primaryStage.setTitle("Customer Management System");

		//		VBox leftBox = new VBox();
		//		leftBox.setPadding(new Insets(10));
		//		leftBox.setPrefWidth(100);

		VBox rightBox = new VBox();
		rightBox.setPadding(new Insets(10));
		rightBox.setPrefWidth(100);

		tableView = new TableView<>();


		TableColumn<Customer, Number> idColumn = new TableColumn<>("ID");
		idColumn.setCellValueFactory(cellData ->
		new SimpleIntegerProperty(cellData.getValue().getId()));
		idColumn.setPrefWidth(150);

		TableColumn<Customer, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(cellData ->
		new SimpleStringProperty(cellData.getValue().getName()));
		nameColumn.setPrefWidth(150);

		TableColumn<Customer, Number> pointsColumn = new TableColumn<>("Points");
		pointsColumn.setCellValueFactory(cellData ->
		new SimpleDoubleProperty(cellData.getValue().getPointer()));
		pointsColumn.setPrefWidth(150);

		tableView.getColumns().addAll(idColumn, nameColumn, pointsColumn);

		customerData = FXCollections.observableArrayList();
		loadCustomerData();


		FilteredList<Customer> filteredData = new FilteredList<>(customerData, c -> true);
		tableView.setItems(filteredData);


		TextField searchField = new TextField();
		searchField.setPromptText("Search...");


		searchField.textProperty().addListener((obs, oldValue, newValue) -> {
			String lowerCaseFilter = (newValue == null) ? "" : newValue.toLowerCase();
			filteredData.setPredicate(customer -> {
				if (lowerCaseFilter.isEmpty()) {
					return true;
				}
				String idStr = String.valueOf(customer.getId());
				String nameStr = customer.getName().toLowerCase();
				String pointsStr = String.valueOf(customer.getPointer());
				return idStr.contains(lowerCaseFilter)
						|| nameStr.contains(lowerCaseFilter)
						|| pointsStr.contains(lowerCaseFilter);
			});
		});

		Button addButton = new Button("Add");
		addButton.setPrefWidth(150);
		addButton.setPrefHeight(30);
		Button updateButton = new Button("Update");
		updateButton.setPrefWidth(150);
		updateButton.setPrefHeight(30);
		Button deleteButton = new Button("Delete");
		deleteButton.setPrefWidth(150);
		deleteButton.setPrefHeight(30);
		HBox buttonBox = new HBox(15, addButton, deleteButton,updateButton);
		buttonBox.setPadding(new Insets(10));
		buttonBox.setAlignment(Pos.CENTER);

		VBox searchBox = new VBox(10, searchField);
		searchBox.setPadding(new Insets(10));
		searchBox.setAlignment(Pos.CENTER);

		VBox vb= new VBox(30,searchBox,tableView,buttonBox);



		addButton.setOnAction(e -> showAddCustomerScene());

		deleteButton.setOnAction(e -> {
			Customer selectedCustomer = tableView.getSelectionModel().getSelectedItem();
			if (selectedCustomer != null) {
				Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
				confirmationAlert.setTitle("Delete Confirmation");
				confirmationAlert.setHeaderText("Are you sure you want to delete this customer?");
				confirmationAlert.setContentText("Customer: " + selectedCustomer.getName());
				confirmationAlert.showAndWait().ifPresent(response -> {
					if (response == ButtonType.OK) {
						customerTest.deleteCustomer(selectedCustomer.getId());
						customerData.remove(selectedCustomer);
						tableView.getSelectionModel().clearSelection();
						showInfoAlert("Customer deleted successfully!");
					}
				});
			} else {
				showErrorAlert("No Customer Selected", "Please select a customer to delete.");
			}
		});

		updateButton.setOnAction(e -> {

			Customer selectedCustomer = tableView.getSelectionModel().getSelectedItem();
			TablePosition<?, ?> selectedCell = tableView.getSelectionModel().getSelectedCells().isEmpty()
					? null
							: tableView.getSelectionModel().getSelectedCells().get(0);

			if (selectedCustomer != null && selectedCell != null) {
				TableColumn<?, ?> selectedColumn = selectedCell.getTableColumn();
				String columnName = selectedColumn.getText();

				if ("ID".equals(columnName)) {
					Alert errorAlert = new Alert(Alert.AlertType.ERROR);
					errorAlert.setTitle("Update Error");
					errorAlert.setHeaderText("Cannot update Customer ID!");
					errorAlert.setContentText("Customer ID is auto-generated and cannot be changed.");
					errorAlert.showAndWait();
					return;
				}

				TextInputDialog dialog = new TextInputDialog();
				dialog.setTitle("Update Customer Data");
				dialog.setHeaderText("Update " + columnName);
				dialog.setContentText("Enter new value:");

				dialog.showAndWait().ifPresent(newValue -> {
					try {
						switch (columnName) {
						case "Name":
							customerTest.updateCustomerName(selectedCustomer.getId(), newValue);
							selectedCustomer.setName(newValue);
							break;
						case "Points":
							double newPoints = Double.parseDouble(newValue);
							customerTest.updateCustomerPoints(selectedCustomer.getId(), newPoints);
							selectedCustomer.setPointer(newPoints);
							break;
						default:
							showErrorAlert("Invalid Column", "You can only update 'Name' or 'Points'.");
							return;
						}
						tableView.refresh();
						tableView.getSelectionModel().clearSelection();
						showInfoAlert(columnName + " updated successfully!");
					} catch (NumberFormatException ex) {
						showErrorAlert("Error updating customer!", "Invalid number format for points.");
					}
				});
			} else {
				showErrorAlert("No Selection", "Please select a customer and a specific cell to update.");
			}
		});
		return vb;
	}

	private void loadCustomerData() {
		customerData.clear();
		List<Customer> allCustomers = customerTest.getAllCustomers();
		customerData.addAll(allCustomers);
	}

	private void showAddCustomerScene() {
		Stage addStage = new Stage();
		addStage.setTitle("Add New Customer");

		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(20));
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setAlignment(Pos.CENTER);

		TextField nameField = new TextField();
		nameField.setPromptText("Customer Name");
		TextField pointsField = new TextField();
		pointsField.setPromptText("Customer Points");

		Button saveButton = new Button("Save");
		Button cancelButton = new Button("Cancel");

		saveButton.setOnAction(e -> {
			System.out.println(nameField.getText().isEmpty());
			if(!nameField.getText().isEmpty()&&!pointsField.getText().isEmpty()) {
				String name = nameField.getText();
				try {
					double points = Double.parseDouble(pointsField.getText());
					Customer cu=new Customer(name, points);
					customerTest.addNewCustomer(cu);
					loadCustomerData();
					addStage.close();
					showInfoAlert("New customer added successfully!");
				}
				catch (NumberFormatException ex) {
					showErrorAlert("Input Error", "Please enter valid numeric points.");
				}
			}
			else {
				showErrorAlert("Input Error", "Please enter all value.");
			}

		});

		cancelButton.setOnAction(e -> addStage.close());

		gridPane.add(new Label("Name:"), 0, 0);
		gridPane.add(nameField, 1, 0);
		gridPane.add(new Label("Points:"), 0, 1);
		gridPane.add(pointsField, 1, 1);
		gridPane.add(saveButton, 0, 2);
		gridPane.add(cancelButton, 1, 2);

		Scene addScene = new Scene(gridPane, 300, 200);
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
