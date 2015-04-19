package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	private Stage ps;
	private BorderPane bv;
	@Override
	public void start(Stage primaryStage) {
		this.ps = primaryStage;
		this.ps.setTitle("Task Manager");
		
		initBigView();

        showTaskView();
	}
	
	 public void initBigView() {
	        try {
	            // Load root layout from fxml file.
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(Main.class.getResource("../view/BigView.fxml"));
	            bv = (BorderPane) loader.load();

	            // Show the scene containing the root layout.
	            Scene scene = new Scene(bv);
	            ps.setScene(scene);
	            ps.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    /**
	     * Shows the person overview inside the root layout.
	     */
	    public void showTaskView() {
	        try {
	            // Load person overview.
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(Main.class.getResource("../view/TaskView.fxml"));
	            AnchorPane tv = (AnchorPane) loader.load();

	            // Set person overview into the center of root layout.
	            bv.setCenter(tv);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    public Stage getPrimaryStage() {
	        return ps;
	    }
	
	public static void main(String[] args) {
		launch(args);
	}
}
