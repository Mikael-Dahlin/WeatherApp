package application;

/**
 * 
 * @author Mikael-Dahlin
 * Class that contains all the daily weather data.
 */
public class DailyData {
	
	// Declaration of variables.
	private String day;
	private String dayOfWeek;
	private String description;
	private String icon;
	private String rain;
	private String sunrise;
	private String sunset;
	private String temperature;
	private String windSpeed;
	
	// Getters and setters for all the variables.
	public String getDay() {
		return day;
	}
	
	public void setDay(String day) {
		this.day = day;
	}
	
	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getIcon() {
		return icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getRain() {
		return rain;
	}
	
	public void setRain(String rain) {
		this.rain = rain;
	}
	
	public String getSunrise() {
		return sunrise;
	}

	public void setSunrise(String sunrise) {
		this.sunrise = sunrise;
	}

	public String getSunset() {
		return sunset;
	}

	public void setSunset(String sunset) {
		this.sunset = sunset;
	}

	public String getTemperature() {
		return temperature;
	}
	
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	
	public String getWindSpeed() {
		return windSpeed;
	}
	
	public void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
	}
}
