package umgc.cmsc495.earlybirds;

import java.io.FileReader;
// import org.json.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class App {
    public static void main(String[] args) throws Exception {
        // Parse JSON object:
        JSONParser parser = new JSONParser();
        JSONObject jsonObj = new JSONObject();

        try {
            // Opening the file
            Object obj = parser.parse(new FileReader("WeeklyCases.json"));
            // creating a Json object
            jsonObj = (JSONObject) obj;

        } catch (Exception e) {
            // TODO: handle exception
        }

        LineChartVisual lineChart = new LineChartVisual(jsonObj);
        BarChartVisual barChart = new BarChartVisual(jsonObj);
    }
}
