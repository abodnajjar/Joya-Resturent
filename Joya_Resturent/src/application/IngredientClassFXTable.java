package application;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class IngredientClassFXTable {

    private Query query;

    public IngredientClassFXTable() {
    	query = new Query(); // Initialize the ingredientTest class
    }

    public VBox getIngredientTableView() {
        TableView<Ingredient> table = new TableView<>();
        
        ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();
        List<Ingredient> inger = query.loadIngredientsBelow100FromDatabase();
        System.out.println(inger.size());
        
        ingredients.addAll(inger);
        // Define columns
        TableColumn<Ingredient, Integer> idColumn = new TableColumn<>("ID");
//        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setCellValueFactory(cellData -> 
        new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getId()));

        TableColumn<Ingredient, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> 
        new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getName()));

        TableColumn<Ingredient, String> supplierColumn = new TableColumn<>("Supplier");
        supplierColumn.setCellValueFactory(cellData -> 
        new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getSupplier()));;

        TableColumn<Ingredient, Double> quantityColumn = new TableColumn<>("Quantity in Stock");
        quantityColumn.setCellValueFactory(cellData -> 
        new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getQuantity_in_stock()));;

        // Add columns to the table
        table.getColumns().addAll(idColumn, nameColumn, supplierColumn, quantityColumn);
       
        table.setItems(ingredients);

        // Create and return a layout
        VBox vbox = new VBox(table);
        return vbox;
    }
}