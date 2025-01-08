package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CashGraph {
    private final Query query;

    public CashGraph() {
        query = new Query();
    }

    public VBox CashGraphbox() {
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        // ComboBox for selecting data type
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Sales", "Cash");
        comboBox.setValue("Sales");

        // Create a line chart
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Date");
        yAxis.setLabel("Total Value");

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Data Trends");
        lineChart.setPrefWidth(600);
        lineChart.setPrefHeight(250);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Data by Date");

        updateChart(series, "Sales");

        comboBox.setOnAction(event -> {
            String selectedOption = comboBox.getValue();
            updateChart(series, selectedOption);
        });

        lineChart.getData().add(series);
        vbox.getChildren().addAll(comboBox, lineChart);       
        return vbox;
    }

    private void updateChart(XYChart.Series<Number, Number> series, String dataType) {
        series.getData().clear();
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = 6; i >= 0; i--) {
            LocalDate date = currentDate.minusDays(i);
            String formattedDate = date.format(formatter);
            double value = 0.0;

            if (dataType.equals("Sales")) {
                value = query.getTotalSalesForDate(formattedDate);
            } else if (dataType.equals("Cash")) {
                value = query.getTotalMoneyForDate(formattedDate);
            }

            series.getData().add(new XYChart.Data<>(i, value));
        }
    }

    public void start(Stage stage) {
        Scene scene = new Scene(CashGraphbox(), 800, 600);
        stage.setScene(scene);
        stage.setTitle("Dashboard with Data Line Chart");
        stage.show();
    }


}