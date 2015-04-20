package view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.controlsfx.dialog.Dialogs;

import application.Proyecto;
import util.DateUtil;

/**
 * Dialog to edit details of a person.
 * 
 * @author Marco Jakob
 */
public class ProjectEditDialogController {

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


    private Stage dialogStage;
    private Proyecto proyect;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
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
    public void setPerson(Proyecto proyect) {
        this.proyect = proyect;

        firstNameField.setText(proyect.getName());
        descriptionField.setText(proyect.getDescription());
        priorityField.setText(Integer.toString(proyect.getPriority()));
        contextField.setText(proyect.getContext());
        deadlineField.setText(DateUtil.format(proyect.getDeadline()));
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
            proyect.setName(firstNameField.getText());
            proyect.setDescription(descriptionField.getText());
            proyect.setPriority(Integer.parseInt(priorityField.getText()));
            proyect.setContext(contextField.getText());
            proyect.setDeadline(DateUtil.parse(deadlineField.getText()));

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