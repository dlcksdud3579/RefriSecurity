
import java.io.*;
import java.util.Scanner;

import Server.SUser;
import Client.*;
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
public class TestMain implements ChatIF 
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
	    try 
	    {
	      client= new ChatClient(host, port, this);
	    } 
	    catch(IOException exception) 
	    {
	      System.out.println("Error: Can't setup connection!"
	                + " Terminating client.");
	      System.exit(1);
	    }
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
     addEmptyFood/�������Ʈ/ food.tostring(�̸�/�ѹ�/�ۼ�Ʈ/�������(��/��/��)/��������(��/��/��)/�ڸ�Ʈ/)
     addRefri/�̸�/����� ���ʾ��̵�/
     addEmptyFood/�����ø���/ food.tostring(�̸�/�ѹ�/�ۼ�Ʈ/�������(��/��/��)/��������(��/��/��)/�ڸ�Ʈ/)
    
     removeUser/��������/
     removeRefri/�����ø���/
     removeFood/�����ø���/�����̸�/
     removeEmptyFood/�����ø���/�����̸�/
     removeMyRefri/�������̵�/�����ø���/
     
     InviteRefri/����� �ø���/���ʾ��̵� / �ʴ��һ�����̵�/
     KickRefri/����� �ø���/���ʾ��̵�/�ʴ��һ�����̵�/
     
     getUser/�������̵�/
     getRefrigerator/ �����ø���/
     getFood/�����ø���/�����̸�/
     getUserList/
     getRefrigeratorList/
     getFoodList/����� �ø���/
     getEmptyFoodList/���۰�ø���/
     getEmptyfood/�����ѹ�/Ǫ���̸�/
     getUserRefriList/�����̸�/
     getUserRefri/�����̸�/����� �ø���/
     
     */
     
      while (true) 
      {
    	String msg = fromConsole.readLine();
    	 //String msg = new String("addUser/ay47/3579/������/");
    	  
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
    
    TestMain chat= new TestMain(host, port);
    
    chat.accept();  //Wait for console data
  }

  @Override
  public void display(String message) 
  {
	  System.out.println("> " + message);
  }
  
}
//End of ConsoleChat class
