package Client;

import java.sql.Date;

public class CFood {

	private String ownerID;
	private int number;
	private float percent;
	private Date exprieDate; 
	private Date startDate;
	private String comment;
	
	public void ChangeFood()
	{
		
	}

	public String getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(String ownerID) {
		this.ownerID = ownerID;
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
	
}
