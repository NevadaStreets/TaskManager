package view;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.controlsfx.dialog.Dialogs;

import util.DateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import application.Contexto;
import application.Main;
import application.Proyecto;

public class FilterEditDialogController {
	 	@FXML
	    private CheckBox projectCB;
	 	@FXML
	    private CheckBox contextCB;
	 	@FXML
	    private CheckBox stateCB;
	 	@FXML
	    private CheckBox beforeCB;
	 	@FXML
	    private CheckBox afterCB;
	 	
	 	@FXML
	    private ComboBox<Proyecto> proyectos;
	 	@FXML
	    private ComboBox<Contexto> contextos;
	 	@FXML
	    private ComboBox<String> estados;
	 	
	 	@FXML
	    private TextField preDia;
	 	@FXML
	    private TextField preMes;
	 	@FXML
	    private TextField preAno;
	 	@FXML
	    private TextField postDia;
	 	@FXML
	    private TextField postMes;
	 	@FXML
	    private TextField postAno;

	    private Stage dialogStage;
	    private boolean okClicked = false;
	    private Main mainApp;

	    @FXML
	    private void initialize() {
	    }
	    public boolean isOkClicked() {
	        return okClicked;
	    }

	    public void setDialogStage(Stage dialogStage) {
	        this.dialogStage = dialogStage;
	    }
	    
	    public void setMainApp(Main mainApp) {
	        this.mainApp = mainApp;
	        proyectos.setItems(mainApp.getProyectData());
	        contextos.setItems(mainApp.getContextData());
	        ObservableList<String> jiji =  FXCollections.observableArrayList();
	        jiji.add("Activa");
	        jiji.add("En Pausa");
	        jiji.add("Completada");
	        jiji.add("Vencida");
	        estados.setItems(jiji);
	        
	        Proyecto P = mainApp.getFilterData().getProject();
	        Contexto C = mainApp.getFilterData().getContext();
	        String S = mainApp.getFilterData().getEstado();
	        LocalDate B = mainApp.getFilterData().getBefore();
	        LocalDate A = mainApp.getFilterData().getAfter();
	        if(P!=null){
	        	projectCB.setSelected(true);
	        	proyectos.setValue(P);
	        }
	        if(C!=null){
	        	contextCB.setSelected(true);
	        	contextos.setValue(C);
	        }
	        if(S!=null){
	        	stateCB.setSelected(true);
	        	estados.setValue(S);
	        }
	        if(B!=null){
	        	beforeCB.setSelected(true);
	        	Integer dia = B.getDayOfMonth();
	        	Integer mes = B.getMonthValue();
	        	Integer ano = B.getYear();
	        	preDia.setText(dia.toString());
	        	preMes.setText(mes.toString());
	        	preAno.setText(ano.toString());
	        }
	        if(A!=null){
	        	afterCB.setSelected(true);
	        	Integer dia = A.getDayOfMonth();
	        	Integer mes = A.getMonthValue();
	        	Integer ano = A.getYear();
	        	postDia.setText(dia.toString());
	        	postMes.setText(mes.toString());
	        	postAno.setText(ano.toString());
	        }
	    }
	    
	    @FXML
	    private void handleOk() {
	    	if (projectCB.isSelected()){
	    		if(projectValid()){
	    			mainApp.getFilterData().setProject(proyectos.getSelectionModel().getSelectedItem());
	    		}
	    	}
	    	else{
	    		mainApp.getFilterData().setProject(null);
	    	}
	    	if(contextCB.isSelected()){
	    		if(contextValid()){
	    			mainApp.getFilterData().setContext(contextos.getSelectionModel().getSelectedItem());
	    		}
	    	}
	    	else{
	    		mainApp.getFilterData().setContext(null);
	    	}
	    	if(stateCB.isSelected()){
	    		if(stateValid()){
	    			mainApp.getFilterData().setEstado(estados.getSelectionModel().getSelectedItem());
	    		}
	    	}
	    	else{
	    		mainApp.getFilterData().setEstado(null);
	    	}
	    	if(beforeCB.isSelected()){
	    		if(beforeValid()){
	    			String s = preDia.getText() + "." + preMes.getText() + "." + preAno.getText() + " 00:00:00";
	    			LocalDateTime tiempo = DateUtil.parse(s);
	    			mainApp.getFilterData().setBefore(tiempo.toLocalDate());
	    		}
	    	}
	    	else{
	    		mainApp.getFilterData().setBefore(null);
	    	}
	    	if(afterCB.isSelected()){
	    		if(afterValid()){
	    			String s = postDia.getText() + "." + postMes.getText() + "." + postAno.getText() + " 00:00:00";
	    			LocalDateTime tiempo = DateUtil.parse(s);
	    			mainApp.getFilterData().setAfter(tiempo.toLocalDate());
	    		}
	    	}
	    	else{
	    		mainApp.getFilterData().setAfter(null);
	    	}
	    	okClicked = true;
	    	dialogStage.close();
	    }
	     
