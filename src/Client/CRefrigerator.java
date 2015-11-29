package Client;

import java.util.ArrayList;

public class CRefrigerator {
	private String ownerID;
	private ArrayList<CFood> foodList;
	private ArrayList<CFood> emptyFoodList;
	private ArrayList<CUser> userList;
	
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

	public ArrayList<CFood> getFoodList() {
		return foodList;
	}

	public void setFoodList(ArrayList<CFood> foodList) {
		this.foodList = foodList;
	}

	public ArrayList<CFood> getEmptyFoodList() {
		return emptyFoodList;
	}

	public void setEmptyFoodList(ArrayList<CFood> emptyFoodList) {
		this.emptyFoodList = emptyFoodList;
	}

	public ArrayList<CUser> getUserList() {
		return userList;
	}

	public void setUserList(ArrayList<CUser> userList) {
		this.userList = userList;
	}
	
}
