package Server;

import java.io.Serializable;
import java.util.ArrayList;

public class SRefrigerator implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ownerID;
	private String name;
	private int serial;

	private ArrayList<SFood> foodList;
	private ArrayList<SFood> emptyFoodList;
	private ArrayList<SUser> userList;
	
	

	public int getSerial() {
		return serial;
	}

	public void setSerial(int serial) {
		this.serial = serial;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public SRefrigerator() {
		super();
		foodList = new ArrayList<SFood>();
		emptyFoodList = new ArrayList<SFood>();
		userList = new ArrayList<SUser>();
		
	}

	public void CreateFood()
	{
		
	}
	
	public void DeleteeFood()
	{
		
	}
	
	public void MoveToEmptyFoodList()
	{
		
	}
	
	public void InviteOtherUser()
	{
		
	}

	public String getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(String ownerID) {
		this.ownerID = ownerID;
	}

	public ArrayList<SFood> getFoodList() {
		return foodList;
	}

	public void setFoodList(ArrayList<SFood> foodList) {
		this.foodList = foodList;
	}

	public ArrayList<SFood> getEmptyFoodList() {
		return emptyFoodList;
	}

	public void setEmptyFoodList(ArrayList<SFood> emptyFoodList) {
		this.emptyFoodList = emptyFoodList;
	}

	public ArrayList<SUser> getUserList() {
		return userList;
	}

	public void setUserList(ArrayList<SUser> userList) {
		this.userList = userList;
	}

	public SUser searchSUser(String id)
	{
		SUser tempUser = null;
		for(int i =0;i<this.getUserList().size();i++)
			if(this.getUserList().get(i).getID() == id)
				tempUser=this.getUserList().get(i);
		return tempUser;
	}
	public SFood searchFood(String name)
	{
		SFood tempFood = null;
		for(int i =0;i<this.getFoodList().size();i++)
			if(this.getFoodList().get(i).getName() == name)
				tempFood=this.getFoodList().get(i);
		return tempFood;
	}
	public SFood searchEmptyFood(String name)
	{
		SFood tempFood = null;
		for(int i =0;i<this.getEmptyFoodList().size();i++)
			if(this.getEmptyFoodList().get(i).getName() == name)
				tempFood=this.getEmptyFoodList().get(i);
		return tempFood;
	}
	 
	@Override
	public String toString()
	{
		String foodList ="";
		String emptyFoodList = "";
		String userList = "";
		
		for(int i=0; i<this.foodList.size();i++)
			foodList +=this.foodList.get(i).toString()+"/";
		
		for(int i=0; i<this.emptyFoodList.size();i++)
			emptyFoodList +=this.emptyFoodList.get(i).toString()+"/";
		
		for(int i=0; i<this.userList.size();i++)
			userList +=this.userList.get(i).getID()+"/";
		
		return  ownerID+"/foodList/"+foodList+"/emptyFoodList/" +emptyFoodList+"/userList/" + userList+"/";
	}
	
}
