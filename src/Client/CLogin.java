package Client;

import Server.SDataList;
import Server.SUser;

public class CLogin {
///15-11-27 문섭
	
	private SDataList userList = new SDataList();///logincheck할 데이터(?)
	////로그인 할 때와 signup할 때, 입력받을 id,pw
	private String ID;
	private String PW;
	
	private String name;///signup할 때, 입력받을 name
	private SUser user;
	
	public CLogin(String iD, String pW) {
		super();
		ID = iD;
		PW = pW;
	}// login에 필요한 정보
	
	public CLogin(String iD, String pW, String name) {
		super();
		ID = iD;
		PW = pW;
		this.name = name;
	}// signup에 필요한 정보


	public void SignUp()
	{
		user = new SUser();
		
		user.setID(getID());
		user.setPW(getPW());
		user.setName(getName());
		//user에 id,pw,name을 set
		
		getUserList().getUserList().add(getUser());
		//userlist에 추가
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
				return 1;///로그인 성공
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
				return -1;//id 중복
			}
			else
			{
				return 1;//singup가능
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
