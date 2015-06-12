package application;

import java.io.Serializable;
import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Contexto implements Serializable{
	private String contextx;
	private int priorityx;
	public transient ObservableList<Tarea> Tasks;
	
    private transient StringProperty context;
    private transient IntegerProperty priority;
    
    public Contexto() {
        this(null);
    }
  
	public Contexto(String s, int i){
		//this.context = new SimpleStringProperty(s);
		//this.priority = new SimpleIntegerProperty(i);
		this.Tasks = FXCollections.observableArrayList();
		//contextx = context.get();
		//priorityx = priority.get();
		
		contextx = s;
		priorityx = i;
	}
	
	public Contexto(Object object) {
		// TODO Auto-generated constructor stub
	}

	@Override
    public String toString(){
    	return contextx;
    }
	
	public String getContext(){
		//return context.get();
		return contextx;
	}
	
	public int getPriority(){
		//return priority.get();
		return priorityx;
	}
	
	public void setContext(String s){
		this.contextx = s;
		//this.context.set(s);
	}
	
	public void setPriority(int i){
		this.priorityx = i;
		//this.priority.set(i);
	}
	
	public void ajust(){
    	if(contextx!=null){
            this.priority = new SimpleIntegerProperty(priorityx);
            this.context = new SimpleStringProperty("contextx");
            this.Tasks = FXCollections.observableArrayList();
       	 setPriority(priorityx);
       	 setContext(contextx);

        }
    }
}