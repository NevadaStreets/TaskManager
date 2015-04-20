package application;
	
import java.io.IOException;

import view.ProjectEditDialogController;
import view.TaskViewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	private ObservableList<Proyecto> projectData = FXCollections.observableArrayList();
	private Stage ps;
	private BorderPane bv;
	
	
	@Override
	public void start(Stage primaryStage) {
		this.ps = primaryStage;
		this.ps.setTitle("Task Manager");
		
		projectData.add(new Proyecto("Proyecto de Software"));
		projectData.add(new Proyecto("Proyecto de Vida"));
		projectData.add(new Proyecto("Electro pls"));
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
	            
	            // Give the controller access to the main app.
	            TaskViewController controller = loader.getController();
	            controller.setMainApp(this);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    /**
	     * Opens a dialog to edit details for the specified person. If the user
	     * clicks OK, the changes are saved into the provided person object and true
	     * is returned.
	     * 
	     * @param person the person object to be edited
	     * @return true if the user clicked OK, false otherwise.
	     */
	    public boolean showProjectEditDialog(Proyecto proyect) {
	        try {
	            // Load the fxml file and create a new stage for the popup dialog.
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(Main.class.getResource("../view/ProjectEditDialog.fxml"));
	            AnchorPane page = (AnchorPane) loader.load();

	            // Create the dialog Stage.
	            Stage dialogStage = new Stage();
	            dialogStage.setTitle("Editar Proyecto");
	            dialogStage.initModality(Modality.WINDOW_MODAL);
	            dialogStage.initOwner(ps);
	            Scene scene = new Scene(page);
	            dialogStage.setScene(scene);

	            // Set the person into the controller.
	            ProjectEditDialogController controller = loader.getController();
	            controller.setDialogStage(dialogStage);
	            controller.setPerson(proyect);

	            // Show the dialog and wait until the user closes it
	            dialogStage.showAndWait();

	            return controller.isOkClicked();
	        } catch (IOException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    
	    public Stage getPrimaryStage() {
	        return ps;
	    }
	    
	    public ObservableList<Proyecto> getProyectData() {
	        return projectData;
	    }
	
	public static void main(String[] args) {
		launch(args);
	}
}
