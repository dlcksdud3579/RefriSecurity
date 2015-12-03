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
  
  private SDataList m_data; // ���� �߰� 
  
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
    // ���� �߰� 
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
    // ���� �߰�
     // ������ ����;
    // ���� �߰� 
    
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
  

  /// ���� �߰� 
  public void sendtoOneClient(Object msg,ConnectionToClient client) // ���ϴ� Ŭ���̾�Ʈ���Ը� ���� 
  {
	   try
	   {
	    	client.sendToClient(msg);
	   }
	   catch (Exception ex) {}
  }
  public String divideString() //�޾ƿ� ������  Ŭ������ ������ �˾Ƴ��� �Լ� 
  {
	  String rv = null ; // ���� ��� ���⿡ Ŭ������ ���� �� 
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
	  String tempMsg = ""; // client���� ���� �޼���
	switch(command)
	{
	case "addUser": // /�����߰�/ addUser/User.Tostirng (���� ���̵�/��й�ȣ/�̸�/)
		tempUser = new SUser();
		tempUser.setID(divideString());
		tempUser.setPW(divideString());
		tempUser.setName(divideString());
		if( m_data.addUser(tempUser)== false) // ���� �߰� 
			break;
		sendtoOneClient("true",client);
		return;
		
	case "addRefri":
	case "addRefrigerator":  // ����� �������߰�  //  addRefri/�̸�/����� ���ʾ��̵�/
		tempRefri = new SRefrigerator();
		tempRefri.setName(divideString());
		String OwnerID = divideString();
		m_data.addRefri(tempRefri,OwnerID); // ������ �߰� 
		sendtoOneClient("true",client);
		return;
		
	case "addFood": // Ǫ���߰�//   addFood/�����ø���/food.toString(�̸�/�ѹ�/�ۼ�Ʈ/�������/ ��������/�ڸ�Ʈ/)
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString())); // ������ �� �����ø��� �ѹ� 
		if(tempRefri == null)
			break;
		tempFood = new SFood();
		tempFood.setName(divideString());						// �̸�
		tempFood.setNumber(Integer.parseInt(divideString()));  	// �ѹ�  
		tempFood.setPercent(Float.parseFloat(divideString()));	// �ۼ�Ʈ 
		tempFood.setExprieDate(new Date(Integer.parseInt(divideString()),
				Integer.parseInt(divideString()),
				Integer.parseInt(divideString())));	// ������� 
		tempFood.setStartDate(new Date(Integer.parseInt(divideString()),
				Integer.parseInt(divideString()),
				Integer.parseInt(divideString())));		// �����ð�
		tempFood.setComment(divideString());	// �ڸ�Ʈ
		
		tempRefri.addFood(tempFood); // Ǫ�� �߰� 
		sendtoOneClient("true",client);
		return;
		
		/*// ����
	case "addEmptyFood": //�� ǪƮ �߰�  / addEmptyFood/�����ø���/ food.tostring(�̸�/�ѹ�/�ۼ�Ʈ/�������/ ��������/�ڸ�Ʈ/)
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));// ������ �� �����ѹ� 
		if(tempRefri == null)
			break;
		tempFood = new SFood();
		tempFood.setName(divideString());	// �̸� 
		tempFood.setNumber(Integer.parseInt(divideString()));  	// �ѹ�  
		tempFood.setPercent(Float.parseFloat(divideString()));	// �ۼ�Ʈ 
		tempFood.setExprieDate(new Date(Integer.parseInt(divideString()),
				Integer.parseInt(divideString()),
				Integer.parseInt(divideString())));	// ������� 
		tempFood.setStartDate(new Date(Integer.parseInt(divideString()),
				Integer.parseInt(divideString()),
				Integer.parseInt(divideString())));	;		// �����ð�
		tempFood.setComment(divideString());						// �ڸ�Ʈ
		
		tempRefri.addEmptyFood(tempFood); // Ǫ�� �߰� 
		break;
			*/
		
	case "removeUser": // �׾�� ����  / removeUser/��������/
		tempUser =m_data.searchSUser(divideString());
		m_data.removeUser(tempUser);
		sendtoOneClient("true",client);
		return;
		
	case "removeRefri": // ������� ���ڰ� /removeRefri/�����ø���/
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		m_data.removeRefri(tempRefri);
		sendtoOneClient("true",client);
		return;
		
	case "removeFood": // ���� �Ҹ�  /removeFood/�����ø���/�����̸�/
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		if(tempRefri == null)
			break;
		tempFood = tempRefri.searchFood(divideString());
		if(tempFood == null)
			break;
		
		tempRefri.removeFood(tempFood);
		sendtoOneClient("true",client);
		return;
		
		/*// ���� 
	case "removeEmptyFood": // ������ ����  /removeEmptyFood/�����ø���/�����̸�/
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		if(tempRefri == null)
			break;
		tempFood= tempRefri.searchEmptyFood(divideString());
		tempRefri.removeEmptyFood(tempFood);
		break;
		*/
		
	case "removeMyRefri": //�ڽ��� ����� ������ �ϴ� �Լ��� �θ��� �Լ��� �θ��� �θ����� ������  ��  removeMyRefri /��������/�����ø���/
		tempUser = m_data.searchSUser(divideString());
		if(tempUser == null)
			break;
		tempRefri = tempUser.searchRefrigerator(Integer.parseInt(divideString()));
		tempUser.removeMyRefri(tempRefri);
		if(tempRefri.getUserList().get(0).equals(tempUser.getID()))
			m_data.removeRefri(tempRefri);
		sendtoOneClient("true",client);
		return;
		
	case "InviteRefri": // �Ƹ��� �ʴ� /InviteRefri/����� �ø���/���ʾ��̵� / �ʴ��һ�����̵�
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		if(tempRefri == null)
			break;
		tempUser = m_data.searchSUser(divideString());
		tempRefri.InviteOtherUser(divideString(),tempUser);
		sendtoOneClient("true",client);
		return;
		
	case "KickRefri": // �Ƹ��� ���� /InviteRefri/����� �ø���/���ʾ��̵�/�ʴ��һ�����̵�
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		if(tempRefri == null)
			break;
		tempUser = m_data.searchSUser(divideString());
		tempRefri.KickOtherUser(divideString(),tempUser);
		sendtoOneClient("true",client);
		return;
		
	case "checkEmptyFoodList":  // ����� �ִ� ������ �� ���� ����Ʈ ����   / checkEmptyFoodList/�����ø���/
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		if(tempRefri == null)
			break;
		tempRefri.checkEmptyFoodList();
		sendtoOneClient("true",client);
		return;
		
	case "changeFood": // ���� ���� ü����  /    changeFood/�ø���/Ǫ���̸�/Ǫ���̸�/
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		if(tempRefri == null)
			break;
		tempFood = tempRefri.searchFood(divideString());
		if(tempFood == null)
			break;
		SFood chgFood = new SFood();
		chgFood.setName(divideString());						// �̸�
		chgFood.setNumber(Integer.parseInt(divideString()));  	// �ѹ�  
		chgFood.setPercent(Float.parseFloat(divideString()));	// �ۼ�Ʈ 
		chgFood.setExprieDate(new Date(Integer.parseInt(divideString()),
				Integer.parseInt(divideString()),
				Integer.parseInt(divideString())));	// ������� 
		chgFood.setStartDate(new Date(Integer.parseInt(divideString()),
				Integer.parseInt(divideString()),
				Integer.parseInt(divideString())));		// �����ð�
		chgFood.setComment(divideString());	// �ڸ�Ʈ
		
		tempFood.ChangeFood(chgFood);
		sendtoOneClient("true",client);
		return;
		
	case "getUser": // ���ϴ� ���� ���� �޿���    getUser/�������̵�/��й�ȣ/
		tempUser = m_data.searchSUser(divideString()); // ���� ���̵�� ��ġ 
		if(tempUser == null)
			break;
		if(tempUser.getPW().equals(divideString()))
			break;
		tempMsg = "User/"+tempUser.toString();
		sendtoOneClient("true",client);
		sendtoOneClient(tempMsg,client); // Ŭ���̾�Ʈ�� ���� 
		return;
		
	case "getRefrigerator": // ���ϴ� ���ڰ� ���� �޾ƿ���   / getRefrigerator/ �����ø���/
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		if(tempRefri ==null)
			break;
		tempMsg = "Refrigerator/"+tempRefri.toString();
		sendtoOneClient("true",client);
		sendtoOneClient(tempMsg,client);// Ŭ���̾�Ʈ�� ���� 
		return;
		
	case "getFood": // ���� ���� �޾ƿ���   /getFood/�����ø���/�����̸�/
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		if(tempRefri ==null)
			break;
		tempFood = tempRefri.searchFood(divideString());
		if(tempFood ==null)
			break;
		tempMsg = "Food/"+tempFood.toString();
		sendtoOneClient("true",client);
		sendtoOneClient(tempMsg,client);// Ŭ���̾�Ʈ�� ���� 
		return ;
		
	case "getUserList": // ��������Ʈ��    / getUserList/
		tempMsg = "UserList/";
		for(int i=0;i<m_data.getUserList().size();i++)
			tempMsg += m_data.getUserList().get(i).toString();
		sendtoOneClient("true",client);
		sendtoOneClient(tempMsg,client);// Ŭ���̾�Ʈ�� ���� 
		return;
		
	case "getRefrigeratorList": // ����� ����Ʈ��  /getRefrigeratorList/
		tempMsg = "RefrigeratorList/";
		for(int i=0;i<m_data.getRefriList().size();i++)
			tempMsg += m_data.getRefriList().get(i).toString();
		sendtoOneClient("true",client);
		sendtoOneClient(tempMsg,client);// Ŭ���̾�Ʈ�� ���� 
		return;
		
	case "getFoodList": // ���ϴ� ������� ���� ����Ʈ��   getFoodList/����� �ø���/
		tempMsg = "FoodList/";
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		if(tempRefri ==null)
			break;
		
		for(int i=0;i<tempRefri.getFoodList().size();i++)
			tempMsg += tempRefri.getFoodList().get(i).toString();
		sendtoOneClient("true",client);
		sendtoOneClient(tempMsg,client);// Ŭ���̾�Ʈ�� ���� 
		return;
		
	case "getEmptyFoodList": // �����ĸ���ƮƮƮƮ ���� Ŭ���   /getEmptyFoodList/���۰�ø���/
		tempMsg = "EmptyFoodList/";
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		if(tempRefri == null)
			break;
		for(int i=0;i<tempRefri.getEmptyFoodList().size();i++)
			tempMsg += tempRefri.getEmptyFoodList().get(i).toString();
		sendtoOneClient("true",client);
		sendtoOneClient(tempMsg,client);// Ŭ���̾�Ʈ�� ���� 
		
		return;
		
	case "getEmptyfood": //�� Ǫ�� ����  /getEmptyfood/�����ѹ�/Ǫ���̸�/
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		if(tempRefri == null)
			break;
		tempFood = tempRefri.searchEmptyFood(divideString());
		if(tempFood == null)
			break;
		tempMsg ="Emptyfood/"+tempFood.toString();
		sendtoOneClient("true",client);
		sendtoOneClient(tempMsg,client);// Ŭ���̾�Ʈ�� ���� 
		return;
		 
	case "getUserRefriList": //������ �������Ʈ�� /getUserRefri/�����̸� /
		tempMsg = "UserRefriList/";
		tempUser = m_data.searchSUser(divideString());
		if(tempUser == null)
			break;
		for(int i=0;i<tempUser.getMyRefrigerator().size();i++)
			tempMsg += tempUser.getMyRefrigerator().get(i).toString();
		sendtoOneClient("true",client);
		sendtoOneClient(tempMsg,client);// Ŭ���̾�Ʈ�� ���� 
		return;
		
	case "getUserRefri": //������ �������Ʈ�� /getUserRefri/�����̸� /����� �ø���
		tempMsg = "UserRefri/";
		tempUser = m_data.searchSUser(divideString());
		if(tempUser == null)
			break;
		tempRefri = tempUser.searchRefrigerator(Integer.parseInt(divideString()));
		if(tempRefri == null)
			break;
		tempMsg += tempRefri.toString();
		sendtoOneClient("true",client);
		sendtoOneClient(tempMsg,client);// Ŭ���̾�Ʈ�� ���� 
		return;
		
		
	default:
		break;
	} 
	sendtoOneClient("false",client);
  }
}
//End of EchoServer class
