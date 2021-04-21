import javax.sound.sampled.Line;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.stage.Stage;
// import jdk.internal.parser.JSONParser;

import java.io.FileReader;
import org.json.*;
import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.Reader;

public class LineChartVisual extends Application {
    final static String stageTitle = "Covid-19 Line Chart";
    final static String chartTitle = "12-Weeks Covid-19 Infection Cases";
    // final static String usa = "USA";
    // final static String italy = "Italy";
    // final static String france = "France";
    private JSONObject obj;
    private JSONArray countries;
    private JSONObject country1, country2, country3;

    public LineChartVisual(JSONObject jsonObj) {
        obj = jsonObj;
        countries = (JSONArray) obj.get("Countries");
        country1 = (JSONObject) countries.get(0); // USA
        country2 = (JSONObject) countries.get(1); // Italy
        country3 = (JSONObject) countries.get(2); // France
    }

    public void SerieBuilder(XYChart.Series<Number, Number> serie, JSONObject country) {
        for (int i = 0; i < 12; i++) {
            serie.getData().add(new XYChart.Data<Number, Number>(i + 1, (int) country.get(i)));
        }
    }

    @Override
    public void start(Stage stage) {
        // Set the Visualization Stage:
        stage.setTitle(stageTitle);

        final NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Week");
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Cases");

        // Create a Line Chart Instance:
        final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        lineChart.setTitle(chartTitle);

        // Categorize countries as series:
        Series<Number, Number> series1 = new XYChart.Series<Number, Number>();
        series1.setName(country1.toString());

        // Get case data (week and number) to add into the serie (USA):
        SerieBuilder(series1, country1);

        XYChart.Series<Number, Number> series2 = new XYChart.Series<Number, Number>();
        series2.setName(country2.toString());

        // Get case data (week and number) to add into the serie (Italy):
        SerieBuilder(series2, country2);

        XYChart.Series<Number, Number> series3 = new XYChart.Series<Number, Number>();
        series3.setName(country3.toString());

        // Get case data (week and number) to add into the serie (France):
        SerieBuilder(series3, country3);

        Scene scene = new Scene(lineChart, 800, 600);
        lineChart.getData().addAll(series1, series2, series3);

        stage.setScene(scene);
        stage.show();
    }
}
