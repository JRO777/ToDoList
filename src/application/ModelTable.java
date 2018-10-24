package application;


public class ModelTable {
	
	String id, label, state, date;
	
	
	public ModelTable(String id, String label,String state,String date) {
		this.id = id;
		this.label = label;
		this.state = state;
		this.date = date;
		
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getLabel() {
		return label;
	}


	public void setLabel(String label) {
		this.label = label;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}
	
	
	
	

}
