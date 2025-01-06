package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

import java.util.List;

public class TopsellingTable {

    private TableView<Object[]> tableView;
    private ObservableList<Object[]> data;
    private Query query;

    public TopsellingTable() {
        query = new Query();
        tableView = new TableView<>();
        data = FXCollections.observableArrayList();

        // Define Columns
        TableColumn<Object[], Integer> idColumn = new TableColumn<>("Menu ID");
        idColumn.setCellValueFactory(cellData -> 
            new SimpleIntegerProperty((Integer) cellData.getValue()[0]).asObject());

        TableColumn<Object[], String> nameColumn = new TableColumn<>("Menu Name");
        nameColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty((String) cellData.getValue()[1]));

        TableColumn<Object[], Integer> salesColumn = new TableColumn<>("Sales Count");
        salesColumn.setCellValueFactory(cellData -> 
            new SimpleIntegerProperty((Integer) cellData.getValue()[2]).asObject());

        tableView.getColumns().addAll(idColumn, nameColumn, salesColumn);

        // Load Data
        loadData();
    }

    private void loadData() {
        List<Object[]> topSellingItems = query.getTopSellingItems();
        System.out.print(topSellingItems.size());
        if (topSellingItems != null && !topSellingItems.isEmpty()) {
            data.clear();
            data.addAll(topSellingItems);
            tableView.setItems(data);
        } else {
            System.err.println("No data found for top-selling items.");
        }
    }

    public BorderPane getView() {
        BorderPane root = new BorderPane();
        root.setCenter(tableView);
        return root;
    }
}
