package application;
	
import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import todolist.*;


public class Main extends Application implements EventHandler<ActionEvent>{
	
	TaskList list = new TaskList("todolist.db");
	private String Areatext = null; 
	private Integer Day = null, Month = null, Year = null, Hour = null, Minute = null; 
	Connection connection = null;
	

	
	@FXML
	private Button button_addtask, button_RDV, button_deleteTask, button_markDone, button_refresh;
	
	@FXML
	private TextArea taskArea;
	
	@FXML
	private TextField textday, textmonth, textyear, texthour, textminute;
	
	@FXML
	private TableView<ModelTable> tableview = null;
	
	@FXML
	private TableColumn<ModelTable, String> id_column, label_column, state_column, dateTime_column;
	
	@FXML
	private ObservableList<ModelTable> data = FXCollections.observableArrayList();
	
	
	@Override
	public void start(Stage primaryStage) {
		
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root,740,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);	
		
	}
	
	public void initialize() {
		button_addtask.setDisable(true);
		button_RDV.setDisable(true);
		
		    tableview.getItems().clear();
		
		
		try {
		connection = DriverManager.getConnection("jdbc:postgresql://localhost/todolist?user=tom&password=2878");
		Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			
		
			ResultSet rs = statement.executeQuery("select * from Tasks");
			
			while(rs.next()) {
				data.add(new ModelTable(rs.getString("id"),rs.getString("label"),rs.getString("state"),
						rs.getString("datetime")));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error while getting the database");
			
		}
		
		id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
		label_column.setCellValueFactory(new PropertyValueFactory<>("label"));
		state_column.setCellValueFactory(new PropertyValueFactory<>("state"));
		dateTime_column.setCellValueFactory(new PropertyValueFactory<>("date"));
		
		tableview.setItems(data);
	
		 
	}
	
	
	//Méthode pour "disable" ou "enable" les bouttons
	public void keyReleased() {
		
		Areatext = taskArea.getText();
		
		
		if(Areatext.isEmpty()) {
			button_addtask.setDisable(true);
		}else {
			button_addtask.setDisable(false);
		}
		
		if(textyear.getText().isEmpty()) {
			button_RDV.setDisable(true);
			button_addtask.setDisable(false);
		}else {
			button_RDV.setDisable(false);
			button_addtask.setDisable(true);
		}
		
		
		/*Areatext = taskArea.getText();
		Boolean isDisabled = (Areatext.isEmpty()||Areatext.trim().isEmpty());
		
		button_addtask.setDisable(isDisabled);*/
		
	}
	
	
	@Override
	public void handle(ActionEvent event) {
		
		if(event.getSource() == button_addtask) {
		
			
				Areatext = taskArea.getText();
				
			
				list.addTask(Areatext);
				taskArea.clear();
				initialize();
		
			//System.out.println(list);
			//list.finalize();
			
			System.out.println("Task added");
		}
	
		if(event.getSource() == button_RDV) {
			
			
			Areatext = taskArea.getText();
			Day = Integer.valueOf(textday.getText());
			Month = Integer.valueOf(textmonth.getText());
			Year = Integer.valueOf(textyear.getText());
			Hour = Integer.valueOf(texthour.getText());
			Minute = Integer.valueOf(textminute.getText());
			
			
			list.addTask(Areatext, Day, Month, Year, Hour, Minute);
			taskArea.clear();
			textday.clear();
			textmonth.clear();
			textyear.clear();
			texthour.clear();
			textminute.clear();
			initialize();
			
			System.out.println("RDV added");
		}
		
		if(event.getSource() == button_deleteTask) {
			
			//Supprime tâche dans GUI
			ObservableList<ModelTable> alltasks, singletask;
			alltasks = tableview.getItems();
			
			int intsingletask = tableview.getSelectionModel().getSelectedIndex();
			
			String id_deleted = id_column.getCellData(intsingletask);
			
			singletask = tableview.getSelectionModel().getSelectedItems();
			singletask.forEach(alltasks::remove);
			
			//SUpprime tâche dans la base de données "todolist"
			if(id_deleted == null) {
				System.out.println("None task was selected");
			}else {
			try {
				connection = DriverManager.getConnection("jdbc:postgresql://localhost/todolist?user=tom&password=2878");
				Statement statement = connection.createStatement();
					statement.setQueryTimeout(30);
					
					
					statement.executeQuery("delete from Tasks where id = " + id_deleted + ";");
					
					
					
					System.out.println("Task deleted");
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					System.out.println(" ");
					
				}
			System.out.println("Task #" + id_deleted + " deleted");
			}
		}
	
		if(event.getSource() == button_markDone) {
			
			//Change une tâche à effectuée dans le GUI
			
			int intsingletask = tableview.getSelectionModel().getSelectedIndex();
			
			String id_change = id_column.getCellData(intsingletask);
			
			
			//Change une tâche à effectuée dans la base de données "todolist"
			if(id_change == null) {
				System.out.println("None task was selected");
			}else {
			try {
				connection = DriverManager.getConnection("jdbc:postgresql://localhost/todolist?user=tom&password=2878");
				Statement statement = connection.createStatement();
					statement.setQueryTimeout(30);
					
					
					statement.executeQuery("update Tasks set state = 'effectuée' where id = " + id_change + ";");
					
					
					System.out.println("Task marked as Done");
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					System.out.println(" ");
					
				}
			System.out.println("Task #" + id_change + " marked as Done");
			}
			
		}
		
		initialize();
		
	}
	
}