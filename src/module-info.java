module WeatherApp {
	requires javafx.controls;
	requires json.simple;
	
	opens application to javafx.graphics, javafx.fxml;
}
