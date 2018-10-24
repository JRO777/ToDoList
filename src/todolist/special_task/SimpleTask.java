package todolist.special_task;
import todolist.Task;

/**
 * 
 * Définition d'une tâche simple
 * 
 * @author Jesus RUBIO OLVERA : jrubio2891@gmail.com
 *
 */
public class SimpleTask extends Task{
	/**
	 * 
	 * Constructeur d'une tâche
	 * 
	 * @param label Description de la tâche
	 */
	public SimpleTask(String label) {
		super(label);
	}
	
	@Override
	public String toString() {
		String done;
		
		if(isDone()) {
			done = "(effectuée)";
		}else {
			done = "(à faire)";
		}
		return "Tâche simple : "+ getLabel()+" "+ done;
	}
	
	@Override
	public String state() {
	String state;
			
			if(isDone()) {
				state = "effectuée";
			}else {
				state = "à faire";
			}
			return state;
	}
	
	
}
