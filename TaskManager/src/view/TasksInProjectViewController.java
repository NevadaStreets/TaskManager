package view;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.controlsfx.dialog.Dialogs;

import util.DateUtil;
import application.Main;
import application.Proyecto;
import application.Tarea;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class TasksInProjectViewController {
	
	 	@FXML
	    private TableView<Tarea> taskTable;
	    @FXML
	    private TableColumn<Tarea, String> Name;

	    @FXML
	    private Label projectLabel;
	    
	    @FXML
	    private Label stateLabel;
	    
	    @FXML
	    private Label startLabel;
	    
	    @FXML
	    private Label deadlineLabel;
	    
	    private Stage dialogStage;
	    private boolean okClicked = false;
	    
	    // Referencia al proyecto seleccionado.
	    private Proyecto project;
	
	public TasksInProjectViewController(){
		
	}
	
	@FXML
    private void initialize() {
        Name.setCellValueFactory(cellData -> cellData.getValue().NameProperty());
        taskTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showTaskDetails(newValue));
    }
	
	 public void setDialogStage(Stage dialogStage) {
	        this.dialogStage = dialogStage;
	    }

	    /**
	     * Sets the person to be edited in the dialog.
	     * 
	     * @param person
	     */
	    public void setPerson(Proyecto proyect) {
	        this.project = proyect;
	        projectLabel.setText(proyect.getName());
	        taskTable.setItems(project.Tasks);
	    }
	    
	    private void showTaskDetails(Tarea task) {
	        if (task != null) {
	            stateLabel.setText(task.getEstado());
	            startLabel.setText(DateUtil.format(task.getInicio()));
	            deadlineLabel.setText(DateUtil.format(task.getDeadline()));

	            // birthdayLabel.setText(...);
	        } else {
	            // Person is null, remove all the text.
	            stateLabel.setText("");
	            startLabel.setText("");
	            deadlineLabel.setText("");
	        }
	    }

	    /**
	     * Returns true if the user clicked OK, false otherwise.
	     * 
	     * @return
	     */
	    public boolean isOkClicked() {
	    	
	        return okClicked;
	    }

	    /**
	     * Called when the user clicks ok.
	     */

	    /**
	     * Called when the user clicks cancel.
	     */
	    @FXML
	    private void handleCancel() {
	        dialogStage.close();
	    }
	    
	    @FXML
	    private void handleDayUp() {
	    	int selectedIndex = taskTable.getSelectionModel().getSelectedIndex();
	        if (selectedIndex >= 0) {
	            Tarea tempTask = taskTable.getSelectionModel().getSelectedItem();
	            if (tempTask.getEstado().equals("Completada")){
	            	Dialogs.create()
	                .title("Tarea finalizada")
	                .masthead("Tarea en estado Completada escogida")
	                .message("Operación inválida para esta tarea")
	                .showWarning();
	            }
	            else{
		            tempTask.setDeadline(tempTask.getDeadline().plusDays(1));
		            showTaskDetails(tempTask);
	            }

	    		/*try {
					mainApp.sereal();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
	        } else {
	            // Nothing selected.
	            Dialogs.create()
	                .title("No hay seleccion")
	                .masthead("No elegiste tarea")
	                .message("Por favor selecciona una tarea de la lista.")
	                .showWarning();
	        }
	    }
	    
	    @FXML
	    private void handleWeekUp() {
	    	int selectedIndex = taskTable.getSelectionModel().getSelectedIndex();
	        if (selectedIndex >= 0) {
	            Tarea tempTask = taskTable.getSelectionModel().getSelectedItem();
	            if (tempTask.getEstado().equals("Completada")){
	            	Dialogs.create()
	                .title("Tarea finalizada")
	                .masthead("Tarea en estado Completada escogida")
	                .message("Operación inválida para esta tarea")
	                .showWarning();
	            }
	            else{
	            	tempTask.setDeadline(tempTask.getDeadline().plusWeeks(1));
		            showTaskDetails(tempTask);
	            }
	    		/*try {
					mainApp.sereal();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
	        } else {
	            // Nothing selected.
	            Dialogs.create()
	                .title("No hay seleccion")
	                .masthead("No elegiste tarea")
	                .message("Por favor selecciona una tarea de la lista.")
	                .showWarning();
	        }
	    }
	    
	    
	    @FXML
	    private void handleDayDown() {
	    	int selectedIndex = taskTable.getSelectionModel().getSelectedIndex();
	        if (selectedIndex >= 0) {
	            Tarea tempTask = taskTable.getSelectionModel().getSelectedItem();
	            if (tempTask.getEstado().equals("Completada")){
	            	Dialogs.create()
	                .title("Tarea finalizada")
	                .masthead("Tarea en estado Completada escogida")
	                .message("Operación inválida para esta tarea")
	                .showWarning();
	            }
	            
	            else if (tempTask.getInicio().compareTo(tempTask.getDeadline())<0){
	            	tempTask.setDeadline(tempTask.getDeadline().minusDays(1));
		            showTaskDetails(tempTask);
		    		/*try {
						mainApp.sereal();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
	            }
	            else{
	            	Dialogs.create()
	                .title("Fechas incorrectas")
	                .masthead("Deadline quedaría antes que Inicio")
	                .message("Operación inválida para esta tarea")
	                .showWarning();
	            }
	            
	        } else {
	            // Nothing selected.
	            Dialogs.create()
	                .title("No hay seleccion")
	                .masthead("No elegiste tarea")
	                .message("Por favor selecciona una tarea de la lista.")
	                .showWarning();
	        }
	    }
	    
	    
	    @FXML
	    private void handleWeekDown() {
	    	int selectedIndex = taskTable.getSelectionModel().getSelectedIndex();
	        if (selectedIndex >= 0) {
	            Tarea tempTask = taskTable.getSelectionModel().getSelectedItem();
	            if (tempTask.getEstado().equals("Completada")){
	            	Dialogs.create()
	                .title("Tarea finalizada")
	                .masthead("Tarea en estado Completada escogida")
	                .message("Operación inválida para esta tarea")
	                .showWarning();
	            }
	            else if (tempTask.getInicio().compareTo(tempTask.getDeadline())<-6){
	            	tempTask.setDeadline(tempTask.getDeadline().minusWeeks(1));
		            showTaskDetails(tempTask);
		    		/*try {
						mainApp.sereal();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
	            }
	            else{
	            	Dialogs.create()
	                .title("Fechas incorrectas")
	                .masthead("Deadline quedaría antes que Inicio")
	                .message("Operación inválida para esta tarea")
	                .showWarning();
	            }
	            
	        } else {
	            // Nothing selected.
	            Dialogs.create()
	                .title("No hay seleccion")
	                .masthead("No elegiste tarea")
	                .message("Por favor selecciona una tarea de la lista.")
	                .showWarning();
	        }
	    }

	    /**
	     * Validates the user input in the text fields.
	     * 
	     * @return true if the input is valid
	     */


}
