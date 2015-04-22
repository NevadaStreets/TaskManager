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

public class Tarea implements Comparable<Tarea> , Serializable {
	
	private  String Namex;
	 private  String descriptionx;
	 private  int priorityx;
	 private  String contextx;
	 private  LocalDate iniciox;
	 private  LocalDate deadlinex;
	
	
	
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
	        this.description = new SimpleStringProperty("some description");
	        this.priority = new SimpleIntegerProperty(1234);
	        this.context = new SimpleStringProperty("amska");
	        this.inicio = new SimpleObjectProperty<LocalDate>(LocalDate.now());
	        this.deadline = new SimpleObjectProperty<LocalDate>(LocalDate.of(2015, 4, 19));
	        this.project = P;
	        
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

	    public ObjectProperty<LocalDate> inicioProperty() {
	        return inicio;
	    }

	    @Override
	    public int compareTo(Tarea o) {
	       String a=new String(String.valueOf(this.priorityProperty())+this.getName());
	       String b=new String(String.valueOf(o.priorityProperty())+o.getName());
	       return b.compareTo(a);
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
