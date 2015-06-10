package application;
	
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;









import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;









import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import view.BigViewController;
import view.DashboardController;
import view.EditTaskButtonController;
import view.ProjectEditDialogController;
import view.StatisticController;
import view.TareaViewController;
import view.TaskEditDialogController;
import view.TaskViewController;
import view.TasksInProjectViewController;
import javafx.application.Application;

import org.controlsfx.dialog.Dialogs;

public class Main extends Application implements Serializable{
	
	private ObservableList<Proyecto> projectData = FXCollections.observableArrayList();
	private ArrayList<Proyecto> respaldoproject;
	private ObservableList<Tarea> taskData = FXCollections.observableArrayList();
	private ArrayList<Tarea> respaldotask;
	private Stage ps;
	private BorderPane bv;
	private String email;
	
	
	@Override
	public void start(Stage primaryStage) throws MessagingException {
		File archivo = new File("media.obj");
		
		this.ps = primaryStage;
		this.ps.setTitle("Task Manager");
		this.ps.setMinHeight(400);
		this.ps.setMinWidth(600);
		if(archivo.exists()){
			try {
				desereal();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	}
		else{
		//projectData.add(new Proyecto("Proyecto de Software"));
		//projectData.add(new Proyecto("Proyecto de Vida"));
		//projectData.add(new Proyecto("Electro pls"));
		//Tarea t = new Tarea("A fluir con Fluidos", projectData.get(2));
		//taskData.add(t);
		//projectData.get(2).Tasks.add(t);
			email="taskmanager2015@gmail.com";
			}
		//projectData.add(new Proyecto("Proyecto de Software"+LocalDate.now()));
		
		initBigView();
		ordenar();

        showTaskView();


        try {
			mensajear();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
		private void desereal() throws FileNotFoundException, IOException, ClassNotFoundException{
			ObjectInputStream entrada=new ObjectInputStream(new FileInputStream("media.obj"));
			ArrayList<Proyecto> obj1=(ArrayList<Proyecto>)entrada.readObject();
			
			/*ObservableList<Proyecto> paj = FXCollections.observableArrayList();
		    Proyecto aux=paj.get(0);
			paj.add(new Proyecto("BElectro pls"));*/
			
			projectData = FXCollections.observableArrayList(obj1);
			for(int i=0;i<projectData.size();i++){
				projectData.get(i).ajust();
				taskData.addAll(projectData.get(i).gettask());
				
			}
			for(int i=0;i<taskData.size();i++){
				taskData.get(i).ajust();
				
			}
			
			File archivo = null;
		      FileReader fr = null;
		      BufferedReader br = null;
		 
		      try {
		         // Apertura del fichero y creacion de BufferedReader para poder
		         // hacer una lectura comoda (disponer del metodo readLine()).
		         archivo = new File ("datos.txt");
		         fr = new FileReader (archivo);
		         br = new BufferedReader(fr);
		 
		         // Lectura del fichero
		         String linea;
		         while((linea=br.readLine())!=null)
		           email=linea;
		      }
		      catch(Exception e){
		         e.printStackTrace();
		      }finally{
		         // En el finally cerramos el fichero, para asegurarnos
		         // que se cierra tanto si todo va bien como si salta 
		         // una excepcion.
		         try{                    
		            if( null != fr ){   
		               fr.close();     
		            }                  
		         }catch (Exception e2){ 
		            e2.printStackTrace();
		         }
		      }
			
			
			
		}
		
		
		public void sereal() throws FileNotFoundException, IOException{
			//respaldoproject;
			ordenar();
			
			
			
			Object[] cosas=projectData.toArray();
			ArrayList<Object> listOfStrings = new ArrayList<Object>((Arrays.asList(cosas).size()));
			listOfStrings.addAll(Arrays.asList(cosas));
			
			respaldoproject=(ArrayList)(listOfStrings);

		//	respaldoproject.addAll((Collection<? extends Proyecto>) a);
			
			//ObservableList lista1= FXCollections.observableArrayList(new int[]{12, 15, 11, 4, 32});
			ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("media.obj"));
			//ObjectOutputStream oos = new ObjectOutputStream(oks);
			oos.writeObject(respaldoproject);
			oos.close();
			
			if(email!=null){
			 FileWriter fichero = null;
		     PrintWriter pw = null;
		        try
		        {
		            fichero = new FileWriter("datos.txt");
		            pw = new PrintWriter(fichero);
		 
		                pw.println(email);
		 
		        } catch (Exception e) {
		            e.printStackTrace();
		        } finally {
		           try {

		           if (null != fichero)
		              fichero.close();
		           } catch (Exception e2) {
		              e2.printStackTrace();
		           }
		        }
			}
			
			
		}		
		
		public void mensajear() throws IOException, MessagingException {
			
			/*final String username = "taskmanager@outlook.es";
		    final String password = "Soyunprocesador";

		    Properties props = new Properties();
		    props.put("mail.smtp.auth", "true");
		    props.put("mail.smtp.starttls.enable", "true");
		    props.put("mail.smtp.host", "outlook.office365.com");
		    props.put("mail.smtp.port", "587");

		    Session session = Session.getInstance(props,
		      new javax.mail.Authenticator() {
		        protected PasswordAuthentication getPasswordAuthentication() {
		            return new PasswordAuthentication(username, password);
		        }
		      });

		    try {

		        Message message = new MimeMessage(session);
		        message.setFrom(new InternetAddress("taskmanager@outlook.es"));
		        message.setRecipients(Message.RecipientType.TO,
		            InternetAddress.parse("rorroes@gmail.com"));
		        message.setSubject("Test");
		        message.setText("HI");

		        Transport.send(message);

		        System.out.println("Done");
		        

		        Dialogs.create()
                .title("Campos validos")
                .masthead("todo bn")
                .message("todito bn")
                .showError();
        		        
		        
		    } catch (MessagingException e) {
		        Dialogs.create()
                .title("Campos NO validos")
                .masthead("todo NO bn")
                .message("todito MAAAL")
                .showError();
		        throw new RuntimeException(e);
		        
		    }
		    
		    */
			
	        String mensaje="";
	        mensaje= "<table style="+'"'+""+'"'+ "> <tr>  <td><strong>N�</strong></td>  <td><strong>Nombre de tarea</strong></td>  <td><strong>Deadline</strong></td>"+
	        "</tr>";
	        int proximas=0;
	        String mensaje2="Tareas que vencen hoy o ma�ana:\n";
	        
	        for(Tarea tata : taskData){
	        	int count= (int) -tata.getDeadline().until(LocalDate.now(), ChronoUnit.DAYS) ;
	        	if(count<=1)
	        	{
	        		if(!tata.getEstado().equals("Vencida") && !tata.getEstado().equals("Completada")){
	        		proximas++;
	        		mensaje+="<tr> <td>"+ proximas + "</td><td>" + tata.getName() + "</td><td> " +  tata.getDeadline() + "</td></tr>" ;
	        		mensaje2+=proximas+".-"+ tata.getName() + " | Su deadline es: "+  tata.getDeadline() + "\n";
	        		}
	        	}
	        	
	        }
	        mensaje= mensaje +"</table>";
	        if(proximas!=0){
			Properties props = new Properties();

			// Nombre del host de correo, es smtp.gmail.com
			props.setProperty("mail.smtp.host", "smtp.gmail.com");

			// TLS si est� disponible
			props.setProperty("mail.smtp.starttls.enable", "true");

			// Puerto de gmail para envio de correos
			props.setProperty("mail.smtp.port","587");

			// Nombre del usuario
			props.setProperty("mail.smtp.user", "taskmanager2015@gmail.com");

			// Si requiere o no usuario y password para conectarse.
			props.setProperty("mail.smtp.auth", "true");
			
			Session session = Session.getDefaultInstance(props);
			//session.setDebug(true);
			
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("taskmanager2015@gmail.com"));

			// A quien va dirigido
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
			message.setSubject("Notificaci�n");
			


			message.setText(
					"<head> <style> table, th, td {    border: 1px solid black;    border-collapse: collapse;}</style> </head>"+""
							+ "Tareas que vencen hoy o ma�ana:<br> <br>"+ mensaje+" <br> Este email fue enviado autom�ticamente por <i>ProcesaTask</i>.",
					"ISO-8859-1",
					"html");
			
			
			Transport t = session.getTransport("smtp");
			t.connect("taskmanager2015@gmail.com","Soyunprocesador");
			t.sendMessage(message,message.getAllRecipients());
	        Dialogs.create()
            .title("Alerta de tareas")
            .masthead("Tareas de suma urgencia")
            .message(mensaje2)
            .showConfirm();
		}
	        }
		
		
		
	public void ordenar(){
		FXCollections.sort(projectData);
	}
	
	public void ordenarT(){
		FXCollections.sort(taskData);
	}
	
	 public void initBigView() {
	        try {
	            // Load root layout from fxml file.
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(Main.class.getResource("../view/BigView.fxml"));
	            bv = (BorderPane) loader.load();

	            // Show the scene containing the root layout.
	            Scene scene = new Scene(bv);
	            ps.setScene(scene);
	            ps.show();
	            BigViewController controller = loader.getController();
	            controller.setMainApp(this);
	            
	        
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    /**
	     * Shows the person overview inside the root layout.
	     */
	    public void showTaskView() {
	        try {
	            // Load person overview.
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(Main.class.getResource("../view/TaskView.fxml"));
	            AnchorPane tv = (AnchorPane) loader.load();

	            // Set person overview into the center of root layout.
	            bv.setCenter(tv);
	            this.ps.setWidth(700);
	    		this.ps.setHeight(450);
	    		this.ps.setMaxWidth(8000);
	    		this.ps.setMaxHeight(8000);
	    		this.ps.setMinWidth(800);
	    		this.ps.setMinHeight(450);
	            // Give the controller access to the main app.
	            TaskViewController controller = loader.getController();
	            controller.setMainApp(this);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    public void showTareaView() {
	        try {
	            // Load person overview.
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(Main.class.getResource("../view/TareaView.fxml"));
	            AnchorPane tv = (AnchorPane) loader.load();

	            // Set person overview into the center of root layout.
	            bv.setCenter(tv);
	            //this.ps.setWidth(800);
	    		//this.ps.setHeight(450);
	    		this.ps.setMaxWidth(8000);
	    		this.ps.setMaxHeight(8000);
	    		this.ps.setMinWidth(800);
	    		this.ps.setMinHeight(450);
	            
	            // Give the controller access to the main app.
	            TareaViewController controller = loader.getController();
	            controller.setMainApp(this);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    public void showDashboard() {
	        try {
	            // Load person overview.
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(Main.class.getResource("../view/DashboardView.fxml"));
	            AnchorPane tv = (AnchorPane) loader.load();

	            // Set person overview into the center of root layout.
	            bv.setCenter(tv);
	            /*this.ps.setWidth(800);
	    		this.ps.setHeight(450);
	    		this.ps.setMaxWidth(8000);
	    		this.ps.setMaxHeight(4500);
	    		this.ps.setMinWidth(800);
	    		this.ps.setMinHeight(450);*/
	            
	            // Give the controller access to the main app.
	            DashboardController controller = loader.getController();
	            controller.setMainApp(this);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    public void showStatisticView() {
	        try {
	            // Load person overview.
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(Main.class.getResource("../view/StatisticView.fxml"));
	            AnchorPane tv = (AnchorPane) loader.load();

	            // Set person overview into the center of root layout.
	            bv.setCenter(tv);
	            this.ps.setWidth(800);
	    		this.ps.setHeight(450);
	    		this.ps.setMaxWidth(8000);
	    		this.ps.setMaxHeight(8000);
	    		this.ps.setMinWidth(800);
	    		this.ps.setMinHeight(450);
	            
	            // Give the controller access to the main app.
	            StatisticController controller = loader.getController();
	            controller.setMain(this);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    
	    
	    public boolean showProjectEditDialog(Proyecto proyect) {
	        try {
	            // Load the fxml file and create a new stage for the popup dialog.
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(Main.class.getResource("../view/ProjectEditDialog.fxml"));
	            AnchorPane page = (AnchorPane) loader.load();

	            // Create the dialog Stage.
	            Stage dialogStage = new Stage();
	            dialogStage.setMaxHeight(325);
	            dialogStage.setMaxWidth(350);
	            dialogStage.setMinHeight(325);
	            dialogStage.setMinWidth(350);
	            dialogStage.setTitle("Editar Proyecto");
	            dialogStage.initModality(Modality.WINDOW_MODAL);
	            dialogStage.initOwner(ps);
	            Scene scene = new Scene(page);
	            dialogStage.setScene(scene);

	            // Set the person into the controller.
	            ProjectEditDialogController controller = loader.getController();
	            controller.setDialogStage(dialogStage);
	            controller.setPerson(proyect);
	            
	            
	            //controller.setMainApp(this); //Tirar main a ProjectEditDialog, revisar!!

	            
	            // Show the dialog and wait until the user closes it
	            dialogStage.showAndWait();

	            return controller.isOkClicked();
	        } catch (IOException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    
	    public boolean showTasksInProject(Proyecto proyect) {
	        try {
	            // Load the fxml file and create a new stage for the popup dialog.
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(Main.class.getResource("../view/TasksInProjectView.fxml"));
	            AnchorPane page = (AnchorPane) loader.load();

	            // Create the dialog Stage.
	            Stage dialogStage = new Stage();
	            dialogStage.setTitle("Lista de Tareas");
	            dialogStage.initModality(Modality.WINDOW_MODAL);
	            dialogStage.initOwner(ps);
	            Scene scene = new Scene(page);
	            dialogStage.setScene(scene);
	            dialogStage.setMinWidth(400);
	            dialogStage.setMinHeight(430);

	            // Set the person into the controller.
	            TasksInProjectViewController controller = loader.getController();
	            controller.setDialogStage(dialogStage);
	            controller.setPerson(proyect);
	            controller.setMain(this);

	            // Show the dialog and wait until the user closes it
	            dialogStage.showAndWait();

	            return controller.isOkClicked();
	        } catch (IOException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    
	    public boolean showTaskEditDialog(Tarea proyect) {
	        try {
	            // Load the fxml file and create a new stage for the popup dialog.
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(Main.class.getResource("../view/TaskEditDialog.fxml"));
	            AnchorPane page = (AnchorPane) loader.load();

	            // Create the dialog Stage.
	            Stage dialogStage = new Stage();
	            dialogStage.setTitle("Editar Tarea");
	            dialogStage.initModality(Modality.WINDOW_MODAL);
	            dialogStage.initOwner(ps);
	            Scene scene = new Scene(page);
	            dialogStage.setScene(scene);
	            dialogStage.setMinWidth(340);
	            dialogStage.setMinHeight(550);
	            dialogStage.setMaxWidth(340);
	            dialogStage.setMaxHeight(550);

	            // Set the person into the controller.
	            TaskEditDialogController controller = loader.getController();
	            controller.setDialogStage(dialogStage);
	            controller.setPerson(proyect);
	            controller.setMainApp(this);

	            // Show the dialog and wait until the user closes it
	            dialogStage.showAndWait();

	            return controller.isOkClicked();
	        } catch (IOException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    
	    public boolean showEditTaskButtonDialog(Tarea proyect) {
	        try {
	            // Load the fxml file and create a new stage for the popup dialog.
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(Main.class.getResource("../view/EditTaskButtonDialog.fxml"));
	            AnchorPane page = (AnchorPane) loader.load();

	            // Create the dialog Stage.
	            Stage dialogStage = new Stage();
	            dialogStage.setTitle("Editar Tarea");
	            dialogStage.initModality(Modality.WINDOW_MODAL);
	            dialogStage.initOwner(ps);
	            Scene scene = new Scene(page);
	            dialogStage.setScene(scene);

	            // Set the person into the controller.
	            EditTaskButtonController controller = loader.getController();
	            controller.setDialogStage(dialogStage);
	            controller.setPerson(proyect);
	            controller.setMainApp(this);

	            // Show the dialog and wait until the user closes it
	            dialogStage.showAndWait();

	            return controller.isOkClicked();
	        } catch (IOException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    
	    public Stage getPrimaryStage() {
	        return ps;
	    }
	    
	    public ObservableList<Proyecto> getProyectData() {
	        return projectData;
	    }
	    
	    public ObservableList<Tarea> getTaskData() {
	        return taskData;
	    }
	    
	    public void setEmail(String x) throws FileNotFoundException, IOException{
	    email=x;
	    sereal();
	    }
	
	    public String getEmail(){
	    	return email;
	    }
	
	public static void main(String[] args) {
		launch(args);
	}
}
