package Server;

import java.util.ArrayList;

public class SRefrigerator {
	private String ownerID;
	private ArrayList<SFood> foodList;
	private ArrayList<SFood> emptyFoodList;
	private ArrayList<SUser> userList;
	
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
	
}
