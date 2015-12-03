package Client;

import java.util.ArrayList;

import Server.SRefrigerator;

public class CUser {
	
	private ClientConsole clientConsole;
	private String ID;
	private String PW;
	private String name;
	private ArrayList<CRefrigerator> myRefrigerator;
	
	public CUser(String id, String name, String pw, ClientConsole cc) {
		setClientConsole( cc );
		setID(id);
		setPW(pw);
		setName(name);
		setMyRefrigerator(new ArrayList<CRefrigerator>());
	}
	
	public CRefrigerator searchRefrigerator(int serial)
	{
		CRefrigerator tempRefri = null;
		for(int i =0;i<this.getMyRefrigerator().size();i++)
			if(this.getMyRefrigerator().get(i).getSerial() == serial)
				tempRefri=this.getMyRefrigerator().get(i);
		return tempRefri;
	}
	 
	public void CreateRefrigerator()
	{
		
	}
	public void DeleteRefrigerator()
	{
		
	}
	
	public void loadRefrigerator()
	{
		ClientConsole cc = getClientConsole();
		int cnt = 0;
		
		while (cnt < getMyRefrigerator().size())
		{
			String msg = "getRefrigerator/" + getMyRefrigerator().get(cnt).getSerial() + "/";
			cc.accept(msg);
			
			cc.setWaitBool(true);
			while ( cc.isWaitBool() );
			
			cc.setWaitBool(true);
			while ( cc.isWaitBool() );
			
			cnt++;
		}
		
		System.out.println(">>> load Refrigerators sucess!!");
	}
	
	public void resetRefrigerator() {
		
	}
	
	
	
	
	public ClientConsole getClientConsole() {
		return clientConsole;
	}

	public void setClientConsole(ClientConsole clientConsole) {
		this.clientConsole = clientConsole;
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
