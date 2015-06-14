package view;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.controlsfx.dialog.Dialogs;

import util.DateUtil;
import application.Contexto;
import application.Main;
import application.Proyecto;
import application.Tarea;

public class TaskEditDialogController {
	@FXML
    private TextField firstNameField;
    @FXML
    private TextField descriptionField;
  //  @FXML
  //  private TextField priorityField;
    @FXML
    private ComboBox<Contexto> contextField;
    @FXML
    private TextField startDayField;
    @FXML
    private TextField startMonthField;
    @FXML
    private TextField startYearField;
    @FXML
    private TextField deadlineDayField;
    @FXML
    private TextField deadlineMonthField;
    @FXML
    private TextField deadlineYearField;
    @FXML
    private TextField startHourField;
    @FXML
    private TextField startMinuteField;
    @FXML
    private TextField startSecondField;
    @FXML
    private TextField deadlineHourField;
    @FXML
    private TextField deadlineMinuteField;
    @FXML
    private TextField deadlineSecondField;
    @FXML
    private ComboBox<Proyecto> projectBox;
    @FXML
    private ComboBox<String> stateBox;
    
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
        contextField.setItems(mainApp.getContextData());
        ObservableList <String> states = FXCollections.observableArrayList();
        states.add("Activa");
        states.add("En Pausa");
        states.add("Completada");
        stateBox.setItems(states);
        //ObservableList <String> contexts = FXCollections.observableArrayList();
        //contexts.add("En la oficina");
        //contexts.add("En el auto");
        //contexts.add("Al teléfono");
        //contexts.add("En casa");
        //contextField.setItems(contexts);
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
    //  priorityField.setText(Integer.toString(task.getPriority()));
        contextField.setValue(task.getContext());
        String s = DateUtil.format(task.getInicio());
        String d = DateUtil.format(task.getDeadline());
        projectBox.setValue(task.getProject());
        stateBox.setValue(task.getEstado());
        startDayField.setText(s.substring(0, s.indexOf('.')));
        startMonthField.setText(s.substring(s.indexOf('.')+1, s.lastIndexOf('.')));
        startYearField.setText(s.substring(s.lastIndexOf('.')+1,s.indexOf(" ")));
        startHourField.setText(s.substring(s.indexOf(" ")+1,s.indexOf(":")));
        startMinuteField.setText(s.substring(s.indexOf(":")+1,s.lastIndexOf(":")));
        startSecondField.setText(s.substring(s.lastIndexOf(":")+1,s.length()));
        deadlineDayField.setText(d.substring(0, d.indexOf('.')));
        deadlineMonthField.setText(d.substring(d.indexOf('.')+1, d.lastIndexOf('.')));
        deadlineYearField.setText(d.substring(d.lastIndexOf('.')+1,d.indexOf(" ")));
        deadlineHourField.setText(d.substring(d.indexOf(" ")+1,d.indexOf(":")));
        deadlineMinuteField.setText(d.substring(d.indexOf(":")+1,d.lastIndexOf(":")));
        deadlineSecondField.setText(d.substring(d.lastIndexOf(":")+1,d.length()));
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
      //      tarea.setPriority(Integer.parseInt(priorityField.getText()));
            tarea.setContext(contextField.getValue());
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
    		mainApp.getProyectData().get(indice).taskear();
    		
    		Contexto context = contextField.getValue();
    		int indic = mainApp.getContextData().indexOf(context);
            tarea.setContext(mainApp.getContextData().get(indic));
  
