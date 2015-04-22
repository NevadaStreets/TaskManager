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

/**
 * Model class for a Person.
 *
 */
public class Proyecto implements Comparable<Proyecto>, Serializable {

	 private  String Namex;
	    private  String descriptionx;
	    private  int priorityx;
	    private  String contextx;
	    private  LocalDate deadlinex;
	    private  LocalDate iniciox;
	    private ArrayList<Tarea> tasksx;
	    
    private transient StringProperty Name;
    public transient ObservableList<Tarea> Tasks;
    private transient StringProperty description;
    private transient IntegerProperty priority;
    private transient StringProperty context;
    private transient ObjectProperty<LocalDate> inicio;
    private transient ObjectProperty<LocalDate> deadline;

    /**
     * Default constructor.
     */
    public Proyecto() {
        this(null);
    }

    /**
     * Constructor with some initial data.
     * 
     * @param firstName
     * @param lastName
     */
    public Proyecto(String Name) {
    	
    	
    	
    	
        this.Name = new SimpleStringProperty(Name);
        // Some initial dummy data, just for convenient testing.
        this.description = new SimpleStringProperty("some description");
        this.priority = new SimpleIntegerProperty(1234);
        this.context = new SimpleStringProperty("amska");
        this.inicio = new SimpleObjectProperty<LocalDate>(LocalDate.now());
        this.deadline = new SimpleObjectProperty<LocalDate>(LocalDate.of(2015, 4, 19));
        this.Tasks = FXCollections.observableArrayList();
        setInicio(LocalDate.now());
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
        return description.get();
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
    
    private int prioridad(){
    	int finale=0;
    	if(deadline!=null){
    	long  a=- deadline.get().until(LocalDate.now(), ChronoUnit.DAYS);
    	int b= (int)a;
    	
    	int expon= (int) Math.exp((100-b)/14.137);
    	int priorUser=priority.get();
    	finale=(int)(expon*0.50+ priorUser*0.50);
    	}
        return finale;
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
       String a=new String(String.valueOf(this.prioridad()));
       String b=new String(String.valueOf(o.prioridad()));
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
            this.Tasks = FXCollections.observableArrayList();
            //tasksx=new ArrayList<Tarea>();
            llenartask();
       	 setDescription(descriptionx);
       	 setPriority(priorityx);
       	 setContext(contextx);
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