package application;
import java.util.List;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.geometry.Side;
import javafx.scene.control.Alert.AlertType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.beans.property.*;
import javafx.collections.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Side;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.collections.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Side;
import javafx.scene.input.KeyEvent;
import javafx.geometry.Side;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.transformation.FilteredList;
public class JoyeRestaurantApp extends Application {
	private Employee_Test et=new Employee_Test();
	private Employee employe;
	@Override
	public void start(Stage primaryStage) {
		// Main layout
		VBox mainLayout = new VBox(30);
		mainLayout.setPadding(new Insets(20));
		mainLayout.setAlignment(Pos.CENTER);
		try {
			Image backgroundImage = new Image(getClass().getResource("C:/Users/abdal/OneDrive/Desktop/تصميم بدون عنوان (1).jpg").toExternalForm()); // Adjust the path as needed
			BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
			BackgroundImage bgImage = new BackgroundImage(
					backgroundImage,
					BackgroundRepeat.NO_REPEAT,
					BackgroundRepeat.NO_REPEAT,
					BackgroundPosition.CENTER,
					backgroundSize
					);
			mainLayout.setBackground(new Background(bgImage));
		} catch (Exception e) {
			System.out.println("Background image not found. Please check the file path.");
		}
		// Main label
		Label titleLabel = new Label("Joya Restaurant");
		titleLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");

		// Labels and TextFields
		Label nameLabel = new Label("Id:");
		nameLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
		nameLabel.setPrefWidth(100);
		nameLabel.setPrefHeight(20);
		TextField nameField = new TextField();
		nameField .setPrefHeight(20);
		nameField.setPrefWidth(200); 

		Label emailLabel = new Label("password:");
		emailLabel .setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
		emailLabel.setPrefWidth(100);
		emailLabel .setPrefHeight(20);
		TextField emailField = new TextField();
		emailField.setPrefHeight(20);
		emailField.setPrefWidth(200); 
		// Button Clear
		Button clearButton = new Button("Clear");
		clearButton.setPrefWidth(150);
		clearButton.setPrefHeight(20);
		clearButton.setOnAction(e -> {
			nameField.clear();
			emailField.clear();
		});

		ArrayList<Employee> Employeee_list= (ArrayList<Employee>) et.getAllEmployees();
		// Button Sign
		Button signButton = new Button("Sign");
		signButton.setPrefWidth(150);
		signButton.setPrefHeight(20);
		signButton.setOnAction(e -> {
			if (nameField.getText().isEmpty() || emailField.getText().isEmpty()) {
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText(null);
				alert.setContentText("Please fill out all fields!");
				alert.showAndWait();
			} else {
				try {
					Employee emp =null;
					// Try to convert the input text to an integer
					int convertedValue = Integer.parseInt(nameField.getText());
					for(int i=0;i<Employeee_list.size();i++) {
						if(Employeee_list.get(i).getId()==convertedValue) {
							emp=Employeee_list.get(i);
						}
					}
					if(emp==null) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("No Employy");
						alert.setHeaderText(null); // No header text
						alert.setContentText("We Don't have employee with this  id ");
						alert.showAndWait(); // 
					}
					else {
						if(emp.getPassword().equalsIgnoreCase(emailField.getText())) {
							if(emp.getPostion().equalsIgnoreCase("manger")) {
								this.employe=emp;
								primaryStage.close();
								Stage s=mainstage(primaryStage);
								s.show();
								nameField.setText("");
								emailField.setText("");
							}
							else if(emp.getPostion().equalsIgnoreCase("casher")) {
								this.employe=emp;
								primaryStage.close();
								Stage r=chashertage(primaryStage);
								r.show();
								nameField.setText("");
								emailField.setText("");
							}
							else {
								this.employe=emp;
								primaryStage.close();
								Stage r=cheftage(primaryStage);
								r.show();
								nameField.setText("");
								emailField.setText("");
							}

						}
						else {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("incorrect Password");
							alert.setHeaderText(null); // No header text
							alert.setContentText("The input password is incorrect ");
							alert.showAndWait(); // 
						}
					}

				} catch (NumberFormatException ex) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Enter id (INteger)");
					alert.setHeaderText(null); // No header text
					alert.setContentText("The input is not a valid  integer ");
					alert.showAndWait(); // 
				}




			}
		});

		// Layout for input fields
		GridPane inputLayout = new GridPane();
		inputLayout.setHgap(10);
		inputLayout.setVgap(10);
		inputLayout.setAlignment(Pos.CENTER);
		inputLayout.addRow(0, nameLabel, nameField);
		inputLayout.addRow(1, emailLabel, emailField);

		// Layout for buttons
		HBox buttonLayout = new HBox(50, clearButton, signButton);
		buttonLayout.setAlignment(Pos.CENTER);

		// Add everything to the main layout
		mainLayout.getChildren().addAll(titleLabel, inputLayout, buttonLayout);

		// Scene and stage setup
		Scene scene = new Scene(mainLayout, 700, 500);
		primaryStage.setTitle("Joye Restaurant");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public Stage mainstage(Stage x2) {
		Stage primaryStage=new Stage();
		Profile pro=new Profile(employe);
		VBox vb2=pro.getProfileView(primaryStage,x2);
	
		
		TabPane tabPane = new TabPane();
		//tabPane.setPrefWidth(300);
		//tabPane.setPrefHeight(20);
		tabPane.setSide(Side.LEFT);  
		Tab tab15 = new Tab();
		tab15.setClosable(false);  // Disable close button
		Text text15 = new Text("Name: " + employe.getName() + "\nID: " + employe.getId());
		//tab15.setDisable(true); 
		tab15.setContent(pro.getProfileView2());
		tab15.setStyle("-fx-background-color: lightblue;");
		tab15.setGraphic(vb2);
		tab15.setStyle("-fx-pref-width: 205px; -fx-pref-height: 200px;");
		
		Home_Tab ht=new Home_Tab();
		Tab tab0 = new Tab();
		tab0.setClosable(false);  // Disable close button
		Text text0 = new Text("Home");
		tab0.setContent(ht.createHomeLayout());
		tab0.setGraphic(text0);
		tab0.setStyle("-fx-pref-width: 80px; -fx-pref-height: 200px;");
		Payment_Tab t1=new Payment_Tab();
		VBox vb1=t1.retu();
		Tab tab1 = new Tab();
		tab1.setContent(vb1);
		tab1.setClosable(false);  // Disable close button
		Text text1 = new Text("Payment");
		tab1.setGraphic(text1);
		tab1.setStyle("-fx-pref-width: 80px; -fx-pref-height: 200px;");

		//Order_Tab ot=new Order_Tab(2);
		Tab tab2 = new Tab();
		tab2.setClosable(false);  // Disable close button
		Text text2 = new Text("Order");
		tab2.setGraphic(text2);
		tab2.setStyle("-fx-pref-width: 80px; -fx-pref-height: 200px;");
		tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
			if (newTab == tab2) {
				// Lazy-load the Order_Tab when selected
				Order_Tab ot = new Order_Tab(employe.getId()); // Initialize Order_Tab
				tab2.setContent(ot.main()); // Replace with actual content
			}
		});


		Tab tab3 = new Tab();
		tab3.setClosable(false);  // Disable close button
		Text text3 = new Text("Customer");
		tab3.setGraphic(text3);
		tab3.setStyle("-fx-pref-width: 80px; -fx-pref-height: 200px;");
		tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
			if (newTab == tab3) {
				// Lazy-load the Order_Tab when selected
				Customer_Tab cu=new Customer_Tab();
				tab3.setContent(cu.get_main_Stage()); // Replace with actual content
			}
		});

		Employee_Tabe et=new Employee_Tabe();
		Tab tab4 = new Tab();
		tab4.setContent(et.get_main_stage());
		tab4.setClosable(false);  // Disable close button
		Text text4 = new Text("Employee");
		tab4.setGraphic(text4);
		tab4.setStyle("-fx-pref-width: 80px; -fx-pref-height: 200px;");


		Tab tab5 = new Tab();

		tab5.setClosable(false);  // Disable close button
		Text text5 = new Text("Ingredient");
		tab5.setGraphic(text5);
		tab5.setStyle("-fx-pref-width: 80px; -fx-pref-height: 200px;");
		tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
			if (newTab == tab5) {
				// Lazy-load the Order_Tab when selected
				Ingredient_Tab it =new Ingredient_Tab();
				tab5.setContent(it.main_Stage());
			}
		});

		Menu_item_Tabe mt=new Menu_item_Tabe();
		Tab tab6 = new Tab();
		tab6.setContent(mt.main_Stage());
		tab6.setClosable(false);  // Disable close button
		Text text6 = new Text("Menu Iteam ");
		tab6.setGraphic(text6);
		tab6.setStyle("-fx-pref-width: 75px; -fx-pref-height: 200px;");

		// Add tabs to the TabPane
		tabPane.getTabs().addAll(tab15,tab0,tab1, tab2,tab3,tab4,tab5,tab6 );
		tabPane.getSelectionModel().select(tab0); 
         VBox vb3 =new VBox(0,vb2,tabPane);
		// Create a scene and set the TabPane in the stage
		StackPane root = new StackPane();
		root.getChildren().add(tabPane);

		
		Scene scene = new Scene(root, 1300, 780);
		primaryStage.setTitle("Joye Restaurant");
		primaryStage.setScene(scene);
		primaryStage.show();

		return primaryStage;
	}
	public Stage chashertage(Stage x2) {
		Stage primaryStage=new Stage();
		Profile pro=new Profile(employe);
		VBox vb2=pro.getProfileView(primaryStage,x2);
	
		
		TabPane tabPane = new TabPane();
		//tabPane.setPrefWidth(300);
		//tabPane.setPrefHeight(20);
		tabPane.setSide(Side.LEFT);  
		Tab tab15 = new Tab();
		tab15.setClosable(false);  // Disable close button
		Text text15 = new Text("Name: " + employe.getName() + "\nID: " + employe.getId());
		//tab15.setDisable(true); 
		tab15.setContent(pro.getProfileView2());
		tab15.setStyle("-fx-background-color: lightblue;");
		tab15.setGraphic(vb2);
		tab15.setStyle("-fx-pref-width: 205px; -fx-pref-height: 200px;");
		

		Tab tab0 = new Tab();
		tab0.setClosable(false);  // Disable close button
		Text text0 = new Text("Home");
		tab0.setDisable(true);
		tab0.setGraphic(text0);
		tab0.setStyle("-fx-pref-width: 80px; -fx-pref-height: 200px;");
		Payment_Tab t1=new Payment_Tab();
		VBox vb1=t1.retu();
		Tab tab1 = new Tab();
		tab1.setContent(vb1);
		tab1.setDisable(true);
		tab1.setClosable(false);  // Disable close button
		Text text1 = new Text("Payment");
		tab1.setGraphic(text1);
		tab1.setStyle("-fx-pref-width: 80px; -fx-pref-height: 200px;");

		//Order_Tab ot=new Order_Tab(2);
		Tab tab2 = new Tab();
		tab2.setClosable(false);  // Disable close button
		//tab2.setDisable(true);
		Text text2 = new Text("Order");
		tab2.setGraphic(text2);
		tab2.setStyle("-fx-pref-width: 80px; -fx-pref-height: 200px;");
		tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
			if (newTab == tab2) {
				// Lazy-load the Order_Tab when selected
				Order_Tab ot = new Order_Tab(employe.getId()); // Initialize Order_Tab
				tab2.setContent(ot.main()); // Replace with actual content
			}
		});


		Tab tab3 = new Tab();
		tab3.setDisable(true);
		tab3.setClosable(false);  // Disable close button
		Text text3 = new Text("Customer");
		tab3.setGraphic(text3);
		tab3.setStyle("-fx-pref-width: 80px; -fx-pref-height: 200px;");
		tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
			if (newTab == tab3) {
				// Lazy-load the Order_Tab when selected
				Customer_Tab cu=new Customer_Tab();
				tab3.setContent(cu.get_main_Stage()); // Replace with actual content
			}
		});

		Employee_Tabe et=new Employee_Tabe();
		Tab tab4 = new Tab();
		tab4.setDisable(true);
		tab4.setContent(et.get_main_stage());
		tab4.setClosable(false);  // Disable close button
		Text text4 = new Text("Employee");
		tab4.setGraphic(text4);
		tab4.setStyle("-fx-pref-width: 80px; -fx-pref-height: 200px;");


		Tab tab5 = new Tab();
		tab5.setDisable(true);
		tab5.setClosable(false);  // Disable close button
		Text text5 = new Text("Ingredient");
		tab5.setGraphic(text5);
		tab5.setStyle("-fx-pref-width: 80px; -fx-pref-height: 200px;");
		tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
			if (newTab == tab5) {
				// Lazy-load the Order_Tab when selected
				Ingredient_Tab it =new Ingredient_Tab();
				tab5.setContent(it.main_Stage());
			}
		});

		Menu_item_Tabe mt=new Menu_item_Tabe();
		Tab tab6 = new Tab();
		tab6.setDisable(true);
		tab6.setContent(mt.main_Stage());
		tab6.setClosable(false);  // Disable close button
		Text text6 = new Text("Menu Iteam ");
		tab6.setGraphic(text6);
		tab6.setStyle("-fx-pref-width: 75px; -fx-pref-height: 200px;");

		// Add tabs to the TabPane
		tabPane.getTabs().addAll(tab15, tab2,tab0,tab1,tab3,tab4,tab5,tab6 );
		tabPane.getSelectionModel().select(tab2); 
         VBox vb3 =new VBox(0,vb2,tabPane);
		// Create a scene and set the TabPane in the stage
		StackPane root = new StackPane();
		root.getChildren().add(tabPane);

	
		Scene scene = new Scene(root, 1300, 780);
		primaryStage.setTitle("Joye Restaurant");
		primaryStage.setScene(scene);
		primaryStage.show();

		return primaryStage;
	}
	
	public Stage cheftage(Stage x2) {
		Stage primaryStage=new Stage();
		Profile pro=new Profile(employe);
		VBox vb2=pro.getProfileView(primaryStage,x2);
	
		
		TabPane tabPane = new TabPane();
		//tabPane.setPrefWidth(300);
		//tabPane.setPrefHeight(20);
		tabPane.setSide(Side.LEFT);  
		Tab tab15 = new Tab();
		tab15.setClosable(false);  // Disable close button
		Text text15 = new Text("Name: " + employe.getName() + "\nID: " + employe.getId());
		//tab15.setDisable(true); 
		tab15.setContent(pro.getProfileView2());
		tab15.setStyle("-fx-background-color: lightblue;");
		tab15.setGraphic(vb2);
		tab15.setStyle("-fx-pref-width: 205px; -fx-pref-height: 200px;");
		

		Tab tab0 = new Tab();
		tab0.setClosable(false);  // Disable close button
		Text text0 = new Text("Home");
		tab0.setDisable(true);
		tab0.setGraphic(text0);
		tab0.setStyle("-fx-pref-width: 80px; -fx-pref-height: 200px;");
		Payment_Tab t1=new Payment_Tab();
		VBox vb1=t1.retu();
		Tab tab1 = new Tab();
		tab1.setContent(vb1);
		tab1.setDisable(true);
		tab1.setClosable(false);  // Disable close button
		Text text1 = new Text("Payment");
		tab1.setGraphic(text1);
		tab1.setStyle("-fx-pref-width: 80px; -fx-pref-height: 200px;");

		//Order_Tab ot=new Order_Tab(2);
		Tab tab2 = new Tab();
		tab2.setClosable(false);  // Disable close button
		tab2.setDisable(true);
		Text text2 = new Text("Order");
		tab2.setGraphic(text2);
		tab2.setStyle("-fx-pref-width: 80px; -fx-pref-height: 200px;");
		tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
			if (newTab == tab2) {
				// Lazy-load the Order_Tab when selected
				Order_Tab ot = new Order_Tab(employe.getId()); // Initialize Order_Tab
				tab2.setContent(ot.main()); // Replace with actual content
			}
		});


		Tab tab3 = new Tab();
		tab3.setDisable(true);
		tab3.setClosable(false);  // Disable close button
		Text text3 = new Text("Customer");
		tab3.setGraphic(text3);
		tab3.setStyle("-fx-pref-width: 80px; -fx-pref-height: 200px;");
		tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
			if (newTab == tab3) {
				// Lazy-load the Order_Tab when selected
				Customer_Tab cu=new Customer_Tab();
				tab3.setContent(cu.get_main_Stage()); // Replace with actual content
			}
		});

		Employee_Tabe et=new Employee_Tabe();
		Tab tab4 = new Tab();
		tab4.setDisable(true);
		tab4.setContent(et.get_main_stage());
		tab4.setClosable(false);  // Disable close button
		Text text4 = new Text("Employee");
		tab4.setGraphic(text4);
		tab4.setStyle("-fx-pref-width: 80px; -fx-pref-height: 200px;");


		Tab tab5 = new Tab();
		tab5.setDisable(true);
		tab5.setClosable(false);  // Disable close button
		Text text5 = new Text("Ingredient");
		tab5.setGraphic(text5);
		tab5.setStyle("-fx-pref-width: 80px; -fx-pref-height: 200px;");
		tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
			if (newTab == tab5) {
				// Lazy-load the Order_Tab when selected
				Ingredient_Tab it =new Ingredient_Tab();
				tab5.setContent(it.main_Stage());
			}
		});

		Menu_item_Tabe mt=new Menu_item_Tabe();
		Tab tab6 = new Tab();
		tab6.setDisable(true);
		tab6.setContent(mt.main_Stage());
		tab6.setClosable(false);  // Disable close button
		Text text6 = new Text("Menu Iteam ");
		tab6.setGraphic(text6);
		tab6.setStyle("-fx-pref-width: 75px; -fx-pref-height: 200px;");

		// Add tabs to the TabPane
		tabPane.getTabs().addAll(tab15, tab0,tab1,tab2,tab3,tab4,tab5,tab6 );
		tabPane.getSelectionModel().select(tab15); 
         VBox vb3 =new VBox(0,vb2,tabPane);
		// Create a scene and set the TabPane in the stage
		StackPane root = new StackPane();
		root.getChildren().add(tabPane);

		
		Scene scene = new Scene(root, 1300, 780);
		primaryStage.setTitle("Joye Restaurant");
		primaryStage.setScene(scene);
		primaryStage.show();

		return primaryStage;
	}
	public static void main(String[] args) {
		launch(args);
	}
}