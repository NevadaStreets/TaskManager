package view;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.controlsfx.dialog.Dialogs;

import util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import application.Main;
import application.Proyecto;
import application.Tarea;

public class TareaViewController {
	@FXML
    private TableView<Tarea> taskTable;
    @FXML
    private TableColumn<Tarea, String> firstName;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label projectLabel;
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
    @FXML
    private Label stateLabel;

    // Reference to the main application.
    private Main mainApp;
    
    public TareaViewController() {
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
        showTaskDetails(null);

        // Espera a ingresar cambios y mostrarlos cuando se modifique.
        taskTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showTaskDetails(newValue));
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        taskTable.setItems(mainApp.getTaskData());
    }
    /**
     * Llena los campos de texto para mostrar detalles de las tareas.
     */
    private void showTaskDetails(Tarea proyect) {
        if (proyect != null) {
            firstNameLabel.setText(proyect.getName());
            descriptionLabel.setText(proyect.getDescription());
            priorityLabel.setText(Integer.toString(proyect.getPriority()));
            contextLabel.setText(proyect.getContext());
            inicioLabel.setText(DateUtil.format(proyect.getInicio()));
            deadlineLabel.setText(DateUtil.format(proyect.getDeadline()));
            projectLabel.setText(proyect.getProject().getName());
            stateLabel.setText(proyect.getEstado());

            // birthdayLabel.setText(...);
        } else {
            // Person is null, remove all the text.
            firstNameLabel.setText("");
            descriptionLabel.setText("");
            priorityLabel.setText("");
            contextLabel.setText("");
            inicioLabel.setText("");
            deadlineLabel.setText("");
            projectLabel.setText("");
        }
    }
    /**
     * Se llama al presionar el boton de eliminar proyecto
     */
    @FXML
    private void handleProjectView() {
    	 mainApp.showTaskView();   
    }
    
    @FXML
    private void handleDeleteTask() {
    	Tarea task = taskTable.getSelectionModel().getSelectedItem();
        int selectedIndex = taskTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            taskTable.getItems().remove(selectedIndex);
            mainApp.getTaskData().remove(task);
            int largo = mainApp.getProyectData().size();
            for (int i=0; i < largo; i++){
            	mainApp.getProyectData().get(i).Tasks.remove(task);
            	mainApp.getProyectData().get(i).taskear();
            }
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
                .title("No hay seleccion")
                .masthead("No elegiste tarea")
                .message("Por favor selecciona una tarea de la tabla.")
                .showWarning();
        }
    }
    
    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNewTask() {
        Tarea tempTask = new Tarea();
        boolean okClicked = mainApp.showTaskEditDialog(tempTask);
        if (okClicked) {
            mainApp.getTaskData().add(tempTask);
            mainApp.ordenarT();
        }
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

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditTask() {
        Tarea selectedPerson = taskTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showEditTaskButtonDialog(selectedPerson);
            if (okClicked) {
                showTaskDetails(selectedPerson);
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
                .masthead("No elegiste tarea")
                .message("Por favor selecciona una tarea de la tabla.")
                .showWarning();
        }
    }

}
