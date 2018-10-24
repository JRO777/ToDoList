package todolist;

/**
 * Classe main
 * 
 * @author Jesus RUBIO OLVERA : jrubio2891@gmail.com
 * 
 */
public class TodoList {

	public static void main(String[] args) {
		
		TaskList list = new TaskList("todolist.db");
		
		list.addTask("Test");
		list.addTask("Test_2", 1, 12, 2017, 10, 30);
		
		System.out.println(list);
		list.finalize();
		

	}

}
