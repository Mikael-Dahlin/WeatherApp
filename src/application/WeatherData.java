package application;

import java.util.ArrayList;

public class WeatherData {
	private ArrayList<String> currentWeather;
	private ArrayList<ArrayList<String>> hourlyWeather;
	private ArrayList<ArrayList<String>> dailyWeather;
	
	public WeatherData() {
		
	}
	
	public WeatherData(ArrayList<String> _currentWeather, ArrayList<ArrayList<String>> _hourlyWeather, ArrayList<ArrayList<String>> _dailyWeather) {
		this.currentWeather = _currentWeather;
		this.hourlyWeather = _hourlyWeather;
		this.dailyWeather = _dailyWeather;
	}

	public ArrayList<String> getCurrentWeather() {
		return currentWeather;
	}

	public void setCurrentWeather(ArrayList<String> _currentWeather) {
		this.currentWeather = _currentWeather;
	}

	public ArrayList<ArrayList<String>> getHourlyWeather() {
		return hourlyWeather;
	}

	public void setHourlyWeather(ArrayList<ArrayList<String>> _hourlyWeather) {
		this.hourlyWeather = _hourlyWeather;
	}

	public ArrayList<ArrayList<String>> getDailyWeather() {
		return dailyWeather;
	}

	public void setDailyWeather(ArrayList<ArrayList<String>> _dailyWeather) {
		this.dailyWeather = _dailyWeather;
	}
	
	
	
}
