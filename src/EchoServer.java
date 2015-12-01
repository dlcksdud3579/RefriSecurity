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
    this.sendToAllClients("ó����\n");
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
		m_data.getUserList().add(tempUser); // ���� �߰� 
		break;
	case "addFood": // Ǫ���߰�//   addFood/�����ø���/food.toString(�̸�/�ѹ�/�ۼ�Ʈ/�������/ ��������/�ڸ�Ʈ/)
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString())); // ������ �� �����ø��� �ѹ� 
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
		
		tempRefri.getFoodList().add(tempFood); // Ǫ�� �߰� 
		break;
	case "addRefri":
	case "addRefrigerator":  // ����� �������߰�  //  addRefri/�̸�/��ȣ/����� ���ʾ��̵�/
		tempRefri = new SRefrigerator();
		tempRefri.setName(divideString());
		tempRefri.setSerial(Integer.parseInt(divideString()));
		tempRefri.setOwnerID(divideString());
		tempUser = m_data.searchSUser(tempRefri.getOwnerID());
		if(tempUser ==null)
			break;
		tempRefri.getUserList().add(tempUser);
		tempUser.getMyRefrigerator().add(tempRefri);
		m_data.getRefriList().add(tempRefri); // ������ �߰� 
		break;
		
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
		tempRefri.getEmptyFoodList().add(tempFood); // Ǫ�� �߰� 
		break;
		
		
	case "getUser": // ���ϴ� ���� ���� �޿���    getUser/�������̵�/
		tempUser = m_data.searchSUser(divideString()); // ���� ���̵�� ��ġ 
		if(tempUser == null)
			break;
		tempMsg =tempUser.toString();
		sendtoOneClient(tempMsg,client); // Ŭ���̾�Ʈ�� ���� 
		break;
		
	case "getRefrigerator": // ���ϴ� ���ڰ� ���� �޾ƿ���   / getRefrigerator/ �����ø���/
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		if(tempRefri ==null)
			break;
		tempMsg = tempRefri.toString();
		sendtoOneClient(tempMsg,client);// Ŭ���̾�Ʈ�� ���� 
		break;
	case "getFood": // ���� ���� �޾ƿ���   /getFood/�����ø���/�����̸�/
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		if(tempRefri ==null)
			break;
		tempFood = tempRefri.searchFood(divideString());
		if(tempFood ==null)
			break;
		tempMsg = tempFood.toString();
		sendtoOneClient(tempMsg,client);// Ŭ���̾�Ʈ�� ���� 
		break;
	case "getUserList": // ��������Ʈ��    / getUserList/
		for(int i=0;i<m_data.getUserList().size();i++)
			tempMsg += m_data.getUserList().get(i).toString();
		sendtoOneClient(tempMsg,client);// Ŭ���̾�Ʈ�� ���� 
		break;
	case "getRefrigeratorList": // ����� ����Ʈ��  /getRefrigeratorList/
		for(int i=0;i<m_data.getRefriList().size();i++)
			tempMsg += m_data.getRefriList().get(i).toString();
		sendtoOneClient(tempMsg,client);// Ŭ���̾�Ʈ�� ���� 
		break;
	case "getFoodList": // ���ϴ� ������� ���� ����Ʈ��   getFoodList/����� �ø���/
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		if(tempRefri ==null)
			break;
		
		for(int i=0;i<tempRefri.getFoodList().size();i++)
			tempMsg += tempRefri.getFoodList().get(i).toString();
		sendtoOneClient(tempMsg,client);// Ŭ���̾�Ʈ�� ���� 
		break;
	case "getEmptyFoodList": // �����ĸ���ƮƮƮƮ ���� Ŭ���   /getEmptyFoodList/���۰�ø���/
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		if(tempRefri == null)
			break;
		for(int i=0;i<tempRefri.getEmptyFoodList().size();i++)
			tempMsg += tempRefri.getEmptyFoodList().get(i).toString();
		sendtoOneClient(tempMsg,client);// Ŭ���̾�Ʈ�� ���� 
		break;
		
	case "getEmptyfood": //�� Ǫ�� ����  /getEmptyfood/�����ѹ�/Ǫ���̸�/
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		if(tempRefri == null)
			break;
		tempFood = tempRefri.searchEmptyFood(divideString());
		if(tempFood == null)
			break;
		tempMsg = tempFood.toString();
		sendtoOneClient(tempMsg,client);// Ŭ���̾�Ʈ�� ���� 
		break;
		 
	case "getUserRefriList": //������ �������Ʈ�� /getUserRefri/�����̸� /
		tempUser = m_data.searchSUser(divideString());
		if(tempUser == null)
			break;
		for(int i=0;i<tempUser.getMyRefrigerator().size();i++)
			tempMsg += tempUser.getMyRefrigerator().get(i).toString();
		sendtoOneClient(tempMsg,client);// Ŭ���̾�Ʈ�� ���� 
		break;
	case "getUserRefri": //������ �������Ʈ�� /getUserRefri/�����̸� /����� �ø���
		tempUser = m_data.searchSUser(divideString());
		if(tempUser == null)
			break;
		tempRefri = tempUser.searchRefrigerator(Integer.parseInt(divideString()));
		if(tempRefri == null)
			break;
		tempMsg = tempRefri.toString();
		sendtoOneClient(tempMsg,client);// Ŭ���̾�Ʈ�� ���� 
		break;
		
	case "removeUser": // �׾�� ����  / removeUser/��������/
		tempUser =m_data.searchSUser(divideString());
		if(tempUser ==null)
			break;
		m_data.getUserList().remove(tempUser);
		for(int i=0;i<tempUser.getMyRefrigerator().size();i++)
			tempUser.getMyRefrigerator().get(i).getUserList().remove(tempUser);
		break;
	case "removeRefri": // ������� ���ڰ� /removeRefri/�����ø���/���ʾ��̵�/
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		if(tempRefri == null)
			break;
		if(tempRefri.getOwnerID().equals(divideString())==false)
			break;
		
		for(int i=0;i<tempRefri.getUserList().size();i++)
		{
			if(tempRefri.getUserList().get(i).searchRefrigerator(tempRefri.getSerial()) != null)
				tempRefri.getUserList().get(i).getMyRefrigerator().remove(tempRefri);
		}
		m_data.getRefriList().remove(tempRefri);
		break;
	case "removeFood": // ���� �Ҹ�  /removeFood/�����ø���/�����̸�/
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		tempFood = tempRefri.searchFood(divideString());
		tempRefri.getFoodList().remove(tempFood);
		break;
		
	case "removeEmptyFood": // ������ ����  /removeEmptyFood/�����ø���/�����̸�/
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		if(tempRefri == null)
			break;
		tempFood= tempRefri.searchEmptyFood(divideString());
		if(tempFood == null)
			break;
		tempRefri.getEmptyFoodList().remove(tempFood);
		break;
		
	case "removeMyRefri": //�ڽ��� ����� ������ �ϴ� �Լ��� �θ��� �Լ��� �θ��� �θ����� ������  ��  removeMyRefri /��������/�����ø���/
		tempUser = m_data.searchSUser(divideString());
		if(tempUser == null)
			break;
		tempRefri = tempUser.searchRefrigerator(Integer.parseInt(divideString()));
		if(tempRefri == null)
			break;
		if(tempRefri.getOwnerID().equals(tempUser.getID()))
		{
			for(int i=0;i<tempRefri.getUserList().size();i++)
			{
				if(tempRefri.getUserList().get(i).searchRefrigerator(tempRefri.getSerial()) != null)
					tempRefri.getUserList().get(i).getMyRefrigerator().remove(tempRefri);
			}
			m_data.getRefriList().remove(tempRefri);
		}
		else
		{
			tempUser.getMyRefrigerator().remove(tempRefri);
		}
		break;

	case "InviteRefri": // �Ƹ��� �ʴ� /InviteRefri/����� �ø���/���ʾ��̵� / �ʴ��һ�����̵�
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		if(tempRefri == null)
			break;
		if(tempRefri.getOwnerID().equals(divideString()) == false) //���� �ѹ� üũ 
			break;
		tempUser = m_data.searchSUser(divideString()); // �ʴ� ����� 
		if(tempUser == null)
			break;
		tempUser.getMyRefrigerator().add(tempRefri);
		tempRefri.getUserList().add(tempUser);
		break;
	case "KickRefri": // �Ƹ��� �ʴ� /InviteRefri/����� �ø���/���ʾ��̵�/�ʴ��һ�����̵�
		
		tempRefri = m_data.searchRefrigerator(Integer.parseInt(divideString()));
		if(tempRefri == null)
			break;
		if(tempRefri.getOwnerID().equals(divideString()) ==false) //���� �ѹ� üũ 
			break;
		tempUser = m_data.searchSUser(divideString()); // �ʴ� �ѹ� 
		if(tempUser == null)
			break;
		tempUser.getMyRefrigerator().remove(tempRefri);
		tempRefri.getUserList().remove(tempUser);
		break;
		

	default:
		tempMsg = "null";
		sendtoOneClient(tempMsg,client);
	} 
  }
}
//End of EchoServer class
