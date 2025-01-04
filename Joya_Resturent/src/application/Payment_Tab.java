package application;

import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Payment_Tab {
	Payment_Test paymentTest=new  Payment_Test();
	ObservableList<Payment> PaymentList = FXCollections.observableArrayList();
	public VBox retu() {

		TableView tableView = new TableView<>();

		// Create columns for Employee properties using getter methods
		TableColumn<Payment , Integer> PaymentIDColumn = new TableColumn<>("Payment ID");
		PaymentIDColumn.setCellValueFactory(cellData -> 
		new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getId()));

		TableColumn<Payment , String> PaymentnameColumn = new TableColumn<>("Payment Name");
		PaymentnameColumn.setCellValueFactory(cellData -> 
		new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getMethod()));

		// Add the columns to the TableView
		tableView.getColumns().addAll(PaymentIDColumn, PaymentnameColumn);

		// Create an ObservableList of Employee objects
		PaymentList = FXCollections.observableArrayList();
		PaymentList.addAll(paymentTest.getAllPayments()); 
		tableView.setItems(PaymentList);

		TextField searchField = new TextField();
		searchField.setPromptText("Search for Payment Method ......");

		// Create a FilteredList to hold the filtered data
		FilteredList<Payment> filteredData = new FilteredList<Payment>(PaymentList, p -> true);

		// Bind the TableView to the FilteredList
		tableView.setItems(filteredData);

		// Filter the TableView based on the search field input
		searchField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(payment -> {
				// If the search field is empty, show all items
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				// Perform a case-insensitive search on the name column
				String lowerCaseFilter = newValue.toLowerCase();
				return payment.getMethod().toLowerCase().contains(lowerCaseFilter);
			});
		});


		// Create buttons for Add, Delete, and Update
		Button addButton = new Button("Add");
		addButton.setPrefWidth(150);
		addButton.setPrefHeight(30);
		Button deleteButton = new Button("Delete");
		Button updateButton = new Button("Update");
		deleteButton.setPrefWidth(150);
		deleteButton.setPrefHeight(30);
		updateButton.setPrefWidth(150);
		updateButton.setPrefHeight(30);

		// Button actions
		addButton.setOnAction(e -> {
			Stage r=Insert(PaymentList);
			r.show();
		});

		deleteButton.setOnAction(e -> {
			// Get selected payment from the TableView
			Payment selectedPayment = (Payment) tableView.getSelectionModel().getSelectedItem();

			if (selectedPayment != null) {
				// Confirmation dialog
				Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
				confirmationAlert.setTitle("Delete Confirmation");
				confirmationAlert.setHeaderText("Are you sure you want to delete this payment?");
				confirmationAlert.setContentText("Payment ID: " + selectedPayment.getId());

				// Show confirmation dialog
				confirmationAlert.showAndWait().ifPresent(response -> {
					if (response == ButtonType.OK) {
						// Remove selected payment from ObservableList
						PaymentList.remove(selectedPayment);
						paymentTest.deletePayment(selectedPayment.getId());
						tableView.getSelectionModel().clearSelection();
						Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
						successAlert.setContentText("Payment successfully deleted.");
						successAlert.show();
					}
				});
			} else {
				// Show alert if no payment is selected
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setContentText("Please select a payment to delete.");
				alert.show();
			}
		});

		updateButton.setOnAction(e -> {
			Payment selectedPayment = (Payment) tableView.getSelectionModel().getSelectedItem();

			if (selectedPayment != null) {
				// Show alert to confirm the update
				Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
				confirmationAlert.setTitle("Update Confirmation");
				confirmationAlert.setHeaderText("Are you sure you want to update this payment?");
				confirmationAlert.setContentText("Payment ID: " + selectedPayment.getId());

				// Show confirmation dialog
				confirmationAlert.showAndWait().ifPresent(response -> {
					if (response == ButtonType.OK) {
						Stage r=update(selectedPayment.getId());
						r.show();
						tableView.getSelectionModel().clearSelection();
					}
				});
			} else {
				// Show alert if no payment is selected
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setContentText("Please select a payment to update.");
				alert.show();
			}
		});
		HBox hb=new HBox(30,addButton,deleteButton,updateButton);
		hb.setAlignment(Pos.CENTER);
		// Create a VBox for the content
		VBox vbox = new VBox(30);
		vbox.getChildren().addAll(searchField,tableView,hb);
		return vbox;
	}

	public Stage Insert(ObservableList<Payment> PaymentList) {
		Stage r=new Stage();
		Button clearButton = new Button("Clear");
		clearButton.setPrefHeight(20);
		clearButton.setPrefWidth(150);
		Button insertButton = new Button("Insert");
		insertButton.setPrefHeight(20);
		insertButton.setPrefWidth(150);
		Label nameLabel = new Label("Enter Payment Method :");
		TextField nameTextField = new TextField();
		HBox hb=new HBox(20,nameLabel,nameTextField);
		hb.setAlignment(Pos.CENTER);
		HBox hb1=new HBox(30,clearButton,insertButton );
		hb1.setAlignment(Pos.CENTER);
		// VBox to contain the layout
		VBox vbox = new VBox(30);
		vbox.setAlignment(Pos.CENTER);
		vbox.getChildren().addAll(hb,hb1);

		clearButton.setOnAction(t -> {
			nameTextField.clear();
		});
		insertButton.setOnAction(t -> {
			String methodName = nameTextField.getText().trim();
			if (methodName.isEmpty()) {
				// Show alert if the text field is empty
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setTitle("Input Error");
				alert.setHeaderText(null);
				alert.setContentText("Please enter a method name.");
				alert.showAndWait();
			} else {
				r.close();
				Payment p=new Payment(nameTextField.getText());
				paymentTest.insertPayment(p);
				PaymentList.add(p);
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Success");
				alert.setHeaderText(null);
				alert.setContentText("we instert payment type "+methodName+" successfully.");
				alert.showAndWait();
				// Optionally, you can clear the text field after insertion
				nameTextField.clear();
			}
		});

		// Create a scene and set the VBox into the stage
		Scene scene = new Scene(vbox, 500, 300);
		r.setTitle("Add Payment:-");
		r.setScene(scene);
		r.show();
		return r;
	}

	public Stage update(int id) {
		// Label

		Stage primaryStage=new Stage();

		Label label = new Label("Enter New Payment Method :");

		// TextField
		TextField textField = new TextField();
		textField.setPromptText("Enter payment method");

		HBox hb1=new HBox(20,label,textField);
		hb1.setAlignment(Pos.CENTER);
		// Clear Button
		Button clearButton = new Button("Clear");
		clearButton.setOnAction(e -> textField.clear());

		// Update Button
		Button updateButton = new Button("Update");
		HBox hb2=new HBox(20,clearButton,updateButton);
		hb2.setAlignment(Pos.CENTER);
		updateButton.setOnAction(e -> {
			String input = textField.getText();
			// Validate input: only allow alphabetic characters
			if (input.matches("[a-zA-Z ]+")) {
				primaryStage.close();
				paymentTest.updatePayment(id,textField.getText() );
				PaymentList.clear();
				PaymentList.addAll(paymentTest.getAllPayments()); 
			} else {
				Alert alert = new Alert(Alert.AlertType.ERROR); // Error type alert
				alert.setTitle("Invalid Input");
				alert.setHeaderText(null); // No header text
				alert.setContentText( "Please enter only alphabetic characters.");
				alert.showAndWait();
			}

		});

		// Layout
		VBox layout = new VBox(10,hb1,hb2);
		layout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-spacing: 10;");

		// Scene
		Scene scene = new Scene(layout, 500, 200);

		// Stage
		primaryStage.setTitle("Update Payment :-");
		primaryStage.setScene(scene);
		return primaryStage;
	}

}
