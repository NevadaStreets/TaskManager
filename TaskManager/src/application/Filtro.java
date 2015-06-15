package application;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Filtro {
	private String nombre;
	private Proyecto project;
	private Contexto context;
	private String estado;
	private LocalDate before;
	private LocalDate after;
	
	public Filtro(String s){
		this.nombre = s;
	}
	
	public String getNombre(){
		return nombre;
	}
	
	public void setNombre(String s){
		this.nombre = s;
	}
	public Proyecto getProject(){
		return project;
	}
	
	public void setProject(Proyecto p){
		this.project= p;
	}

	public Contexto getContext(){
		return context;
	}
	
	public void setContext(Contexto c){
		this.context = c;
	}

	public String getEstado(){
		return estado;
	}
	
	public void setEstado(String s){
		this.estado = s;
	}

	public LocalDate getBefore(){
		return before;
	}
	
	public void setBefore(LocalDate t){
		this.before = t;
	}

	public LocalDate getAfter(){
		return after;
	}
	
	public void setAfter(LocalDate t){
		this.after = t;
	}
	
	public boolean cumple(Tarea t){
		if(project!= null){
    		if(t.getProject().getName()!=project.getName()){
    			return false;
    		}
    	}
    	if(context!= null){
    		if(t.getContext().getContext()!=context.getContext()){
    			return false;
    		}
    	}
    	if(estado!= null){
    		if(t.getEstado().equals(estado)==false){
    			return false;
    		}
    	}
    	if(after!= null){
    		if(t.getDeadline().toLocalDate().compareTo(after)<0){
    			return false;
    		}
    	}
    	if(before!= null){
    		if(t.getDeadline().toLocalDate().compareTo(before)>0){
    			return false;
    		}
    	}
		return true;
	}


}
