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
import javafx.scene.control.ComboBox; // For ComboBox
import javafx.scene.layout.BorderPane; // For BorderPane
import javafx.scene.control.cell.PropertyValueFactory; // For PropertyValueFactory
import javafx.geometry.Insets; 
import java.sql.*;

//Tab
public class Ingredient_Tab{

	private Ingredient_Test ingrediantTest = new Ingredient_Test();
	TableView<Ingredient> ingredientTable = new TableView<>();
	ObservableList<Ingredient> ingredientList = FXCollections.observableArrayList();

	public VBox main_Stage() {
		ingredientList = FXCollections.observableArrayList();
		ingredientList.addAll(ingrediantTest.loadIngredientsFromDatabase()); 
		ingredientTable.setItems(ingredientList);

		FilteredList<Ingredient> filteredList = new FilteredList<>(ingredientList, p -> true); // Initially no filter
		ingredientTable.setItems(filteredList);

		TableColumn<Ingredient, Integer> idColumn = new TableColumn<>("ID");
		idColumn.setCellValueFactory(cellData -> 
		new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getId()));

		TableColumn<Ingredient, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(cellData -> 
		new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getName()));

		TableColumn<Ingredient, String> supplierColumn = new TableColumn<>("Supplier");
		supplierColumn.setCellValueFactory(cellData -> 
		new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getSupplier()));

		TableColumn<Ingredient, Double> quantityColumn = new TableColumn<>("Quantity in Stock");
		quantityColumn.setCellValueFactory(cellData -> 
		new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getQuantity_in_stock()));

		ingredientTable.getColumns().addAll(idColumn, nameColumn, supplierColumn, quantityColumn);

		// Search bar
		TextField searchField = new TextField();
		searchField.setPromptText("Search by name or supplier...");
		searchField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredList.setPredicate(ingredient -> {
				if (newValue == null || newValue.isEmpty()) {
					return true; // If search field is empty, show all
				}
				String lowerCaseFilter = newValue.toLowerCase();
				return ingredient.getName().toLowerCase().contains(lowerCaseFilter)
						|| ingredient.getSupplier().toLowerCase().contains(lowerCaseFilter);
			});
		});

		// Buttons
		Button addButton = new Button("Add");
		Button deleteButton = new Button("Delete");
		Button updateButton = new Button("Update");
		addButton.setPrefWidth(150);
		addButton.setPrefHeight(30);
		deleteButton.setPrefWidth(150);
		deleteButton.setPrefHeight(30);
		updateButton.setPrefWidth(150);
		updateButton.setPrefHeight(30);

		addButton.setOnAction(e -> showAddIngredientDialog());
		deleteButton.setOnAction(e -> {
			Ingredient selectedIngredient = (Ingredient)ingredientTable.getSelectionModel().getSelectedItem();
			if (selectedIngredient != null) {
				// Confirmation dialog
				Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
				confirmationAlert.setTitle("Delete Confirmation");
				confirmationAlert.setHeaderText("Are you sure you want to delete this Ingredient?");
				confirmationAlert.setContentText("Ingredient ID: " + selectedIngredient.getId());

				// Show confirmation dialog
				confirmationAlert.showAndWait().ifPresent(response -> {
					if (response == ButtonType.OK) {
						// Remove selected payment from ObservableList
						ingrediantTest.deleteSelectedIngredient(selectedIngredient.getId());
						ingredientList.remove(selectedIngredient);
						Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
						successAlert.setContentText("Ingredient successfully deleted.");
						successAlert.show();
						ingredientTable.getSelectionModel().clearSelection();
					}
				});
			} else {
				// Show alert if no payment is selected
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setContentText("Please select a Ingredient to delete.");
				alert.show();
			}
		});
		updateButton.setOnAction(e -> {
			Ingredient selectedIngredient = (Ingredient)ingredientTable.getSelectionModel().getSelectedItem();
			if (selectedIngredient != null) {
				showUpdateIngredientDialog(selectedIngredient.getId());
				ingredientTable.getSelectionModel().clearSelection();
			}
			else {
				// Show alert if no payment is selected
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setContentText("Please select a Ingredient to delete.");
				alert.show();
			}

		});

		HBox buttonBox = new HBox(15, addButton, deleteButton, updateButton);
		buttonBox.setAlignment(Pos.CENTER);

		VBox topLayout = new VBox(10, searchField); // Add search field to top
		topLayout.setPadding(new Insets(10));
		VBox vb= new VBox(30,topLayout,ingredientTable,buttonBox);


		return vb;
	}


	private void showAddIngredientDialog() {
		Stage dialog = new Stage();
		dialog.setTitle("Add Ingredient");

		TextField nameField = new TextField();
		nameField.setPromptText("Name");

		TextField supplierField = new TextField();
		supplierField.setPromptText("Supplier");

		TextField quantityField = new TextField();
		quantityField.setPromptText("Quantity in Stock");

		Button saveButton = new Button("Save");
		saveButton.setOnAction(e -> {
			if(!nameField.getText().isEmpty()&&!supplierField.getText().isEmpty()&&!quantityField.getText().isEmpty()) {
				String name = nameField.getText();
				String supplier = supplierField.getText();
				double quantity;
				try {
					quantity = Double.parseDouble(quantityField.getText());
				} catch (NumberFormatException ex) {
					showErrorDialog("Invalid input", "Quantity must be a number.");
					return;
				}
				Menu_item m=new Menu_item();
				Ingredient r = new Ingredient(name, supplier, quantity);
				r=ingrediantTest.addIngredientToDatabase(r);
				ingredientList.add(r);
				dialog.close();
			}
			else {
				showErrorDialog("Enter all value ", "Enter all value");
			}
		}
				);

		VBox vbox = new VBox(10, nameField, supplierField, quantityField, saveButton);
		vbox.setPadding(new Insets(10));

		Scene scene = new Scene(vbox, 300, 200);
		dialog.setScene(scene);
		dialog.showAndWait();
	}

	private Stage showUpdateIngredientDialog(int id) {
		Stage primaryStage=new Stage();
		Label label2 = new Label("what you want to update (chose) ??"); 
		ComboBox<String> comboBox = new ComboBox<>();
		comboBox.getItems().addAll("Ingredient Name", "Ingredient Supplier", "Ingredient Quantity");
		comboBox.setValue("Ingredient Name");

		HBox hb1=new HBox(20,label2,comboBox);
		hb1.setAlignment(Pos.CENTER_LEFT);
		// Label
		Label label = new Label("New Ingredient Name"); // Default label text

		// Update label based on ComboBox selection
		comboBox.setOnAction(e -> {
			String selected = comboBox.getValue();
			if ("Ingredient Name".equals(selected)) {
				label.setText("New Ingredient Name :");
			} else if ("Ingredient Supplier".equals(selected)) {
				label.setText("New Ingredient Supplier :");
			} else if ("Ingredient Quantity".equals(selected)) {
				label.setText("New Ingredient Quantity :");
			}
		});

		// TextField
		TextField textField = new TextField();
		textField.setPromptText("Enter new value");

		HBox hb2=new HBox(20,label,textField);
		hb2.setAlignment(Pos.CENTER_LEFT);

		// Clear Button
		Button clearButton = new Button("Clear");
		clearButton.setOnAction(e -> textField.clear());
		clearButton.setPrefWidth(70);
		clearButton.setPrefHeight(20);
		// Update Button
		Button updateButton = new Button("Update");
		updateButton.setPrefWidth(70);
		updateButton.setPrefHeight(20);
		updateButton.setOnAction(e -> {
			if (textField.getText().trim().isEmpty()||textField.getText().isEmpty()) {
				showErrorDialog("Input Error", "The text field cannot be empty.");
			} else {
				String selected = comboBox.getValue();
				if ("Ingredient Name".equals(selected)) {
					ingrediantTest.updateIngredientName(id,textField.getText());
				} else if ("Ingredient Supplier".equals(selected)) {
					ingrediantTest.updateIngredientSupplier(id, textField.getText());
				} else if ("Ingredient Quantity".equals(selected)) {
					try {
						int number = Integer.parseInt(textField.getText());
						ingrediantTest.updateIngredientQuantity(id,number);
					} catch (NumberFormatException y) {
						showErrorDialog("Not Integer ", "Enter int value of Quantity ");
					}

				}
				ingredientList.clear();
				ingredientList.addAll(ingrediantTest.loadIngredientsFromDatabase()); 
				primaryStage.close();
			}
		});
		HBox hb3=new HBox(20,clearButton,updateButton);
		hb3.setAlignment(Pos.CENTER);
		// Layout
		VBox layout = new VBox(30,hb1,hb2,hb3);
		layout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-spacing: 10;");

		// Scene
		Scene scene = new Scene(layout, 450, 350);

		// Stage
		primaryStage.setTitle("Ingredient Manager");
		primaryStage.setScene(scene);
		primaryStage.show();
		return primaryStage;
	}




	private void showErrorDialog(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
	}


}
