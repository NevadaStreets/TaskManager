package view;

import java.awt.event.InputEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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
    	ArrayList <Rectangle> rectangulos = new ArrayList<Rectangle>();
        FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MMMM/yyyy");
    	LocalDate start = DateUtil.parse("01.01.9999");
    	LocalDate end = DateUtil.parse("01.01.1000");
    	double alto = 30;
    	for(int i=0; i<mainApp.getProyectData().size();i++){
    		if(start.compareTo(mainApp.getProyectData().get(i).getInicio())>0){
    			start = mainApp.getProyectData().get(i).getInicio();
    		}
    		alto += 30;
    	}
    	
    	for(int i=0; i<mainApp.getProyectData().size();i++){
    		if(end.compareTo(mainApp.getProyectData().get(i).getDeadline())<0){
    			end = mainApp.getProyectData().get(i).getDeadline();
    		}
    	}
    	
    	if(start.compareTo(LocalDate.now())>0){
			start = LocalDate.now();
		}
    	
    	if(start.compareTo(end)>-8){
			end = start.plusDays(8);
		}
    	
    	for(long i = 0; i<=end.compareTo(start)*120; i+=120){
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
        }
    	
    	double largo = 120;
        Double llenado = 0.0;
    	Canvas canvitas = new Canvas();
       	canvitas.setWidth(end.compareTo(start)*largo);
       	canvitas.setHeight(alto);
       	canvitas.setLayoutX(0);
       	canvitas.setLayoutY(0);
    	GraphicsContext gc = canvitas.getGraphicsContext2D();
    	anchoa.getChildren().add(canvitas);
        drawShapes(gc, end.compareTo(start), LocalDate.now().compareTo(start)*largo, alto);
    	
        for(int i=0; i<mainApp.getProyectData().size();i++){
        	if(mainApp.getProyectData().get(i).gettask().size()>0){
        		double termino = (mainApp.getProyectData().get(i).getDeadline().compareTo(mainApp.getProyectData().get(i).getInicio())+1)*largo;
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
                long iniciox = mainApp.getProyectData().get(i).getInicio().compareTo(start)*120;
                rx.setLayoutX(iniciox);
                rx.setLayoutY(30*(i+1));
                completox.setLayoutX(iniciox);
                completox.setLayoutY(30*(i+1));
                lx.textProperty().set(mainApp.getProyectData().get(i).getName());
                double tamanox = fontLoader.computeStringWidth(lx.getText(),lx.getFont());
                double posx = (rx.getWidth() - tamanox)/2 + rx.getLayoutX();
                rx.setOnMouseClicked(new EventHandler<MouseEvent>(){
                	public void handle(MouseEvent eventoRaton){
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
                        
                	}	
                });
                completox.setOnMouseClicked(new EventHandler<MouseEvent>(){
                	public void handle(MouseEvent eventoRaton){
                		for(int i=0;i<rectangulos.size();i++){
                			rectangulos.get(i).setStrokeWidth(1);
                			rectangulos.get(i).setStroke(Color.DARKBLUE);
                		}
                		rx.setStroke(Color.BLACK);
                        rx.setStrokeWidth(3);
                        titulo.setText(lx.getText());
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
                	}	
                });
                lx.setOnMouseClicked(new EventHandler<MouseEvent>(){
                	public void handle(MouseEvent eventoRaton){
                		for(int i=0;i<rectangulos.size();i++){
                			rectangulos.get(i).setStrokeWidth(1);
                			rectangulos.get(i).setStroke(Color.DARKBLUE);
                		}
                		rx.setStroke(Color.BLACK);
                        rx.setStrokeWidth(3);
                        titulo.setText(lx.getText());
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
                    Tooltip t1 = new Tooltip(mensaje);
                    Tooltip.install(vencidax, t1);
                    vencidax.setOnMouseClicked(new EventHandler<MouseEvent>(){
                    	public void handle(MouseEvent eventoRaton){
                    		for(int i=0;i<rectangulos.size();i++){
                    			rectangulos.get(i).setStrokeWidth(1);
                    			rectangulos.get(i).setStroke(Color.DARKBLUE);
                    		}
                    		rx.setStroke(Color.BLACK);
                            rx.setStrokeWidth(3);
                            titulo.setText(lx.getText());
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
    	
      
        
    }
    
    private void drawShapes(GraphicsContext gc, int d, double p, double a) {
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(0.2);
        for(int i=1; i<=d;i++){
        	gc.strokeLine(i*120, 0, i*120, a);
        }
        gc.setStroke(Color.RED);
        gc.setLineWidth(2);
        LocalTime hora = LocalTime.now();
        gc.strokeLine(p + hora.getHour()*5, 20, p + hora.getHour()*5, a);
        
    }


}
