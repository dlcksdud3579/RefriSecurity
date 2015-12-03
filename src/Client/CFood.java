package Client;

import java.util.Date;

public class CFood {

	private String name;
	private int number;
	private float percent;
	private Date exprieDate; 
	private Date startDate;
	private String comment;
	private ClientConsole clientConsole;
	
	
	public boolean ChangeFood(CFood food,int serial)
	{
		clientConsole.accept("changeFood/"+serial+"/"+ getName()+"/"+food.toString());
		while(clientConsole.isWaitBool());
		if(clientConsole.isFlag()==true)
		{
			return true;
		}
		return false;
	}

	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public float getPercent() {
		return percent;
	}

	public void setPercent(float percent) {
		this.percent = percent;
	}

	public Date getExprieDate() {
		return exprieDate;
	}

	public void setExprieDate(Date exprieDate) {
		this.exprieDate = exprieDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@Override
	public String toString()
	{
		return 	name+"/"+number+"/"+percent+"/"+exprieDate+"/"+startDate+"/"+comment+"/";
	}
}
