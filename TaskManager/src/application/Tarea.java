package application;

import java.io.Serializable;
import java.time.LocalDate;

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
	 private  String contextx;
	 private  LocalDate iniciox;
	 private  LocalDate deadlinex;
	 
	 private String estado;
	
	
	private transient StringProperty Name;
	 private transient StringProperty description;
	 private transient IntegerProperty priority;
	 private transient StringProperty context;
	 private transient ObjectProperty<LocalDate> inicio;
	 private transient ObjectProperty<LocalDate> deadline;
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
	        this.description = new SimpleStringProperty("Ingrese descripcion");
	        this.priority = new SimpleIntegerProperty(1);
	        this.context = new SimpleStringProperty("Ingrese contexto");
	        this.inicio = new SimpleObjectProperty<LocalDate>(LocalDate.now());
	        this.deadline = new SimpleObjectProperty<LocalDate>(LocalDate.now());
	        this.project = P;
	        this.estado = "Activo";
	        
	        Namex=this.Name.get();
	        descriptionx= this.description.get() ;
	        priorityx= this.priority.get() ;
	        contextx=this.context.get();
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
	    	if (LocalDate.now().compareTo(deadlinex)>0){
	    		estado = "Vencida";
	    	}
	    	return estado;
	    }
	    
	    public void setEstado(String state){
	    	estado =  state;
	    	if (LocalDate.now().compareTo(deadlinex)>0){
	    		estado = "Vencida";
	    	}
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
	        return description.get();
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

	    public String getContext() {
	        return context.get();
	    }

	    public void setContext(String city) {
	    	contextx=city;
	        this.context.set(city);
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
	    	if (project.getDeadline().compareTo(deadlinex)<0){
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
	        }
	    }

	    public ObjectProperty<LocalDate> deadlineProperty() {
	        return deadline;
	    }
	    
	    public LocalDate getInicio() {
	        return inicio.get();
	    }

	    public void setInicio(LocalDate birthday) {
	    	iniciox=birthday;
	        if (project.getInicio().compareTo(iniciox)>0){
	        	project.setInicio(iniciox);
	        }
	        
	        this.inicio.set(birthday);
	    }

	    public ObjectProperty<LocalDate> inicioProperty() {
	        return inicio;
	    }

	    @Override
	    public int compareTo(Tarea o) {
	       int a=this.priorityProperty().get();
	       int b=o.priorityProperty().get();
	       return b-a;
	   }

	    public void ajust(){
	    	if(Namex!=null){
	       	 //setName(Namex);
	    		 this.Name = new SimpleStringProperty(Namex);
	    		this.description = new SimpleStringProperty(descriptionx);
	            this.priority = new SimpleIntegerProperty(priorityx);
	            this.context = new SimpleStringProperty("contextx");
	            this.inicio= new SimpleObjectProperty<LocalDate>(iniciox);
	            this.deadline = new SimpleObjectProperty<LocalDate>(deadlinex);
	            
	       	 setDescription(descriptionx);
	       	 setPriority(priorityx);
	       	 setContext(contextx);
	       	 setDeadline(deadlinex);
	       	setInicio(iniciox);
	        }
	    }

}
