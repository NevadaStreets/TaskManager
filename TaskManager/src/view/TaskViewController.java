package view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

import org.controlsfx.dialog.Dialogs;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import application.Main;
import application.Proyecto;
import application.Tarea;
import util.DateUtil;

public class TaskViewController implements Serializable {
    @FXML
    private TableView<Proyecto> proyectTable;
    @FXML
    private TableColumn<Proyecto, String> firstName;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label descriptionLabel;
    @FXML
    private Label priorityLabel;
    @FXML
    private Label contextLabel;
    @FXML
    private Label inicioLabel;
    @FXML
    private Label deadlineLabel;

    // Reference to the main application.
    private Main mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public TaskViewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        firstName.setCellValueFactory(cellData -> cellData.getValue().NameProperty());
        
     // borrar.
        showProjectDetails(null);

        // Espera a ingresar cambios y mostrarlos cuando se modifique.
        proyectTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showProjectDetails(newValue));
    }
    


    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        proyectTable.setItems(mainApp.getProyectData());
    }
    /**
     * Llena los campos de texto para mostrar detalles del proyecto.
     *
     */
    private void showProjectDetails(Proyecto proyect) {
        if (proyect != null) {
            // Fill the labels with info from the person object.
            firstNameLabel.setText(proyect.getName());
            descriptionLabel.setText(proyect.getDescription());
            priorityLabel.setText(Integer.toString(proyect.getPriority()));
            contextLabel.setText(proyect.getContext());
            inicioLabel.setText(DateUtil.format(proyect.getInicio()));
            deadlineLabel.setText(DateUtil.format(proyect.getDeadline()));

            // birthdayLabel.setText(...);
        } else {
            // Person is null, remove all the text.
            firstNameLabel.setText("");
            descriptionLabel.setText("");
            priorityLabel.setText("");
            contextLabel.setText("");
            inicioLabel.setText("");
            deadlineLabel.setText("");
        }
    }
    /**
     * Se llama al presionar el boton de eliminar proyecto
     */
    
    @FXML
    private void handleTaskView() {
    	
        mainApp.showTareaView();   
    }
    
    
    @FXML
    private void handleDeleteProject() {
        int selectedIndex = proyectTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
        	Proyecto p = proyectTable.getSelectionModel().getSelectedItem();
        	while(p.Tasks.isEmpty() == false){
        		mainApp.getTaskData().remove(p.Tasks.get(0));
        		p.Tasks.remove(0);
        	}
        	mainApp.getProyectData().get(selectedIndex).Tasks.clear();
            proyectTable.getItems().remove(selectedIndex);
            try {
				mainApp.sereal();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        } else {
            // Nothing selected.
            Dialogs.create()
                .title("No Selection")
                .masthead("No hay proyecto seleccionado")
                .message("Selecciona un proyecto de la lista pls")
                .showWarning();
        }
    }
    
    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNewProject() {
        Proyecto tempProyect = new Proyecto();
        boolean okClicked = mainApp.showProjectEditDialog(tempProyect);
        if (okClicked) {
            mainApp.getProyectData().add(tempProyect);
            try {
				mainApp.sereal();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
    
    //Toma como referencia el proyecto clickeado en la ventana de proyectos
    @FXML
    private void handleTasksInProject() {
    	int selectedIndex = proyectTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Proyecto tempProyect = proyectTable.getSelectionModel().getSelectedItem();
            boolean okClicked = mainApp.showTasksInProject(tempProyect);
        } else {
            // Nothing selected.
            Dialogs.create()
                .title("No Selection")
                .masthead("No hay proyecto seleccionado")
                .message("Selecciona un proyecto de la lista pls")
                .showWarning();
        }

    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditProject() {
        Proyecto selectedPerson = proyectTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showProjectEditDialog(selectedPerson);
            if (okClicked) {
                showProjectDetails(selectedPerson);
                try {
    				mainApp.sereal();
    			} catch (FileNotFoundException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
            }

        } else {
            // Nothing selected.
            Dialogs.create()
                .title("No hay seleccion")
                .masthead("No elegiste proyecto")
                .message("Selecciona un proyecto de la tabla pls.")
                .showWarning();
        }
    }

}