package application;

import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Tarea implements Comparable<Tarea> {
	 private final StringProperty Name;
	 private final StringProperty description;
	 private final IntegerProperty priority;
	 private final StringProperty context;
	 private final ObjectProperty<LocalDate> inicio;
	 private final ObjectProperty<LocalDate> deadline;
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
	        this.Name.set(Name);
	    }

	    public StringProperty NameProperty() {
	        return Name;
	    }

	    public String getDescription() {
	        return description.get();
	    }

	    public void setDescription(String street) {
	        this.description.set(street);
	    }

	    public StringProperty descriptionProperty() {
	        return description;
	    }

	    public int getPriority() {
	        return priority.get();
	    }

	    public void setPriority(int postalCode) {
	        this.priority.set(postalCode);
	    }

	    public IntegerProperty priorityProperty() {
	        return priority;
	    }

	    public String getContext() {
	        return context.get();
	    }

	    public void setContext(String city) {
	        this.context.set(city);
	    }

	    public StringProperty contextProperty() {
	        return context;
	    }

	    public LocalDate getDeadline() {
	        return deadline.get();
	    }

	    public void setDeadline(LocalDate birthday) {
	        this.deadline.set(birthday);
	    }

	    public ObjectProperty<LocalDate> deadlineProperty() {
	        return deadline;
	    }
	    
	    public LocalDate getInicio() {
	        return inicio.get();
	    }

	    public void setInicio(LocalDate birthday) {
	        this.inicio.set(birthday);
	    }

	    public ObjectProperty<LocalDate> inicioProperty() {
	        return inicio;
	    }

	    @Override
	    public int compareTo(Tarea o) {
	       String a=new String(String.valueOf(this.priorityProperty())+this.getName());
	       String b=new String(String.valueOf(o.priorityProperty())+o.getName());
	       return a.compareTo(b);
	   }


}
