package Client;

import java.util.ArrayList;

public class CRefrigerator {
	
	private String name;
	private int serial;
	private ClientConsole clientConsole;
	
	private ArrayList<CFood> foodList;
	private ArrayList<CFood> emptyFoodList;
	
	public CRefrigerator() {
		setName(null);
		setSerial(0);
		setClientConsole(null);
		setFoodList(new ArrayList<CFood>());
		setEmptyFoodList(new ArrayList<CFood>());
	}
	
	public CRefrigerator(String name, int serial,
			ArrayList<CFood> foodList, ArrayList<CFood> emptyFoodList,
			ClientConsole clientConsole) {
		super();
		this.name = name;
		this.serial = serial;
		this.clientConsole = clientConsole;
		this.foodList = foodList;
		this.emptyFoodList = emptyFoodList;
	}

	public boolean getRefriInfoFromServer()
	{
		clientConsole.accept("getRefrigerator/"+serial+"/");
		clientConsole.setWaitBool(true);
		while(clientConsole.isWaitBool());
		if(clientConsole.isFlag()==true)
		{
			return true;
		}
		return false;
	}
	
	public boolean CreateFood(CFood food)
	{
		clientConsole.accept("addFood/"+serial+"/"+food.toString());
		clientConsole.setWaitBool(true);
		while(clientConsole.isWaitBool());
		if(clientConsole.isFlag()==true)
		{
			if(getRefriInfoFromServer() ==true)
				return false;
			return true;
		}
		return false;
		
	}
	
	public boolean DeleteFood(CFood food)
	{
		clientConsole.accept("removeFood/"+serial+"/"+food.getName()+"/");
		clientConsole.setWaitBool(true);
		while(clientConsole.isWaitBool());
		if(clientConsole.isFlag()==true)
		{
			if(getRefriInfoFromServer() ==true)
				return false;
			return true;
		}
		return false;
	}
	

	
	public boolean InviteOtherUser(CUser onrUser,CUser cuUser)
	{
		clientConsole.accept("InviteRefri/"+serial+"/"+onrUser.getID() + "/"+ cuUser.getID()+"/");
		clientConsole.setWaitBool(true);
		while(clientConsole.isWaitBool());
		if(clientConsole.isFlag()==true)
		{
			if(getRefriInfoFromServer() ==true)
				return false;
			return true;
		}
		return false;
	}
	public boolean KickOtherUser(CUser onrUser,CUser cuUser)
	{
		clientConsole.accept("KickRefri/"+serial+"/"+onrUser.getID() + "/"+ cuUser.getID()+"/");
		clientConsole.setWaitBool(true);
		while(clientConsole.isWaitBool());
		if(clientConsole.isFlag()==true)
		{
			if(getRefriInfoFromServer() ==true)
				return false;
			return true;
		}
		return false;
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


	public ClientConsole getClientConsole() {
		return clientConsole;
	}

	public void setClientConsole(ClientConsole clientConsole) {
		this.clientConsole = clientConsole;
	}
	
}