	    private boolean beforeValid() {
	    	String errorMessage = "";
	        
	        if (preDia.getText().length()==1){
	        	preDia.setText("0"+preDia.getText());
	        }
	        if (preMes.getText().length()==1){
	        	preMes.setText("0"+preMes.getText());
	        }
	        if (preAno.getText().length()==1){
	        	preAno.setText("0"+preAno.getText());
	        }
	        String Before = preDia.getText()+"."+preMes.getText()+"."+preAno.getText() + " 00:00:00" ;
	        if (Before == null || Before.length() == 0) {
	            errorMessage += "fecha no válida!\n";
	        } else {
	            if (!DateUtil.validDate(Before)) {
	                errorMessage += "fecha no válida. Usa el formato dd MM yyyy!\n";
	            }
	        }
	        if (errorMessage.length() == 0) {
	            return true;
	        }
	        else {
	            // Show the error message.
	            Dialogs.create()
	                .title("Campos invalidos")
	                .masthead("Por favor cambiar los campos invalidos")
	                .message(errorMessage)
	                .showError();
	            return false;
	        }
	    }
	    
	    private boolean afterValid() {
	    	String errorMessage = "";
	        
	    	if (postDia.getText().length()==1){
	        	postDia.setText("0"+postDia.getText());
	        }
	        if (postMes.getText().length()==1){
	        	postMes.setText("0"+postMes.getText());
	        }
	        if (postAno.getText().length()==1){
	        	postAno.setText("0"+postAno.getText());
	        }
	        String After = postDia.getText()+"."+postMes.getText()+"."+postAno.getText() + " 00:00:00";

	        if (After == null || After.length() == 0) {
	            errorMessage += "fecha no válida!\n";
	        } else {
	            if (!DateUtil.validDate(After)) {
	                errorMessage += "fecha no válida. Usa el formato dd MM yyyy!\n";
	            }
	        }
	        if (errorMessage.length() == 0) {
	            return true;
	        }
	        else {
	            // Show the error message.
	            Dialogs.create()
	                .title("Campos invalidos")
	                .masthead("Por favor cambiar los campos invalidos")
	                .message(errorMessage)
	                .showError();
	            return false;
	        }
	    }
	    
	    private boolean projectValid() {
	    	String errorMessage = "";
	        
	    	if (proyectos.getValue() == null) {
	            errorMessage += "Proyecto no seleccionado!\n"; 
	        }
	        if (errorMessage.length() == 0) {
	            return true;
	        }
	        else {
	            // Show the error message.
	            Dialogs.create()
	                .title("Campos invalidos")
	                .masthead("Por favor cambiar los campos invalidos")
	                .message(errorMessage)
	                .showError();
	            return false;
	        }
	    }
	    private boolean contextValid() {
	    	String errorMessage = "";
	        
	    	if (contextos.getValue() == null) {
	            errorMessage += "Contexto no seleccionado!\n"; 
	        }
	        if (errorMessage.length() == 0) {
	            return true;
	        }
	        else {
	            // Show the error message.
	            Dialogs.create()
	                .title("Campos invalidos")
	                .masthead("Por favor cambiar los campos invalidos")
	                .message(errorMessage)
	                .showError();
	            return false;
	        }
	    }
	    private boolean stateValid() {
	    	String errorMessage = "";
	        
	    	if (estados.getValue() == null) {
	            errorMessage += "Estado no seleccionado!\n"; 
	        }
	        if (errorMessage.length() == 0) {
	            return true;
	        }
	        else {
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
