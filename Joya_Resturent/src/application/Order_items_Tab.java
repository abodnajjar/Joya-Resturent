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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Order_items_Tab {
	private TableView<Test> tableView;
	private ObservableList<Test> order_iteam_list= FXCollections.observableArrayList();
	private order_items_Test test;
	private menu_item_ingredient_Test mii=new menu_item_ingredient_Test();
	private Ingredient_Test ingredent_test=new Ingredient_Test();
     public double changed_price=0;
	public Stage main(int id) {
		order_iteam_list.clear();
		Stage primaryStage=new Stage();
		test = new order_items_Test();

		primaryStage.setTitle("Iteam for Order  ");

		tableView = new TableView<>();
		TableColumn<Test, Number> idColumn = new TableColumn<>("ID");
		idColumn.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getId()));
		idColumn.setPrefWidth(100);

		TableColumn<Test, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
		nameColumn.setPrefWidth(200);

		TableColumn<Test, Double> Price = new TableColumn<>("Price ");
		Price.setCellValueFactory(cellData -> 
		new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getPrice()));
		Price.setPrefWidth(100);

		TableColumn<Test,Number> Quatity = new TableColumn<>("Quatity");
		Quatity.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getQuatity()));
		Quatity.setPrefWidth(100);

		TableColumn<Test, Double> Total_cost = new TableColumn<>("Total Price ");
		Total_cost.setCellValueFactory(cellData -> 
		new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getTotal_Price()));
		Total_cost.setPrefWidth(100);

		tableView.getColumns().addAll( idColumn, nameColumn,Price,Quatity,Total_cost  );
		ArrayList<Test> Array_test= Test_list(id);
		order_iteam_list.addAll(Array_test); 
		tableView.setItems(order_iteam_list);
		FilteredList<Test> filteredData = new FilteredList<Test>(order_iteam_list, p -> true);
		tableView.setItems(filteredData);

		TextField searchField = new TextField();
		searchField.setPromptText("Search for iteam ......");

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

		Button addButton = new Button("Add");
		addButton.setPrefWidth(150);
		addButton.setPrefHeight(30);
		
		Button deleteButton = new Button("Delete");
		deleteButton.setPrefWidth(150);
		deleteButton.setPrefHeight(30);

		Button UpdateButton = new Button("Update");
		UpdateButton.setPrefWidth(150);
		UpdateButton.setPrefHeight(30);


		// Button actions

		Menu_iteam_Test x2=new Menu_iteam_Test();
		List<Menu_item> Menu_item_list = x2.load_menu_item_FromDatabase();
		ObservableList<Menu_item> observableItems2 = FXCollections.observableArrayList(Menu_item_list );
		ComboBox<Menu_item> Menu_item_combox = new ComboBox<>();
		Menu_item_combox.setEditable(true); // Enable search functionality
		Menu_item_combox.setItems(observableItems2);

		Menu_item_combox.setConverter(new StringConverter<Menu_item>() { 
			@Override
			public String toString(Menu_item menuItem) {
				// Return the name of the Menu_item or an empty string if null
				return (menuItem == null) ? "" : menuItem.getName();
			}

			@Override
			public Menu_item fromString(String name) {
				// Find the Menu_item object by its name in the observable list
				return observableItems2.stream()
						.filter(menuItem -> menuItem.getName().equals(name))
						.findFirst()
						.orElse(null);
			}
		});
		
		// Handle ComboBox selection change
		Label com_label=new Label("Select iteam :");
		Label s_label=new Label("Select Quantity :");
		Spinner<Integer> spinner = new Spinner<>();
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1)); 
        
        addButton.setOnAction(e -> {
        	if (Menu_item_combox.getValue() == null) {
        		Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Please select a item to Add .");
				alert.show();
        	} else {
        		Menu_item m=Menu_item_combox.getValue();
        		double to=m.getPrice()*spinner.getValue();
        		Test test=new Test(m.getId(),m.getName(),m.getPrice(),spinner.getValue(),to);
        		this.changed_price=this.changed_price + to;
        		order_iteam_list.add(test);
        		this.test.addNewOrderItem(id,m.getId(),test.getQuatity(),test.getTotal_Price());
        		  ArrayList<Ingredient> ingredent_list =(ArrayList<Ingredient>) mii.getIngredientsForMenuItem(m.getId());
                  for(int j=0;j<ingredent_list.size();j++) {
                	  Ingredient in =ingredent_list.get(j); 
                	  double q=in.getQuantity_in_stock();
                	  double r=q-spinner.getValue();
                	  in.setQuantity_in_stock(r);
                      ingredent_test.updateIngredientQuantity(in.getId(),r);
                  }
        		Menu_item_combox.setValue(null);
        		spinner.getValueFactory().setValue(1);
        	}
        	
        });
        UpdateButton.setOnAction(e->{
        	Test selected_iteam =  tableView.getSelectionModel().getSelectedItem();
			if (selected_iteam != null) {
				Stage stages=new Stage();
				Label label = new Label("New Quantity:");
		        // Spinner
		        Spinner<Integer> spinner2 = new Spinner<>();
		        spinner2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000,selected_iteam.getQuatity())); // Range: 1-100, initial value: 1

		        // Button
		        Button updateButton = new Button("Update");

		        // Handle button click
		        updateButton.setOnAction(y -> {
		        	stages.close();
		        	int ab=spinner2.getValue()-selected_iteam.getQuatity();
		        	double ch=ab*selected_iteam.getPrice();
		        	this.changed_price=this.changed_price + ch;
		        	double r=(spinner2.getValue()*selected_iteam.getPrice());
		        	this.test.update_Quatity_Price(id,selected_iteam.getId(),spinner2.getValue(),r);
		        	order_iteam_list.removeAll();
		        	order_iteam_list.clear();
		        	order_iteam_list.addAll(Test_list(id));		        	
		        	 ArrayList<Ingredient> ingredent_list =(ArrayList<Ingredient>) mii.getIngredientsForMenuItem(selected_iteam.getId());
	                 for(int j=0;j<ingredent_list.size();j++) {
	               	  Ingredient in =ingredent_list.get(j); 
	               	  double q=in.getQuantity_in_stock();
	               	  double p=q-ab;
	               	  in.setQuantity_in_stock(r);
	                     ingredent_test.updateIngredientQuantity(in.getId(),p);
	                 }
			            tableView.refresh();
		            tableView.getSelectionModel().clearSelection();
		        });

		        // HBox to arrange the label, spinner, and button horizontally
		        HBox hbox = new HBox(10, label, spinner2, updateButton); // 10 px spacing
		        hbox.setStyle("-fx-padding: 10; -fx-alignment: center;"); // Center alignment and padding

		        // Scene and Stage setup
		        Scene scene = new Scene(hbox, 500, 100);
		        stages.setTitle("Updata Quatity ");
		        stages.setScene(scene);
		        stages.show();
				
			} else {
				// Show alert if no payment is selected
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setContentText("Please select a item to Update.");
				alert.show();
			}
		});
        
        
		deleteButton.setOnAction(e -> {
			// Get selected payment from the TableView
			Test selected_iteam =  tableView.getSelectionModel().getSelectedItem();

			if (selected_iteam != null) {
				test.deleteOrder_iteam(id,selected_iteam.getId());
				Array_test.remove(selected_iteam);
				this.changed_price=this.changed_price - selected_iteam.getTotal_Price();
				order_iteam_list.remove(selected_iteam);
				 ArrayList<Ingredient> ingredent_list =(ArrayList<Ingredient>) mii.getIngredientsForMenuItem(selected_iteam.getId());
                 for(int j=0;j<ingredent_list.size();j++) {
               	  Ingredient in =ingredent_list.get(j); 
               	  double q=in.getQuantity_in_stock();
               	  double r=q+selected_iteam.getQuatity();
               	  in.setQuantity_in_stock(r);
                     ingredent_test.updateIngredientQuantity(in.getId(),r);
                 }
				
			} else {
				// Show alert if no payment is selected
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setContentText("Please select a item to delete.");
				alert.show();
			}
		});
		HBox hb1=new HBox(10,com_label,Menu_item_combox);
        HBox hb2=new HBox(10,s_label,spinner);
		VBox vb1= new VBox(20,hb1,hb2,addButton );
		HBox hb=new HBox(30,vb1,deleteButton,UpdateButton);
		hb.setAlignment(Pos.CENTER);
		// Create a VBox for the content
		VBox vbox = new VBox(30);
		vbox.getChildren().addAll(searchField,tableView,hb);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(20, 20, 50, 20));
		Scene root=new Scene(vbox,700,600);
		primaryStage.setScene(root);
		return primaryStage;
	}

	public String Get_iteam_Name(int id) {
		String u=" ";
		Menu_iteam_Test mit=new Menu_iteam_Test();
		ArrayList<Menu_item> kist =(ArrayList<Menu_item>) mit.load_menu_item_FromDatabase();
		for(int i=0;i<kist.size();i++) {
			if(kist.get(i).getId()==id) {
				u=kist.get(i).getName();
			}
		}
		return u;
	}
	public ArrayList<Test> Test_list(int id) {
		ArrayList<order_items> x=(ArrayList<order_items>) test.getIngredientsForMenuItem(id);
		ArrayList<Test> Array_test=new ArrayList<>();
		for(int i=0;i<x.size();i++) {
			String u=Get_iteam_Name(x.get(i).getMenuId());
			double d=x.get(i).getOrderPrice()/x.get(i).getQuantity();
			Test test=new Test(x.get(i).getMenuId(),u,d,x.get(i).getQuantity(),x.get(i).getOrderPrice());
			Array_test.add(test);
		}
		return Array_test;
	}
	

}
