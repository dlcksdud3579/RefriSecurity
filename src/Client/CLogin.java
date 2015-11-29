package Client;

import Server.SDataList;
import Server.SUser;

public class CLogin {
///15-11-27 ����
	
	private SDataList userList = new SDataList();///logincheck�� ������(?)
	////�α��� �� ���� signup�� ��, �Է¹��� id,pw
	private String ID;
	private String PW;
	
	private String name;///signup�� ��, �Է¹��� name
	private SUser user;
	
	public CLogin(String iD, String pW) {
		super();
		ID = iD;
		PW = pW;
	}// login�� �ʿ��� ����
	
	public CLogin(String iD, String pW, String name) {
		super();
		ID = iD;
		PW = pW;
		this.name = name;
	}// signup�� �ʿ��� ����


	public void SignUp()
	{
		user = new SUser();
		
		user.setID(getID());
		user.setPW(getPW());
		user.setName(getName());
		//user�� id,pw,name�� set
		
		getUserList().getUserList().add(getUser());
		//userlist�� �߰�
	}
	
	public int LoginCheck()
	{
		for(int i = 0; i < getUserList().getUserList().size(); i++)
		{
			if(!getUserList().getUserList().get(i).getID().equals(getID()))
			{
				return -1;//id error
			}
			else if(!getUserList().getUserList().get(i).getPW().equals(getPW()))
			{
				return -2;//pw error
			}
			else
			{
				return 1;///�α��� ����
			}
		}
		return 0;//default
	}
	
	public int SignUpCheck()
	{
		for(int i = 0; i < getUserList().getUserList().size(); i++)
		{
			if(getUserList().getUserList().get(i).getID().equals(getID()))
			{
				return -1;//id �ߺ�
			}
			else
			{
				return 1;//singup����
			}
		}
		return 0;//default
	}

	public SDataList getUserList() {
		return userList;
	}

	public String getID() {
		return ID;
	}

	public String getPW() {
		return PW;
	}

	public String getName() {
		return name;
	}

	public SUser getUser() {
		return user;
	}

	public void setUserList(SDataList userList) {
		this.userList = userList;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public void setPW(String pW) {
		PW = pW;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUser(SUser user) {
		this.user = user;
	}

	
	
}
