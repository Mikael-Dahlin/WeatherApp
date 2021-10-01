package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

/**
 * 
 * @author Mikael-Dahlin
 * Main class that starts the program and generates a GUI.
 */
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			// Generate GUI based on Main.fxml file and add to root.
			Parent root = FXMLLoader.load(getClass().getResource("/application/Main.fxml"));
			
			// Put it in a scene and set window size.
			Scene scene = new Scene(root,640,550);
			
			// Add icon to the frame.
			primaryStage.getIcons().add(new Image("file:icons/02d.png"));
			
			// Add the scene to the frame.
			primaryStage.setScene(scene);
			
			// Set the frame to not be resizable.
			primaryStage.setResizable(false);
			
			// Show the window to the user.
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
