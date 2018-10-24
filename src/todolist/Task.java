package todolist;
/*
 * Définition d'une tâche
 * 
 * @author Jesus RUBIO OLVERA: jrubio2891@gmail.com
 * 
 * */
public abstract class Task {
	
	private String label;
	private Boolean state;
	
	/**
	 * Accesseurs et Modificateurs des attributs
	 * 
	 */
	
	protected String getLabel() {
		return label;
	}
	
	protected Boolean getState() {
		return state;
	}
	
	protected void setLabel(String label) {
		this.label=label;
	}
	
	protected void setState(Boolean state) {
		this.state =state;
	}

	
	/**
	 * Constructeur d'une tâche
	 * 
	 * @param label Description de la tâche
	 * @param state Etat de la tâche
	 */
	
	public Task(String label) {
		setLabel(label);
		setState(false);
		
	}
	
	
	/**
	 * Passage d'une tâche à l'état "fait"
	 */
	public void done() {
		setState(true);
	}
	
	/**
	 * Indique l'état de la tâche
	 * 
	 * @return Etat de la tâche :<ul>
	 * 	<li>true : fait</li>
	 * 	<li>false : à faire</li>
	 * </ul>
	 */
	
	public Boolean isDone() {
		return getState();
	}
	
	/**
	 * Affichage d'une tâche sous forme de chaîne de caractères
	 * 
	 * @return Le nom de la tâche suivi de son état
	 */
	
	public abstract String toString();
	
	public abstract String state();
	
}
