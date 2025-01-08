package application;

import javafx.beans.property.SimpleIntegerProperty;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class TopsellingTable {

	private TableView<Object[]> tableView;
	private ObservableList<Object[]> data;
	Order_Test order_test=new Order_Test() ;
	order_items_Test order_iteam_test=new order_items_Test();
	Menu_iteam_Test iteam_ttest=new Menu_iteam_Test();

	public ArrayList<Menu_item> getBest(){
		ArrayList<Menu_item> men_list=(ArrayList<Menu_item>) iteam_ttest.load_menu_item_FromDatabase();
		ArrayList<Order> o_list=(ArrayList<Order>) order_test.getAllCustomers();
		for(int i=0;i<o_list.size();i++) {
			ArrayList<order_items> x=(ArrayList<order_items>) order_iteam_test.getIngredientsForMenuItem(o_list.get(i).getId());
			for(int j=0;j<x.size();j++) {
				int id=x.get(j).getMenuId();
				for(int k=0;k<men_list.size();k++) {
					if(men_list.get(k).getId()==id) {
						men_list.get(k).Increment_count();
					}
				}


			}
		}
		Collections.sort(men_list, new Comparator<Menu_item>() {
			@Override
			public int compare(Menu_item o1, Menu_item o2) {
				return Double.compare(o2.getCount(), o1.getCount()); // Descending order
			}

		});
		return men_list;
	}
	public VBox mainStage() {
		TableView<Menu_item> tableView = new TableView<>();

		Label lb=new Label("Menu Iteam Best Sellers ");
		lb.setAlignment(Pos.CENTER);
		lb.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
		HBox hb=new HBox(lb);
		hb.setAlignment(Pos.CENTER);
		// Define columns
		TableColumn<Menu_item, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(cellData -> 
		new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getName()));

		TableColumn<Menu_item, Integer> idColumn = new TableColumn<>("ID");
		idColumn.setCellValueFactory(cellData -> 
		new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getId()));

		TableColumn<Menu_item, Integer> countColumn = new TableColumn<>("Count");
		countColumn.setCellValueFactory(cellData -> 
		new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getCount()));

		TableColumn<Menu_item, Double> priceColumn = new TableColumn<>("Price");
		priceColumn.setCellValueFactory(cellData -> 
		new javafx.beans.property.ReadOnlyObjectWrapper<>(cellData.getValue().getPrice()));

		// Add columns to the TableView
		tableView.getColumns().add(idColumn);
		tableView.getColumns().add(nameColumn);
		tableView.getColumns().add(priceColumn);
		tableView.getColumns().add(countColumn);


		// Get data from the ArrayList and convert it to an ObservableList
		ObservableList<Menu_item> items = FXCollections.observableArrayList(getBest());
		tableView.setItems(items);

		// Add TableView to the layout
		VBox vbox = new VBox(hb,tableView);
		return vbox;

	}

}
