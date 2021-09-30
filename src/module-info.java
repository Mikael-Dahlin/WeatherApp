module WeatherApp {
	requires javafx.controls;
	requires json.simple;
	requires javafx.fxml;
	requires javafx.graphics;
	
	opens application to javafx.graphics, javafx.fxml;
}
