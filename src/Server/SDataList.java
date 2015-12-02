package Server;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


public class SDataList implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<SUser> userList;
	private ArrayList<SRefrigerator> refriList;
	
	public ArrayList<SUser> getUserList() {
		return userList;
	}
	public void setUserList(ArrayList<SUser> userList) {
		this.userList = userList;
	}
	public ArrayList<SRefrigerator> getRefriList() {
		return refriList;
	}
	public void setRefriList(ArrayList<SRefrigerator> refriList) {
		this.refriList = refriList;
	}
	
	public SDataList() {
		super();
		userList = new ArrayList<SUser>();
		refriList = new ArrayList<SRefrigerator>();
		// 테스트 
	}
	
	// 파일 입출력 
	public void readFromFile()
	{
		try
		{
			FileInputStream fin = new FileInputStream("data.txt");
			ObjectInputStream oin = new ObjectInputStream(fin);
			
			SDataList temp = (SDataList)oin.readObject();
			
			for(int i=0;i<temp.getUserList().size();i++)
				getUserList().add(temp.getUserList().get(i));
			for(int i=0;i<temp.getRefriList().size();i++)
				getRefriList().add(temp.getRefriList().get(i));
			
			oin.close();
			fin.close();
			
			for(int i = 0; i < getUserList().size(); i++)
			{
				System.out.println("UserList"+"["+i+"]: " + getUserList().get(i).toString());
			}
			
			for(int i = 0; i < getRefriList().size(); i++)
			{
				System.out.println("RefriList"+"["+i+"]: "  + getRefriList().get(i).toString());
			}
		}
		catch(Exception exception)
		{
			System.err.println("cannot open and read the file!");
			//writeToFile();
		}
		finally
		{
		}
	}
	
	
	public void writeToFile()
	{
		try
		{
			FileOutputStream fout = new FileOutputStream("data.txt");
			ObjectOutputStream oout = new ObjectOutputStream(fout);
			
			oout.writeObject(this);

			oout.close();
			fout.close();
			
			for(int i = 0; i < getUserList().size(); i++)
			{
				System.out.println("UserList"+"["+i+"]: " + getUserList().get(i).toString());
			}
			
			for(int i = 0; i < getRefriList().size(); i++)
			{
				System.out.println("RefriList"+"["+i+"]: "  + getRefriList().get(i).toString());
			}
			
		}
		catch(Exception exception)
		{
			System.err.println("cannot open and write the file!");
		}
		finally
		{

		}
	}

	public SRefrigerator searchRefrigerator(int serial)
	{
		SRefrigerator tempRefri = null;
		for(int i =0;i<this.getRefriList().size();i++)
		{
			if(this.getRefriList().get(i).getSerial() == serial)
				tempRefri=this.getRefriList().get(i);
		}
		return tempRefri;
	}
	  
	public SUser searchSUser(String id)
	{
		SUser tempUser = null;
		for(int i =0;i<this.getUserList().size();i++)
		{
			if(this.getUserList().get(i).getID().equals(id))
				tempUser=this.getUserList().get(i);
		}
		return tempUser;
	}

	public boolean addUser(SUser user)
	{
		if(this.searchSUser(user.getID()) !=null)
			return false;
		this.getUserList().add(user);
		return true;
	}
	public void addRefri(SRefrigerator refri,String Id)
	{
		SUser tempUser = searchSUser(Id);
		if(tempUser ==null)
			return;
		int serial = this.getRefriList().size()+100; // 
		refri.setSerial(serial);
		
		refri.addUser(tempUser);
		tempUser.addMyRefri(refri);
		
		getRefriList().add(refri);
	}
	
	public void removeUser(SUser user)
	{
		if(user ==null)
			return;
		for(int i=0;i<user.getMyRefrigerator().size();i++)
			user.getMyRefrigerator().get(i).getUserList().remove(user);
		this.getUserList().remove(user);
	}
	public void removeRefri(SRefrigerator refri)
	{
		if(refri == null)
			return;
		for(int i=0;i<refri.getUserList().size();i++)
		{
			if(refri.getUserList().get(i).searchRefrigerator(refri.getSerial()) != null)
				refri.getUserList().get(i).getMyRefrigerator().remove(refri);
		}
		getRefriList().remove(refri);
	
	}
}
