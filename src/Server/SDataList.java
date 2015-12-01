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
			if(this.getRefriList().get(i).getSerial() == serial)
				tempRefri=this.getRefriList().get(i);
		return tempRefri;
	}
	 
	public SUser searchSUser(String id)
	{
		SUser tempUser = null;
		for(int i =0;i<this.getUserList().size();i++)
			if(this.getUserList().get(i).getID() == id)
				tempUser=this.getUserList().get(i);
		return tempUser;
	}
	 
	
}
