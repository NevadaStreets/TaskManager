package view;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import application.Main;
import application.Proyecto;
import application.Tarea;

public class StatisticController {
	
    @FXML
    private Label deadlineLabel;
    
    @FXML
    private ComboBox estadistica;
    
    @FXML
    private PieChart piedelimon;
    
    private Stage dialogStage;
    private boolean okClicked = false;
    private Main mainApp;


    public StatisticController(){
	
    }

    @FXML
    private void initialize() {
    	
    }

    public void setDialogStage(Stage dialogStage) {
    		this.dialogStage = dialogStage;
    	}
    public void setMain(Main mainApp) {
        this.mainApp = mainApp;

        ObservableList<String> myComboBoxData = FXCollections.observableArrayList();
        myComboBoxData.add("Proyectos");
        myComboBoxData.add("Tareas"); 
        estadistica.setItems(myComboBoxData);
        estadistica.getSelectionModel().select(0);
        ObservableList<PieChart.Data>avance = FXCollections.observableArrayList();
        double tamano = mainApp.getProyectData().size();
        double activo = 0;
        double pausa = 0;
        double completo = 0;
        double vencida = 0;
        for(int i=0;i<mainApp.getProyectData().size();i++){
        	if (mainApp.getProyectData().get(i).getContext().equals("Activo")){
        		activo++;
        	}
        	else if (mainApp.getProyectData().get(i).getContext().equals("En Pausa")){
        		pausa++;
        	}
        	else if (mainApp.getProyectData().get(i).getContext().equals("Terminado")){
        		completo++;
        	}
        	else {
        		vencida++;
        	}
        }

        avance.add(new PieChart.Data("Vencido",(vencida/tamano)*100));
        avance.add(new PieChart.Data("En Pausa",(pausa/tamano)*100));
        avance.add(new PieChart.Data("Terminado",(completo/tamano)*100));
        avance.add(new PieChart.Data("Activo",(activo/tamano)*100));
        final Label caption = new Label("");
        caption.setTextFill(Color.DARKORANGE);
        caption.setStyle("-fx-font: 24 arial;");
        piedelimon.setData(avance);
        
    }
    
    @FXML
    private void mostrarTarea() {
    	ObservableList<PieChart.Data>avance = FXCollections.observableArrayList();
        double tamano = mainApp.getTaskData().size();
        double activo = 0;
        double pausa = 0;
        double completo = 0;
        double vencida = 0;
        for(int i=0;i<tamano;i++){
        	if (mainApp.getTaskData().get(i).getEstado().equals("Activa")){
        		activo++;
        	}
        	else if (mainApp.getTaskData().get(i).getEstado().equals("En Pausa")){
        		pausa++;
        	}
        	else if (mainApp.getTaskData().get(i).getEstado().equals("Completada")){
        		completo++;
        	}
        	else {
        		vencida++;
        	}
        }

        avance.add(new PieChart.Data("Vencida",(vencida/tamano)*100));
        avance.add(new PieChart.Data("En Pausa",(pausa/tamano)*100));
        avance.add(new PieChart.Data("Completada",(completo/tamano)*100));
        avance.add(new PieChart.Data("Activa",(activo/tamano)*100));
        final Label caption = new Label("");
        caption.setTextFill(Color.DARKORANGE);
        caption.setStyle("-fx-font: 24 arial;");
        piedelimon.setData(avance);
        piedelimon.setTitle("Tareas y sus estados");
    }
    
    @FXML
    private void mostrarProyecto() {
    	ObservableList<PieChart.Data>avance = FXCollections.observableArrayList();
        double tamano = mainApp.getProyectData().size();
        double activo = 0;
        double pausa = 0;
        double completo = 0;
        double vencida = 0;
        for(int i=0;i<mainApp.getProyectData().size();i++){
        	if (mainApp.getProyectData().get(i).getContext().equals("Activo")){
        		activo++;
        	}
        	else if (mainApp.getProyectData().get(i).getContext().equals("En Pausa")){
        		pausa++;
        	}
        	else if (mainApp.getProyectData().get(i).getContext().equals("Terminado")){
        		completo++;
        	}
        	else {
        		vencida++;
        	}
        }

        avance.add(new PieChart.Data("Vencido",(vencida/tamano)*100));
        avance.add(new PieChart.Data("En Pausa",(pausa/tamano)*100));
        avance.add(new PieChart.Data("Terminado",(completo/tamano)*100));
        avance.add(new PieChart.Data("Activo",(activo/tamano)*100));
        final Label caption = new Label("");
        caption.setTextFill(Color.DARKORANGE);
        caption.setStyle("-fx-font: 24 arial;");
        piedelimon.setData(avance);
        piedelimon.setTitle("Proyectos y sus estados");
    }
    
    @FXML
    private void setear(){
    	if (estadistica.getSelectionModel().getSelectedItem().equals("Proyectos")){
    		mostrarProyecto();
    	}
    	else{
    		mostrarTarea();
    	}
    }
    @FXML
    private void dashbordear(){
    	mainApp.showDashboard();
    }
}
