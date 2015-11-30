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
	  String tempMsg = ""; // client���� ���� �޼���
	switch(command)
	{
	case "addUser": // /�����߰�/ addUser/User.Tostirng (���� ���̵�/��й�ȣ/�̸�/)
		tempUser = new SUser();
		tempUser.setID(divideString());
		tempUser.setPW(divideString());
		tempUser.setName(divideString());
		m_data.getUserList().add(tempUser); // ���� �߰� 
		break;
	case "addFood": // Ǫ���߰�//   addFood/�����ѹ�/food.toString(�̸�/�ѹ�/�ۼ�Ʈ/�������/ ��������/�ڸ�Ʈ/)
		refriNum = Integer.parseInt(divideString()); // ������ �� �����ѹ� 
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
		tempFood.setComment(divideString());						// �ڸ�Ʈ
		m_data.getRefriList().get(refriNum).getFoodList().add(tempFood); // Ǫ�� �߰� 
		break;
	case "addRefri":
	case "addRefrigerator":  // ����� �������߰�  //  addRefri/�̸�/��ȣ/����� ���ʾ��̵�/
		tempRefri = new SRefrigerator();
		tempRefri.setOwnerID(divideString());
		for(int i =0;i<m_data.getUserList().size();i++)
			if(m_data.getUserList().get(i).getID() == tempRefri.getOwnerID())
				tempRefri.getUserList().add((m_data.getUserList().get(i)));
		
		m_data.getRefriList().add(tempRefri); // ������ �߰� 
		break;
	case "getUser": // ���ϴ� ���� ���� �޿���    getUser/�������̵�
		index = Integer.parseInt(divideString());
		tempMsg = m_data.getUserList().get(index).toString();
		sendtoOneClient(tempMsg,client); // Ŭ���̾�Ʈ�� ���� 
		break;
	case "getRefrigerator": // ���ϴ� ���ڰ� ���� �޾ƿ���   / �����ѹ�
		index = Integer.parseInt(divideString());
		tempMsg = m_data.getRefriList().get(index).toString();
		sendtoOneClient(tempMsg,client);// Ŭ���̾�Ʈ�� ���� 
		break;
	case "getFood": // ���� ���� �޾ƿ��� 
		refriNum = Integer.parseInt(divideString());
		foodNum = Integer.parseInt(divideString());
		tempMsg = m_data.getRefriList().get(refriNum).getFoodList().get(foodNum).toString();
		sendtoOneClient(tempMsg,client);// Ŭ���̾�Ʈ�� ���� 
		break;
	case "getUserList": // ��������Ʈ�� 
		for(int i=0;i<m_data.getUserList().size();i++)
			tempMsg = m_data.getUserList().get(i).toString();
		sendtoOneClient(tempMsg,client);// Ŭ���̾�Ʈ�� ���� 
		break;
	case "getRefrigeratorList": // ����� ����Ʈ�� 
		for(int i=0;i<m_data.getRefriList().size();i++)
			tempMsg = m_data.getRefriList().get(i).toString();
		sendtoOneClient(tempMsg,client);// Ŭ���̾�Ʈ�� ���� 
		break;
	case "getFoodList": // ���ϴ� ������� ���� ����Ʈ�� 
		refriNum = Integer.parseInt(divideString());
		for(int i=0;i<m_data.getRefriList().get(refriNum).getFoodList().size();i++)
			tempMsg = m_data.getRefriList().get(refriNum).getFoodList().get(i).toString();
		sendtoOneClient(tempMsg,client);// Ŭ���̾�Ʈ�� ���� 
		break;
	case "removeUser": // �׾�� ���� 
		index = Integer.parseInt(divideString());
		m_data.getUserList().remove(index);
		
	case "removeRefri": // ������� ���ڰ�
		refriNum= Integer.parseInt(divideString());
		m_data.getRefriList().remove(refriNum);
		
	case "removeFood": // ���� �Ҹ� 
		refriNum= Integer.parseInt(divideString());
		foodNum= Integer.parseInt(divideString());
		m_data.getRefriList().get(refriNum).getFoodList().remove(foodNum);
		
	case "getEmptyFoodList": // �����ĸ���ƮƮƮƮ ���� Ŭ��� 
		refriNum = Integer.parseInt(divideString());
		for(int i=0;i<m_data.getRefriList().get(refriNum).getEmptyFoodList().size();i++)
			tempMsg = m_data.getRefriList().get(refriNum).getEmptyFoodList().get(i).toString();
		sendtoOneClient(tempMsg,client);// Ŭ���̾�Ʈ�� ���� 
		break;
	case "addEmptyFood": //�� ǪƮ �߰� 
		refriNum = Integer.parseInt(divideString()); // ������ �� �����ѹ� 
		tempFood = new SFood();
		tempFood.setNumber(Integer.parseInt(divideString()));  	// �ѹ�  
		tempFood.setName(divideString());						// �̸� 
		tempFood.setPercent(Float.parseFloat(divideString()));	// �ۼ�Ʈ 
		tempFood.setExprieDate(new Date(Integer.parseInt(divideString()),
				Integer.parseInt(divideString()),
				Integer.parseInt(divideString())));	// ������� 
		tempFood.setStartDate(new Date(Integer.parseInt(divideString()),
				Integer.parseInt(divideString()),
				Integer.parseInt(divideString())));	;		// �����ð�
		tempFood.setComment(divideString());						// �ڸ�Ʈ
		m_data.getRefriList().get(refriNum).getEmptyFoodList().add(tempFood); // Ǫ�� �߰� 
		break;
	case "removeEmptyFood": // ������ ����  
		refriNum= Integer.parseInt(divideString());
		foodNum= Integer.parseInt(divideString());
		m_data.getRefriList().get(refriNum).getEmptyFoodList().remove(foodNum);
		break;
	case "getEmptyfood": //�� Ǫ�� ����  /�����ѹ�/ 
		refriNum = Integer.parseInt(divideString());
		foodNum = Integer.parseInt(divideString());
		tempMsg = m_data.getRefriList().get(refriNum).getEmptyFoodList().get(foodNum).toString();
		sendtoOneClient(tempMsg,client);// Ŭ���̾�Ʈ�� ���� 
		break;
		 
	case "InviteRefri": // �Ƹ��� �ʴ�  / ���ʾ��̵� / �ʴ��һ�����̵�
		refriNum = Integer.parseInt(divideString());
		String OwnerId = divideString(); // ���� �ѹ�
		String visitorId = divideString(); // �ʴ� �ѹ� 
		if(m_data.getRefriList().get(refriNum).getOwnerID() != OwnerId)
			break;
			
			
		m_data.getUserList().get(index).getMyRefrigerator().add(m_data.getRefriList().get(refriNum));
		m_data.getRefriList().get(refriNum).getUserList().add(m_data.getUserList().get(index));
		
	case "removeMyRefri": //�ڽ��� ����� ������ �ϴ� �Լ��� �θ��� �Լ��� �θ��� �θ����� ������  �� 
		
	default:
	} 
	m_data.writeToFile();
  }
}
//End of EchoServer class
