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

public class WeatherLogic {
	
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
	
	public WeatherLogic(){
		ah = new ApiHandler();
		wd = new WeatherData();
	}
	
	public void getCity() {
		selectedCity.setText(ah.getWeather(cityName.getText(), wd));
		if(!selectedCity.getText().contains("not availible")) {
			displayCurrentWeather();
			displayHourlyWeather();
			displayDailyWeather();
		}
	}

	private void displayCurrentWeather() {
		ArrayList<String> current = wd.getCurrentWeather();
		
		// Change background to show the image better and load image.
		currentWeather.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255), new CornerRadii(0.0), new Insets(0.0))));
		currentIcon.setImage(new Image("file:icons/"+ current.get(0) + ".png"));
		
		// Set the text of the label.
		currentWeather.setText("Description: " + current.get(1) + "\n"
								+ ((current.size() < 6) ? "" : "Rain: " + current.get(5) + "mm \n")
							 	+ "Temperature: " + current.get(2) + "°C" + "\n"
							 	+ "Wind Speed: " + current.get(3) + "m/s" + "\n"
							 	+ "Sunrise: " + current.get(4) + " | " + "Sunset: " + current.get(5));
	}
	
	private void displayHourlyWeather() {
		ArrayList<ArrayList<String>> hourlyData = wd.getHourlyWeather();
		ArrayList<String> hourData;
		Label label;
		
		for (int i = 0; i < hourlyData.size(); i++) {
			hourData = hourlyData.get(i);
			label = new Label();

			// Add image and text to the labels.
			label.setGraphic(new ImageView(new Image("file:icons/"+ hourData.get(1) + ".png")));
			label.setText("Time: " + hourData.get(0) + "\n"
					+ "Description: " + hourData.get(2) + "\n" 
					+ ((hourData.size() < 6) ? "" : "Rain: " + hourData.get(5) + "mm \n")
				 	+ "Temperature: " + hourData.get(3) + "°C" + "\n" 
				 	+ "Wind Speed: " + hourData.get(4) + "m/s" + "\n");
			
			label.setPadding(new Insets(10));
			hourlyWeather.getChildren().add(label);
		}
		
	}
	
	private void displayDailyWeather() {
		ArrayList<ArrayList<String>> dailyData = wd.getDailyWeather();
		ArrayList<String> dayData;
		Label label;
		
		for (int i = 0; i < dailyData.size(); i++) {
			dayData = dailyData.get(i);
			label = new Label();
			
			label.setGraphic(new ImageView(new Image("file:icons/"+ dayData.get(1) + ".png")));
			label.setText("Time: " + dayData.get(0) + "\n"
					+ "Description: " + dayData.get(2) + "\n"
					+ ((dayData.size() < 8) ? "" : "Rain: " + dayData.get(7) + "mm \n")
				 	+ "Temperature: " + dayData.get(3) + "°C" + "\n"
				 	+ "Wind Speed: " + dayData.get(4) + "m/s" + "\n"
				 	+ "Sunrise: " + dayData.get(5) + " | " + "Sunset: " + dayData.get(6));
			
			label.setPadding(new Insets(10));
			System.out.println(dayData.size());
			dailyWeather.getChildren().add(label);
		}
	}
}
