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
    double user, contx, dead, primera, razon;
    
    private Main mainApp;
    @FXML
    private void initialize() {

    }
    
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    	
    	user=mainApp.getUser()*100;
    	contx=mainApp.getContx()*100;
    	dead=mainApp.getDead()*100; 
    	primera=mainApp.getPrimera()*100; 
    	razon=mainApp.getRazon()*100;
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

    
    
    @FXML
    public void user() throws FileNotFoundException, IOException {
    	String mensaje;


    	Optional<String> response =  Dialogs.create()
        .title("Cambio de Prioridad")
        
        .masthead("Prioridad de usuario (P.U)")
        .message("Ingrese un valor entre 1-100 para determinar la ponderaci�n \n que tendr� la P.U en el algotirmo de Prioridad de los proyectos.")
       // .showChoices(lista);
        .showTextInput(""+ (int) this.user);
        
        if (response.isPresent()) {
        	if(!isNumeric(response.get())){
        		Dialogs.create()
		        .title("Error en ponderaci�n")
		        
		        .masthead("Ponderaci�n incorrecta")
		        .message("Debe ingresar un n�mero entre 1 y 100")
		       // .showChoices(lista);
		        .showError();
        		
        	}
        	else if(Integer.parseInt(response.get())<1 || Integer.parseInt(response.get())>(double)100 ){
        		 Dialogs.create()
        		        .title("Error en ponderaci�n")
        		        
        		        .masthead("Ponderaci�n incorrecta")
        		        .message("Debe ingresar un n�mero entre 1 y 100")
        		       // .showChoices(lista);
        		        .showError();
        	}
        	else{
        		user=Integer.parseInt(response.get());
        		ponderar();
        		
        	}
        
        }
        
        
        
        
        
        
   
    }
  
    
    @FXML
    public void dead() throws FileNotFoundException, IOException {
    	String mensaje;


    	Optional<String> response =  Dialogs.create()
        .title("Cambio de Prioridad")
        
        .masthead("Prioridad de Dead Line (P.D)")
        .message("Ingrese un valor entre 1-100 para determinar la ponderaci�n que\n tendr� la P.D en el algotirmo de Prioridad de los proyectos."
        		+ "\nP.D es la importancia que se le da a estar cercanos a la fecha\n de t�rmino para un proyecto")
       // .showChoices(lista);
        .showTextInput(""+ (int) this.dead);
        
        if (response.isPresent()) {
        	if(!isNumeric(response.get())){
        		Dialogs.create()
		        .title("Error en ponderaci�n")
		        
		        .masthead("Ponderaci�n incorrecta")
		        .message("Debe ingresar un n�mero entre 1 y 100")
		       // .showChoices(lista);
		        .showError();
        		
        	}
        	else if(Integer.parseInt(response.get())<1 || Integer.parseInt(response.get())>100 ){
        		 Dialogs.create()
        		        .title("Error en ponderaci�n")
        		        
        		        .masthead("Ponderaci�n incorrecta")
        		        .message("Debe ingresar un n�mero entre 1 y 100")
        		       // .showChoices(lista);
        		        .showError();
        	}
        	else{
        		dead=Integer.parseInt(response.get());
        		ponderar();
        		
        	}
        
        }
        
        
        
        
        
        
   
    }
  
    
    @FXML
    public void contx() throws FileNotFoundException, IOException {
    	String mensaje;


    	Optional<String> response =  Dialogs.create()
        .title("Cambio de Prioridad")
        
        .masthead("Prioridad de usuario (P.C)")
        .message("Ingrese un valor entre 1-100 para determinar la ponderaci�n que\n tendr� la P.C en el algotirmo de Prioridad de los proyectos."
        		+ "\nP.C es la importancia que tiene para usted ordenar los proyectos\n seg�n la prioirdad que cada contexto tiene")
       // .showChoices(lista);
        .showTextInput(""+ (int)this.contx);
        
        if (response.isPresent()) {
        	if(!isNumeric(response.get())){
        		Dialogs.create()
		        .title("Error en ponderaci�n")
		        
		        .masthead("Ponderaci�n incorrecta")
		        .message("Debe ingresar un n�mero entre 1 y 100")
		       // .showChoices(lista);
		        .showError();
        		
        	}
        	else if(Integer.parseInt(response.get())<1 || Integer.parseInt(response.get())>100 ){
        		 Dialogs.create()
        		        .title("Error en ponderaci�n")
        		        
        		        .masthead("Ponderaci�n incorrecta")
        		        .message("Debe ingresar un n�mero entre 1 y 100")
        		       // .showChoices(lista);
        		        .showError();
        	}
        	else{
        		contx=Integer.parseInt(response.get());
        		ponderar();
        		
        	}
        
        }
        
        
        
        
        
        
   
    }
    
    @FXML
    public void primera() throws FileNotFoundException, IOException {
    	String mensaje;


    	Optional<String> response =  Dialogs.create()
        .title("Cambio de Prioridad")
        
        .masthead("Prioridad de Primera tarea en terminar (P.Pt)")
        .message("Ingrese un valor entre 1-100 para determinar la ponderaci�n que\n tendr� la P.Pt en el algotirmo de Prioridad de los proyectos."
        		+ "\nP.Pt es la importancia que tiene para usted ordenar los proyectos\n seg�n la tarea m�s pr�xima en llegar a su fin.")
       // .showChoices(lista);
        .showTextInput(""+ (int)this.primera);
        
        if (response.isPresent()) {
        	if(!isNumeric(response.get())){
        		Dialogs.create()
		        .title("Error en ponderaci�n")
		        
		        .masthead("Ponderaci�n incorrecta")
		        .message("Debe ingresar un n�mero entre 1 y 100")
		       // .showChoices(lista);
		        .showError();
        		
        	}
        	else if(Integer.parseInt(response.get())<1 || Integer.parseInt(response.get())>100 ){
        		 Dialogs.create()
        		        .title("Error en ponderaci�n")
        		        
        		        .masthead("Ponderaci�n incorrecta")
        		        .message("Debe ingresar un n�mero entre 1 y 100")
        		       // .showChoices(lista);
        		        .showError();
        	}
        	else{
        		primera=Integer.parseInt(response.get());
        		ponderar();
        		
        	}
        
        }
        
        
        
        
        
        
   
    }

    @FXML
    public void razon() throws FileNotFoundException, IOException {
    	String mensaje;


    	Optional<String> response =  Dialogs.create()
        .title("Cambio de Prioridad")
        
        .masthead("Prioridad de Raz�n Tareas-Tiempo (P.R)")
        .message("Ingrese un valor entre 1-100 para determinar la ponderaci�n que tendr�\n la P.R en el algotirmo de Prioridad de los proyectos."
        		+ "\nP.C es la importancia que tiene para usted ordenar los proyectos seg�n\n la raz�n entre cantidad de tareas y tiempo disponible para hacerlas")
       // .showChoices(lista);
        .showTextInput(""+ (int)this.razon);
        
        if (response.isPresent()) {
        	if(!isNumeric(response.get())){
        		Dialogs.create()
		        .title("Error en ponderaci�n")
		        
		        .masthead("Ponderaci�n incorrecta")
		        .message("Debe ingresar un n�mero entre 1 y 100")
		       // .showChoices(lista);
		        .showError();
        		
        	}
        	else if(Integer.parseInt(response.get())<1 || Integer.parseInt(response.get())>100 ){
        		 Dialogs.create()
        		        .title("Error en ponderaci�n")
        		        
        		        .masthead("Ponderaci�n incorrecta")
        		        .message("Debe ingresar un n�mero entre 1 y 100")
        		       // .showChoices(lista);
        		        .showError();
        	}
        	else{
        		razon=Integer.parseInt(response.get());
        		ponderar();
        		
        	}
        
        }
        
        
        
        
        
        
   
    }
    
    
    
    
    private void ponderar(){
    	double suma = user + contx+ dead + primera+ razon;
    	mainApp.avisarPrioirdad(user/suma, contx/suma, dead/suma, primera/suma, razon/suma);
    }

        
        private static boolean isNumeric(String cadena){
        	try {
        		Integer.parseInt(cadena);
        		return true;
        	} catch (NumberFormatException nfe){
        		return false;
        	}
        }


}