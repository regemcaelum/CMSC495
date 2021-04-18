import javax.sound.sampled.Line;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
// import jdk.internal.parser.JSONParser;

import java.io.FileReader;
// import org.json.*;
import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.Reader;

public class LineChartVisual extends Application {
    final static String stageTitle = "Covid-19 Line Chart";
    final static String chartTitle = "12-Weeks Covid-19 Infection Cases";
    final static String usa = "USA";
    final static String italy = "Italy";
    final static String france = "France";
    private JSONObject obj;

    public LineChartVisual(JSONObject jsonObj) {
        obj = jsonObj;
    }

    @Override
    public void start(Stage stage) {
        // Set the Visualization Stage:
        stage.setTitle(stageTitle);

        final CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Week");
        final NumberAxis yAxis = new NumberAxis();

        // Create a Line Chart Instance:
        final LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);
        lineChart.setTitle(chartTitle);

        // Categorize countries as series:
        XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
        series1.setName(usa);

        // Get case data (week and number) to add into the serie (USA):

        XYChart.Series<String, Number> series2 = new XYChart.Series<String, Number>();
        series2.setName(italy);

        // Get case data (week and number) to add into the serie (Italy):

        XYChart.Series<String, Number> series3 = new XYChart.Series<String, Number>();
        series3.setName(france);

        // Get case data (week and number) to add into the serie (France):
    }
}
