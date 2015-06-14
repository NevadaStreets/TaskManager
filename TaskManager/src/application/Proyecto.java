package application;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.DateUtil;
import view.TaskEditDialogController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Proyecto implements Comparable<Proyecto>, Serializable {

	 private  String Namex;
	    private  String descriptionx;
	    private  int priorityx;
	    private  String contextx;
	    private  LocalDate deadlinex;
	    private  LocalDate iniciox;
	    private ArrayList<Tarea> tasksx;
	    private double pCont;
	    private double pDead;
	    private double pPropor;
	    private double pUser;
	    private double pCorto;
	    
	    
	    
    private transient StringProperty Name;
    public transient ObservableList<Tarea> Tasks;
    private transient StringProperty description;
    private transient IntegerProperty priority;
    private transient StringProperty context;
    private transient ObjectProperty<LocalDate> inicio;
    private transient ObjectProperty<LocalDate> deadline;


    public Proyecto() {
        this(null);
    }


    public Proyecto(String Name) {
        this.Name = new SimpleStringProperty(Name);
        // Some initial dummy data, just for convenient testing.
        this.description = new SimpleStringProperty("Ingrese descripcion");
        this.priority = new SimpleIntegerProperty(1);
        this.context = new SimpleStringProperty("En Pausa");
        this.inicio = new SimpleObjectProperty<LocalDate>(LocalDate.now());
        this.deadline = new SimpleObjectProperty<LocalDate>(LocalDate.now());
        this.Tasks = FXCollections.observableArrayList();
        setInicio(LocalDate.now());
    }
    
    public void avisoPrioirdad(double user,double contx,double dead,double primera,double razon){
	    pCont=contx;
	    pDead=dead;
	    pPropor=razon;
	    pUser=user;
	    pCorto=primera;
	    	
	    } 

    public double getContx(){
    	return this.pCont;
    }
    public double getDead(){
    	return this.pDead;
    }
  
    public double getRazon(){
    	return this.pPropor;
    }
    public double getUser(){
    	return this.pUser;
    }
    public double getPrimera(){
    	return this.pCorto;
    }
    
    
    @Override
    public String toString(){
    	return Name.get();
    }

    public String getName() {
        return Name.get();
    }

    public void setName(String Name) {
    	this.Namex=Name;
        this.Name.set(Name);
    }

    public StringProperty NameProperty() {
        return Name;
    }
    
    
    public String getDescription() {
        return (description.get()) ;
    	//return ""+prioridad();
    }

    public void setDescription(String street) {
    	descriptionx=street;
        this.description.set(street);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public int getPriority() {
  
       return priority.get();
    }

    public void setPriority(int postalCode) {
    	priorityx=postalCode;
        this.priority.set(postalCode);
    }
    
    public int prioridad(){
    	int finale=0;
    	if(Tasks.size()!=0){
    	if(deadline!=null){
    	long  a=- deadline.get().until(LocalDate.now(), ChronoUnit.DAYS);
    	int b= (int)a;
    	
    	int Fin_de_pro= (int) Math.exp((100-b)/14.137); // prioridad según tiempo que le queda al proyecto
    	int priorUser=priority.get(); // Entrega la prioridad del proyecto según usuario
    
    	ObservableList<Tarea> aux = FXCollections.observableArrayList();
    	aux.addAll(Tasks);
    	FXCollections.sort(aux);
    	Tarea cortita= aux.get(0) ;
    	int corto= (int) Math.exp((100+cortita.getDeadline().until(LocalDate.now(), ChronoUnit.DAYS))/14.137); //tiempo de la tarea más proxima a terminar
    
    	int n_tareas= Tasks.size();
    	int propor = (int) b / n_tareas; // Proporción de tiempo/tareas
    	int time_propor= (int) Math.exp((100-propor)/14.137); //prioridad según la relación tiempo_dispobile/cantidad_de_tareas
    	
    	int prior=0;
    	int numero=0;
    	for(Tarea t:tasksx){
    		prior+=t.getContext().getPriority();
    		numero++;
    	}
    	prior=prior/numero;
    	
    	finale=(int)(Fin_de_pro*this.pDead+ priorUser*this.pUser +corto*this.pCorto+ time_propor*this.pPropor+ prior*this.pCont);
    	}
    	}
        return finale;
    }

    public IntegerProperty priorityProperty() {
        return priority;
    }

    public String getContext() {
        return context.get();
    }

    public void setContext() {
        boolean vencida = false;
        String estado = "";
        int contador = 0;
        for(int i=0;i<Tasks.size();i++){
        	if(Tasks.get(i).getEstado().equals("Activa")){
        		estado = "Activo";
        	}
        	if(Tasks.get(i).getEstado().equals("Vencida")){
        		vencida = true;
        	}
        	if(Tasks.get(i).getEstado().equals("Completada")){
        		contador++;
        	}
        }
        if(contador == Tasks.size()){
        	estado = "Terminado";
        }
        else if(vencida==true){
        	estado = "Vencido";
        }
        else if(estado.equals("") && vencida == false){
    		estado = "En Pausa";
    	}
        
        contextx=estado;
        this.context.set(estado);
    }

    public StringProperty contextProperty() {
        return context;
    }

    public LocalDate getDeadline() {
        return deadline.get();
    }

    public void setDeadline(LocalDate birthday) {
    	deadlinex=birthday;
        this.deadline.set(birthday);
    }
    
    public void setDeadline2(){
    	LocalDate max = DateUtil.parse("01.01.1000");
		for (int i=0;i<Tasks.size(); i ++){
			if(max.compareTo(Tasks.get(i).getDeadline())<0){
				max = Tasks.get(i).getDeadline();
			}
		}
		deadlinex=max;
    	setDeadline(max);
    }

    public ObjectProperty<LocalDate> deadlineProperty() {
        return deadline;
    }
    
    
    
    public LocalDate getInicio() {
        return inicio.get();
    }

    public void setInicio(LocalDate birthday) {
    	iniciox=birthday;
        this.inicio.set(birthday);
    }
    
    public void setInicio2(){
    	LocalDate min = DateUtil.parse("01.01.9999");
		for (int i=0;i<Tasks.size(); i ++){
			if(min.compareTo(Tasks.get(i).getInicio())>0){
				min = Tasks.get(i).getInicio();
			}
		}
		iniciox=min;
    	setInicio(min);
    }

    public ObjectProperty<LocalDate> inicioProperty() {
        return inicio;
    }
    
    public ObservableList<Tarea> gettask(){
    
    	return Tasks;
    }
    
    public void taskear() {
		//respaldoproject;
		Object[] cosas=Tasks.toArray();
		ArrayList<Object> listOfStrings = new ArrayList<Object>((Arrays.asList(cosas).size()));
		listOfStrings.addAll(Arrays.asList(cosas));
		
		tasksx=(ArrayList)(listOfStrings);
	}		
    
    
    
    
    
    @Override
    public int compareTo(Proyecto o) {
       int a=this.prioridad();
       int b=o.prioridad();
       return b-a;
   }
    
    public void ajust(){
    	if(Namex!=null){
       	 //setName(Namex);
    		 this.Name = new SimpleStringProperty(Namex);
    		this.description = new SimpleStringProperty(descriptionx);
            this.priority = new SimpleIntegerProperty(priorityx);
            this.context = new SimpleStringProperty(contextx);
            this.inicio= new SimpleObjectProperty<LocalDate>(iniciox);
            this.deadline = new SimpleObjectProperty<LocalDate>(deadlinex);
            this.Tasks = FXCollections.observableArrayList();
            //tasksx=new ArrayList<Tarea>();
            llenartask();
       	 setDescription(descriptionx);
       	 setPriority(priorityx);
       	 //setContext(contextx);
       	 //setContext();
       	 setDeadline(deadlinex);
       	 
       	setInicio(iniciox);
        }
    }
    
    private void llenartask(){
    	  try {
    		  if(!tasksx.isEmpty())
    		    	Tasks=FXCollections.observableArrayList(tasksx);
	            
	        } catch (java.lang.NullPointerException e) {
	            e.printStackTrace();
	           
	        }
    	
    }
    
    
}