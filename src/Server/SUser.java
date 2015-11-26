package Server;

import java.util.ArrayList;

public class SUser {

	private String ID;
	private String PW;
	private String name;
	private ArrayList<SRefrigerator> myRefrigerator;
	
	public void CreateRefrigerator()
	{
		
	}
	public void DeleteRefrigerator()
	{
		
	}
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getPW() {
		return PW;
	}
	public void setPW(String pW) {
		PW = pW;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<SRefrigerator> getMyRefrigerator() {
		return myRefrigerator;
	}
	public void setMyRefrigerator(ArrayList<SRefrigerator> myRefrigerator) {
		this.myRefrigerator = myRefrigerator;
	}
}
