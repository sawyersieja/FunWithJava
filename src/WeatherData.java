package src;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherData {
    private double temperature;
    private String weatherCondition;
    private String cityName;

    private WeatherData(double temp, String weather, String city) {
        temperature = temp;
        weatherCondition = weather;
        cityName = city;
    }

    public static WeatherData fromApi(String cityID, String apiKey) throws IOException {
        //I used chatgpt to assist me with the URL, HttpURLConnection, and Json stuff
            //since I didn't know how to do that stuff and I'm still learning Java.
        //I wanted to actually make something using real world data that is useful.
        String urlString = "https://api.openweathermap.org/data/2.5/weather?id=" + cityID + "&appid=" + apiKey + "&units=imperial";
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int status = con.getResponseCode();
        if (status != HttpURLConnection.HTTP_OK) {
            throw new IOException("Unexpected response code: " + status);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        String responseData = content.toString();
        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(responseData, JsonObject.class);

        double temp = jsonObj.getAsJsonObject("main").get("temp").getAsDouble();
        String weatherCondition = jsonObj.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();
        String cityName = jsonObj.get("name").getAsString();

        return new WeatherData(temp, weatherCondition, cityName);
    }

    public double getTemperature() {
        return temperature;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public String getCityName() {
        return cityName;
    }
}
