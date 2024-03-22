package src;

import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        
        String eyes = ":";
        String smile = ")";
        String smileyFace = eyes + smile;
        
        boolean Java = true;
        boolean Kotlin = true;
        boolean Clojure = false;

        System.out.println("Fun with Java!");
        System.out.println("We going to have fun with vanilla Java!: " + Java);
        System.out.println("We going to have fun with Kotlin!: " + Kotlin);
        System.out.println("We going to have fun with Clojure!: " + Clojure);

        System.out.print(eyes);
        
        for(int i = 0; i < 10; i++) {
            System.out.print(smile);
        }

        //api stuff
        Dotenv dotenv = Dotenv.configure().load();
        String apiKey = dotenv.get("OWM_API_KEY");

        if (apiKey == null || apiKey.isEmpty()) {
            System.out.println("API key is missing. Make sure you have a .env file with the key inside.");
            System.out.println("Hint: create a .env file and set 'OWM_API_KEY = YOUR_KEY'");
            return;
        }

        boolean noCity = true;

        do{
            System.out.println("\nWoW! AlL oF a SuDdEn YoU'rE iNtErEsTeD iN kNoWiNg WhAt ThE wEaThEr Is! \nEnter the name of a city like 'San Marcos': ");
            Scanner scanner = new Scanner(System.in);
            String testCity = scanner.nextLine();
            scanner.close();
            if(testCity.equals("San Marcos") || testCity.equals("san marcos")){
                System.out.println("\n" + eyes + smile + smile);
            } else {
                System.out.println("\nWell, you entered '" + testCity + "' but I'm going to check San Marcos instead " + eyes + smile + smile);
            }
            noCity = false;
            System.out.println("\nThis program actually checks the current weather conditions, and will need an active internet connection btw...");
        } while (noCity);

        //I don't think I like Vanilla Java.
        //I have to use it for SE471 and I think my early negative experiences have tainted my experiences with the language.
        //I just want to make something fun, and have it work.
        //I feel like Java gets in the way of itself sometimes.

        String cityID = "5392368";

        try {
            WeatherData weatherData = WeatherData.fromApi(cityID, apiKey);

            System.out.println("\n\nTemperature in " + weatherData.getCityName() + ", CA: " + weatherData.getTemperature() + " degrees(F)");
            System.out.println("Weather Condition: " + weatherData.getWeatherCondition());
            System.out.println(smileyFace);

        } catch (IOException e) {
            System.out.println("Error making API request: " + e.getMessage());
        }
    }
}