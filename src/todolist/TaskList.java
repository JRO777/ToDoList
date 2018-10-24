package todolist;
/**
 * Définition d'une liste de tâches

 * 
 * @author Jesus RUBIO OLVERA : jrubio2891@gmail.com
 *
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Calendar;

import todolist.special_task.RDV;
import todolist.special_task.SimpleTask;


public class TaskList {
	private ArrayList<Task> tasks = new ArrayList<Task>();
	private String base = null;
	private Connection connection = null;
	
	/**
	 * Constructeur de Tasklist
	 */
	public TaskList() {
		
	}
	
	/**
	 * Constructeur de TaskList
	 * 
	 * @param base Nom de la base de données
	 */
	public TaskList(String base) {
		this.base = base;
		
		try {
			//Connexion
			connection = DriverManager.getConnection("jdbc:postgresql://localhost/todolist?user=tom&password=2878");
		Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			
			//Création de la table Tasks
			/*try {
				statement.executeUpdate("create database test;" + 
			"alter database test owner to tom;");
				System.out.println("Création de la base de données test");
			}catch (SQLException e) {
				System.out.println("La base de données existait déjà");
			}*/
			
			//connection =  DriverManager.getConnection("jdbc:postgresql://localhost/test?user=tom&password=2878");
			
			//Création de la table Tasks
			
			
			
			//DROP TABLE IF NEEDED
			/*try {
				statement.executeUpdate("drop table Tasks;");
				System.out.println("Table Tasks supprimée");
			}catch (SQLException e) {
				System.out.println("La table Tasks n'a pas été supprimée");
			}*/
			
			
			try {
				statement.executeUpdate("create table Tasks (" + 
			"id serial, " + 
			"label varchar(80), state varchar(80)," + 
			"datetime timestamp);");
				System.out.println("Création de la table Tasks");
			}catch (SQLException e) {
				System.out.println("La table Tasks existait déjà");
			}
			
			//Lecture des enregistrements
			/*ResultSet rs = statement.executeQuery("select * from Tasks");
			while (rs.next()) {
				if (rs.getTimestamp("datetime") == null) {
					SimpleTask t =new SimpleTask(rs.getString("label"));
					tasks.add(t);
				}else {
					RDV rdv = new RDV(rs.getString("label"), rs.getInt("day"),
					rs.getInt("month"), rs.getInt("year"), rs.getInt("hour"),
					rs.getInt("minutes"));
				tasks.add(rdv);
				}
			}*/
		}catch(Exception e) {
			System.out.println("Erreur : " + e.getMessage());
		}
	}
	
	/**
	 * Méthode exécutée à la destruction de l'objet
	 */
	public void finalize() {
		//Déconnexion
		try {
			if(connection != null) {
				connection.close();
				System.out.println("Déconnexion de la base de données");
			}
		}catch(SQLException e) {
			System.err.println(e);
		}
	}
	
	
	/**
	 * Ajout d'une tâche
	 * 
	 * @param newTask
	 * @return <ul>
	 * 	<li>true : la tâche a été ajoutée</li>
	 * 	<li>false : la tâche n'a pas pu être ajoutée</li>
	 * </ul>
	 */
	public Boolean addTask(Task newTask) {
		//Si la base de données est utilisée il faut enregistrer la tâche
		if(base != null) {
			PreparedStatement stmt = null;
			
			try {
				if(newTask instanceof SimpleTask) {
					stmt = connection.prepareStatement(
							"insert into Tasks(label, state) values(?, ?)");
					stmt.setString(1, newTask.getLabel());
					stmt.setString(2, newTask.state());
				}else {
					stmt = connection.prepareStatement(
							"insert into Tasks(label, state, datetime) values(?, ?, ?)");
						stmt.setString(1, newTask.getLabel());
						stmt.setString(2, newTask.state());
						
				
						int intday = ((RDV) newTask).getDate().get(Calendar.DAY_OF_MONTH);
						int intmonth = ((RDV) newTask).getDate().get(Calendar.MONTH);
						int intyear = ((RDV) newTask).getDate().get(Calendar.YEAR);
						int inthour = ((RDV) newTask).getDate().get(Calendar.HOUR);
						int intminute = ((RDV) newTask).getDate().get(Calendar.MINUTE);
						
						@SuppressWarnings("deprecation")
						java.sql.Timestamp in_dateTime = new java.sql.Timestamp(intyear-1900, intmonth-1,
								intday, inthour, intminute, 0, 0);
						
						stmt.setTimestamp(3, in_dateTime);
						
					
						
				}
				stmt.executeUpdate();
					System.out.println("Ajout d'un enregistrement");
			}catch(SQLException e) {
				System.out.println("Ajout impossible : " + e.getMessage());
			}
		}
		
		return tasks.add(newTask);
			
	}
	
	/**
	 * Ajout d'une tâche simple
	 * 
	 */
	
	
	public Boolean addTask(String label) {
		SimpleTask newTask = new SimpleTask(label);
		
		return addTask(newTask);
	}
	
	
	
	/**
	 * Ajout d'un rendez-vous
	 * 
	 */
	
	public Boolean addTask(String label,int day, int month, int year, int hour, int minutes) {
		RDV newTaskRDV = new RDV(label,day,month,year,hour,minutes);
		
		return addTask(newTaskRDV);
	}
	
	
	/**
	 * 
	 * @param idTask de la tâche dans la liste 
	 * @return <ul>
	 * 	<li>true : la tâche a été notée comme effectuée</li>
	 * 	<li>false : l'état n'a pas pu être modifié</li>
	 * </ul>
	 */
	
	public Boolean taskDone(int idTask) throws MonException{
		if((idTask < 0 || (idTask >= tasks.size()))) {
			throw new MonException(1,"Index hors limites");
		}else if(tasks.get(idTask).isDone()){
			tasks.get(idTask).done();
			return false;
		} else {
			tasks.get(idTask).done();
			return true;
		}
	}
	

	
	/**
	 * Afichage d'une liste de tâches sous forme de chaîne de caractères
	 * 
	 * @return La liste des tâches et leur état
	 */
	public String toString() {
		String result ="";
		int len = tasks.size();
		
		for(int i=0;i<len;i++) {
			result += i +1 + "/" + len + ": " + tasks.get(i) + "\n";
		}
		return result;
	}

}
