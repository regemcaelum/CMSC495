package umgc.cmsc495.earlybirds;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.CategoryAxis; // For Bar Chart
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.stage.Stage;
import java.util.HashMap;

import java.io.FileReader;
import org.json.simple.*;
import org.json.simple.parser.*;

public class App extends Application {
    final static String stageTitle = "Covid-19 Line Chart";
    final static String chartTitle = "12-Weeks Covid-19 Infection Cases";

    // private JSONObject _obj;
    // private Object country1, country2, country3;

    // public LineChartVisual() {
    // }

    // public LineChartVisual(JSONObject obj) {
    // _obj = obj;
    // country1 = obj.get("country1");
    // country2 = obj.get("country2");
    // country3 = obj.get("country3");
    // }

    public void SerieBuilder(XYChart.Series<Number, Number> serie, int[] weekly_cases) {
        for (int i = 0; i < 12; i++) {
            serie.getData().add(new XYChart.Data<Number, Number>(i + 1, weekly_cases[i]));
        }
    }

    public void BarChartSerieBuilder(XYChart.Series<String, Number> serie, int[] weekly_cases) {
        for (int i = 0; i < 12; i++) {
            serie.getData().add(new XYChart.Data<String, Number>(String.valueOf(i + 1), weekly_cases[i]));
        }
    }

    public static int[] toIntArray(String[] input) {
        int[] ints = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            ints[i] = Integer.parseInt(input[i]);
        }
        return ints;
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Set the Visualization Stage:
        stage.setTitle(stageTitle);

        final NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Week");
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Cases");

        final CategoryAxis xAxis_bar = new CategoryAxis();
        xAxis_bar.setLabel("Week");

        // Create a Chart Instances:
        final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        final AreaChart<Number, Number> areaChart = new AreaChart<Number, Number>(xAxis, yAxis);
        final BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis_bar, yAxis);

        lineChart.setTitle(chartTitle);
        areaChart.setTitle(chartTitle);
        barChart.setTitle(chartTitle);

        // Parse JSON object:
        JSONParser parser = new JSONParser();
        JSONObject countriesObj = new JSONObject();

        // Opening the file
        Object obj = parser.parse(new FileReader("WeeklyCases.json"));
        countriesObj = (JSONObject) ((HashMap) obj).get("countries");

        var country1_name = countriesObj.get("country1").toString();
        var country2_name = countriesObj.get("country2").toString();
        var country3_name = countriesObj.get("country3").toString();

        var country1 = (JSONObject) ((HashMap) obj).get("country1");
        var country2 = (JSONObject) ((HashMap) obj).get("country2");
        var country3 = (JSONObject) ((HashMap) obj).get("country3");

        var weeklyCases1Obj = ((HashMap) country1).get("weeklyCases");
        var weeklyCases2Obj = ((HashMap) country2).get("weeklyCases");
        var weeklyCases3Obj = ((HashMap) country3).get("weeklyCases");

        String weeklyCasesString = weeklyCases1Obj.toString().replace("[", "").replace("]", "");
        String weeklyCasesString2 = weeklyCases2Obj.toString().replace("[", "").replace("]", "");
        String weeklyCasesString3 = weeklyCases3Obj.toString().replace("[", "").replace("]", "");

        int[] weeklyCases1 = toIntArray(weeklyCasesString.split(","));
        int[] weeklyCases2 = toIntArray(weeklyCasesString2.split(","));
        int[] weeklyCases3 = toIntArray(weeklyCasesString3.split(","));

        // Categorize countries as series:
        Series<Number, Number> series1 = new XYChart.Series<Number, Number>();
        series1.setName(country1_name);

        // Get case data (week and number) to add into the serie (USA):
        SerieBuilder(series1, weeklyCases1);

        XYChart.Series<Number, Number> series2 = new XYChart.Series<Number, Number>();
        series2.setName(country2_name);
        SerieBuilder(series2, weeklyCases2);

        XYChart.Series<Number, Number> series3 = new XYChart.Series<Number, Number>();
        series3.setName(country3_name);
        SerieBuilder(series3, weeklyCases3);

        // For Bar Chart Specific:
        Series<String, Number> series1_bar = new XYChart.Series<String, Number>();
        series1_bar.setName(country1_name);
        BarChartSerieBuilder(series1_bar, weeklyCases1);

        Series<String, Number> series2_bar = new XYChart.Series<String, Number>();
        series2_bar.setName(country2_name);
        BarChartSerieBuilder(series2_bar, weeklyCases2);

        Series<String, Number> series3_bar = new XYChart.Series<String, Number>();
        series3_bar.setName(country3_name);
        BarChartSerieBuilder(series3_bar, weeklyCases3);

        Scene scene_line = new Scene(lineChart, 800, 600);
        Scene scene_area = new Scene(areaChart, 800, 600);
        Scene scene_bar = new Scene(barChart, 800, 600);

        lineChart.getData().addAll(series1, series2, series3);
        areaChart.getData().addAll(series1, series2, series3);
        barChart.getData().addAll(series1_bar, series2_bar, series3_bar);

        // stage.setScene(scene_line);
        // stage.setScene(scene_area);
        stage.setScene(scene_bar);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
