package view;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.controlsfx.dialog.Dialogs;

import util.DateUtil;
import application.Main;
import application.Proyecto;
import application.Tarea;

public class TaskEditDialogController {
	@FXML
    private TextField firstNameField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField priorityField;
    @FXML
    private TextField contextField;
    @FXML
    private TextField deadlineField;
    @FXML
    private ComboBox<Proyecto> projectBox;
    
    private Main mainApp;


    private Stage dialogStage;
    private Tarea tarea;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }
    
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;

        //Mostramos una lista con los proyectos disponibles
        projectBox.setItems(mainApp.getProyectData());
    }

    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the person to be edited in the dialog.
     * 
     * @param person
     */
    public void setPerson(Tarea task) {
        this.tarea = task;

        firstNameField.setText(task.getName());
        descriptionField.setText(task.getDescription());
        priorityField.setText(Integer.toString(task.getPriority()));
        contextField.setText(task.getContext());
        deadlineField.setText(DateUtil.format(task.getDeadline()));
        deadlineField.setPromptText("dd.mm.yyyy");
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
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            tarea.setName(firstNameField.getText());
            tarea.setDescription(descriptionField.getText());
            tarea.setPriority(Integer.parseInt(priorityField.getText()));
            tarea.setContext(contextField.getText());
            tarea.setDeadline(DateUtil.parse(deadlineField.getText()));
            if (tarea.getProject()!=null){
            	int largo = mainApp.getProyectData().size();
                for (int i=0; i < largo; i++){
                	mainApp.getProyectData().get(i).Tasks.remove(tarea);
                }
            }

            Proyecto project = projectBox.getValue();
            int indice = mainApp.getProyectData().indexOf(project);
            tarea.setProject(mainApp.getProyectData().get(indice));
    		mainApp.getProyectData().get(indice).Tasks.add(tarea);
            //mainApp.getProyectData();
            okClicked = true;
            dialogStage.close();
        }
    }

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
    private boolean isInputValid() {
        String errorMessage = "";

        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "Nombre no valido!\n"; 
        }

        if (descriptionField.getText() == null || descriptionField.getText().length() == 0) {
            errorMessage += "Descripcion no valida!\n"; 
        }

        if (priorityField.getText() == null || priorityField.getText().length() == 0) {
            errorMessage += "Prioridad no valida!\n"; 
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(priorityField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Prioridad no valida (debe ser un entero)!\n"; 
            }
        }

        if (contextField.getText() == null || contextField.getText().length() == 0) {
            errorMessage += "Contexto no valido!\n"; 
        }

        if (deadlineField.getText() == null || deadlineField.getText().length() == 0) {
            errorMessage += "deadline no valido!\n";
        } else {
            if (!DateUtil.validDate(deadlineField.getText())) {
                errorMessage += "Deadline no valido. Usa el formato dd.mm.yyyy!\n";
            }
        }
        
        if (projectBox.getValue() == null) {
            errorMessage += "Proyecto no seleccionado!\n"; 
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Dialogs.create()
                .title("Campos invalidos")
                .masthead("Corrige los campos invalidos pls")
                .message(errorMessage)
                .showError();
            return false;
        }
    }

}
