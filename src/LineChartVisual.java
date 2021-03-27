import javax.sound.sampled.Line;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import jdk.nashorn.internal.parser.JSONParser;

import java.io.FileReader;

import org.json.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class LineChartVisual extends Application {
    @Override
    public void start(Stage stage) {
        // Set the Visualization Stage:
        stage.setTitle("Covid-19 Line Chart");

        final CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Week");
        final NumberAxis yAxis = new NumberAxis();

        // Create a Line Chart Instance:
        final LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);
        lineChart.setTitle("12-Weeks Covid-19 Infection Cases");

        // Parse JSON onject:
        Object obj = new JSONParser().parse(new FileReader("WeeklyCases.json"));
        JsonObject jsonObj = (JsonObject) obj;

        // Categorize countries as series:
        XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
        series1.setName("USA");

        // Get case data (week and number) to add into the serie (USA):

        XYChart.Series<String, Number> series2 = new XYChart.Series<String, Number>();
        series2.setName("Italy");

        // Get case data (week and number) to add into the serie (Italy):

        XYChart.Series<String, Number> series3 = new XYChart.Series<String, Number>();
        series3.setName("France");

        // Get case data (week and number) to add into the serie (France):
    }
}
