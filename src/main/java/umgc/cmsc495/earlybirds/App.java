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
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;

import java.util.HashMap;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;

import org.json.simple.*;
import org.json.simple.parser.*;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;

public class App extends Application {
    final static String chartTitle = "12-Weeks Covid-19 Infection Cases";
    final static String lineStageTitle = "Covid-19 Line Chart";
    final static String areaStageTitle = "Covid-19 Area Chart";
    final static String barStageTitle = "Covid-19 Bar Chart";

    final Button lineChartBtn = new Button("Line Chart");
    final Button areaChartBtn = new Button("Area Chart");
    final Button barChartBtn = new Button(" Bar Chart");

    MenuBar menuBar = new MenuBar();
    Menu menu = new Menu("Available Countries");
    CheckMenuItem countryItem1 = new CheckMenuItem();
    CheckMenuItem countryItem2 = new CheckMenuItem();
    CheckMenuItem countryItem3 = new CheckMenuItem();

    Label msgLabel = new Label("Choose your preferred visuallization chart");

    // Initializing variables for file input try-catch block
    String country1_name = "", country2_name = "", country3_name = "";
    Boolean showCountry1 = false, showCountry2 = false, showCountry3 = false;

    JSONObject country1 = new JSONObject();
    JSONObject country2 = new JSONObject();
    JSONObject country3 = new JSONObject();
    Object weeklyCases1Obj = new Object();
    Object weeklyCases2Obj = new Object();
    Object weeklyCases3Obj = new Object();
    String weeklyCasesString = "", weeklyCasesString2 = "", weeklyCasesString3 = "";
    int[] weeklyCases1 = new int[12];
    int[] weeklyCases2 = new int[12];
    int[] weeklyCases3 = new int[12];

    WebView webView = new WebView();

    // Set the Visualization Stage:
    final NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();

    final NumberAxis xAxis_area = new NumberAxis();
    final NumberAxis yAxis_area = new NumberAxis();

    final CategoryAxis xAxis_bar = new CategoryAxis();
    final NumberAxis yAxis_bar = new NumberAxis();

