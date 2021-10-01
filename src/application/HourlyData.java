package application;

/**
 * 
 * @author Mikael-Dahlin
 * Class that contains all the hourly weather data.
 */
public class HourlyData {
	
	// Declaration of variables.
	private String hour;
	private String description;
	private String icon;
	private String rain;
	private String temperature;
	private String windSpeed;
	
	// Getters and setters for all the variables.
	public String getHour() {
		return hour;
	}
	
	public void setHour(String hour) {
		this.hour = hour;
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
