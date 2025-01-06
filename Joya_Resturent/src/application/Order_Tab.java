package application;
import javafx.stage.Stage;

import javafx.stage.WindowEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
public class Order_Tab {
	private TableView<Order> tableView =new TableView<>();
	private ObservableList<Order> order_list= FXCollections.observableArrayList();
	private Order_Test test=new Order_Test();
	private Customer_Test customer_test=new Customer_Test();
	private Menu_iteam_Test iteam_test=new Menu_iteam_Test();
	private int employy_id =0;
	private Payment_Test payment_test =new Payment_Test();
	private order_items_Test order_iteam_test=new order_items_Test();
    private menu_item_ingredient_Test mii=new menu_item_ingredient_Test();
    private Ingredient_Test ingredent_test=new Ingredient_Test();
	public Order_Tab(int employy_id) {
		super();
		this.employy_id = employy_id;
	}

	public VBox main() {
		order_list = FXCollections.observableArrayList();
		order_list.addAll(test.getAllCustomers()); 
		tableView.setItems(order_list);

		FilteredList<Order> filteredList = new FilteredList<>( order_list, p -> true); // Initially no filter
		tableView.setItems(filteredList);

		TableColumn<Order, Integer> Order_id = new TableColumn<>("Order ID");
		Order_id.setCellValueFactory(cellData -> 
		new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getId()));

