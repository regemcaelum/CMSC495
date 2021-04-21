import java.io.FileReader;
// import org.json.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class App {
    public static void main(String[] args) throws Exception {
        // System.out.println("Hello World from David, Xingyi, and Corey!!");

        // Parse JSON object:
        JSONParser parser = new JSONParser();
        JSONObject jsonObj = new JSONObject();

        try {
            // Opening the file
            Object obj = parser.parse(new FileReader("WeeklyCases.json"));
            // creating a Json object
            jsonObj = (JSONObject) obj;

        } catch (Exception e) {
            e.printStackTrace();
        }

        LineChartVisual lineChart = new LineChartVisual(jsonObj);
        BarChartVisual barChart = new BarChartVisual(jsonObj);
        AreaChartVisual areaChart = new AreaChartVisual(jsonObj);

    }
}
