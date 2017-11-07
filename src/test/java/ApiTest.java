import entity.DailyForecast;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class ApiTest {
    private DailyForecast dailyForecast;

    @Before
    public void setUpBeforeEachTest() {
        try {
            dailyForecast = new ForecastService().getHTML("Tallinn");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGotWeatherInfoFromAPI() {
        Assert.assertNotEquals(null, dailyForecast);
    }

    @Test
    public void testReceivesWeatherForecasts() {
        assertNotNull(dailyForecast.getForecasts().get(0).getTemperatures());
    }

    @Test
    public void testReceivesTemperatureAmplitude() {
        Assert.assertNotEquals(null, dailyForecast.getForecasts());
    }

    @Test
    public void testReceivesDailyLowestTemp() {
        assertNotNull(dailyForecast.getForecasts().get(0).getTemperatures().getTemp_min());

    }

    @Test
    public void testReceivesDailyHighestTemp() {
        assertNotNull(dailyForecast.getForecasts().get(0).getTemperatures().getTemp_max());
    }

    @Test
    public void correctCoordinatesLon() { //24.7D
        assertEquals(24.7D, dailyForecast.getCity().getCoordinates().getLon(), 0.5);
    }

    @Test
    public void correctCoordinatesLat() { //59.437D
        assertEquals(59.437D, dailyForecast.getCity().getCoordinates().getLat(), 0.5);
    }

    @Test
    public void testReceivesLongitude() {
        assertNotNull(dailyForecast.getCity().getCoordinates().getLon());
    }

    @Test
    public void testReceivesLatitude() {
        assertNotNull(dailyForecast.getCity().getCoordinates().getLat());
    }

    @Test
    public void testCorrectCoordinatesFormat() {
        assertEquals("59:25", dailyForecast.getCity().getCoordinates().formatCoordinates());
    }

    @Test
    public void testReceivesFiveDaysForecast() {
        assertEquals(40, dailyForecast.getForecasts().size());
    }

    @Test
    public void testMinimumTemperatureSmallerThanCurrentTemperature() {
        assertTrue(dailyForecast.getForecasts().get(0).getTemperatures().getTemp_min() <=
                dailyForecast.getForecasts().get(0).getTemperatures().getTemp());
    }

    @Test
    public void testMaximumTemperatureBiggerThanCurrentTemperature() {
        assertTrue(dailyForecast.getForecasts().get(0).getTemperatures().getTemp_max() >=
                dailyForecast.getForecasts().get(0).getTemperatures().getTemp());
    }

}
