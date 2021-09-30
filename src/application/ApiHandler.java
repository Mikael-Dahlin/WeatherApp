package application;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ApiHandler {

	private Keys keys;
	private String url;
	
	public ApiHandler() {
		keys = new Keys();
	}
	
	public String getWeather(String city, WeatherData wd) {
		url = "http://api.openweathermap.org/data/2.5/weather?q=" +
			  city.toLowerCase() + ",se&APPID=" + keys.API_KEY + "&mode=json";
		JSONObject res = APICall(url);
		if (res != null) {
			String lat = String.valueOf(((JSONObject) res.get("coord")).get("lat"));
			String lon = String.valueOf(((JSONObject) res.get("coord")).get("lon"));
			getPrognosis(lat, lon, wd);
			return "Current city: " + city + ", sweden";
		} else {
			return "Cityname was not availible!";
		}
	}
	
	public void getPrognosis(String lat, String lon, WeatherData wd) {
		
		String url = "https://api.openweathermap.org/data/2.5/onecall?lat=" +
					 lat + "&lon=" + lon + 
					 "&exclude=minutely&units=metric&appid=" + keys.API_KEY + "&mode=json";
		
		JSONObject json = APICall(url);
		
		System.out.println(json);
		System.out.println();

		wd.setCurrentWeather(getCurrentWeatherInfo((JSONObject) json.get("current")));
		wd.setHourlyWeather(getHourlyWeatherInfo((JSONArray) json.get("hourly")));
		wd.setDailyWeather(getDailyWeatherInfo((JSONArray) json.get("daily")));
	}
	
	private ArrayList<String> getCurrentWeatherInfo(JSONObject currentWeather) {
		ArrayList<String> dataList = new ArrayList<String>();
		SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
		
		JSONObject weather = (JSONObject) ((JSONArray) currentWeather.get("weather")).get(0);

		dataList.add(weather.get("icon").toString());
		dataList.add(weather.get("description").toString());
		dataList.add(currentWeather.get("temp").toString());
		dataList.add(currentWeather.get("wind_speed").toString());
		dataList.add(hourFormat.format((long) currentWeather.get("sunrise")*1000));
		dataList.add(hourFormat.format((long) currentWeather.get("sunset")*1000));
		if(weather.get("main").toString().equalsIgnoreCase("rain")) {
			dataList.add(((JSONObject) currentWeather.get("rain")).get("1h").toString());				
		}
		
		return dataList;
	}
	
	private ArrayList<ArrayList<String>> getHourlyWeatherInfo(JSONArray hourlyWeather) {
		ArrayList<ArrayList<String>> hourlyData = new ArrayList<ArrayList<String>>();
		SimpleDateFormat hourlyFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		ArrayList<String> dataList;
		JSONObject obj;
		
		for(int i = 0; i < hourlyWeather.size(); i++) {
			obj = (JSONObject) hourlyWeather.get(i);
			
			JSONObject weather = (JSONObject) ((JSONArray) obj.get("weather")).get(0);
			dataList = new ArrayList<String>();
			
			dataList.add(hourlyFormat.format((long) obj.get("dt")*1000));
			dataList.add(weather.get("icon").toString());
			dataList.add(weather.get("description").toString());
			dataList.add(obj.get("temp").toString());
			dataList.add(obj.get("wind_speed").toString());
			if(weather.get("main").toString().equalsIgnoreCase("rain")) {
				dataList.add(((JSONObject) obj.get("rain")).get("1h").toString());				
			}
			
			hourlyData.add(dataList);
		}
		return hourlyData;
	}

	private ArrayList<ArrayList<String>> getDailyWeatherInfo(JSONArray dailyWeather) {
		ArrayList<ArrayList<String>> dailyData = new ArrayList<ArrayList<String>>();
		ArrayList<String> dataList;
		SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
		JSONObject obj;
		for(int i = 0; i < dailyWeather.size(); i++) {
			obj = (JSONObject) dailyWeather.get(i);
			
			JSONObject weather = (JSONObject) ((JSONArray) obj.get("weather")).get(0);
			dataList = new ArrayList<String>();
			
			dataList.add(dayFormat.format((long) obj.get("dt")*1000));
			dataList.add(weather.get("icon").toString());
			dataList.add(weather.get("description").toString());
			dataList.add(((JSONObject) obj.get("temp")).get("day").toString());
			dataList.add(obj.get("wind_speed").toString());
			dataList.add(hourFormat.format((long) obj.get("sunrise")*1000));
			dataList.add(hourFormat.format((long) obj.get("sunset")*1000));
			if(weather.get("main").toString().equalsIgnoreCase("rain")) {
				dataList.add(obj.get("rain").toString());				
			}
			
			dailyData.add(dataList);
		}
		return dailyData;
	}

	public JSONObject APICall(String url) {
		
		try {
			URL api_url;
			HttpURLConnection line;
			JSONParser Jparser;
			
			api_url = new URL(url);
			
			// Create a HTTP connection to sent the GET request over
			line = (HttpURLConnection) api_url.openConnection();
			line.setDoInput(true);
			line.setDoOutput(true);
			line.setRequestMethod("GET");
			
			Jparser = new JSONParser();
			return (JSONObject) Jparser.parse(new InputStreamReader(line.getInputStream()));
			
		} catch (IOException | ParseException e) {
			System.out.println("Invalid API call!!");
			return null;
		}
	}
}
