package Server;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.util.Date;

import Server.*;



/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  private SDataList m_data; // 찬영 추가 
  
  private String tempMsg;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port) 
  {
    super(port);
    // 찬영 추가 
    m_data = new SDataList();
    m_data.readFromFile();
  }

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient
    (Object msg, ConnectionToClient client)
  {
	  
	tempMsg= (String)msg; 
	process(client);
    System.out.println("Message received: " + tempMsg + " from " + client);
    m_data.writeToFile();
    
  }
  
  
    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }
  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */
  public static void main(String[] args) 
  {
	  
    int port = 0; //Port to listen on
    // 찬영 추가
     // 데이터 생성;
    // 찬영 추가 
    
    try
    {
      port = Integer.parseInt(args[0]); //Get port from command line
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT; //Set port to 5555
    }
	
    EchoServer sv = new EchoServer(port);
    
    try 
    {
      sv.listen(); //Start listening for connections
    } 
    catch (Exception ex) 
    {
      System.out.println("ERROR - Could not listen for clients!");
    }
  }
  @Override
  protected void clientConnected(ConnectionToClient client) {}

  
  @Override
  synchronized protected void clientDisconnected(
    ConnectionToClient client) {}
  @Override
  synchronized protected void clientException(
		    ConnectionToClient client, Throwable exception) {}
  

  /// 찬영 추가 
  public void sendtoOneClient(Object msg,ConnectionToClient client) // 원하는 클라이언트에게만 전송 
  {
	   try
	   {
	    	client.sendToClient(msg);
	   }
	   catch (Exception ex) {}
  }
  public String divideString() //받아온 데이터  클래스의 종류를 알아내는 함수 
  {
	  String rv = null ; // 리턴 밸류 여기에 클래스를 저장 후 
	  int i=0;
	  
	  for(; tempMsg.charAt(i) !='/';i++);
	  rv = tempMsg.substring(0, i);
	  tempMsg = tempMsg.substring(i+1, tempMsg.length());
	  System.out.println(rv);
	  return rv;
  }
  
  public void process(ConnectionToClient client)
  {
	  String command =divideString();
	  SUser tempUser = null;
	  SFood tempFood = null;
	  SRefrigerator tempRefri = null; 
	  String tempMsg = ""; // client에게 보낼 메세지
	switch(command)
	{
	case "addUser": // /유저추가/ addUser/User.Tostirng (유저 아이디/비밀번호/이름/)
		tempUser = new SUser();
		tempUser.setID(divideString());
		tempUser.setPW(divideString());
		tempUser.setName(divideString());
		if( m_data.addUser(tempUser)== false) // 유저 추가 
			break;
		sendtoOneClient("true",client);
		return;
		
	case "addRefri":
	case "addRefrigerator":  // 냉장고 추추추추가  //  addRefri/이름/냉장고 오너아이디/
		tempRefri = new SRefrigerator();
		tempRefri.setName(divideString());
		String OwnerID = divideString();
		m_data.addRefri(tempRefri,OwnerID); // 레프리 추가 
		sendtoOneClient("true",client);
		return;
		
	case "addFood": // 푸드추가//   addFood/내장고시리얼/food.toString(이름/넘버/퍼센트/유통기한/ 보관시작/코멘트/)
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString())); // 음식이 들어갈 냄장고시리얼 넘버 
		if(tempRefri == null)
			break;
		tempFood = new SFood();
		tempFood.setName(divideString());						// 이름
		tempFood.setNumber(Integer.parseInt(divideString()));  	// 넘버  
		tempFood.setPercent(Float.parseFloat(divideString()));	// 퍼센트 
		tempFood.setExprieDate(new Date(Integer.parseInt(divideString()),
				Integer.parseInt(divideString()),
				Integer.parseInt(divideString())));	// 유통기한 
		tempFood.setStartDate(new Date(Integer.parseInt(divideString()),
				Integer.parseInt(divideString()),
				Integer.parseInt(divideString())));		// 보관시간
		tempFood.setComment(divideString());	// 코멘트
		
		tempRefri.addFood(tempFood); // 푸드 추가 
		sendtoOneClient("true",client);
		return;
		
		/*// 삭제
	case "addEmptyFood": //빈 푸트 추가  / addEmptyFood/냉장고시리얼/ food.tostring(이름/넘버/퍼센트/유통기한/ 보관시작/코멘트/)
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));// 음식이 들어갈 냄장고넘버 
		if(tempRefri == null)
			break;
		tempFood = new SFood();
		tempFood.setName(divideString());	// 이름 
		tempFood.setNumber(Integer.parseInt(divideString()));  	// 넘버  
		tempFood.setPercent(Float.parseFloat(divideString()));	// 퍼센트 
		tempFood.setExprieDate(new Date(Integer.parseInt(divideString()),
				Integer.parseInt(divideString()),
				Integer.parseInt(divideString())));	// 유통기한 
		tempFood.setStartDate(new Date(Integer.parseInt(divideString()),
				Integer.parseInt(divideString()),
				Integer.parseInt(divideString())));	;		// 보관시간
		tempFood.setComment(divideString());						// 코멘트
		
		tempRefri.addEmptyFood(tempFood); // 푸드 추가 
		break;
			*/
		
	case "removeUser": // 죽어라 유저  / removeUser/유저네임/
		tempUser =m_data.searchSUser(divideString());
		m_data.removeUser(tempUser);
		sendtoOneClient("true",client);
		return;
		
	case "removeRefri": // 사라져라 냉자고 /removeRefri/냉장고시리얼/
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		m_data.removeRefri(tempRefri);
		sendtoOneClient("true",client);
		return;
		
	case "removeFood": // 음식 소멸  /removeFood/냉장고시리얼/음식이름/
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		if(tempRefri == null)
			break;
		tempFood = tempRefri.searchFood(divideString());
		if(tempFood == null)
			break;
		
		tempRefri.removeFood(tempFood);
		sendtoOneClient("true",client);
		return;
		
		/*// 삭제 
	case "removeEmptyFood": // 빈음식 삭제  /removeEmptyFood/냉장고시리얼/음식이름/
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		if(tempRefri == null)
			break;
		tempFood= tempRefri.searchEmptyFood(divideString());
		tempRefri.removeEmptyFood(tempFood);
		break;
		*/
		
	case "removeMyRefri": //자신의 냉장고를 삭제를 하는 함수를 부르는 함수를 부르른 부르르는 느느는  거  removeMyRefri /유저네임/냉장고시리얼/
		tempUser = m_data.searchSUser(divideString());
		if(tempUser == null)
			break;
		tempRefri = tempUser.searchRefrigerator(Integer.parseInt(divideString()));
		tempUser.removeMyRefri(tempRefri);
		if(tempRefri.getUserList().get(0).equals(tempUser.getID()))
			m_data.removeRefri(tempRefri);
		sendtoOneClient("true",client);
		return;
		
	case "InviteRefri": // 아몰랑 초대 /InviteRefri/냉장고 시리얼/오너아이디 / 초대할사람아이디
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		if(tempRefri == null)
			break;
		tempUser = m_data.searchSUser(divideString());
		tempRefri.InviteOtherUser(divideString(),tempUser);
		sendtoOneClient("true",client);
		return;
		
	case "KickRefri": // 아몰랑 나가 /InviteRefri/냉장고 시리얼/오너아이디/초대할사람아이디
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		if(tempRefri == null)
			break;
		tempUser = m_data.searchSUser(divideString());
		tempRefri.KickOtherUser(divideString(),tempUser);
		sendtoOneClient("true",client);
		return;
		
	case "checkEmptyFoodList":  // 냉장고에 있는 오래된 빈 음식 리스트 삭제   / checkEmptyFoodList/냉장고시리얼/
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		if(tempRefri == null)
			break;
		tempRefri.checkEmptyFoodList();
		sendtoOneClient("true",client);
		return;
		
	case "changeFood": // 음식 정보 체인지  /    changeFood/시리얼/푸드이름/푸드이름/
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		if(tempRefri == null)
			break;
		tempFood = tempRefri.searchFood(divideString());
		if(tempFood == null)
			break;
		SFood chgFood = new SFood();
		chgFood.setName(divideString());						// 이름
		chgFood.setNumber(Integer.parseInt(divideString()));  	// 넘버  
		chgFood.setPercent(Float.parseFloat(divideString()));	// 퍼센트 
		chgFood.setExprieDate(new Date(Integer.parseInt(divideString()),
				Integer.parseInt(divideString()),
				Integer.parseInt(divideString())));	// 유통기한 
		chgFood.setStartDate(new Date(Integer.parseInt(divideString()),
				Integer.parseInt(divideString()),
				Integer.parseInt(divideString())));		// 보관시간
		chgFood.setComment(divideString());	// 코멘트
		
		tempFood.ChangeFood(chgFood);
		sendtoOneClient("true",client);
		return;
		
	case "getUser": // 원하는 유저 정보 받오는    getUser/유저아이디/비밀번호/
		tempUser = m_data.searchSUser(divideString()); // 유저 아이디로 서치 
		if(tempUser == null)
			break;
		if(tempUser.getPW().equals(divideString()))
			break;
		tempMsg = "User/"+tempUser.toString();
		sendtoOneClient("true",client);
		sendtoOneClient(tempMsg,client); // 클라이언트로 전송 
		return;
		
	case "getRefrigerator": // 원하는 냉자고 정보 받아오는   / getRefrigerator/ 냉장고시리얼/
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		if(tempRefri ==null)
			break;
		tempMsg = "Refrigerator/"+tempRefri.toString();
		sendtoOneClient("true",client);
		sendtoOneClient(tempMsg,client);// 클라이언트로 전송 
		return;
		
	case "getFood": // 음식 정보 받아오는   /getFood/냉장고시리얼/음식이름/
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		if(tempRefri ==null)
			break;
		tempFood = tempRefri.searchFood(divideString());
		if(tempFood ==null)
			break;
		tempMsg = "Food/"+tempFood.toString();
		sendtoOneClient("true",client);
		sendtoOneClient(tempMsg,client);// 클라이언트로 전송 
		return ;
		
	case "getUserList": // 유저리스트를    / getUserList/
		tempMsg = "UserList/";
		for(int i=0;i<m_data.getUserList().size();i++)
			tempMsg += m_data.getUserList().get(i).toString();
		sendtoOneClient("true",client);
		sendtoOneClient(tempMsg,client);// 클라이언트로 전송 
		return;
		
	case "getRefrigeratorList": // 냉장고 리스트를  /getRefrigeratorList/
		tempMsg = "RefrigeratorList/";
		for(int i=0;i<m_data.getRefriList().size();i++)
			tempMsg += m_data.getRefriList().get(i).toString();
		sendtoOneClient("true",client);
		sendtoOneClient(tempMsg,client);// 클라이언트로 전송 
		return;
		
	case "getFoodList": // 원하는 냉장고의 음식 리스트를   getFoodList/냉장고 시리얼/
		tempMsg = "FoodList/";
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		if(tempRefri ==null)
			break;
		
		for(int i=0;i<tempRefri.getFoodList().size();i++)
			tempMsg += tempRefri.getFoodList().get(i).toString();
		sendtoOneClient("true",client);
		sendtoOneClient(tempMsg,client);// 클라이언트로 전송 
		return;
		
	case "getEmptyFoodList": // 빈음식리스트트트트 보냄 클라로   /getEmptyFoodList/냉작고시리얼/
		tempMsg = "EmptyFoodList/";
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		if(tempRefri == null)
			break;
		for(int i=0;i<tempRefri.getEmptyFoodList().size();i++)
			tempMsg += tempRefri.getEmptyFoodList().get(i).toString();
		sendtoOneClient("true",client);
		sendtoOneClient(tempMsg,client);// 클라이언트로 전송 
		
		return;
		
	case "getEmptyfood": //빈 푸드 삭제  /getEmptyfood/냉장고넘버/푸드이름/
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		if(tempRefri == null)
			break;
		tempFood = tempRefri.searchEmptyFood(divideString());
		if(tempFood == null)
			break;
		tempMsg ="Emptyfood/"+tempFood.toString();
		sendtoOneClient("true",client);
		sendtoOneClient(tempMsg,client);// 클라이언트로 전송 
		return;
		 
	case "getUserRefriList": //유저의 냉장고리스트를 /getUserRefri/유저이름 /
		tempMsg = "UserRefriList/";
		tempUser = m_data.searchSUser(divideString());
		if(tempUser == null)
			break;
		for(int i=0;i<tempUser.getMyRefrigerator().size();i++)
			tempMsg += tempUser.getMyRefrigerator().get(i).toString();
		sendtoOneClient("true",client);
		sendtoOneClient(tempMsg,client);// 클라이언트로 전송 
		return;
		
	case "getUserRefri": //유저의 냉장고리스트를 /getUserRefri/유저이름 /냉장고 시리얼
		tempMsg = "UserRefri/";
		tempUser = m_data.searchSUser(divideString());
		if(tempUser == null)
			break;
		tempRefri = tempUser.searchRefrigerator(Integer.parseInt(divideString()));
		if(tempRefri == null)
			break;
		tempMsg += tempRefri.toString();
		sendtoOneClient("true",client);
		sendtoOneClient(tempMsg,client);// 클라이언트로 전송 
		return;
		
		
	default:
		break;
	} 
	sendtoOneClient("false",client);
  }
}
//End of EchoServer class
