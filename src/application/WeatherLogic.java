package application;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * 
 * @author Mikael-Dahlin
 * Main controller class that handles changes in the GUI.
 */
public class WeatherLogic {
	// Declaration of variables and FXML id's.
	private WeatherData wd;
	private ApiHandler ah;
	
	@FXML
	private Label selectedCity;
	
	@FXML
	private Label currentWeather;
	
	@FXML
	private ImageView currentIcon;
	
	@FXML
	private TextField cityName;
	
	@FXML
	private VBox hourlyWeather;
	
	@FXML
	private VBox dailyWeather;
	
	/*
	 * Constructor that generates class instances for handling API requests and data storage.
	 */
	public WeatherLogic(){
		ah = new ApiHandler();
		wd = new WeatherData();
	}
	
	/*
	 * Method that runs when you click the search button.
	 */
	public void getCity() {
		selectedCity.setText(ah.getWeather(cityName.getText(), wd));
		if(!selectedCity.getText().contains("not availible")) {
			displayCurrentWeather();
			displayHourlyWeather();
			displayDailyWeather();
		}
	}

	/*
	 * Method that displays the current weather on the GUI.
	 */
	private void displayCurrentWeather() {
		CurrentData current = wd.getCurrentWeather();
		
		// Change background to show the image better and load image.
		currentWeather.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255), new CornerRadii(0.0), new Insets(0.0))));
		currentIcon.setImage(new Image("file:icons/"+ current.getIcon() + ".png"));
		
		// Set the text of the label.
		currentWeather.setText("Description: " + current.getDescription() + "\n"
								+ (current.getRain().isEmpty() ? "" : "Rain: " + current.getRain() + "mm \n")
							 	+ "Temperature: " + current.getTemperature() + "°C" + "\n"
							 	+ "Wind Speed: " + current.getWindSpeed() + "m/s" + "\n"
							 	+ "Sunrise: " + current.getSunrise() + " | " + "Sunset: " + current.getSunset());
	}
	
	/*
	 * Method for printing the hourly weather data to the hourlyWeather VBox.
	 */
	private void displayHourlyWeather() {
		ArrayList<HourlyData> hourlyData = wd.getHourlyWeather();
		HourlyData hourData;
		Label label;
		
		// Loop through the ArrayList of data and generate labels.
		for (int i = 0; i < hourlyData.size(); i++) {
			hourData = hourlyData.get(i);
			label = new Label();

			// Add image and text to the labels based on data.
			label.setGraphic(new ImageView(new Image("file:icons/"+ hourData.getIcon() + ".png")));
			label.setText("Time: " + hourData.getHour() + "\n"
					+ "Description: " + hourData.getDescription() + "\n" 
					+ (hourData.getRain().isEmpty() ? "" : "Rain: " + hourData.getRain() + "mm \n")
				 	+ "Temperature: " + hourData.getTemperature() + "°C" + "\n" 
				 	+ "Wind Speed: " + hourData.getWindSpeed() + "m/s" + "\n");
			
			// Add a bit of padding between labels for better spacing.
			label.setPadding(new Insets(10));
			
			// Adds the label to the VBox.
			hourlyWeather.getChildren().add(label);
		}
		
	}
	
	/*
	 * Method for printing the daily weather data to the dailyWeather VBox.
	 */
	private void displayDailyWeather() {
		ArrayList<DailyData> dailyData = wd.getDailyWeather();
		DailyData dayData;
		Label label;
		
		// Loop through the ArrayList of data and generate labels.
		for (int i = 0; i < dailyData.size(); i++) {
			dayData = dailyData.get(i);
			label = new Label();
			
			// Add image and text to the labels based on data.
			label.setGraphic(new ImageView(new Image("file:icons/"+ dayData.getIcon() + ".png")));
			label.setText("Date: " + dayData.getDayOfWeek() + ", " + dayData.getDay() + "\n"
					+ "Description: " + dayData.getDescription() + "\n"
					+ (dayData.getRain().isEmpty() ? "" : "Rain: " + dayData.getRain() + "mm \n")
				 	+ "Temperature: " + dayData.getTemperature() + "°C" + "\n"
				 	+ "Wind Speed: " + dayData.getWindSpeed() + "m/s" + "\n"
				 	+ "Sunrise: " + dayData.getSunrise() + " | " + "Sunset: " + dayData.getSunset());
			
			// Add a bit of padding between labels for better spacing.
			label.setPadding(new Insets(10));
			
			// Adds the label to the VBox.
			dailyWeather.getChildren().add(label);
		}
	}
}
