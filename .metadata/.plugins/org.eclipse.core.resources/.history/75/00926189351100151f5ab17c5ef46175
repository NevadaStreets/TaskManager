package view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.controlsfx.dialog.Dialogs;

import application.Main;
import application.Proyecto;
import util.DateUtil;


public class BigViewController {

    @FXML
    private MenuItem Email;
    
    private String Emailx;
    private Main mainApp;
    @FXML
    private void initialize() {
    }
    
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public String Emailget(){
    	return Emailx;
    }
    
    @FXML
    public void isOkClicked() throws FileNotFoundException, IOException {
    	String mensaje;
    	Optional<String> response =  Dialogs.create()
        .title("Alerta de tareas")
        .masthead("Tareas de suma urgencia")
        .message("Escriba su email")
        .showTextInput(""+ mainApp.getEmail());
        
        if (response.isPresent()) {
        	if(response.get()!=null)
            Emailx = response.get();
        }
   
        mainApp.setEmail(Emailx);
    }


}