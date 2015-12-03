package Client;

import java.util.ArrayList;

public class CUser {

	private String ID;
	private String PW;
	private String name;
	private ArrayList<CRefrigerator> myRefrigerator;
	
	public CUser(String id, String name, String pw) {
		setID(id);
		setPW(pw);
		setName(name);
	}
	
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
	public ArrayList<CRefrigerator> getMyRefrigerator() {
		return myRefrigerator;
	}
	public void setMyRefrigerator(ArrayList<CRefrigerator> myRefrigerator) {
		this.myRefrigerator = myRefrigerator;
	}
}
