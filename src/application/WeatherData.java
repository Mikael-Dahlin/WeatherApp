package application;

import java.util.ArrayList;

/**
 * 
 * @author Mikael-Dahlin
 * Main model class that handles the data storage.
 */
public class WeatherData {
	private CurrentData currentWeather;
	private ArrayList<HourlyData> hourlyWeather;
	private ArrayList<DailyData> dailyWeather;
	
	/*
	 * Empty constructor that just generates a new object.
	 */
	public WeatherData() {}
	
	/*
	 * Optional constructor that generates a new object with the data provided. (Not currently used in the code, future proofing)
	 */
	public WeatherData(CurrentData _currentWeather, ArrayList<HourlyData> _hourlyWeather, ArrayList<DailyData> _dailyWeather) {
		this.currentWeather = _currentWeather;
		this.hourlyWeather = _hourlyWeather;
		this.dailyWeather = _dailyWeather;
	}

	// Getters and setters for the data.
	public CurrentData getCurrentWeather() {
		return currentWeather;
	}

	public void setCurrentWeather(CurrentData _currentWeather) {
		this.currentWeather = _currentWeather;
	}

	public ArrayList<HourlyData> getHourlyWeather() {
		return hourlyWeather;
	}

	public void setHourlyWeather(ArrayList<HourlyData> _hourlyWeather) {
		this.hourlyWeather = _hourlyWeather;
	}

	public ArrayList<DailyData> getDailyWeather() {
		return dailyWeather;
	}

	public void setDailyWeather(ArrayList<DailyData> _dailyWeather) {
		this.dailyWeather = _dailyWeather;
	}

}
