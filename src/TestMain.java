
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
     
    // String msg = new String("addUser/ay47/3579/이찬영/"); // 유저 추가 표준 
    // String msg = new String("addFood/0/문어/1/1.0/2015/11/29/2015/11/30/삼시세끼/"); // 유저 추가 표준
     /*
     addUser/User.Tostirng (유저 아이디/비밀번호/이름/)
     addFood/내장고시리얼/food.toString(이름/넘버/퍼센트/유통기한/ 보관시작/코멘트/)
     addEmptyFood/냉장고리스트/ food.tostring(이름/넘버/퍼센트/유통기한(년/월/일)/보관시작(년/월/일)/코멘트/)
     addRefri/이름/냉장고 오너아이디/
     addEmptyFood/냉장고시리얼/ food.tostring(이름/넘버/퍼센트/유통기한(년/월/일)/보관시작(년/월/일)/코멘트/)
    
     removeUser/유저네임/
     removeRefri/냉장고시리얼/
     removeFood/냉장고시리얼/음식이름/
     removeEmptyFood/냉장고시리얼/음식이름/
     removeMyRefri/유저아이디/냉장고시리얼/
     
     InviteRefri/냉장고 시리얼/오너아이디 / 초대할사람아이디/
     KickRefri/냉장고 시리얼/오너아이디/초대할사람아이디/
     
     getUser/유저아이디/
     getRefrigerator/ 냉장고시리얼/
     getFood/냉장고시리얼/음식이름/
     getUserList/
     getRefrigeratorList/
     getFoodList/냉장고 시리얼/
     getEmptyFoodList/냉작고시리얼/
     getEmptyfood/냉장고넘버/푸드이름/
     getUserRefriList/유저이름/
     getUserRefri/유저이름/냉장고 시리얼/
     
     */
     
      while (true) 
      {
    	String msg = fromConsole.readLine();
    	 //String msg = new String("addUser/ay47/3579/이찬영/");
    	  
    	client.handleMessageFromClientUI(msg); // 클라이언트에서 처리
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
