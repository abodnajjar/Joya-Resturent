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
import javafx.scene.control.TableCell;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class Menu_item_Tabe {
	private Menu_iteam_Test menu_iteam_Test = new Menu_iteam_Test();
	TableView<Menu_item> menuTable = new TableView<>();
	ObservableList<Menu_item> menuList = FXCollections.observableArrayList();
	menu_item_ingredient_Tabe menu_item_ingredient_Tabe =new menu_item_ingredient_Tabe ();
	public VBox main_Stage() {
		menuList = FXCollections.observableArrayList();
		menuList.addAll(menu_iteam_Test.load_menu_item_FromDatabase()); 
		menuTable.setItems(menuList);

		FilteredList<Menu_item> filteredList = new FilteredList<>( menuList, p -> true); // Initially no filter
		menuTable.setItems(filteredList);

		TableColumn<Menu_item, Integer> idColumn = new TableColumn<>("ID");
		idColumn.setCellValueFactory(cellData -> 
		new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getId()));

		TableColumn<Menu_item, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(cellData -> 
		new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getName()));
		nameColumn.setPrefWidth(150);


		TableColumn<Menu_item, String> supplierColumn = new TableColumn<>("Descirption");
		supplierColumn.setCellValueFactory(cellData -> 
		new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getDescirption()));
		supplierColumn.setPrefWidth(500);

		TableColumn<Menu_item, Double> quantityColumn = new TableColumn<>("Price");
		quantityColumn.setCellValueFactory(cellData -> 
		new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getPrice()));
		quantityColumn.setPrefWidth(100);

		TableColumn<Menu_item,String> actionColumn = new TableColumn<>("Action");
		actionColumn .setPrefWidth(200);
		actionColumn.setCellFactory(column -> new TableCell<>() {
			private final Button button = new Button("show ingredient ");
			
			{
				button.setOnAction(event -> {
					 Menu_item item = getTableView().getItems().get(getIndex());
					Stage s=menu_item_ingredient_Tabe.main(item.getId());
					s.show();
				});
			}
			
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);  // Set graphic to null if the row is empty
				} else {
					setGraphic(button);  // Set the button graphic when the cell is not empty
				}
			}

		});



		menuTable.getColumns().addAll(idColumn, nameColumn, supplierColumn, quantityColumn,actionColumn);

		// Search bar
		TextField searchField = new TextField();
		searchField.setPromptText("Search by name or Descirption  ....");
		searchField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredList.setPredicate(ingredient -> {
				if (newValue == null || newValue.isEmpty()) {
					return true; // If search field is empty, show all
				}
				String lowerCaseFilter = newValue.toLowerCase();
				return ingredient.getName().toLowerCase().contains(lowerCaseFilter)
						|| ingredient.getDescirption().toLowerCase().contains(lowerCaseFilter);
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
			Menu_item selectedIngredient = (Menu_item)menuTable.getSelectionModel().getSelectedItem();
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
						menu_iteam_Test.deleteSelected_iteam(selectedIngredient.getId());
						menuList.remove(selectedIngredient);
						Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
						successAlert.setContentText(" menu iteam successfully deleted.");
						successAlert.show();
						menuTable.getSelectionModel().clearSelection();
					}
				});
			} else {
				// Show alert if no payment is selected
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setContentText("Please select a menu iteam to delete.");
				alert.show();
			}
		});
		updateButton.setOnAction(e -> {
			Menu_item selectedIngredient = (Menu_item)menuTable.getSelectionModel().getSelectedItem();
			if (selectedIngredient != null) {
				showUpdateIngredientDialog(selectedIngredient.getId(),selectedIngredient.getDescirption());
				menuTable.getSelectionModel().clearSelection();

			}
			else {
				// Show alert if no payment is selected
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setContentText("Please select a menu iteam to delete.");
				alert.show();
			}

		});

		HBox buttonBox = new HBox(15, addButton, deleteButton, updateButton);
		buttonBox.setAlignment(Pos.CENTER);

		VBox topLayout = new VBox(10, searchField); // Add search field to top
		topLayout.setPadding(new Insets(10));
		VBox vb= new VBox(30,topLayout,menuTable,buttonBox);
		return vb;
	}

	private void showAddIngredientDialog() {
		Stage dialog = new Stage();
		dialog.setTitle("Add meanue iteam :-");

		TextField nameField = new TextField();
		nameField.setPromptText("MenuName");

		TextField supplierField = new TextField();
		supplierField.setPromptText("MenuDescription");

		TextField quantityField = new TextField();
		quantityField.setPromptText("price");

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
				Menu_item m=new Menu_item(name,supplier,quantity);
				menu_iteam_Test.addIngre_Iteam_ToDatabase(m);
				menuList.add(m);
				dialog.close();
			}
			else {
				showErrorDialog("enter all value ", "enter all value ");
			}
		}
				);

		VBox vbox = new VBox(10, nameField, supplierField, quantityField, saveButton);
		vbox.setPadding(new Insets(10));

		Scene scene = new Scene(vbox, 300, 200);
		dialog.setScene(scene);
		dialog.showAndWait();
	}

	private Stage showUpdateIngredientDialog(int id,String d) {
		Stage primaryStage=new Stage();
		Label label2 = new Label("what you want to update (chose) ??"); 
		ComboBox<String> comboBox = new ComboBox<>();
		comboBox.getItems().addAll("Menu Name", "Menu Description", "price");
		comboBox.setValue("Menu Name");

		HBox hb1=new HBox(20,label2,comboBox);
		hb1.setAlignment(Pos.CENTER_LEFT);
		// Label
		Label label = new Label("New Menu Name"); // Default label text
		TextField textField = new TextField();
		textField.setPromptText("Enter new value");
		// Update label based on ComboBox selection
		comboBox.setOnAction(e -> {
			String selected = comboBox.getValue();
			if ("Menu Name".equals(selected)) {
				label.setText("New Menu Name :");
			} else if ("Menu Description".equals(selected)) {
				textField.setText(d);
				textField.setPrefWidth(400);
				textField.setPrefHeight(20);
				label.setText("New Description :");
				textField.setMaxWidth(300);
			} else if ("price".equals(selected)) {
				label.setText("New price :");
			}
		});

		// TextField


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
				if ("Menu Name".equals(selected)) {
					menu_iteam_Test.updateMenuName(id,textField.getText());
				} else if ("Menu Description".equals(selected)) {

					menu_iteam_Test.updateMenuDescription(id,textField.getText());
				} else if ("price".equals(selected)) {
					try {
						Double number = Double.parseDouble(textField.getText());
						menu_iteam_Test.updateMenuprice(id,number);
					} catch (NumberFormatException y) {
						showErrorDialog("Not Integer ", "Enter int value of Quantity ");
					}

				}
				menuList.clear();
				menuList.addAll(menu_iteam_Test.load_menu_item_FromDatabase());
				primaryStage.close();
			}
		});
		HBox hb3=new HBox(20,clearButton,updateButton);
		hb3.setAlignment(Pos.CENTER);
		// Layout
		VBox layout = new VBox(30,hb1,hb2,hb3);
		layout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-spacing: 10;");

		// Scene
		Scene scene = new Scene(layout, 800, 350);

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
