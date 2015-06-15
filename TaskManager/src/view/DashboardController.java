package view;

import java.awt.event.InputEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.*;
import java.util.Calendar;
import java.time.*;

import javax.xml.stream.EventFilter;

import util.DateUtil;

import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import application.Main;
import application.Proyecto;

public class DashboardController {
	@FXML
	private Canvas canvas;
	
	@FXML
	private AnchorPane anchoa;
	
	@FXML
	private AnchorPane anchoa2;
	
	@FXML
	private Label titulo;
	
	@FXML
	private Label t1;
	
	@FXML
	private Label t2;
	
	@FXML
	private Label t3;
	
	@FXML
	private Label cumplida;
	
	@FXML
	private Label nocumplida;
	
	@FXML
	private ScrollPane sp;
	
	private Main mainApp;
	private boolean pintado = false;
	
	int onClick = -1;

	ArrayList <Rectangle> rectangulos = new ArrayList<Rectangle>();

    public DashboardController() {
    }


    @FXML
    private void initialize() {
       
    }
    
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        handleColocar();

    }
    
    @FXML
    private void handleInbox() {
    	mainApp.showTaskView();
    }
    
    @FXML
    private void handleColocar() {
    	if(rectangulos.isEmpty()){
    		
    	}
    	else{
    		rectangulos.clear();
	    	anchoa.getChildren().clear();
    	}
        FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MMMM/yyyy");
    	LocalDate start = DateUtil.parse("01.01.9999 23:59:59").toLocalDate();
    	LocalDate end = DateUtil.parse("01.01.1000 00:00:00").toLocalDate();
    	double alto = 30;
    	for(int i=0; i<mainApp.getProyectData().size();i++){
    		if(start.compareTo(mainApp.getProyectData().get(i).getInicio().toLocalDate())>0){
    			start = mainApp.getProyectData().get(i).getInicio().toLocalDate();
    		}
    		alto += 30;
    	}
    	
    	for(int i=0; i<mainApp.getProyectData().size();i++){
    		if(end.compareTo(mainApp.getProyectData().get(i).getDeadline().toLocalDate())<0){
    			end = mainApp.getProyectData().get(i).getDeadline().toLocalDate();
    		}
    	}
    	
    	if(start.compareTo(LocalDate.now())>0){
			start = LocalDate.now();
		}
    	int desfase = 0;
    	if(end.getYear()>start.getYear()){
    		desfase = desfase + (end.getYear()-start.getYear())*365;
    	}
    	if(end.getDayOfYear()-start.getDayOfYear() +desfase<8){
			end = start.plusDays(8);
		}
    	System.out.println(end);
    	System.out.println(start);
    	System.out.println("diferencia: " + (end.getDayOfYear()-start.getDayOfYear()));
    	System.out.println("desfase: " + desfase);
    	double diff = end.getDayOfYear()-start.getDayOfYear() +desfase;
    	if(end.getDayOfYear()-start.getDayOfYear() +desfase>60){
			end = start.plusDays(61);
		}
    	int diferencia = 60;
    	for(long i = 0; i<=diff*120; i+=120){
        	Label diax = new Label(start.plusDays(i/120).format(fmt).toString());
            diax.layoutXProperty().set(i);
            diax.layoutYProperty().set(0);
            double tamdiax = fontLoader.computeStringWidth(diax.getText(),diax.getFont());
            double posdiax = (120 - tamdiax)/2 + diax.getLayoutX();
            diax.layoutXProperty().set(posdiax);
            anchoa.getChildren().add(diax);
        }
        if(alto<560){
        	alto = 560;
        };
        double factor = diff/61;

        System.out.println(diff);
        System.out.println(factor);
    	double largo = 120;
        Double llenado = 0.0;
        int count = 0;
        for(int k=1;k<factor;k++){
            Canvas canvitas2 = new Canvas();
           	canvitas2.setWidth(diferencia*largo);
           	canvitas2.setHeight(alto);
           	canvitas2.setLayoutX((k-1)*diferencia*largo);
           	canvitas2.setLayoutY(0);
        	GraphicsContext gc2 = canvitas2.getGraphicsContext2D();
        	anchoa.getChildren().add(canvitas2);
            drawShapes(gc2, diferencia, (LocalDate.now().getDayOfYear()-start.getDayOfYear())*largo, alto);
            count ++;
        	
        }
        if(diff-(61*count)>0){
        	Canvas canvitas = new Canvas();
        	System.out.println("faltante: " + (diff-(61*count)));
           	canvitas.setWidth((diff-(61*count))*largo);
           	canvitas.setHeight(alto);
           	canvitas.setLayoutX(count*diferencia*largo);
           	canvitas.setLayoutY(0);
        	GraphicsContext gc = canvitas.getGraphicsContext2D();
        	anchoa.getChildren().add(canvitas);
            drawShapes(gc, diff-(61*count), (LocalDate.now().getDayOfYear()-start.getDayOfYear())*largo, alto);	
        }
    	//sp.setHvalue((LocalDate.now().getDayOfYear()-start.getDayOfYear())/(diff*120));
        sp.setHvalue((LocalDate.now().getDayOfYear()-start.getDayOfYear())/(diff));
        for(int i=0; i<mainApp.getProyectData().size();i++){
        	if(mainApp.getProyectData().get(i).gettask().size()>0){
        		int desfasado = 0;
        		if(mainApp.getProyectData().get(i).getDeadline().getYear()>mainApp.getProyectData().get(i).getInicio().getYear()){
        			desfasado = desfasado + 365*(mainApp.getProyectData().get(i).getDeadline().getYear()-mainApp.getProyectData().get(i).getInicio().getYear());
        		}
        		double termino = (mainApp.getProyectData().get(i).getDeadline().getDayOfYear()-mainApp.getProyectData().get(i).getInicio().getDayOfYear() + desfasado+1)*largo - ((24-mainApp.getProyectData().get(i).getDeadline().getHour()+mainApp.getProyectData().get(i).getInicio().getHour())*5);
        		//double termino = (mainApp.getProyectData().get(i).getDeadline().getHour()-mainApp.getProyectData().get(i).getInicio().getHour())*largo/24;
        		llenado = 0.0;
            	Double totaltareas = 0.0;
            	Double vencidas = 0.0;
            	Proyecto P = mainApp.getProyectData().get(i);
            	for (int j=0;j<mainApp.getProyectData().get(i).gettask().size();j++){
            		if(mainApp.getProyectData().get(i).gettask().get(j).getEstado().equals("Completada")){
            			llenado += 1;
            		}
            		if(mainApp.getProyectData().get(i).gettask().get(j).getEstado().equals("Vencida")){
            			vencidas += 1;
            		}
            		totaltareas += 1;
            	}
            	int completas = llenado.intValue();
            	Label lx = new Label();
                Rectangle rx = new Rectangle(termino,20,Color.TRANSPARENT);
                
                Rectangle completox = new Rectangle((llenado/totaltareas)*termino,20,Color.rgb(2, 200, 1, 0.5));
                
                rx.setStroke(Color.DARKBLUE);
                rectangulos.add(rx);
                String mensaje = String.valueOf(llenado.intValue()) + " de " + String.valueOf(totaltareas.intValue()) + " tareas realizadas";
                long iniciox = mainApp.getProyectData().get(i).getInicio().toLocalDate().compareTo(start)*120 +mainApp.getProyectData().get(i).getInicio().getHour()*5 ;
                rx.setLayoutX(iniciox);
                rx.setLayoutY(30*(i+1));
                completox.setLayoutX(iniciox);
                completox.setLayoutY(30*(i+1));
                lx.textProperty().set(mainApp.getProyectData().get(i).getName());
                double tamanox = fontLoader.computeStringWidth(lx.getText(),lx.getFont());
                double posx = (rx.getWidth() - tamanox)/2 + rx.getLayoutX();
                int indice = i;
                rx.setOnMouseClicked(new EventHandler<MouseEvent>(){
                	public void handle(MouseEvent eventoRaton){
                		if(eventoRaton.getButton().equals(MouseButton.PRIMARY)){
                            if(eventoRaton.getClickCount() == 2){
                                mainApp.showTasksInProject(P);
                                onClick = indice;
                                //mainApp.showDashboard();
                                double xd = sp.getHvalue();
                                handleColocar();
                                sp.setHvalue(xd);
                                
                            }
                            //else{
                            	for(int j=0;j<rectangulos.size();j++){
                        			rectangulos.get(j).setStrokeWidth(1);
                        			rectangulos.get(j).setStroke(Color.DARKBLUE);
                        		}
                        		rx.setStroke(Color.BLACK);
                                rx.setStrokeWidth(3);
                                titulo.setText(P.getName());
                                Double c=0.0;
                                Double v=0.0;
                                Double t=0.0;
                                for (int j=0;j<P.gettask().size();j++){
                            		if(P.gettask().get(j).getEstado().equals("Completada")){
                            			c += 1;
                            		}
                            		if(P.gettask().get(j).getEstado().equals("Vencida")){
                            			v += 1;
                            		}
                            		t += 1;
                            	}
                                t1.setText(String.valueOf(t.intValue()));
                                t2.setText(String.valueOf(c.intValue()));
                                t3.setText(String.valueOf(v.intValue()));
                                Double p = c/t;
                                cumplida.setText(String.valueOf(p.doubleValue()));
                                nocumplida.setText(String.valueOf(v/t));
                            //}
                        }          
                	}	
                });

                completox.setOnMouseClicked(new EventHandler<MouseEvent>(){
                	public void handle(MouseEvent eventoRaton){
                		if(eventoRaton.getButton().equals(MouseButton.PRIMARY)){
                            if(eventoRaton.getClickCount() == 2){
                            	mainApp.showTasksInProject(P);
                                //mainApp.showDashboard();
                            	onClick = indice;
                            	double xd = sp.getHvalue();
                                handleColocar();
                                sp.setHvalue(xd);
                            }
                            //else{
                            	for(int j=0;j<rectangulos.size();j++){
                        			rectangulos.get(j).setStrokeWidth(1);
                        			rectangulos.get(j).setStroke(Color.DARKBLUE);
                        		}
                        		rx.setStroke(Color.BLACK);
                                rx.setStrokeWidth(3);
                                titulo.setText(P.getName());
                                Double c=0.0;
                                Double v=0.0;
                                Double t=0.0;
                                for (int j=0;j<P.gettask().size();j++){
                            		if(P.gettask().get(j).getEstado().equals("Completada")){
                            			c += 1;
                            		}
                            		if(P.gettask().get(j).getEstado().equals("Vencida")){
                            			v += 1;
                            		}
                            		t += 1;
                            	}
                                t1.setText(String.valueOf(t.intValue()));
                                t2.setText(String.valueOf(c.intValue()));
                                t3.setText(String.valueOf(v.intValue()));
                                Double p = c/t;
                                cumplida.setText(String.valueOf(p.doubleValue()));
                                nocumplida.setText(String.valueOf(v/t));
                            //}
                        }          
                	}		
                });
                lx.setOnMouseClicked(new EventHandler<MouseEvent>(){
                	public void handle(MouseEvent eventoRaton){
                		if(eventoRaton.getButton().equals(MouseButton.PRIMARY)){
                            if(eventoRaton.getClickCount() == 2){
                            	mainApp.showTasksInProject(P);
                                //mainApp.showDashboard();
                            	onClick = indice;
                            	double xd = sp.getHvalue();
                                handleColocar();
                                sp.setHvalue(xd);
                            }
                            //else{
                            	for(int j=0;j<rectangulos.size();j++){
                        			rectangulos.get(j).setStrokeWidth(1);
                        			rectangulos.get(j).setStroke(Color.DARKBLUE);
                        		}
                        		rx.setStroke(Color.BLACK);
                                rx.setStrokeWidth(3);
                                titulo.setText(P.getName());
                                Double c=0.0;
                                Double v=0.0;
                                Double t=0.0;
                                for (int j=0;j<P.gettask().size();j++){
                            		if(P.gettask().get(j).getEstado().equals("Completada")){
                            			c += 1;
                            		}
                            		if(P.gettask().get(j).getEstado().equals("Vencida")){
                            			v += 1;
                            		}
                            		t += 1;
                            	}
                                t1.setText(String.valueOf(t.intValue()));
                                t2.setText(String.valueOf(c.intValue()));
                                t3.setText(String.valueOf(v.intValue()));
                                Double p = c/t;
                                cumplida.setText(String.valueOf(p.doubleValue()));
                                nocumplida.setText(String.valueOf(v/t));
                            //}
                        }          
                	}	
                });
                anchoa.getChildren().add(rx);
                anchoa.getChildren().add(completox);
                anchoa.getChildren().add(lx);

                if(vencidas > 0){
                	if( vencidas < 2){
                		mensaje += "\n" + String.valueOf(vencidas.intValue()) + " tarea vencidas";
                	}
                	else{
                		mensaje += "\n" + String.valueOf(vencidas.intValue()) + " tareas vencidas";
                	}
                	Rectangle vencidax = new Rectangle((vencidas/totaltareas)*termino,20,Color.rgb(200, 2, 1, 0.5));
            		vencidax.setLayoutX(iniciox + (llenado/totaltareas)*termino);
                    vencidax.setLayoutY(30*(i+1));
                    Tooltip tt1 = new Tooltip(mensaje);
                    Tooltip.install(vencidax, tt1);
                    vencidax.setOnMouseClicked(new EventHandler<MouseEvent>(){
                    	public void handle(MouseEvent eventoRaton){
                    		if(eventoRaton.getButton().equals(MouseButton.PRIMARY)){
                                if(eventoRaton.getClickCount() == 2){
                                	mainApp.showTasksInProject(P);
                                    //mainApp.showDashboard();
                                	onClick = indice;
                                	double xd = sp.getHvalue();
                                    handleColocar();
                                    sp.setHvalue(xd);
                                }
                                //else{
                                	for(int j=0;j<rectangulos.size();j++){
                            			rectangulos.get(j).setStrokeWidth(1);
                            			rectangulos.get(j).setStroke(Color.DARKBLUE);
                            		}
                            		rx.setStroke(Color.BLACK);
                                    rx.setStrokeWidth(3);
                                    titulo.setText(P.getName());
                                    Double c=0.0;
                                    Double v=0.0;
                                    Double t=0.0;
                                    for (int j=0;j<P.gettask().size();j++){
                                		if(P.gettask().get(j).getEstado().equals("Completada")){
                                			c += 1;
                                		}
                                		if(P.gettask().get(j).getEstado().equals("Vencida")){
                                			v += 1;
                                		}
                                		t += 1;
                                	}
                                    t1.setText(String.valueOf(t.intValue()));
                                    t2.setText(String.valueOf(c.intValue()));
                                    t3.setText(String.valueOf(v.intValue()));
                                    Double p = c/t;
                                    cumplida.setText(String.valueOf(p.doubleValue()));
                                    nocumplida.setText(String.valueOf(v/t));
                                //}
                            }          
                    	}	
                    });
                    anchoa.getChildren().add(vencidax);
                }

                Tooltip t = new Tooltip(mensaje);
                Tooltip.install(rx, t);
                Tooltip.install(completox, t);
                Tooltip.install(lx, t);
                lx.layoutXProperty().set(posx);
                lx.layoutYProperty().set(30*(i+1));
                
                
        	}
        	
    	}
        if(onClick >= 0){
            rectangulos.get(onClick).setStroke(Color.BLACK);
            rectangulos.get(onClick).setStrokeWidth(3);
        }
      
        
    }
    
    private void drawShapes(GraphicsContext gc, double d, double p, double a) {
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(0.2);
        for(int i=1; i<=d;i++){
        	gc.strokeLine(i*120, 0, i*120, a);
        }
        if(pintado == false){

            gc.setStroke(Color.RED);
            gc.setLineWidth(2);
            LocalTime hora = LocalTime.now();
            gc.strokeLine(p + hora.getHour()*5, 20, p + hora.getHour()*5, a);
            sp.setHvalue(p/(d*120));
            pintado = true;
        }
    }
    
    @FXML
    private void estadisticas(){
    	mainApp.showStatisticView();
    }
    

}
