package src;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        System.out.println("Fun with Java!");
        Dotenv dotenv = Dotenv.configure().load();
        String apiKey = dotenv.get("OWM_API_KEY");
        String cityID = "5392368";

        if (apiKey == null || apiKey.isEmpty()) {
            System.out.println("API key is missing. Please set the OWM_API_KEY environment variable.");
            return;
        }

        String urlString = "https://api.openweathermap.org/data/2.5/weather?id=" + cityID + "&appid=" + apiKey + "&units=imperial";
        try {
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

            // Continue using Gson for JSON parsing
            Gson gson = new Gson();
            JsonObject jsonObj = gson.fromJson(responseData, JsonObject.class);
            double temp = jsonObj.getAsJsonObject("main").get("temp").getAsDouble();
            String weatherCondition = jsonObj.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();

            System.out.println("Temperature in San Marcos, CA: " + temp + " degrees(F)");
            System.out.println("Weather Condition: " + weatherCondition);

        } catch (IOException e) {
            System.out.println("Error making API request: " + e.getMessage());
        }
    }
}