    // Create a Chart Instances:
    final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
    final AreaChart<Number, Number> areaChart = new AreaChart<Number, Number>(xAxis_area, yAxis_area);
    final BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis_bar, yAxis_bar);

    public void SerieBuilder(XYChart.Series<Number, Number> serie, String countryName, int[] weekly_cases) {
        serie.setName(countryName);
        for (int i = 0; i < 12; i++) {
            serie.getData().add(new XYChart.Data<Number, Number>(i + 1, weekly_cases[i]));
        }
    }

    public void BarChartSerieBuilder(XYChart.Series<String, Number> serie, String countryName, int[] weekly_cases) {
        serie.setName(countryName);
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

    public void clearCharts() {
        lineChart.getData().clear();
        areaChart.getData().clear();
        barChart.getData().clear();
    }

    @Override
    public void start(Stage stage) throws Exception {
        // load our HTML to JavaFX web view:
        URL webPage = getClass().getResource("Web/index.html");
        webView.getEngine().load(webPage.toExternalForm());
        VBox vBox = new VBox(webView);

        // // Set the Visualization Stage:
        // final NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Week");
        // final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Cases");

        // final NumberAxis xAxis_area = new NumberAxis();
        xAxis_area.setLabel("Week");
        // final NumberAxis yAxis_area = new NumberAxis();
        yAxis_area.setLabel("Cases");

        // final CategoryAxis xAxis_bar = new CategoryAxis();
        xAxis_bar.setLabel("Week");
        // final NumberAxis yAxis_bar = new NumberAxis();
        yAxis_bar.setLabel("Cases");

        lineChart.setTitle(chartTitle);
        areaChart.setTitle(chartTitle);
        barChart.setTitle(chartTitle);

        // Parse JSON object:
        JSONParser parser = new JSONParser();
        JSONObject countriesObj = new JSONObject();
        Object obj = new Object();

        try {
            // Opening the file
            obj = parser.parse(new FileReader("test/WeeklyCases.json"));
            // obj = parser.parse(new FileReader("corrupted.json"));
            countriesObj = (JSONObject) ((HashMap) obj).get("countries");
        } catch (FileNotFoundException fileNotFoundEx) {
            System.out.println("The input file does not exist.");
            System.exit(0);
        } catch (Exception exception) {
            exception.printStackTrace(System.out);
        }

        try {
            countriesObj = (JSONObject) ((HashMap) obj).get("countries");

            country1_name = countriesObj.get("country1").toString();
            country2_name = countriesObj.get("country2").toString();
            country3_name = countriesObj.get("country3").toString();

            countryItem1 = new CheckMenuItem(country1_name);
            countryItem2 = new CheckMenuItem(country2_name);
            countryItem3 = new CheckMenuItem(country3_name);

            country1 = (JSONObject) ((HashMap) obj).get("country1");
            country2 = (JSONObject) ((HashMap) obj).get("country2");
            country3 = (JSONObject) ((HashMap) obj).get("country3");

            weeklyCases1Obj = ((HashMap) country1).get("weeklyCases");
            weeklyCases2Obj = ((HashMap) country2).get("weeklyCases");
            weeklyCases3Obj = ((HashMap) country3).get("weeklyCases");

            weeklyCasesString = weeklyCases1Obj.toString().replace("[", "").replace("]", "");
            weeklyCasesString2 = weeklyCases2Obj.toString().replace("[", "").replace("]", "");
            weeklyCasesString3 = weeklyCases3Obj.toString().replace("[", "").replace("]", "");

            weeklyCases1 = toIntArray(weeklyCasesString.split(","));
            weeklyCases2 = toIntArray(weeklyCasesString2.split(","));
            weeklyCases3 = toIntArray(weeklyCasesString3.split(","));
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

        // For Line Chart Specific:
        Series<Number, Number> series1 = new XYChart.Series<Number, Number>();
        SerieBuilder(series1, country1_name, weeklyCases1);

        XYChart.Series<Number, Number> series2 = new XYChart.Series<Number, Number>();
        SerieBuilder(series2, country2_name, weeklyCases2);

        XYChart.Series<Number, Number> series3 = new XYChart.Series<Number, Number>();
        SerieBuilder(series3, country3_name, weeklyCases3);

        // For Area Chart Specific:
        Series<Number, Number> series1_area = new XYChart.Series<Number, Number>();
        SerieBuilder(series1_area, country1_name, weeklyCases1);

        XYChart.Series<Number, Number> series2_area = new XYChart.Series<Number, Number>();
        SerieBuilder(series2_area, country2_name, weeklyCases2);

        XYChart.Series<Number, Number> series3_area = new XYChart.Series<Number, Number>();
        SerieBuilder(series3_area, country3_name, weeklyCases3);

        // For Bar Chart Specific:
        Series<String, Number> series1_bar = new XYChart.Series<String, Number>();
        BarChartSerieBuilder(series1_bar, country1_name, weeklyCases1);

        Series<String, Number> series2_bar = new XYChart.Series<String, Number>();
        BarChartSerieBuilder(series2_bar, country2_name, weeklyCases2);

        Series<String, Number> series3_bar = new XYChart.Series<String, Number>();
        BarChartSerieBuilder(series3_bar, country3_name, weeklyCases3);

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                clearCharts();

                if (((CheckMenuItem) ae.getSource()).isSelected()) {
                    if (((CheckMenuItem) ae.getSource()).getText() == country1_name)
                        showCountry1 = true;
                    if (((CheckMenuItem) ae.getSource()).getText() == country2_name)
                        showCountry2 = true;
                    if (((CheckMenuItem) ae.getSource()).getText() == country3_name)
                        showCountry3 = true;
                }

                if (showCountry1) {
                    if (showCountry2) {
                        if (showCountry3) { // Display all countries
                            lineChart.getData().addAll(series1, series2, series3);
                            areaChart.getData().addAll(series1_area, series2_area, series3_area);
                            barChart.getData().addAll(series1_bar, series2_bar, series3_bar);
                        }
                        // Display country 1 & 2
                        lineChart.getData().addAll(series1, series2);
                        areaChart.getData().addAll(series1_area, series2_area);
                        barChart.getData().addAll(series1_bar, series2_bar);
                    } else if (showCountry3) { // Display country 1 & 3
                        lineChart.getData().addAll(series1, series3);
                        areaChart.getData().addAll(series1_area, series3_area);
                        barChart.getData().addAll(series1_bar, series3_bar);
                    } else { // Display country 1
                        lineChart.getData().addAll(series1);
                        areaChart.getData().addAll(series1_area);
                        barChart.getData().addAll(series1_bar);
                    }
                } else if (showCountry2) {
                    if (showCountry3) { // Display country 2 & 3
                        lineChart.getData().addAll(series2, series3);
                        areaChart.getData().addAll(series2_area, series3_area);
                        barChart.getData().addAll(series2_bar, series3_bar);
                    } else { // Display country 2
                        lineChart.getData().addAll(series2);
                        areaChart.getData().addAll(series2_area);
                        barChart.getData().addAll(series2_bar);
                    }
                } else if (showCountry3) { // Display Country 3
                    lineChart.getData().addAll(series3);
                    areaChart.getData().addAll(series3_area);
                    barChart.getData().addAll(series3_bar);
                }
            }
        };

        // Adding icons to the app
        InputStream iconStream = getClass().getResourceAsStream("img/graph.png");
        Image image = new Image(iconStream);

        countryItem1.setOnAction(event);
        countryItem2.setOnAction(event);
        countryItem3.setOnAction(event);
        menu.getItems().addAll(countryItem1, countryItem2, countryItem3);

        menuBar.getMenus().add(menu);

        vBox.getChildren().add(msgLabel);
        vBox.getChildren().addAll(menuBar);
        vBox.getChildren().addAll(lineChartBtn, areaChartBtn, barChartBtn);

        lineChartBtn.setOnAction(e -> {
            if (vBox.getChildren().contains(lineChart)) {
                vBox.getChildren().remove(lineChart);
                lineChartBtn.setTextFill(Color.BLACK);
            } else {
                vBox.getChildren().add(lineChart);
                lineChartBtn.setTextFill(Color.GREEN);
            }
        });

        areaChartBtn.setOnAction(e -> {
            if (vBox.getChildren().contains(areaChart)) {
                vBox.getChildren().remove(areaChart);
                areaChartBtn.setTextFill(Color.BLACK);
            } else {
                vBox.getChildren().add(areaChart);
                areaChartBtn.setTextFill(Color.GREEN);
            }
        });

        barChartBtn.setOnAction(e -> {
            if (vBox.getChildren().contains(barChart)) {
                vBox.getChildren().remove(barChart);
                barChartBtn.setTextFill(Color.BLACK);
            } else {
                vBox.getChildren().add(barChart);
                barChartBtn.setTextFill(Color.GREEN);
            }
        });

        Scene webScene = new Scene(vBox, 1000, 750);

        // -------- Getting the components for the app.css file ----
        // Invoking the app.css file
        //webScene.getStylesheets().add("Web/css/app.css");
        webScene.getStylesheets().add(getClass().getResource("Web/css/app.css").toExternalForm());
        // Declaring menu as the id for the dropdown menu
        menu.setId("menu");
        msgLabel.setId("label");

        // Declaring one id for each item from the dropdown menu
        countryItem1.setId("usa");
        countryItem2.setId("italy");
        countryItem3.setId("france");

        // Declaring unique IDs to style the three buttons
        lineChartBtn.setId("lineChartBtn");
        areaChartBtn.setId("areaChartBtn");
        barChartBtn.setId("barChartBtn");

        // Center the bottons
        vBox.setSpacing(10);
        vBox.setPrefWidth(200);
        vBox.setAlignment(Pos.CENTER);
        // ---------------------- END ------------------------------

        stage.setScene(webScene);
        stage.getIcons().add(image);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}