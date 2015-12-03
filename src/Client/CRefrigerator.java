package Client;

import java.util.ArrayList;

public class CRefrigerator {
	
	private String name;
	private int serial;
	
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
	
	public void showFoodList()
	{
		CFood tempFood;
		for(int i = 0; i< this.getFoodList().size();i++)
		{
			tempFood =getFoodList().get(i);
			System.out.printf("%3d%7s%3f%%%5s%5s%10s\n","Num","Name","Per","Exprie","Start","Comment");
			System.out.printf("%3d%7s%3f%%%5s%5s%10s\n",tempFood.getNumber(),
					tempFood.getName(),
					tempFood.getPercent(),
					tempFood.getExprieDate().toString(),
					tempFood.getStartDate().toString(),
					tempFood.getComment());
		}
		
	}
	
	public void showFood(int index)
	{
		CFood tempFood =getFoodList().get(index);
		System.out.printf("%3d%7s%3f%%%5s%5s%10s\n","Num","Name","Per","Exprie","Start","Comment");
		System.out.printf("%3d%7s%3f%%%5s%5s%10s\n",tempFood.getNumber(),
				tempFood.getName(),
				tempFood.getPercent(),
				tempFood.getExprieDate().toString(),
				tempFood.getStartDate().toString(),
				tempFood.getComment());
	}
	


	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSerial() {
		return serial;
	}

	public void setSerial(int serial) {
		this.serial = serial;
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
