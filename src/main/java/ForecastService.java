
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import entity.DailyForecast;
import entity.Response;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;


public class ForecastService {
    public DailyForecast getHTML(String city) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?q=" + city + "&appid=2e573e8867a672c2e2513f5c2172cb86");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        System.out.println(result);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Files.write(Paths.get("output.txt"), result.toString().getBytes());
        return objectMapper.readValue(result.toString(), DailyForecast.class);
    }

    public DailyForecast getCityFromConsole() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter String");
        try {
            String city = br.readLine();
            return getHTML(city);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public DailyForecast getCityFromFile() throws IOException {
        String city = new String(Files.readAllBytes(Paths.get("input.txt")));
        return getHTML(city);
    }
}
