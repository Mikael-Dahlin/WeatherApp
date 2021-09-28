package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ApiHandler {

	private Keys keys;
	private String url;
	
	public ApiHandler() {
		keys = new Keys();
		url = "http://api.openweathermap.org/data/2.5/weather?q=malmö,se&APPID=" + keys.API_KEY + "&mode=json";

		
		URL line_api_url;
		try {
			line_api_url = new URL(url);
			
			// Create a HTTP connection to sent the GET request over
			HttpURLConnection linec = (HttpURLConnection) line_api_url.openConnection();
			linec.setDoInput(true);
			linec.setDoOutput(true);
			linec.setRequestMethod("GET");
			
			
			JSONParser Jparser = new JSONParser();
			JSONObject JObj = (JSONObject) Jparser.parse(new InputStreamReader(linec.getInputStream()));

			System.out.println(((JSONObject) JObj.get("coord")) + ": " + ((JSONObject) JObj.get("coord")).get("lon") + ", " + ((JSONObject) JObj.get("coord")).get("lat"));
			
			String oneCall = "https://api.openweathermap.org/data/2.5/onecall?lat=" + ((JSONObject) JObj.get("coord")).get("lat") + "&lon=" + ((JSONObject) JObj.get("coord")).get("lon") + "&exclude=minutely&units=metric&appid=" +keys.API_KEY + "&mode=json";
			line_api_url = new URL(oneCall);
			
			// Create a HTTP connection to sent the GET request over
			HttpURLConnection lineb = (HttpURLConnection) line_api_url.openConnection();
			lineb.setDoInput(true);
			lineb.setDoOutput(true);
			lineb.setRequestMethod("GET");
			
			JSONObject json = (JSONObject) Jparser.parse(new InputStreamReader(lineb.getInputStream()));
			
			System.out.println(json);
			System.out.println();
			
			JSONArray array = (JSONArray) json.get("hourly");
			JSONObject obj;
			for(int i = 0; i < array.size(); i++) {
				obj = (JSONObject) array.get(i);
				System.out.println("temp: " + obj.get("temp") + " degrees celcius, time: " + new Date((long) obj.get("dt")*1000));
			}
			System.out.println();
			
			JSONArray arrayD = (JSONArray) json.get("daily");
			JSONObject objD;
			for(int i = 0; i < arrayD.size(); i++) {
				objD = (JSONObject) arrayD.get(i);
				System.out.println("temp: " + objD.get("temp") + " degrees celcius, time: " + new Date((long) objD.get("dt")*1000));
			}
			System.out.println();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

	}
}
