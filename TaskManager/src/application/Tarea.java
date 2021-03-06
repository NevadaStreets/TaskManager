package application;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import util.DateUtil;

public class Tarea implements Comparable<Tarea> , Serializable {
	
	private  String Namex;
	 private  String descriptionx;
	 private  int priorityx;
	 private Contexto context;
	 private  LocalDateTime iniciox;
	 private  LocalDateTime deadlinex;
	 
	 private String estado;
	
	
	private transient StringProperty Name;
	 private transient StringProperty description;
	 private transient IntegerProperty priority;
	 //private transient StringProperty context;
	 private transient ObjectProperty<LocalDateTime> inicio;
	 private transient ObjectProperty<LocalDateTime> deadline;
	 private Proyecto project;
	 
	 public Tarea() {
	        this(null, null);
	    }

	    /**
	     * Constructor with some initial data.
	     * 
	     * @param firstName
	     * @param lastName
	     */
	    public Tarea(String Name, Proyecto P) {
	        this.Name = new SimpleStringProperty(Name);
	        
	        // Some initial dummy data, just for convenient testing.
	        this.description = new SimpleStringProperty("Ingrese descripción");
	        this.priority = new SimpleIntegerProperty(1);
	        //this.context = new SimpleStringProperty("Ingrese contexto");
	        this.inicio = new SimpleObjectProperty<LocalDateTime>(LocalDateTime.now());
	        this.deadline = new SimpleObjectProperty<LocalDateTime>(LocalDateTime.now().plusDays(1));
	        this.project = P;
	        this.estado = "Activa";
	        
	        Namex=this.Name.get();
	        descriptionx= this.description.get() ;
	        priorityx= this.priority.get() ;
	        //context=this.context.get();
	        context = new Contexto("Ingrese contexto",0);
	        iniciox=this.inicio.get() ;
	        deadlinex=this.deadline.get();
	        
	    }
	    
	    public Proyecto getProject() {
	        return project;
	    }
	    
	    //REVISAR ESTO 
	    public void setProject(Proyecto p) {
	        project = p;
	    }
	    
	    public String getEstado(){
	    	if (LocalDateTime.now().compareTo(deadlinex)>0){
	    		estado = "Vencida";
	    	}
	    	return estado;
	    }
	    
	    public void setEstado(String state){
	    	estado =  state;
	    	if (LocalDateTime.now().compareTo(deadlinex)>0){
	    		estado = "Vencida";
	    	}
	    	project.setContext();
	    }
	    
	    public String getName() {
	        return Name.get();
	    }

	    public void setName(String Name) {
	    	Namex=Name;
	        this.Name.set(Name);
	    }

	    public StringProperty NameProperty() {
	        return Name;
	    }

	    public String getDescription() {
	        return description.get(); //amska
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

	    public IntegerProperty priorityProperty() {
	        return priority;
	    }

	    public Contexto getContext() {
	        return context;
	    }

	    public void setContext(Contexto city) {
	    	context=city;
	        //this.context.set(city);
	    }

	    //public StringProperty contextProperty() {
	      //  return context;
	    //}

	    public LocalDateTime getDeadline() {
	        //return deadline.get();
	    	return deadlinex;
	    }

	    public void setDeadline(LocalDateTime birthday) {
	    	deadlinex=birthday;
    		this.deadline.set(birthday);
	    	/*if (project.getDeadline().compareTo(deadlinex)<0){
	        	project.setDeadline(deadlinex);
	        }
	    	else if (project.getDeadline().compareTo(birthday)>0){
	    		LocalDate max = DateUtil.parse("01.01.2000");
	    		for (int i=0;i<project.gettask().size(); i ++){
	    			if(max.compareTo(project.gettask().get(i).getDeadline())<0){
	    				max = project.gettask().get(i).getDeadline();
	    			}
	    		}
	        	project.setDeadline(max);
	        }*/
    		
    		project.setDeadline2();
	    }

	    public ObjectProperty<LocalDateTime> deadlineProperty() {
	        return deadline;
	    }
	    
	    public LocalDateTime getInicio() {
	        //return inicio.get();
	        return iniciox;
	    }

	    public void setInicio(LocalDateTime birthday) {
	    	iniciox=birthday;
	        //if (project.getInicio().compareTo(iniciox)>0){
	        	//project.setInicio(iniciox);
	        //}
	        this.inicio.set(birthday);
	        project.setInicio2();
	    }

	    public ObjectProperty<LocalDateTime> inicioProperty() {
	        return inicio;
	    }

	    @Override
	    public int compareTo(Tarea o) {

	        	long  c=- deadline.get().until(LocalDateTime.now(), ChronoUnit.DAYS);
	        	int d= (int)c;
	        	int Fin_de_proa= (int) Math.exp((100-d)/14.137);
	        	
	       int a=Fin_de_proa;
	       
       	long  e=- o.getDeadline().until(LocalDateTime.now(), ChronoUnit.DAYS);
       	int f= (int)e;
       	int Fin_de_prob= (int) Math.exp((100-f)/14.137);
	       
	       int b=Fin_de_prob;
	       return (o.context.getPriority()*3+b*7)- (this.context.getPriority()*3+a*7);
	    //return o.context.getPriority()- this.context.getPriority();
	   }

	    public void ajust(){
	    	if(Namex!=null){
	       	 //setName(Namex);
	    		 this.Name = new SimpleStringProperty(Namex);
	    		this.description = new SimpleStringProperty(descriptionx);
	            this.priority = new SimpleIntegerProperty(priorityx);
	            //this.context = new SimpleStringProperty("contextx");
	            this.inicio= new SimpleObjectProperty<LocalDateTime>(iniciox);
	            this.deadline = new SimpleObjectProperty<LocalDateTime>(deadlinex);
	            
	       	 setDescription(descriptionx);
	       	 setPriority(priorityx);
	       	 //setContext(contextx);
	       	 setDeadline(deadlinex);
	       	setInicio(iniciox);
	        }
	    }

}
