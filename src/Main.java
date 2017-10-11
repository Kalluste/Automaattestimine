public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println(Forecast.getHTML
                ("http://api.openweathermap.org/data/2.5/forecast?q=Tallinn&appid=2e573e8867a672c2e2513f5c2172cb86"));
    }
}
