package todolist;

/**
 * Définition d'un Exception
 * 
 * @author Jesus RUBIO OLVERA : jrubio2891@gmail.com
 * 
 */

public class MonException extends Exception{
protected int id;
static final long serialVersionUID = 2;

public MonException(int id,String msg) {
	super(msg);
	this.id = id;
}

public void display() {
	System.out.println("ERREUR de type "+ id + " !!!");
	System.out.println("Info : "+ getMessage());
}
	
}
