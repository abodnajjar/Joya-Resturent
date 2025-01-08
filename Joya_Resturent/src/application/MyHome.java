package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class MyHome {
    private final Query query;

    public MyHome() {
        query = new Query();
    }

    public HBox MyHomeBox() {
        HBox hbox = new HBox(40);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(25));

        StackPane emp = square("# Employees", query.getTotalEmployees());
        StackPane customer = square(" # Customers", query.getTotalCustomers());
        StackPane order = square("# Orders", query.getTotalOrders());
        StackPane menu = square("# MenuItems", query.getTotalMenuItems());
        StackPane ingred = square("# Ingredients", query.getTotalIngredient());
        
        hbox.getChildren().addAll(emp, customer, order, menu, ingred);
        return hbox;
    }
    

    private StackPane square(String total, int num) {
        StackPane stack = new StackPane();
        stack.setAlignment(Pos.CENTER);

        Rectangle background = new Rectangle(100, 100);
        background.setArcWidth(20);
        background.setArcHeight(20);
        background.setFill(Color.LIGHTBLUE);
        background.setStroke(Color.BLACK);

      
        VBox text1 = new VBox(5);
        text1.setAlignment(Pos.CENTER);

        Label title1 = new Label(total);
        title1.setFont(new Font(15));
        title1.setWrapText(true);
        title1.setAlignment(Pos.CENTER);

        Label number = new Label(String.valueOf(num));
        number.setFont(new Font(40));
        number.setTextFill(Color.BLACK);

        text1.getChildren().addAll(title1, number);
        stack.getChildren().addAll(background, text1);

        return stack;
    }
}