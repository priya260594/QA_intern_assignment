package Program_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;


public class WeatherReport2 {
	private static final String API_URL ="https://samples.openweathermap.org/data/2.5/forecast/hourly?q=London,us&appid=b6907d289e10d714a6e88b30761fae22";
	public static void main(String[] args) {
		int choice;
	Scanner sc=new Scanner(System.in);
	do {
		System.out.println("Choose options from 0 to 1");
		System.out.println("1.Getting Temperature");
		System.out.println("2.Getting WindSpeed");
		System.out.println("3.Getting Pressure");
		System.out.println("0.Exit the options");
		System.out.println("Enter option: ");
		   choice=sc.nextInt();
		  sc.nextLine();
		  
		  switch(choice) {
		  case 1 :  System.out.print("Enter date with time in following format (yyyy-MM-dd HH:mm:ss): ");
	                String inputDate = sc.nextLine();
	                getTemperatureValue(inputDate);
	                break;
		  case 2:   System.out.print("Enter date with time in following format (yyyy-MM-dd HH:mm:ss): ");
	                inputDate = sc.nextLine();
	                getWindSpeedValue(inputDate);
	                break;  
		  case 3 :  System.out.print("Enter date with time in following format (yyyy-MM-dd HH:mm:ss): ");
	                inputDate = sc.nextLine();
	                getPressureValue(inputDate);
	                break;
		  case 0  : System.out.println("Exiting program.");
	                break;
		  default:  System.out.println("Invalid choice.. Press number from o to 3");
		            break;
		  }
	} while(choice != 0);		
}

	private static void getTemperatureValue(String inputDate) {
		 JSONObject weatherData = APIFetching();
         JSONArray list = weatherData.getJSONArray("list");

         for (int i = 0; i < list.length(); i++) {
             JSONObject weather1 = list.getJSONObject(i);
             String weatherWithDate = weather1.getString("dt_txt");

             if (weatherWithDate.equals(inputDate)) {
                 JSONObject main = weather1.getJSONObject("main");
                 double temperature = main.getDouble("temp");
                 System.out.println("Temperature at " + inputDate + ": " + temperature );
                 return;
             } }
          System.out.println("No data found for the provided date and time.");
         }
	
   
	private static void getWindSpeedValue(String inputDate) {
        JSONObject weatherData = APIFetching();
        JSONArray list = weatherData.getJSONArray("list");

        for (int i = 0; i < list.length(); i++) {
            JSONObject forecast = list.getJSONObject(i);
            String forecastDate = forecast.getString("dt_txt");

            if (forecastDate.equals(inputDate)) {
                JSONObject wind = forecast.getJSONObject("wind");
                double windSpeed = wind.getDouble("speed");
                System.out.println("Wind Speed at " + inputDate + ": " + windSpeed );
                return;
            }
        }

        System.out.println("No data found for the provided date and time.");
    }

    private static void getPressureValue(String inputDate) {
        JSONObject weatherData = APIFetching();
        JSONArray list = weatherData.getJSONArray("list");

        for (int i = 0; i < list.length(); i++) {
            JSONObject forecast = list.getJSONObject(i);
            String forecastDate = forecast.getString("dt_txt");

            if (forecastDate.equals(inputDate)) {
                JSONObject main = forecast.getJSONObject("main");
                double pressure = main.getDouble("pressure");
                System.out.println("Pressure at " + inputDate + ": " + pressure);
                return;
            }
        }

        System.out.println("No data found for the provided date and time.");
    }

	
	 private static JSONObject APIFetching() {
    	 try {
             URL url = new URL(API_URL);
             HttpURLConnection connection = (HttpURLConnection) url.openConnection();
             connection.setRequestMethod("GET");

             int responseCode = connection.getResponseCode();
             if (responseCode == 200) {
                 BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                 StringBuilder response = new StringBuilder();
                 String line;
                 while ((line = reader.readLine()) != null) {
                     response.append(line);
                 }
                 reader.close();
                 return new JSONObject(response.toString());
             } else {
                 System.out.println("Failed to fetch data from the API. Response code: " + responseCode);
             }
         } catch (IOException e) {
             e.printStackTrace();
         }
         return null;
     }
 


}
