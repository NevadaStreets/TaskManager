package application;
	
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import view.EditTaskButtonController;
import view.ProjectEditDialogController;
import view.TareaViewController;
import view.TaskEditDialogController;
import view.TaskViewController;
import view.TasksInProjectViewController;
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


public class Main extends Application implements Serializable{
	
	private ObservableList<Proyecto> projectData = FXCollections.observableArrayList();
	private ArrayList<Proyecto> respaldoproject;
	private ObservableList<Tarea> taskData = FXCollections.observableArrayList();
	private ArrayList<Tarea> respaldotask;
	private Stage ps;
	private BorderPane bv;
	
	
	@Override
	public void start(Stage primaryStage) {
		File archivo = new File("media.obj");
		
		this.ps = primaryStage;
		this.ps.setTitle("Task Manager");
		if(archivo.exists()){
			try {
				desereal();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	}
		else{
		//projectData.add(new Proyecto("Proyecto de Software"));
		//projectData.add(new Proyecto("Proyecto de Vida"));
		//projectData.add(new Proyecto("Electro pls"));
		//Tarea t = new Tarea("A fluir con Fluidos", projectData.get(2));
		//taskData.add(t);
		//projectData.get(2).Tasks.add(t);
			}
		initBigView();
		ordenar();

        showTaskView();
	}
	
		private void desereal() throws FileNotFoundException, IOException, ClassNotFoundException{
			ObjectInputStream entrada=new ObjectInputStream(new FileInputStream("media.obj"));
			ArrayList<Proyecto> obj1=(ArrayList<Proyecto>)entrada.readObject();
			
			/*ObservableList<Proyecto> paj = FXCollections.observableArrayList();
		    Proyecto aux=paj.get(0);
			paj.add(new Proyecto("BElectro pls"));*/
			
			projectData = FXCollections.observableArrayList(obj1);
			for(int i=0;i<projectData.size();i++){
				projectData.get(i).ajust();
				taskData.addAll(projectData.get(i).gettask());
				
			}
			for(int i=0;i<taskData.size();i++){
				taskData.get(i).ajust();
				
			}
			
			
			
		}
		
		
		public void sereal() throws FileNotFoundException, IOException{
			//respaldoproject;
			ordenar();
			Object[] cosas=projectData.toArray();
			ArrayList<Object> listOfStrings = new ArrayList<Object>((Arrays.asList(cosas).size()));
			listOfStrings.addAll(Arrays.asList(cosas));
			
			respaldoproject=(ArrayList)(listOfStrings);

		//	respaldoproject.addAll((Collection<? extends Proyecto>) a);
			
			//ObservableList lista1= FXCollections.observableArrayList(new int[]{12, 15, 11, 4, 32});
			ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("media.obj"));
			//ObjectOutputStream oos = new ObjectOutputStream(oks);
			oos.writeObject(respaldoproject);
			oos.close();
		}		
		
		
	public void ordenar(){
		FXCollections.sort(projectData);
	}
	
	public void ordenarT(){
		FXCollections.sort(taskData);
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
	    
	    public void showTareaView() {
	        try {
	            // Load person overview.
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(Main.class.getResource("../view/TareaView.fxml"));
	            AnchorPane tv = (AnchorPane) loader.load();

	            // Set person overview into the center of root layout.
	            bv.setCenter(tv);
	            
	            // Give the controller access to the main app.
	            TareaViewController controller = loader.getController();
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
	    
	    public boolean showTasksInProject(Proyecto proyect) {
	        try {
	            // Load the fxml file and create a new stage for the popup dialog.
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(Main.class.getResource("../view/TasksInProjectView.fxml"));
	            AnchorPane page = (AnchorPane) loader.load();

	            // Create the dialog Stage.
	            Stage dialogStage = new Stage();
	            dialogStage.setTitle("Lista de Tareas");
	            dialogStage.initModality(Modality.WINDOW_MODAL);
	            dialogStage.initOwner(ps);
	            Scene scene = new Scene(page);
	            dialogStage.setScene(scene);

	            // Set the person into the controller.
	            TasksInProjectViewController controller = loader.getController();
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
	    
	    public boolean showTaskEditDialog(Tarea proyect) {
	        try {
	            // Load the fxml file and create a new stage for the popup dialog.
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(Main.class.getResource("../view/TaskEditDialog.fxml"));
	            AnchorPane page = (AnchorPane) loader.load();

	            // Create the dialog Stage.
	            Stage dialogStage = new Stage();
	            dialogStage.setTitle("Editar Tarea");
	            dialogStage.initModality(Modality.WINDOW_MODAL);
	            dialogStage.initOwner(ps);
	            Scene scene = new Scene(page);
	            dialogStage.setScene(scene);

	            // Set the person into the controller.
	            TaskEditDialogController controller = loader.getController();
	            controller.setDialogStage(dialogStage);
	            controller.setPerson(proyect);
	            controller.setMainApp(this);

	            // Show the dialog and wait until the user closes it
	            dialogStage.showAndWait();

	            return controller.isOkClicked();
	        } catch (IOException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    
	    public boolean showEditTaskButtonDialog(Tarea proyect) {
	        try {
	            // Load the fxml file and create a new stage for the popup dialog.
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(Main.class.getResource("../view/EditTaskButtonDialog.fxml"));
	            AnchorPane page = (AnchorPane) loader.load();

	            // Create the dialog Stage.
	            Stage dialogStage = new Stage();
	            dialogStage.setTitle("Editar Tarea");
	            dialogStage.initModality(Modality.WINDOW_MODAL);
	            dialogStage.initOwner(ps);
	            Scene scene = new Scene(page);
	            dialogStage.setScene(scene);

	            // Set the person into the controller.
	            EditTaskButtonController controller = loader.getController();
	            controller.setDialogStage(dialogStage);
	            controller.setPerson(proyect);
	            controller.setMainApp(this);

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
	    
	    public ObservableList<Tarea> getTaskData() {
	        return taskData;
	    }
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
