package application;

import java.util.List;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class TableViewCustomerPoints {

    private Query query;

    public TableViewCustomerPoints() {
        query = new Query();
    }

    public VBox createTableView() {

        TableView<Customer> tableView = new TableView<>();
        ObservableList<Customer> customerData = FXCollections.observableArrayList();

        // Load data from the Query class
        List<Customer> customers = query.getCustomersByPointsDesc();
        customerData.addAll(customers);

        // Table Columns
        TableColumn<Customer, Number> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()));

        TableColumn<Customer, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        TableColumn<Customer, Number> pointsColumn = new TableColumn<>("Points");
        pointsColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPointer()));

        tableView.getColumns().addAll(idColumn, nameColumn, pointsColumn);
        tableView.setItems(customerData);
        tableView.setPrefHeight(400);


        // Add TableView to BorderPane
        VBox vbox = new VBox(tableView);
        return vbox;
    }
}