package Server;

import java.io.Serializable;
import java.util.ArrayList;

public class SUser implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
	
	public SRefrigerator searchRefrigerator(int serial)
	{
		SRefrigerator tempRefri = null;
		for(int i =0;i<this.getMyRefrigerator().size();i++)
			if(this.getMyRefrigerator().get(i).getSerial() == serial)
				tempRefri=this.getMyRefrigerator().get(i);
		return tempRefri;
	}
	 
	
	@Override
	public String toString()
	{
		String refri = "";
		
		for(int i =0;i<this.getMyRefrigerator().size();i++)
			refri +=getMyRefrigerator().get(i).getSerial()+"/";
		
		return ID+"/"+name+"/"+PW+"/myRefrigerator/"+refri;
	}
	
}
