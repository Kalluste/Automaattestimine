
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
import java.util.ArrayList;
import java.util.List;


public class ForecastService {
    public DailyForecast getHTML(String city) throws IOException {
        String result = readURL(city);
        System.out.println(result);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Files.write(Paths.get("output.txt"), result.getBytes());
        return objectMapper.readValue(result, DailyForecast.class);
    }

    String readURL(String city) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?q=" + city
                + "&appid=459d16e78c8ff4a179b9884bbbc18cce");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }

    public void writeToFile(String city, String data) throws IOException {
        Files.write(Paths.get(city + ".txt"), data.getBytes());
    }

    /*
    You can give cityes trough input and it creates file for every city.
     */
    void saveForecastToFile() throws IOException {
        List<DailyForecast> forecasts = getCityForecastForEachCity();
        for (DailyForecast forecast : forecasts) {
            int j = 0;
            StringBuilder buildData = new StringBuilder();
            String city = String.valueOf(forecast.getCity().getName());
            String coordinates = forecast.getCity().getCoordinates().formatCoordinates() + "\n";


            while (j < forecast.getForecasts().size()) {
                if (j == 0) {
                    buildData.append("Esimene päev: " + "\n\n");
                } else if (j == 7) {
                    buildData.append("Teine päev: " + "\n\n");
                } else if (j == 15) {
                    buildData.append("Kolmas päev: " + "\n\n");
                }
                buildData.append("Max temp: ")
                        .append(forecast.getForecasts().get(j).getTemperatures().getTemp_max())
                        .append("\n")
                        .append("Min temp: ")
                        .append(forecast.getForecasts().get(j).getTemperatures().getTemp_min())
                        .append("\n")
                        .append("Current temp: ")
                        .append(forecast.getForecasts().get(j).getTemperatures().getTemp())
                        .append("\n\n\n");

                j++;
                if (j == 3) {
                    j = 7;
                } else if (j == 10) {
                    j = 15;
                } else if (j == 18) {
                    break;
                }
            }
            String data = city + " " + coordinates + buildData;
            writeToFile(city, data);

        }
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

    public List<String> getCityForecastsFromFile() throws IOException {
        return Files.readAllLines(Paths.get("input.txt"));
    }

    public List<DailyForecast> getCityForecastForEachCity() throws IOException {
        List<DailyForecast> dailyForecasts = new ArrayList<>();
        for (String city : getCityForecastsFromFile()) {
            dailyForecasts.add(getHTML(city));
        }
        return dailyForecasts;
    }
}
