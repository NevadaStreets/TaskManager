package view;

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
	    
	    private Stage dialogStage;
	    private boolean okClicked = false;
	    
	    // Referencia al proyecto seleccionado.
	    private Proyecto project;
	
	public TasksInProjectViewController(){
		
	}
	
	@FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        Name.setCellValueFactory(cellData -> cellData.getValue().NameProperty());
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

	    /**
	     * Validates the user input in the text fields.
	     * 
	     * @return true if the input is valid
	     */


}
