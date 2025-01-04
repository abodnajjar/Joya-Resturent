package application;

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
		Label label2 = new Label("what you want to update (chose) ??"); 
		ComboBox<String> comboBox = new ComboBox<>();
		comboBox.getItems().addAll("OrderTime", "Customer", "Payment","OrderDate");

		HBox hb1=new HBox(20,label2,comboBox);
		hb1.setAlignment(Pos.CENTER_LEFT);
		// Label
		// Default label text


		HBox hb2=new HBox(20);
		hb2.setAlignment(Pos.CENTER_LEFT);




		TextField textField=new TextField();;

		Button updateButton = new Button("Update");
		// Update Button

		HBox hb3=new HBox(updateButton);
		hb3.setAlignment(Pos.CENTER_LEFT);
		// Layout
		VBox layout = new VBox(30,hb1,hb3);
		layout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-spacing: 10;");
		Customer_Test c=new Customer_Test();
		ArrayList<Customer> cl=(ArrayList<Customer>) c.getAllCustomers();
		ComboBox<String> CustomerComboBox = new ComboBox<>();
		CustomerComboBox.setValue(cl.get(0).getName());
		Payment_Test p=new Payment_Test();
		CustomerComboBox.setPrefWidth(300); // Set preferred width
		CustomerComboBox.setPrefHeight(30); 

		ArrayList<Payment> pa=p.getAllPayments();
		ComboBox<String> paymentComboBox = new ComboBox<>();
		paymentComboBox.setValue(pa.get(0).getMethod());
		paymentComboBox.setPrefWidth(200); // Set preferred width
		paymentComboBox.setPrefHeight(30); 
		comboBox.setOnAction(e -> {
			hb2.getChildren().removeAll();
			layout.getChildren().remove(hb2);
			layout.getChildren().add(1,hb2);
			String selected = comboBox.getValue();
			if ("OrderTime".equals(selected)) {
				Label label = new Label();
				label.setText("New Time  :");
				Button getDate=new Button("Get the Current time");
				TextField  textField1 = new TextField();
				textField1.setPromptText("Enter new value");
				textField.setText(textField1.getText());
				getDate.setPrefWidth(300);
				hb2.getChildren().add(label);
				hb2.getChildren().add(textField1);
				hb2.getChildren().add(getDate);
				getDate.setOnAction(u->{
					LocalTime currentTime = LocalTime.now();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
					String timeString = currentTime.format(formatter);
					textField.setText(timeString);
					textField1.setText(timeString);
				});


			} else if ("OrderDate".equals(selected)) {
				Label label = new Label();
				label.setText("New Date :");
				Button getDate=new Button("Get the Current Date ");
				TextField textField1= new TextField();
				textField.setPromptText("Enter new value");
				textField.setText(textField1.getText());
				getDate.setPrefWidth(300);
				hb2.getChildren().add(label);
				hb2.getChildren().add(textField);
				hb2.getChildren().add(getDate);
				getDate.setOnAction(u->{
					LocalDate currentDate = LocalDate.now();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
					String dateString = currentDate.format(formatter);
					textField.setText(dateString);
					textField1.setText(dateString);
				});
				textField.setMaxWidth(300);
			} else if ("Payment".equals(selected)) {
				Label label = new Label();
				label.setText("Select New  Payment type  :");
				hb2.getChildren().add(label);
				paymentComboBox.getItems().addAll(pa.toString());
				hb2.getChildren().add(paymentComboBox);
			}
			else {
				Label label = new Label();
				label.setText(" Select New Customer  :");
				hb2.getChildren().add(label);
				CustomerComboBox.getItems().addAll(cl.toString());
				hb2.getChildren().add(CustomerComboBox);
			}
		});

		updateButton.setPrefWidth(70);
		updateButton.setPrefHeight(20);
		updateButton.setOnAction(e -> {
			if (textField.getText().trim().isEmpty()||textField.getText().isEmpty()) {
				showErrorDialog("Input Error", "The text field cannot be empty.");
			} else {
				String selected = comboBox.getValue();
				if ("OrderTime".equals(selected)) {
					test.updateorder_time(id,textField.getText());
				} else if ("OrderDate".equals(selected)) {
					test.updateorder_Date(id,textField.getText() );

				} else if ("price".equals(selected)) {
					paymentComboBox.setOnAction(event -> {
						int z=0;
						String selectedPayment = paymentComboBox.getValue();
						for(int i=0;i<pa.size();i++) {
							if(pa.get(i).getMethod().equalsIgnoreCase(selectedPayment)) {
								z=pa.get(i).getId();
							}
						}
						test.updatepayment_id(id, z);
					});
				}
				else {
					CustomerComboBox.setOnAction(event ->{
						int z=0;
						String selectedPayment = CustomerComboBox.getValue();
						for(int i=0;i<cl.size();i++) {
							if(cl.get(i).getName().equalsIgnoreCase(selectedPayment)) {
								z=cl.get(i).getId();
							}
						}
						test.updateCustomer_id(id,z);
					});

				}
				order_list.clear();
				order_list.addAll(test.getAllCustomers());
				primaryStage.close();
			}
		});
		// TextField
		// Scene
		Scene scene = new Scene(layout, 800, 350);

		// Stage
		primaryStage.setTitle("Order Update ");
		primaryStage.setScene(scene);
		primaryStage.show();
		return primaryStage;
	}
	int  customer_id=0;
	
	public Stage AddOrder() {
		LocalTime currentTime = LocalTime.now();
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("HH:mm");
		String timeString = currentTime.format(formatter1);
		
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-DD");
		String dateString = currentDate.format(formatter);
		
		Order order=new Order(dateString,timeString,this.employy_id);
		test.insertOrderT(order);
		
		Stage stage=new Stage();
		ArrayList<Customer> customer_list=(ArrayList<Customer>) customer_test.getAllCustomers();
		Label customer1_label =new Label("Select Customer  : ");
		ObservableList<Customer> observableItems = FXCollections.observableArrayList(customer_list);
		// Step 3: Create a ComboBox
		ComboBox<Customer> comboBox = new ComboBox<>();
		comboBox.setEditable(true); // Make the ComboBox editable to allow searching
		comboBox.setItems(observableItems);
		comboBox.setConverter(new StringConverter<>() {
			@Override
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
      spinner.setEditable(true);
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
     HBox hb5=new HBox(20,vb2,tableView);
     hb5.setAlignment(Pos.CENTER);
      Button Add_order=new Button("Add this order");
      Add_order.setAlignment(Pos.CENTER);
      Add_order.setPrefWidth(300);
      Add_order.setPrefHeight(40);
      
      /// this is a vbox
      VBox vb3=new VBox(40,hb2,hb5,Add_order);
      vb3.setAlignment(Pos.CENTER);
      vb3.setPadding(new Insets(0, 0, 20, 0)); 
      Scene scene = new Scene(vb3, 800, 600);
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
