package application;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * 
 * @author Mikael-Dahlin
 * Class that handles the API calls and response.
 */
public class ApiHandler {

	// Declaration of variables.
	private Keys keys;
	private String url;
	
	// Constuctor that gets the API Key from a separate class.
	public ApiHandler() {
		keys = new Keys();
	}
	
	/*
	 * Method that tests if the city could be found in the API and returns a String response.
	 * If successful takes the coordinates from the response and sends it to the next API call.
	 */
	public String getWeather(String city, WeatherData wd) {
		// Generate the url.
		url = "http://api.openweathermap.org/data/2.5/weather?q=" +
			  city.toLowerCase() + ",se&APPID=" + keys.API_KEY + "&mode=json";
		
		// Send API request with APICall.
		JSONObject res = APICall(url);
		
		// Checks that the response is not null.
		if (res != null) {
			String lat = String.valueOf(((JSONObject) res.get("coord")).get("lat"));
			String lon = String.valueOf(((JSONObject) res.get("coord")).get("lon"));
			getPrognosis(lat, lon, wd);
			return "Current city: " + city + ", sweden";
		} else {
			return "Cityname was not availible!";
		}
	}
	
	/*
	 * Method that takes latitude and longitude of a city to call the API and store any relevant data in the provided WeatherData Object.
	 */
	public void getPrognosis(String lat, String lon, WeatherData wd) {
		// Generate the url.
		String url = "https://api.openweathermap.org/data/2.5/onecall?lat=" +
					 lat + "&lon=" + lon + 
					 "&exclude=minutely&units=metric&appid=" + keys.API_KEY + "&mode=json";
		
		// Send API request with APICall.
		JSONObject json = APICall(url);

		// Store the response in the data object.
		wd.setCurrentWeather(getCurrentWeatherInfo((JSONObject) json.get("current")));
		wd.setHourlyWeather(getHourlyWeatherInfo((JSONArray) json.get("hourly")));
		wd.setDailyWeather(getDailyWeatherInfo((JSONArray) json.get("daily")));
	}
	
	/*
	 * Method for storing the current weather data.
	 */
	private CurrentData getCurrentWeatherInfo(JSONObject currentWeather) {
		// Declare local variables.
		CurrentData currentData = new CurrentData();
		SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
		
		// Get the weather object from the API's JSON response.
		JSONObject weather = (JSONObject) ((JSONArray) currentWeather.get("weather")).get(0);
		
		// Store the relevant data in currentData.
		currentData.setDescription(weather.get("description").toString());
		currentData.setIcon(weather.get("icon").toString());
		currentData.setRain(weather.get("main").toString().equalsIgnoreCase("rain") ? ((JSONObject) currentWeather.get("rain")).get("1h").toString() : "");
		currentData.setTemperature(currentWeather.get("temp").toString());
		currentData.setWindSpeed(currentWeather.get("wind_speed").toString());
		currentData.setSunrise(hourFormat.format((long) currentWeather.get("sunrise")*1000));
		currentData.setSunset(hourFormat.format((long) currentWeather.get("sunset")*1000));
		
		// Returns the CurrentData object.
		return currentData;
	}
	
	/*
	 * Method for storing the hourly weather data.
	 */
	private ArrayList<HourlyData> getHourlyWeatherInfo(JSONArray hourlyWeather) {
		// Declare local variables.
		ArrayList<HourlyData> hourlyData = new ArrayList<HourlyData>();
		SimpleDateFormat hourlyFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		HourlyData hourData;
		JSONObject obj;
		
		// Loop through all the hour objects in the API's response.
		for(int i = 0; i < hourlyWeather.size(); i++) {
			obj = (JSONObject) hourlyWeather.get(i);
			hourData = new HourlyData();
			
			// Get the weather object from the API's JSON response.
			JSONObject weather = (JSONObject) ((JSONArray) obj.get("weather")).get(0);
			
			// Store the relevant data in an HourlyData object.
			hourData.setHour(hourlyFormat.format((long) obj.get("dt")*1000));
			hourData.setDescription(weather.get("description").toString());
			hourData.setIcon(weather.get("icon").toString());
			hourData.setRain(weather.get("main").toString().equalsIgnoreCase("rain") ? ((JSONObject) obj.get("rain")).get("1h").toString() : "");
			hourData.setTemperature(obj.get("temp").toString());
			hourData.setWindSpeed(obj.get("wind_speed").toString());
			
			// Adds the new object to the ArrayList.
			hourlyData.add(hourData);
		}
		
		// Returns an ArrayList of HourlyData objects.
		return hourlyData;
	}

	/*
	 * Method for storing the daily weather data.
	 */
	private ArrayList<DailyData> getDailyWeatherInfo(JSONArray dailyWeather) {
		// Declare local variables.
		ArrayList<DailyData> dailyData = new ArrayList<DailyData>();
		SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
		DailyData dayData;
		JSONObject obj;
		String day;
		
		// Loop through all the hour objects in the API's response.
		for(int i = 0; i < dailyWeather.size(); i++) {
			obj = (JSONObject) dailyWeather.get(i);
			dayData = new DailyData();
			
			// Get the weather object from the API's JSON response.
			JSONObject weather = (JSONObject) ((JSONArray) obj.get("weather")).get(0);
			
			// Store the relevant data in an HourlyData object.
			day = dayFormat.format((long) obj.get("dt")*1000);
			dayData.setDay(day);
			dayData.setDayOfWeek(LocalDate.parse(day).getDayOfWeek().toString());
			dayData.setIcon(weather.get("icon").toString());
			dayData.setDescription(weather.get("description").toString());
			dayData.setTemperature(((JSONObject) obj.get("temp")).get("day").toString());
			dayData.setWindSpeed(obj.get("wind_speed").toString());
			dayData.setSunrise(hourFormat.format((long) obj.get("sunrise")*1000));
			dayData.setSunset(hourFormat.format((long) obj.get("sunset")*1000));
			dayData.setRain(weather.get("main").toString().equalsIgnoreCase("rain") ? obj.get("rain").toString() : "");
			
			// Adds the new object to the ArrayList.
			dailyData.add(dayData);
		}
		// Returns an ArrayList of DailyData objects.
		return dailyData;
	}

	/*
	 * Method that handles the API calls based on the provided url.
	 */
	public JSONObject APICall(String url) {
		
		try {
			// Declare local variables.
			URL api_url = new URL(url);
			HttpURLConnection line;
			JSONParser Jparser; 
			
			// Create a HTTP connection to sent the GET request over
			line = (HttpURLConnection) api_url.openConnection();
			line.setDoInput(true);
			line.setDoOutput(true);
			line.setRequestMethod("GET");
			
			// Parse and return the response as JSONObject.
			Jparser = new JSONParser();
			return (JSONObject) Jparser.parse(new InputStreamReader(line.getInputStream()));
			
		} catch (IOException | ParseException e) {
			// If there is an error returns null.
			return null;
		}
	}
}
