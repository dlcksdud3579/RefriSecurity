package Client;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.util.Scanner;

import Server.SUser;
import Client.*;
import HumanComputerInteraction.ConsoleUI;
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
public class ClientConsole implements ChatIF 
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
  private ConsoleUI cui;
  private CUser nowUser;
  private String tempMsg;
  private boolean flag;
  private boolean waitBool;
  //Constructors ****************************************************

  /**
   * Constructs an instance of the ClientConsole UI.
   *
   * @param host The host to connect to.
   * @param port The port to connect on.
   */
  public ClientConsole(String host, int port) 
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
    
    this.flag = false;
  }

  
  //Instance methods ************************************************
  
  /**
   * This method waits for input from the console.  Once it is 
   * received, it sends it to the client's message handler.
   */
  public void accept(String msg) 
  {
    try
    {
    // BufferedReader fromConsole = 
    //    new BufferedReader(new InputStreamReader(System.in));
    
    // String msg = new String("addUser/ay47/3579/이찬영/"); // 유저 추가 표준 
    // String msg = new String("addFood/0/문어/1/1.0/2015/11/29/2015/11/30/삼시세끼/"); // 유저 추가 표준
      

      //while (true) 
      //{
        //message = fromConsole.readLine();
      client.handleMessageFromClientUI(msg); // 클라이언트에서 처리
      //}
    } 
    catch (Exception ex) 
    {
      System.out.println
        ("Unexpected error while reading from console!");
    }
  }

  /**
   * This method overrides the method in the ChatIF interface.  It
   * displays a message onto the screen.
   *
   * @param message The string to be displayed.
   */
  public void display(String message) 
  {
	  System.out.println(">" + message);
	  receiveMsg(message);
  }

  public void receiveMsg(String msg) // 메세지를 받아서 해석하는 함수
  {
	  this.tempMsg = msg;
	  String cmd = msg;
	  
	  
	  if (msg.equals("true")) {
		  this.flag = true;
		  setWaitBool(false);
		  return;
	  }
	  else if (msg.equals("false")) {
		  this.flag = false;
		  setWaitBool(false);
		  return;
	  }
	  
	  cmd = divideString();
	  
	  switch (cmd)
	  {

	  case "User":
		  
		  break;
	  }
	  setWaitBool(false);
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
    ConsoleUI cui = new ConsoleUI();
    chat.setCui( cui );
    cui.setClientConsole(chat);
    
    cui.start();
  }
  
  
  //과재용  추가 하는 함수 
  private static int getInputPort()
  {
	  int port = 0;
	  
	  Scanner input = new Scanner(System.in);
	  port = input.nextInt();
	  
	return port;
  }


  public ConsoleUI getCui() {
	  return cui;
  }


  public void setCui(ConsoleUI cui) {
	  this.cui = cui;
  }


public boolean isFlag() {
	return flag;
}


public void setFlag(boolean flag) {
	this.flag = flag;
}


public boolean isWaitBool() {
	return waitBool;
}


public void setWaitBool(boolean waitBool) {
	this.waitBool = waitBool;
}
  
  
}
//End of ConsoleChat class
