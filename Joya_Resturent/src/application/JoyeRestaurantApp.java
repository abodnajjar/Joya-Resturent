package application;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.geometry.Side;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;
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
		Label titleLabel = new Label("Joye Restaurant");
		titleLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");

		// Labels and TextFields
		Label nameLabel = new Label("Name:");
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
				primaryStage.close();
				Stage s=mainstage();
				s.show();
				// Placeholder action for the sign button
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
	public Stage mainstage() {
		TabPane tabPane = new TabPane();
		tabPane.setPrefWidth(300);
		tabPane.setPrefHeight(40);
		tabPane.setSide(Side.LEFT);  // Set tabs on the left (vertical)
		Tab tab0 = new Tab();
		tab0.setClosable(false);  // Disable close button
		Text text0 = new Text("Home");
		tab0.setGraphic(text0);
		tab0.setStyle("-fx-pref-width: 70px; -fx-pref-height: 200px;");
		Payment_Tab t1=new Payment_Tab();
		VBox vb1=t1.retu();
		Tab tab1 = new Tab();
		tab1.setContent(vb1);
		tab1.setClosable(false);  // Disable close button
		Text text1 = new Text("Payment");
		tab1.setGraphic(text1);
		tab1.setStyle("-fx-pref-width: 70px; -fx-pref-height: 200px;");

		Order_Tab ot=new Order_Tab(2);
		Tab tab2 = new Tab();
		tab2.setClosable(false);  // Disable close button
		Text text2 = new Text("Order");
		tab2.setContent(ot.main());
		tab2.setGraphic(text2);
		tab2.setStyle("-fx-pref-width: 70px; -fx-pref-height: 200px;");
		
		Customer_Tab cu=new Customer_Tab();
		Tab tab3 = new Tab();
		tab3.setContent(cu.get_main_Stage());
		tab3.setClosable(false);  // Disable close button
		Text text3 = new Text("Customer");
		tab3.setGraphic(text3);
		tab3.setStyle("-fx-pref-width: 70px; -fx-pref-height: 200px;");
		
		Employee_Tabe et=new Employee_Tabe();
		Tab tab4 = new Tab();
		tab4.setContent(et.get_main_stage());
		tab4.setClosable(false);  // Disable close button
		Text text4 = new Text("Employee");
		tab4.setGraphic(text4);
		tab4.setStyle("-fx-pref-width: 70px; -fx-pref-height: 200px;");
		
		Ingredient_Tab it =new Ingredient_Tab();
		Tab tab5 = new Tab();
		tab5.setContent(it.main_Stage());
		tab5.setClosable(false);  // Disable close button
		Text text5 = new Text("Ingredient");
		tab5.setGraphic(text5);
		tab5.setStyle("-fx-pref-width: 70px; -fx-pref-height: 200px;");
		
		Menu_item_Tabe mt=new Menu_item_Tabe();
		Tab tab6 = new Tab();
		tab6.setContent(mt.main_Stage());
		tab6.setClosable(false);  // Disable close button
		Text text6 = new Text("Menu Iteam ");
		tab6.setGraphic(text6);
		tab6.setStyle("-fx-pref-width: 70px; -fx-pref-height: 200px;");
		
		// Add tabs to the TabPane
		tabPane.getTabs().addAll(tab0,tab1, tab2,tab3,tab4,tab5,tab6 );

		// Create a scene and set the TabPane in the stage
		StackPane root = new StackPane();
		root.getChildren().add(tabPane);

		Stage primaryStage=new Stage();
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