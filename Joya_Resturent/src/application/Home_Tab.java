package application;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.text.Font;
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
      
        HBox box1 = new HBox(30);
        box1.setAlignment(Pos.CENTER);
        box1.getChildren().addAll(customer.createTableView(), tableIng.getIngredientTableView(),sell.mainStage());
        borderPane.setCenter(box1);
        
        
        
        CashGraph g=new CashGraph();
       // VBox x=g.CashGraphbox();
        
        borderPane.setBottom(((CashGraph) g).CashGraphbox());
        return borderPane;
    }

  
}


