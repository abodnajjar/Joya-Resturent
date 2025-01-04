package application;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class menu_item_ingredient_Tabe {
	private TableView<Ingredient> tableView;
	private ObservableList<Ingredient> item_ingredient_list= FXCollections.observableArrayList();
	private menu_item_ingredient_Test test;

	public Stage main(int id) {
		item_ingredient_list.clear();
		Stage primaryStage=new Stage();
		test = new menu_item_ingredient_Test();

		primaryStage.setTitle("Ingredien for iteam ");

		tableView = new TableView<>();
		TableColumn<Ingredient, Number> idColumn = new TableColumn<>("ID");
		idColumn.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getId()));
		idColumn.setPrefWidth(100);

		TableColumn<Ingredient, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
		nameColumn.setPrefWidth(200);

		tableView.getColumns().addAll( idColumn, nameColumn );
		item_ingredient_list.add((Ingredient) test.getIngredientsForMenuItem(id)); 
		tableView.setItems(item_ingredient_list);
		FilteredList<Ingredient> filteredData = new FilteredList<Ingredient>(item_ingredient_list, p -> true);
		tableView.setItems(filteredData);

		TextField searchField = new TextField();
		searchField.setPromptText("Search for ingredient ......");

		searchField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(payment -> {
				// If the search field is empty, show all items
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				// Perform a case-insensitive search on the name column
				String lowerCaseFilter = newValue.toLowerCase();
				return payment.getName().toLowerCase().contains(lowerCaseFilter);
			});
		});

		Label addButton = new Label("Add");
		addButton.setPrefWidth(150);
		addButton.setPrefHeight(30);
		VBox vb1= new VBox(20,addButton );
		Button deleteButton = new Button("Delete");
		deleteButton.setPrefWidth(150);
		deleteButton.setPrefHeight(30);


		// Button actions
	
			Ingredient_Test x=new Ingredient_Test();
			List<Ingredient> ingredientsList = x.loadIngredientsFromDatabase();
			ComboBox<Ingredient> ingredientComboBox = new ComboBox<>();
			ingredientComboBox.setValue(ingredientsList.get(1)); // Set the initial value
			ingredientComboBox.getItems().addAll(ingredientsList);
			vb1.getChildren().add(ingredientComboBox);
			// Handle ComboBox selection change
			ingredientComboBox.setOnAction(o -> {
			    Ingredient selectedIngredient = ingredientComboBox.getValue();
			    if (selectedIngredient != null) {
			        // Create menu_item_ingredient object with the selected ingredient
			        menu_item_ingredient c = new menu_item_ingredient(id, selectedIngredient.getId());

			        // Insert into the database
			        test.insert_menu_item_ingredient(c);

			        // Clear and update the list
			        item_ingredient_list.clear();
			        item_ingredient_list.add((Ingredient) test.getIngredientsForMenuItem(id));

			        // Close the relevant window or do further actions
			       
			    }
			});
		

		deleteButton.setOnAction(e -> {
			// Get selected payment from the TableView
			Ingredient selectedPayment = (Ingredient) tableView.getSelectionModel().getSelectedItem();

			if (selectedPayment != null) {
				// Confirmation dialog
				Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
				confirmationAlert.setTitle("Delete Confirmation");
				confirmationAlert.setHeaderText("Are you sure you want to delete this payment?");
				confirmationAlert.setContentText("Ingredient ID: " + selectedPayment.getId());

				// Show confirmation dialog
				confirmationAlert.showAndWait().ifPresent(response -> {
					if (response == ButtonType.OK) {
						// Remove selected payment from ObservableList
						item_ingredient_list.remove(selectedPayment);
						test.deleteSelected_iteam(id,selectedPayment.getId() );
						tableView.getSelectionModel().clearSelection();
						Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
						successAlert.setContentText("Ingredient successfully deleted.");
						successAlert.show();
					}
				});
			} else {
				// Show alert if no payment is selected
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setContentText("Please select a Ingredient to delete.");
				alert.show();
			}
		});

		HBox hb=new HBox(30,vb1,deleteButton);
		hb.setAlignment(Pos.CENTER);
		// Create a VBox for the content
		VBox vbox = new VBox(30);
		vbox.getChildren().addAll(searchField,tableView,hb);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(20, 20, 50, 20));
		Scene root=new Scene(vbox,500,500);
		primaryStage.setScene(root);
		return primaryStage;
	}
//	public Stage Insert(int id) {
//		Stage r=new Stage();
//		Ingredient_Test x=new Ingredient_Test();
//		List<Ingredient> ingredientsList = x.loadIngredientsFromDatabase();
//		ComboBox<Ingredient> ingredientComboBox = new ComboBox<>();
//		ingredientComboBox.setValue(ingredientsList.get(1));
//		ingredientComboBox.getItems().addAll(ingredientsList);
//
//		Button add=new Button(" Add ");
//		add.setOnAction(e->{
//			Ingredient selectedIngredient = ingredientComboBox.getValue();
//			menu_item_ingredient c=new menu_item_ingredient(id,selectedIngredient.getId());
//			test.insert_menu_item_ingredient(c);
//			item_ingredient_list.clear();
//			item_ingredient_list.addAll(test.getIngredientsForMenuItem(id)); 
//			r.close();
//		});
//		add.setPrefWidth(150);
//		add.setPrefHeight(20);
//		VBox vb=new VBox(30,ingredientComboBox,add);
//		vb.setAlignment(Pos.CENTER);
//		Scene scene = new Scene(vb, 500, 300);
//		r.setTitle("Add Payment:-");
//		r.setScene(scene);
//		return r;
//	}

}

