
import java.io.*;
import java.util.Scanner;

import Server.SUser;
import Client.*;
import Client.CUser;
import common.*;


/**
 * This class constructs the UI for a chat client.  It implements the
 * chat interface in order to activate the display() method.
 * Warning: Some of the code here is cloned in ServerConsole 
 *
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Dr Timothy C. Lethbridge  
 * @author Dr Robert Lagani&egrave;re
 * @version July 2000
 */
public class TestMain extends ClientConsole  implements ChatIF 
{
  //Class variables *************************************************
  
  /**
   * The default port to connect on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  //Instance variables **********************************************
  
  /**
   * The instance of the client that created this ConsoleChat.
   */
  ChatClient client;

  
  //Constructors ****************************************************

  /**
   * Constructs an instance of the ClientConsole UI.
   *
   * @param host The host to connect to.
   * @param port The port to connect on.
   */
  public TestMain(String host, int port) 
  {
	  super(host,port);

  }

  
  //Instance methods ************************************************
  
  /**
   * This method waits for input from the console.  Once it is 
   * received, it sends it to the client's message handler.
   */
  public void accept() 
  {
    try
    {
     BufferedReader fromConsole = 
        new BufferedReader(new InputStreamReader(System.in));
     
    // String msg = new String("addUser/ay47/3579/������/"); // ���� �߰� ǥ�� 
    // String msg = new String("addFood/0/����/1/1.0/2015/11/29/2015/11/30/��ü���/"); // ���� �߰� ǥ��
     /*
     addUser/User.Tostirng (���� ���̵�/��й�ȣ/�̸�/)
     addFood/�����ø���/food.toString(�̸�/�ѹ�/�ۼ�Ʈ/�������/ ��������/�ڸ�Ʈ/)
     addRefri/�̸�/��ȣ/����� ���ʾ��̵�/
     getUser/�������̵�/
     getRefrigerator/ �����ø���/
     getFood/�����ø���/�����̸�/
     getUserList/
     getRefrigeratorList/
     getFoodList/����� �ø���/
     removeUser/��������/
     removeRefri/�����ø���/
     removeFood/�����ø���/�����̸�/
     getEmptyFoodList/���۰�ø���/
     addEmptyFood/�������Ʈ/ food.tostring(�̸�/�ѹ�/�ۼ�Ʈ/�������/ ��������/�ڸ�Ʈ/)
     removeEmptyFood/�����ø���/�����̸�/
     getEmptyfood/�����ѹ�/Ǫ���̸�/
     InviteRefri/����� �ø���/���ʾ��̵� / �ʴ��һ�����̵�
     InviteRefri/����� �ø���/���ʾ��̵�/�ʴ��һ�����̵�
     removeMyRefri /��������/�����ø���/
     getUserRefri/�����̸�/
     getUserRefri/�����̸�/����� �ø���
     */
     
      while (true) 
      {
    	
    	String msg = fromConsole.readLine();
    	client.handleMessageFromClientUI(msg); // Ŭ���̾�Ʈ���� ó��
      }
    } 
    catch (Exception ex) 
    {
      System.out.println
        ("Unexpected error while reading from console!");
    }
  }

  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of the Client UI.
   *
   * @param args[0] The host to connect to.
   */
  public static void main(String[] args) 
  {
    String host = "";
    int port = 0;  //The port number
  //  port = getInputPort();
    try
    {
      host = args[0];
    }
    catch(ArrayIndexOutOfBoundsException e)
    {
      host = "localhost";
    }
    
    try{
    	port = Integer.parseInt(args[1]);
    }
    catch(ArrayIndexOutOfBoundsException e)
    {
      port = 5555;
    }
    
    ClientConsole chat= new ClientConsole(host, port);
    
    chat.accept();  //Wait for console data
  }
  
}
//End of ConsoleChat class
