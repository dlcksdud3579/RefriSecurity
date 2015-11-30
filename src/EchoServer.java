// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.sql.Date;

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
    //this.sendToAllClients(msg);
    
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
	  System.out.println(tempMsg);
	  for(; tempMsg.charAt(i) !='/';i++);
	  rv = tempMsg.substring(0, i);
	  tempMsg = tempMsg.substring(i+1, tempMsg.length());
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
		m_data.getUserList().add(tempUser); // 유저 추가 
		break;
	case "addFood": // 푸드추가//   addFood/내장고시리얼/food.toString(이름/넘버/퍼센트/유통기한/ 보관시작/코멘트/)
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString())); // 음식이 들어갈 냄장고시리얼 넘버 
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
		
		tempRefri.getFoodList().add(tempFood); // 푸드 추가 
		break;
	case "addRefri":
	case "addRefrigerator":  // 냉장고 추추추추가  //  addRefri/이름/번호/냉장고 오너아이디/
		tempRefri = new SRefrigerator();
		tempRefri.setOwnerID(divideString());
		tempUser = m_data.searchSUser(tempRefri.getOwnerID());
		if(tempUser.getID() == tempRefri.getOwnerID())
			tempRefri.getUserList().add(tempUser);
		
		m_data.getRefriList().add(tempRefri); // 레프리 추가 
		break;
	case "getUser": // 원하는 유저 정보 받오는    getUser/유저아이디
		m_data.searchSUser(divideString()); // 유저 아이디로 서치 
		tempMsg =m_data.toString();
		
		sendtoOneClient(tempMsg,client); // 클라이언트로 전송 
		break;
	case "getRefrigerator": // 원하는 냉자고 정보 받아오는   / getRefrigerator/ 냉장고시리얼/
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		tempMsg = tempRefri.toString();
		sendtoOneClient(tempMsg,client);// 클라이언트로 전송 
		break;
	case "getFood": // 음식 정보 받아오는   /getFood/냉장고시리얼/음식이름/
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		tempFood = tempRefri.searchFood(divideString());
		tempMsg = tempFood.toString();
		sendtoOneClient(tempMsg,client);// 클라이언트로 전송 
		break;
	case "getUserList": // 유저리스트를    / getUserList/
		for(int i=0;i<m_data.getUserList().size();i++)
			tempMsg = m_data.getUserList().get(i).toString();
		sendtoOneClient(tempMsg,client);// 클라이언트로 전송 
		break;
	case "getRefrigeratorList": // 냉장고 리스트를  /getRefrigeratorList/
		for(int i=0;i<m_data.getRefriList().size();i++)
			tempMsg = m_data.getRefriList().get(i).toString();
		sendtoOneClient(tempMsg,client);// 클라이언트로 전송 
		break;
	case "getFoodList": // 원하는 냉장고의 음식 리스트를  
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		for(int i=0;i<tempRefri.getFoodList().size();i++)
			tempMsg = tempRefri.getFoodList().get(i).toString();
		sendtoOneClient(tempMsg,client);// 클라이언트로 전송 
		break;
	case "removeUser": // 죽어라 유저  / removeUser/유저네임/
		tempUser =m_data.searchSUser(divideString());
		m_data.getUserList().remove(tempUser);
		for(int i=0;i<tempUser.getMyRefrigerator().size();i++)
			tempUser.getMyRefrigerator().get(i).getUserList().remove(tempUser);
		break;
	case "removeRefri": // 사라져라 냉자고  /removeRefri/냉장고시리얼/
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		m_data.getRefriList().remove(tempRefri);
		for(int i=0;i<tempUser.getMyRefrigerator().size();i++)
			tempRefri.getUserList().get(i).getMyRefrigerator().remove(tempRefri);
		break;
	case "removeFood": // 음식 소멸  /removeFood/냉장고시리얼/음식이름/
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		tempFood = tempRefri.searchFood(divideString());
		tempRefri.getFoodList().remove(tempFood);
		break;
	case "getEmptyFoodList": // 빈음식리스트트트트 보냄 클라로   /getEmptyFoodList/냉작고시리얼/
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		for(int i=0;i<tempRefri.getEmptyFoodList().size();i++)
			tempMsg += tempRefri.getEmptyFoodList().get(i).toString();
		sendtoOneClient(tempMsg,client);// 클라이언트로 전송 
		break;
	case "addEmptyFood": //빈 푸트 추가  / addEmptyFood/냉장고리스트/ food.tostring(이름/넘버/퍼센트/유통기한/ 보관시작/코멘트/)
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));// 음식이 들어갈 냄장고넘버 
		tempFood = new SFood();
		tempFood.setNumber(Integer.parseInt(divideString()));  	// 넘버  
		tempFood.setName(divideString());						// 이름 
		tempFood.setPercent(Float.parseFloat(divideString()));	// 퍼센트 
		tempFood.setExprieDate(new Date(Integer.parseInt(divideString()),
				Integer.parseInt(divideString()),
				Integer.parseInt(divideString())));	// 유통기한 
		tempFood.setStartDate(new Date(Integer.parseInt(divideString()),
				Integer.parseInt(divideString()),
				Integer.parseInt(divideString())));	;		// 보관시간
		tempFood.setComment(divideString());						// 코멘트
		tempRefri.getEmptyFoodList().add(tempFood); // 푸드 추가 
		break;
	case "removeEmptyFood": // 빈음식 삭제  /removeEmptyFood/냉장고시리얼/음식이름/
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		tempFood= tempRefri.searchEmptyFood(divideString());
		tempRefri.getEmptyFoodList().remove(tempFood);
		break;
	case "getEmptyfood": //빈 푸드 삭제  /getEmptyfood/냉장고넘버/푸드이름/
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		tempFood = tempRefri.searchEmptyFood(divideString());
		tempMsg = tempFood.toString();
		sendtoOneClient(tempMsg,client);// 클라이언트로 전송 
		break;
		 
	case "InviteRefri": // 아몰랑 초대 /InviteRefri/냉장고 시리얼/오너아이디 / 초대할사람아이디
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		if(tempRefri.getOwnerID() != divideString()) //오너 넘버 체크 
			break;
		tempUser = tempRefri.searchSUser(divideString()); // 초대 넘버 
		tempUser.getMyRefrigerator().add(tempRefri);
		tempRefri.getUserList().add(tempUser);
		break;
	case "KickRefri": // 아몰랑 초대 /InviteRefri/냉장고 시리얼/오너아이디/초대할사람아이디
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
	
		if(tempRefri.getOwnerID() != divideString()) //오너 넘버 체크 
			break;
		tempUser = tempRefri.searchSUser(divideString()); // 초대 넘버 
		tempUser.getMyRefrigerator().remove(tempRefri);
		tempRefri.getUserList().remove(tempUser);
		break;
		
	case "removeMyRefri": //자신의 냉장고를 삭제를 하는 함수를 부르는 함수를 부르른 부르르는 느느는  거  removeMyRefri /유저네임/냉장고시리얼
		tempUser = m_data.searchSUser(divideString());
		tempRefri = tempUser.searchRefrigerator(Integer.parseInt(divideString()));
		tempUser.getMyRefrigerator().remove(tempRefri);
		break;
	case "getUserRefriList": //유저의 냉장고리스트를 /getUserRefri/유저이름 /
		tempUser = m_data.searchSUser(divideString());
		for(int i=0;i<tempUser.getMyRefrigerator().size();i++)
			tempMsg += tempUser.getMyRefrigerator().get(i).toString();
		sendtoOneClient(tempMsg,client);// 클라이언트로 전송 
		break;
	case "getUserRefri": //유저의 냉장고리스트를 /getUserRefri/유저이름 /냉장고 시리얼
		tempUser = m_data.searchSUser(divideString());
		tempRefri = tempUser.searchRefrigerator(Integer.parseInt(divideString()));
		tempMsg = tempRefri.toString();
		sendtoOneClient(tempMsg,client);// 클라이언트로 전송 
		break;
	default:
	} 
	m_data.writeToFile();
  }
}
//End of EchoServer class
