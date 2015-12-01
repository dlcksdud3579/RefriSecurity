package Server;

import java.io.Serializable;
import java.util.ArrayList;

public class SRefrigerator implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private int serial;
	private String ownerID;


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
			if(this.getUserList().get(i).getID().equals( id))
				tempUser=this.getUserList().get(i);
		return tempUser;
	}
	public SFood searchFood(String name)
	{
		SFood tempFood = null;
		for(int i =0;i<this.getFoodList().size();i++)
			if(this.getFoodList().get(i).getName().equals(name))
				tempFood=this.getFoodList().get(i);
		return tempFood;
	}
	public SFood searchEmptyFood(String name)
	{
		SFood tempFood = null;
		for(int i =0;i<this.getEmptyFoodList().size();i++)
			if(this.getEmptyFoodList().get(i).getName().equals(name))
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
			foodList +=this.foodList.get(i).toString();
		
		for(int i=0; i<this.emptyFoodList.size();i++)
			emptyFoodList +=this.emptyFoodList.get(i).toString();
		
		for(int i=0; i<this.userList.size();i++)
			userList +=this.userList.get(i).getID()+"/";
		
		return name+"/"+ serial+"/"+ ownerID+"/"+"foodList/"+foodList+"emptyFoodList/" +emptyFoodList+"userList/" + userList;
	}


	
	public void DeleteeFood()
	{
		
	}
	
	public void MoveToEmptyFoodList()
	{
		
	}
	
	public void InviteOtherUser(String id,SUser user)
	{
		if(getOwnerID().equals(id) == false) //오너 넘버 체크 
			return;
		 // 초대 멤버버 
		if(user == null)
			return;
		user.addMyRefri(this);
		addUser(user);
	}
	public void KickOtherUser(String id,SUser user)
	{
		if(getOwnerID().equals(id) == false) //오너 넘버 체크 
			return;
		 // 강퇴 멤버
		if(user == null)
			return;
		user.removeMyRefri(this);
		removeUser(user);
	}
	
	public void addUser(SUser user)
	{
		this.getUserList().add(user);
	}
	public void addFood(SFood food)
	{
		this.getFoodList().add(food);
	}
	public void addEmptyFood(SFood food)
	{
		this.getEmptyFoodList().add(food);
	}
	public void removeUser(SUser user)
	{
		this.getUserList().remove(user);
	}
	public void removeFood(SFood food)
	{
		if(food == null)
			return;
		this.getFoodList().remove(food);
	}
	public void removeEmptyFood(SFood food)
	{
		if(food == null)
			return;
		this.getEmptyFoodList().remove(food);
	}
	
	
}
