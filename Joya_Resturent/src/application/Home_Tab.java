package application;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.geometry.Pos;

public class Home_Tab {

    public BorderPane createHomeLayout() {
        BorderPane borderPane = new BorderPane();
        MyHome hm = new MyHome();
        
        borderPane.setTop(hm.MyHomeBox());
        
        IngredientClassFXTable tableIng = new IngredientClassFXTable();
        TableViewCustomerPoints customer = new TableViewCustomerPoints();
        TopsellingTable sell = new TopsellingTable();
        HBox box1 = new HBox(10);
        box1.getChildren().addAll(customer.createTableView(), tableIng.getIngredientTableView(), sell.getView());
        borderPane.setCenter(box1);

        return borderPane;
    }

  
}

