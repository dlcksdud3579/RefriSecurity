package Server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class SRefrigerator implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
		
		return name+"/"+ serial +"/"+"foodList/"+foodList+"emptyFoodList/" +emptyFoodList+"userList/" + userList;
	}
	
	public void MoveToEmptyFoodList(SFood food)
	{
		food.setStartDate(new Date());
		addEmptyFood(food);
	}
	
	public void checkEmptyFoodList()
	{
		Date CurData =  new Date();
		int CurDay = CurData.getYear()*365 + CurData.getMonth()*30+CurData.getDay();
		Date FoodDate = null;
		int FoodDay =0;;
		for(int i=0;i<this.getEmptyFoodList().size();i++)
		{
			FoodDate = this.getEmptyFoodList().get(i).getStartDate();
			FoodDay = FoodDate.getYear()*365 + FoodDate.getMonth()*30+FoodDate.getDay();
			
			if(CurDay -FoodDay >= 7)
			{
				removeEmptyFood(getEmptyFoodList().get(i));
			}
		}
				
	}
	
	public void InviteOtherUser(String id,SUser user)
	{
		if(this.getUserList().get(0).getID().equals(id) == false) //���� �ѹ� üũ 
			return;
		 // �ʴ� ����� 
		if(user == null)
			return;
		user.addMyRefri(this);
		addUser(user);
	}
	public void KickOtherUser(String id,SUser user)
	{
		if(this.getUserList().get(0).getID().equals(id) == false) //���� �ѹ� üũ 
			return;
		 // ���� ���
		if(user == null)
			return;
		user.removeMyRefri(this);
		removeUser(user);
	}
	
	public void addUser(SUser user)
	{
		this.getUserList().add(user);
	}
	public boolean addFood(SFood food)
	{
		if(this.searchFood(food.getName()) != null)
			return false;
		this.getFoodList().add(food);
		return true;
	}
	public boolean addEmptyFood(SFood food)
	{
		if(this.searchEmptyFood(food.getName()) != null)
			return false;
		this.getEmptyFoodList().add(food);
		return true;
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
