package Client;

import Server.SDataList;
import Server.SUser;

public class CLogin {
///15-11-27 문섭
	
	private String tempMsg;
	private ClientConsole clientConsole;
	
	public CLogin(ClientConsole cc) {
		this.tempMsg = null;
		this.clientConsole = cc;
	}
	
	public CUser createUser(String msg) {
		//System.out.println(msg);
		this.tempMsg = msg;
		CUser tempUser = new CUser(divideString(), divideString(), divideString());
		//System.out.println(tempMsg);
		
		divideString();
		//System.out.println(tempMsg);
		
		while ( !tempMsg.equals("") )
		{
			System.out.println(tempMsg);
			String refriName = divideString();
			String refriSerial = divideString();
			CRefrigerator newRefri = new CRefrigerator();
			newRefri.setName( refriName );
			newRefri.setSerial( Integer.parseInt( refriSerial ) );
			
			tempUser.getMyRefrigerator().add(newRefri);
		}
		
		return tempUser;
	}

	
	public String divideString() //받아온 데이터  클래스의 종류를 알아내는 함수 
	  {
		  String rv = null ; // 리턴 밸류 여기에 클래스를 저장 후 
		  int i=0;
		  
		  for(; tempMsg.charAt(i) !='/';i++);
		  rv = tempMsg.substring(0, i);
		  tempMsg = tempMsg.substring(i+1, tempMsg.length());
		  //System.out.println(rv);
		  return rv;
	  }
}
