package todolist.special_task;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import todolist.Task;
import java.util.Calendar;

/**

 * 
 * @author Jesus RUBIO OLVERA : jrubio2891@gmail.com
 * 
 */

public class RDV extends Task{
	
	protected GregorianCalendar date;
	
	public GregorianCalendar getDate() {
		return date;
	}
	
	public RDV(String label, int day, int month, int year, int hour, int minutes) {
		super(label);
		date=new GregorianCalendar(TimeZone.getTimeZone("Europe/Paris"));
		date.set(year, month, day, hour, minutes, 0);
		
	}

	
	/**
	 * Affichage d'un rendez-vous sous forme de chaîne de caractères
	 * 
	 * @return Le nom du RDV suivi de la date, de l'heure et de l'état
	 * 
	 */
	
	@Override
	public String toString() {
		String done;
		
		if(isDone()) {
			done = "(effectuée)";
		}else {
			done = "(à faire)";
		}
		return "Rendez-vous \""+ getLabel()+"\" le " + date.get(Calendar.DAY_OF_MONTH)+ "/"+ date.get(Calendar.MONTH)+ "/"+date.get(Calendar.YEAR)+ " à " + date.get(Calendar.HOUR)+ ":"+ date.get(Calendar.MINUTE)+ " "+ done;
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
	
	/**
	 * Vérifie si un rendez-vous est dépassé ou non
	 * 
	 * @return <ul>
	 * 	<li>true : rendez-vous dépasé</li>
	 * 	<li>false : rendez-vous toujours valide</li>
	 * </ul>
	 */
	
	public Boolean isLate() {
		GregorianCalendar now = new GregorianCalendar(TimeZone.getTimeZone("Europe/Paris"));
		if(date.compareTo(now) == -1) {
			return true;
		}else {
			return false;
		}
	}

}
