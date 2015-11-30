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
	  int index =0;
	  SUser tempUser = null;
	  SFood tempFood = null;
	  SRefrigerator tempRefri = null;
	  int refriNum = 0;
	  int foodNum = 0;
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
	case "addFood": // 푸드추가//   addFood/내장고넘버/food.toString(이름/넘버/퍼센트/유통기한/ 보관시작/코멘트/)
		refriNum = Integer.parseInt(divideString()); // 음식이 들어갈 냄장고넘버 
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
		tempFood.setComment(divideString());						// 코멘트
		m_data.getRefriList().get(refriNum).getFoodList().add(tempFood); // 푸드 추가 
		break;
	case "addRefri":
	case "addRefrigerator":  // 냉장고 추추추추가  //  addRefri/이름/번호/냉장고 오너아이디/
		tempRefri = new SRefrigerator();
		tempRefri.setOwnerID(divideString());
		for(int i =0;i<m_data.getUserList().size();i++)
			if(m_data.getUserList().get(i).getID() == tempRefri.getOwnerID())
				tempRefri.getUserList().add((m_data.getUserList().get(i)));
		
		m_data.getRefriList().add(tempRefri); // 레프리 추가 
		break;
	case "getUser": // 원하는 유저 정보 받오는    getUser/유저아이디
		index = Integer.parseInt(divideString());
		tempMsg = m_data.getUserList().get(index).toString();
		sendtoOneClient(tempMsg,client); // 클라이언트로 전송 
		break;
	case "getRefrigerator": // 원하는 냉자고 정보 받아오는   / 냉장고넘버
		index = Integer.parseInt(divideString());
		tempMsg = m_data.getRefriList().get(index).toString();
		sendtoOneClient(tempMsg,client);// 클라이언트로 전송 
		break;
	case "getFood": // 음식 정보 받아오는 
		refriNum = Integer.parseInt(divideString());
		foodNum = Integer.parseInt(divideString());
		tempMsg = m_data.getRefriList().get(refriNum).getFoodList().get(foodNum).toString();
		sendtoOneClient(tempMsg,client);// 클라이언트로 전송 
		break;
	case "getUserList": // 유저리스트를 
		for(int i=0;i<m_data.getUserList().size();i++)
			tempMsg = m_data.getUserList().get(i).toString();
		sendtoOneClient(tempMsg,client);// 클라이언트로 전송 
		break;
	case "getRefrigeratorList": // 냉장고 리스트를 
		for(int i=0;i<m_data.getRefriList().size();i++)
			tempMsg = m_data.getRefriList().get(i).toString();
		sendtoOneClient(tempMsg,client);// 클라이언트로 전송 
		break;
	case "getFoodList": // 원하는 냉장고의 음식 리스트를 
		refriNum = Integer.parseInt(divideString());
		for(int i=0;i<m_data.getRefriList().get(refriNum).getFoodList().size();i++)
			tempMsg = m_data.getRefriList().get(refriNum).getFoodList().get(i).toString();
		sendtoOneClient(tempMsg,client);// 클라이언트로 전송 
		break;
	case "removeUser": // 죽어라 유저 
		index = Integer.parseInt(divideString());
		m_data.getUserList().remove(index);
		
	case "removeRefri": // 사라져라 냉자고
		refriNum= Integer.parseInt(divideString());
		m_data.getRefriList().remove(refriNum);
		
	case "removeFood": // 음식 소멸 
		refriNum= Integer.parseInt(divideString());
		foodNum= Integer.parseInt(divideString());
		m_data.getRefriList().get(refriNum).getFoodList().remove(foodNum);
		
	case "getEmptyFoodList": // 빈음식리스트트트트 보냄 클라로 
		refriNum = Integer.parseInt(divideString());
		for(int i=0;i<m_data.getRefriList().get(refriNum).getEmptyFoodList().size();i++)
			tempMsg = m_data.getRefriList().get(refriNum).getEmptyFoodList().get(i).toString();
		sendtoOneClient(tempMsg,client);// 클라이언트로 전송 
		break;
	case "addEmptyFood": //빈 푸트 추가 
		refriNum = Integer.parseInt(divideString()); // 음식이 들어갈 냄장고넘버 
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
		m_data.getRefriList().get(refriNum).getEmptyFoodList().add(tempFood); // 푸드 추가 
		break;
	case "removeEmptyFood": // 빈음식 삭제  
		refriNum= Integer.parseInt(divideString());
		foodNum= Integer.parseInt(divideString());
		m_data.getRefriList().get(refriNum).getEmptyFoodList().remove(foodNum);
		break;
	case "getEmptyfood": //빈 푸드 삭제  /냉장고넘버/ 
		refriNum = Integer.parseInt(divideString());
		foodNum = Integer.parseInt(divideString());
		tempMsg = m_data.getRefriList().get(refriNum).getEmptyFoodList().get(foodNum).toString();
		sendtoOneClient(tempMsg,client);// 클라이언트로 전송 
		break;
		 
	case "InviteRefri": // 아몰랑 초대  / 오너아이디 / 초대할사람아이디
		refriNum = Integer.parseInt(divideString());
		String OwnerId = divideString(); // 오너 넘버
		String visitorId = divideString(); // 초대 넘버 
		if(m_data.getRefriList().get(refriNum).getOwnerID() != OwnerId)
			break;
			
			
		m_data.getUserList().get(index).getMyRefrigerator().add(m_data.getRefriList().get(refriNum));
		m_data.getRefriList().get(refriNum).getUserList().add(m_data.getUserList().get(index));
		
	case "removeMyRefri": //자신의 냉장고를 삭제를 하는 함수를 부르는 함수를 부르른 부르르는 느느는  거 
		
	default:
	} 
	m_data.writeToFile();
  }
}
//End of EchoServer class