    		//mainApp.getContextData().get(indic).taskear();
    		/*
            int hora_i = Integer.parseInt(startHourField.getText());
            int minuto_i = Integer.parseInt(startMinuteField.getText());
            int segundo_i = Integer.parseInt(startSecondField.getText());
            int hora_f = Integer.parseInt(deadlineHourField.getText());
            int minuto_f = Integer.parseInt(deadlineMinuteField.getText());
            int segundo_f = Integer.parseInt(deadlineSecondField.getText());
            
            tarea.setDeadline(DateUtil.parse(deadlineDayField.getText()+"."+deadlineMonthField.getText()+"."+deadlineYearField.getText()).atTime(hora_f, minuto_f, segundo_f));
            tarea.setInicio(DateUtil.parse(startDayField.getText()+"."+startMonthField.getText()+"."+startYearField.getText()).atTime(hora_i, minuto_i, segundo_i));
            */
            tarea.setDeadline(DateUtil.parse(deadlineDayField.getText()+"."+deadlineMonthField.getText()+"."+deadlineYearField.getText()+" "+deadlineHourField.getText()+":"+deadlineMinuteField.getText()+":"+deadlineSecondField.getText()));
            tarea.setInicio(DateUtil.parse(startDayField.getText()+"."+startMonthField.getText()+"."+startYearField.getText()+" "+startHourField.getText()+":"+startMinuteField.getText()+":"+startSecondField.getText()));
            tarea.setEstado(stateBox.getValue());

    		
            //mainApp.getProyectData();
            //mainApp.getContextData().get(indic).Tasks.add(tarea);
            okClicked = true;
    		try {
				mainApp.sereal();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
        
        if (startDayField.getText().length()==1){
        	startDayField.setText("0"+startDayField.getText());
        }
        if (startMonthField.getText().length()==1){
        	startMonthField.setText("0"+startMonthField.getText());
        }
        if (startHourField.getText().length()==1){
        	startHourField.setText("0"+startHourField.getText());
        }
        if (startMinuteField.getText().length()==1){
        	startMinuteField.setText("0"+startMinuteField.getText());
        }
        if (startSecondField.getText().length()==1){
        	startSecondField.setText("0"+startSecondField.getText());
        }
        if (deadlineDayField.getText().length()==1){
        	deadlineDayField.setText("0"+deadlineDayField.getText());
        }
        if (deadlineMonthField.getText().length()==1){
        	deadlineMonthField.setText("0"+deadlineMonthField.getText());
        }
        if (deadlineHourField.getText().length()==1){
        	deadlineHourField.setText("0"+deadlineHourField.getText());
        }
        if (deadlineMinuteField.getText().length()==1){
        	deadlineMinuteField.setText("0"+deadlineMinuteField.getText());
        }
        if (deadlineSecondField.getText().length()==1){
        	deadlineSecondField.setText("0"+deadlineSecondField.getText());
        }
        String Start = startDayField.getText()+"."+startMonthField.getText()+"."+startYearField.getText() + " " + startHourField.getText()+":"+startMinuteField.getText()+":"+startSecondField.getText();
        String Deadline = deadlineDayField.getText()+"."+deadlineMonthField.getText()+"."+deadlineYearField.getText()+ " " + deadlineHourField.getText()+":"+deadlineMinuteField.getText()+":"+deadlineSecondField.getText();

        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "Nombre no valido!\n"; 
        }

        if (descriptionField.getText() == null || descriptionField.getText().length() == 0) {
            errorMessage += "Descripcion no valida!\n"; 
        }

        /*if (priorityField.getText() == null || priorityField.getText().length() == 0) {
            errorMessage += "Prioridad no valida!\n"; 
        } else {
            
            try {
                Integer.parseInt(priorityField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Prioridad no valida (debe ser un entero)!\n"; 
            }
        }*/

        if (contextField.getValue() == null || contextField.getValue().getContext() == "Ingrese contexto" ) {
            errorMessage += "Contexto no valido!\n"; 
        }

        //validar fecha de inicio
        if (Start == null || Start.length() == 0) {
            errorMessage += "fecha inicio no valida!\n";
        } else {
            if (!DateUtil.validDate(Start)) {
                errorMessage += "Fecha inicio no valida. Usa el formato dd MM yyyy HH mm ss!\n";
            }
        }

      //validar fecha de fin
        if (Deadline == null || Deadline.length() == 0) {
            errorMessage += "deadline no valido!\n";
        } else {
            if (!DateUtil.validDate(Deadline)) {
                errorMessage += "Deadline no valido. Usa el formato dd MM yyyy HH mm ss!\n";
            }
            //Compara fechas solo si estas estan bien creadas
            if (DateUtil.validDate(Deadline) && DateUtil.validDate(Start)){
            	if (DateUtil.parse(Deadline).compareTo(DateUtil.parse(Start)) < 0) {
                    errorMessage += "fecha de inicio debe venir antes del deadline!\n";
                }
            	/*//Plazo de tareas debe estar entre los plazos del proyecto
                if (DateUtil.parse(Deadline).compareTo(projectBox.getSelectionModel().getSelectedItem().getDeadline()) > 0) {
                    errorMessage += "deadline de tarea debe estar entre los plazos del proyecto!\n";
                }
                
                if (DateUtil.parse(Start).compareTo(projectBox.getSelectionModel().getSelectedItem().getInicio()) < 0) {
                    errorMessage += "fecha de inicio de tarea debe estar entre los plazos del proyecto!\n";
                }*/

            }
        }
                
        
        if (projectBox.getValue() == null) {
            errorMessage += "Proyecto no seleccionado!\n"; 
        }
        
        if (stateBox.getValue() == null) {
            errorMessage += "Estado no seleccionado!\n"; 
        }


        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Dialogs.create()
                .title("Campos invalidos")
                .masthead("Por favor cambiar los campos invalidos")
                .message(errorMessage)
                .showError();
            return false;
        }
    }

}