		TableColumn<Order, Integer> customer_id = new TableColumn<>("Customer ID");
		customer_id.setCellValueFactory(cellData -> 
		new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getCustomer_id()));
		customer_id.setPrefWidth(150);


		TableColumn<Order,Integer> Employee_id = new TableColumn<>("Employee ID");
		Employee_id.setCellValueFactory(cellData -> 
		new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getEmployee_id()));
		Employee_id.setPrefWidth(100);

		TableColumn<Order,Integer> Payment_id = new TableColumn<>("Payment ID");
		Payment_id.setCellValueFactory(cellData -> 
		new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getPayment_id()));
		Payment_id.setPrefWidth(100);

		TableColumn<Order,String> Date = new TableColumn<>("Date");
		Date.setCellValueFactory(cellData -> 
		new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getDate()));
		Date.setPrefWidth(100);


		TableColumn<Order,String> Time = new TableColumn<>("Time");
		Time.setCellValueFactory(cellData -> 
		new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getTime()));
		Time.setPrefWidth(100);

		TableColumn<Order,Double> Price = new TableColumn<>("Total Price");
		Price.setCellValueFactory(cellData -> 
		new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getPrice()));
		Price.setPrefWidth(100);


		TableColumn<Order,String> actionColumn = new TableColumn<>("Action");
		actionColumn .setPrefWidth(200);
		actionColumn.setCellFactory(column -> new TableCell<>() {
			private final Button button = new Button("show Iteam ");

			{
				button.setOnAction(event -> {
					Order item = getTableView().getItems().get(getIndex());
					Order_items_Tab ot=new Order_items_Tab();
					
					Stage s=ot.main(item.getId());
					s.show();
					
					 s.setOnCloseRequest(t -> {
						 double p=ot.changed_price;
							double u=item.getPrice()+p;
							test.updatePrice(item.getId(),u);
						 order_list.clear();
							order_list.addAll(test.getAllCustomers()); 
				        });
					
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



		tableView.getColumns().addAll(Order_id, customer_id, Employee_id,Payment_id,Date, Time,Price,actionColumn);

		// Search bar
		TextField searchField = new TextField();
		searchField.setPromptText("Search by  id or Date   ....");
		searchField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredList.setPredicate(Order -> {
				if (newValue == null || newValue.isEmpty()) {
					return true; // If search field is empty, show all
				}
				String lowerCaseFilter = newValue.toLowerCase();
				return Order.getDate().toLowerCase().contains(lowerCaseFilter)||
						String.valueOf(Order.getId()).toLowerCase().contains(lowerCaseFilter)
						;

			});
		});

		Button addButton = new Button("Add");
		Button deleteButton = new Button("Delete");
		Button updateButton = new Button("Update");
		addButton.setPrefWidth(150);
		addButton.setPrefHeight(30);
		deleteButton.setPrefWidth(150);
		deleteButton.setPrefHeight(30);
		updateButton.setPrefWidth(150);
		updateButton.setPrefHeight(30);
		HBox buttonBox = new HBox(15, addButton, deleteButton, updateButton);
		buttonBox.setAlignment(Pos.CENTER);
		addButton.setOnAction(e->{
			Stage r= AddOrder();
			r.show();
		});
		deleteButton.setOnAction(e -> {
			Order selectedIngredient = (Order)tableView.getSelectionModel().getSelectedItem();
			if (selectedIngredient != null) {
				Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
				confirmationAlert.setTitle("Delete Confirmation");
				confirmationAlert.setHeaderText("Are you sure you want to delete this order?");
				confirmationAlert.setContentText("oder ID: " + selectedIngredient.getId());

				// Show confirmation dialog
				confirmationAlert.showAndWait().ifPresent(response -> {
					if (response == ButtonType.OK) {
						// Remove selected payment from ObservableList
						test.deleteSelected_iteam(selectedIngredient.getId());
						order_list.remove(selectedIngredient);
						Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
						successAlert.setContentText(" menu iteam successfully deleted.");
						successAlert.show();
						tableView.getSelectionModel().clearSelection();
					}
				});
			} else {
				// Show alert if no payment is selected
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setContentText("Please select a order to delete.");
				alert.show();
			}
		});
		updateButton.setOnAction(e->{
			Order selectedPayment = (Order) tableView.getSelectionModel().getSelectedItem();

			if (selectedPayment != null) {

				Stage r=showUpdateIngredientDialog(selectedPayment.getId());
				r.show();
				tableView.getSelectionModel().clearSelection();
			}
			else {
				// Show alert if no payment is selected
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setContentText("Please select a Order to update.");
				alert.show();
			}
		});

		VBox topLayout = new VBox(10, searchField); // Add search field to top
		topLayout.setPadding(new Insets(10));
		VBox vb= new VBox(30,topLayout,tableView,buttonBox);
		return vb;
	}

	private Stage showUpdateIngredientDialog(int id) {
		Stage primaryStage=new Stage();
		

		Customer_Test c=new Customer_Test();
		ObservableList<Customer> observableItems = FXCollections.observableArrayList(customer_list);
		// Step 3: Create a ComboBox
		ComboBox<Customer> comboBox = new ComboBox<>();
		comboBox.setEditable(true); // Make the ComboBox editable to allow searching
		comboBox.setItems(observableItems);
		comboBox.setConverter(new StringConverter<>() {
		
			public String toString(Customer customer) {
				return (customer == null) ? "" : customer.getName();
			}

			@Override
			public Customer fromString(String name) {
				return observableItems.stream()
						.filter(customer -> customer.getName().equals(name))
						.findFirst()
						.orElse(null);
			}
		});
		
		ArrayList<Payment> payment_list=(ArrayList<Payment>) payment_test.getAllPayments();
		ObservableList<Payment> observableItems3 = FXCollections.observableArrayList(payment_list);
		// Step 3: Create a ComboBox
		ComboBox<Payment> comboBox3 = new ComboBox<>();
		comboBox3.setEditable(true); // Make the ComboBox editable to allow searching
		comboBox3.setItems(observableItems3);
		comboBox3.setConverter(new StringConverter<Payment>() {
			@Override
			public String toString(Payment customer) {
				return (customer == null) ? "" : customer.getMethod();
			}

			@Override
			public Payment fromString(String name) {
				return observableItems3.stream()
						.filter(customer -> customer.getMethod().equals(name))
						.findFirst()
						.orElse(null);
			}
		});
				Label label = new Label();
				label.setFont(Font.font(20));
				label.setText("Select New  Payment type  :");
				
				Button update1=new Button(" Update Payment ");
				update1.setOnAction(r->{
					Payment p=comboBox3.getValue();
					test.updatepayment_id(id, p.getId());
					order_list.clear();
					order_list.addAll(test.getAllCustomers());
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Update Payment Method ");
					alert.setContentText("We Update Order to New Payment Method "+p.getMethod());
					alert.showAndWait();
				});
				HBox hb1=new HBox(30,label,comboBox3,update1);
				hb1.setAlignment(Pos.CENTER_LEFT);
		
				
				Label label2 = new Label();
				label2.setFont(Font.font(20));
				label2.setText(" Select New Customer  :");
				Button update2=new Button(" Update Customer ");
				update2.setOnAction(r->{
					Customer cus=comboBox.getValue();
					test.updateCustomer_id(id,cus.getId());
					order_list.clear();
					order_list.addAll(test.getAllCustomers());
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Update Customer");
					alert.setContentText("We Update Order to New Customer name "+cus.getName());
					alert.showAndWait();
				});
				HBox hb5=new HBox(30,label2,comboBox,update2);
				hb5.setAlignment(Pos.CENTER_LEFT);
				
				
		
			
		VBox layout = new VBox(30,hb1,hb5);
		layout.setPadding(new Insets(10, 20, 10, 20));
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout, 700, 300);

		// Stage
		primaryStage.setTitle("Order Update ");
		primaryStage.setScene(scene);
		primaryStage.show();
		return primaryStage;
	}
	
	int  customer_id=0;
	double total=0; 
	ArrayList<Test> selec_iteam=new ArrayList<>();
	ArrayList<Customer> customer_list=(ArrayList<Customer>) customer_test.getAllCustomers();
	
	public Stage AddOrder() {

		Stage stage=new Stage();
		
		Label customer1_label =new Label("Select Customer  : ");
		ObservableList<Customer> observableItems = FXCollections.observableArrayList(customer_list);
		// Step 3: Create a ComboBox
		ComboBox<Customer> comboBox = new ComboBox<>();
		comboBox.setEditable(true); // Make the ComboBox editable to allow searching
		comboBox.setItems(observableItems);
		comboBox.setConverter(new StringConverter<>() {
		
			public String toString(Customer customer) {
				return (customer == null) ? "" : customer.getName();
			}

			@Override
			public Customer fromString(String name) {
				return observableItems.stream()
						.filter(customer -> customer.getName().equals(name))
						.findFirst()
						.orElse(null);
			}
		});

		Button customer2_add=new Button("Add Customer ");
		HBox vb1=new HBox(30,customer1_label,comboBox,customer2_add);
		vb1.setAlignment(Pos.CENTER_LEFT);


		customer2_add.setOnAction(e->{
			Stage s=new Stage();
			Label customer2_label =new Label("Add New Customer ");
			customer2_label.setAlignment(Pos.CENTER);
			Label customer2_Name=new Label("Customer Name : ");
			TextField customer2_textfiled=new TextField();
			Button ad=new Button("Add customer ");
			customer2_add.setAlignment(Pos.CENTER);
			
			HBox hb=new HBox(30,customer2_Name,customer2_textfiled,ad);
			hb.setAlignment(Pos.CENTER);

			ad.setOnAction(r->{
				if(customer2_textfiled.getText().isEmpty()){
					showErrorDialog("Customer Name", "Enter a new Customer Name"); 
				}
				else {
					Customer cust=new Customer(customer2_textfiled.getText(), 0.0);
					customer_test.addNewCustomer(cust);
					customer_id=cust.getId();
					customer_list.add(cust);
					customer_list.removeAll(customer_list);
					customer_list=(ArrayList<Customer>) customer_test.getAllCustomers();
					observableItems.setAll(customer_list);
					s.close();
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Alert");
					alert.setHeaderText("This is an information alert");
					alert.setContentText("we add New Customer with name "+cust.getName());
					alert.showAndWait();	
				}
			});
			Scene scene = new Scene(hb, 500, 150);
			s.setTitle("Add new Customer");
			s.setScene(scene);
			s.show();

		});




		ArrayList<Payment> payment_list=(ArrayList<Payment>) payment_test.getAllPayments();
		Label payment_label =new Label("Select Payment Method  : ");
		ObservableList<Payment> observableItems3 = FXCollections.observableArrayList(payment_list);
		// Step 3: Create a ComboBox
		ComboBox<Payment> comboBox3 = new ComboBox<>();
		comboBox3.setEditable(true); // Make the ComboBox editable to allow searching
		comboBox3.setItems(observableItems3);
		comboBox3.setConverter(new StringConverter<Payment>() {
			@Override
			public String toString(Payment customer) {
				return (customer == null) ? "" : customer.getMethod();
			}

			@Override
			public Payment fromString(String name) {
				return observableItems3.stream()
						.filter(customer -> customer.getMethod().equals(name))
						.findFirst()
						.orElse(null);
			}
		});


		HBox hb8=new HBox(30,payment_label,comboBox3);
		hb8.setAlignment(Pos.CENTER_LEFT);

		VBox hb2=new VBox(30,vb1,hb8);  
		hb2.setAlignment(Pos.CENTER);
		hb2.setPadding(new Insets(20, 0, 0, 20)); 

		Label iteam_label=new Label("Select the iteams ");
		ArrayList<Menu_item> itea_list=(ArrayList<Menu_item>) iteam_test.load_menu_item_FromDatabase();
		ObservableList<Menu_item> observableItems2 = FXCollections.observableArrayList(itea_list);

		// Step 3: Create a ComboBox
		ComboBox<Menu_item> comboBox2 = new ComboBox<>();
		comboBox2.setEditable(true); // Enable search functionality
		comboBox2.setItems(observableItems2);

		comboBox2.setConverter(new StringConverter<Menu_item>() { 
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


		Label Q_label=new Label("Enter Quatity : ");
		Spinner<Integer> spinner = new Spinner<>();
		spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
		// Optional: Set the Spinner to editable
		spinner.setEditable(false);
		HBox hb4=new HBox(20, Q_label,spinner);
		hb4.setAlignment(Pos.CENTER);
		HBox hb1=new HBox(20,iteam_label,comboBox2);
		hb1.setAlignment(Pos.CENTER);
		Button Add=new Button(" Add ");

		Add.setAlignment(Pos.CENTER);
		Add.setPrefHeight(30);
		Add.setPrefWidth(200);

		VBox vb2=new VBox(20,hb1,hb4,Add);
		vb2.setAlignment(Pos.CENTER);

		////////////////////////////////////////////////////////////////////////////////////////////
		TableView<Test> tableView = new TableView<>();
		// ObservableList<Test> menuItems = FXCollections.observableArrayList();
		TableColumn<Test, Integer> idColumn = new TableColumn<>("ID");
		idColumn .setCellValueFactory(cellData -> 
		new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getId()));

		TableColumn<Test, String> nameColumn = new TableColumn<>("Name");
		nameColumn .setCellValueFactory(cellData -> 
		new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getName()));
		nameColumn.setPrefWidth(200);

		TableColumn<Test, Double> priceColumn = new TableColumn<>("Price");
		priceColumn .setCellValueFactory(cellData -> 
		new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getPrice()));

		TableColumn<Test, Integer> quantityColumn = new TableColumn<>("Quantity");
		quantityColumn .setCellValueFactory(cellData -> 
		new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getQuatity()));

		TableColumn<Test, Double> totalPriceColumn = new TableColumn<>("Total Price");
		totalPriceColumn.setCellValueFactory(cellData -> 
		new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getTotal_Price()));


		// Add columns to TableView
		tableView.getColumns().addAll(idColumn, nameColumn, priceColumn, quantityColumn, totalPriceColumn);
		ObservableList<Test> data = FXCollections.observableArrayList();
		tableView.setItems(data);

		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		String s=String.valueOf(total);
		Label Total=new Label("Total Cost = "+s);
		Add.setOnAction(r->{
			Menu_item selectedItem = comboBox2.getValue();
			if (selectedItem == null) {
				showErrorDialog("No iteam Selected ","Select iteam to add ");
			} else {
				
				// selectedItem.getPrice() ;
				int spinnerValue = spinner.getValue();
				Double cost= selectedItem.getPrice()*spinnerValue;
				total=total+cost;
				String c=String.valueOf(total);
				Total.setText("Total Cost = "+c);
				Test love=new Test(selectedItem.getId(),selectedItem.getName(),selectedItem.getPrice(),spinnerValue,cost );
				selec_iteam.add(love);
				data.add(love);
				spinner.getValueFactory().setValue(1);
				comboBox2.setValue(null); 
				

			}  
		});

		Button delet_row=new Button("Delete");
		delet_row.setOnAction(e->{
			Test selectedItem = tableView.getSelectionModel().getSelectedItem();
			if (selectedItem != null) {
				selec_iteam.remove(selectedItem);
				tableView.getItems().remove(selectedItem);
				total=total-selectedItem.getTotal_Price();
				String c=String.valueOf(total);
				Total.setText("Total Cost = "+c);
				tableView.getSelectionModel().clearSelection();
			} else {
				showErrorDialog(" NO selected ","No itaem selected to delete ");
			}
		});
		
		Button update_row=new Button("Updata");
		update_row.setOnAction(e->{
			Test selectedItem = tableView.getSelectionModel().getSelectedItem();
			if (selectedItem != null) {
				Stage primaryStage=new Stage();
				Label label = new Label("New Quantity:");
		        // Spinner
		        Spinner<Integer> spinner2 = new Spinner<>();
		        spinner2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000,selectedItem.getQuatity())); // Range: 1-100, initial value: 1

		        // Button
		        Button updateButton = new Button("Update");

		        // Handle button click
		        updateButton.setOnAction(y -> {
		        	primaryStage.close();
		        	total=total-selectedItem.getTotal_Price();
		            int selectedValue = spinner2.getValue();
		            selectedItem.setQuatity(selectedValue);
		            selectedItem.setTotal_Price(selectedValue*selectedItem.getPrice());
		            total=total+selectedItem.getTotal_Price();
		            String c=String.valueOf(total);
					Total.setText("Total Cost = "+c);
					data.clear();
					data.addAll(selec_iteam);
			            // Refresh TableView
			            tableView.refresh();
		            tableView.getSelectionModel().clearSelection();
		        });

		        // HBox to arrange the label, spinner, and button horizontally
		        HBox hbox = new HBox(10, label, spinner2, updateButton); // 10 px spacing
		        hbox.setStyle("-fx-padding: 10; -fx-alignment: center;"); // Center alignment and padding

		        // Scene and Stage setup
		        Scene scene = new Scene(hbox, 300, 100);
		        primaryStage.setTitle("Updata Quatity ");
		        primaryStage.setScene(scene);
		        primaryStage.show();
				
			} else {
				showErrorDialog(" NO selected ","No itaem selected to Update ");
			}
		});
        HBox hb6=new HBox(20,delet_row,update_row);
        hb6.setAlignment(Pos.CENTER);
        //hb6.setPadding(new Insets(20, 20, 20, 20)); 
		VBox vb9=new VBox(20,tableView, hb6);
		
		HBox hb5=new HBox(20,vb2,vb9);
		hb5.setAlignment(Pos.CENTER);
		
		
		Total.setFont(Font.font(15));
		Total.setAlignment(Pos.CENTER_LEFT);
		Button Add_order=new Button("Add this order");
		Add_order.setOnAction(t->{
			if(comboBox.getValue()==null) {
				showErrorDialog("No customer selected ","Select a Customer");
			}
			else if(comboBox3.getValue()==null) {
				showErrorDialog("No Payment selected ","Select a Payment Method to pay ");
			}
			else if(selec_iteam.size()==0) {
				showErrorDialog("No iteam selected ","you Don't select Any iteam i cant do Order ");
			}
			else if(comboBox.getValue()!=null &&comboBox3.getValue()!=null&&selec_iteam.size()!=0){
				LocalTime currentTime = LocalTime.now();
				DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("HH:mm");
				String timeString = currentTime.format(formatter1);

				LocalDate currentDate = LocalDate.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-DD");
				String dateString = currentDate.format(formatter);
				Customer c=comboBox.getValue();
				int customer_id=comboBox.getValue().getId();
				double z=c.getPointer()+20.0;
				customer_test.updateCustomerPoints(customer_id,z);
				int payment_id=comboBox3.getValue().getId();
				Order order=new Order(dateString,timeString,customer_id,employy_id,payment_id,total);
				order_list.add(order);
				test.insertOrder(order);
				//addNewOrder_iteam(int o_id,int i_id,int quantity,double price)
				for(int i=0;i<selec_iteam.size();i++) {
                  order_iteam_test.addNewOrderItem(order.getId(), selec_iteam.get(i).getId(),selec_iteam.get(i).getQuatity()
                		  ,selec_iteam.get(i).getTotal_Price());
                  ArrayList<Ingredient> ingredent_list =(ArrayList<Ingredient>) mii.getIngredientsForMenuItem(selec_iteam.get(i).getId());
                  for(int j=0;j<ingredent_list.size();j++) {
                	  Ingredient in =ingredent_list.get(j); 
                	  double q=in.getQuantity_in_stock();
                	  double r=q-selec_iteam.get(i).getQuatity();
                	  in.setQuantity_in_stock(r);
                	  ingredent_test.updateIngredientQuantity(in.getId(),r);
                  }
				}
				stage.close();
			}


		});
		Add_order.setAlignment(Pos.CENTER);
		Add_order.setPrefWidth(300);
		Add_order.setPrefHeight(50);

		/// this is a vbox
		VBox vb3=new VBox(40,hb2,hb5,Total,Add_order);
		vb3.setAlignment(Pos.CENTER);
		vb3.setPadding(new Insets(20, 20, 20, 20)); 
		Scene scene = new Scene(vb3, 900, 700);
		stage.setTitle("Text Field with Arrows");
		stage.setScene(scene);
		return stage;

	}


	private void showErrorDialog(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
	}





}
