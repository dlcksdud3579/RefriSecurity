package HumanComputerInteraction;

import java.util.Scanner;

import Client.*;

public class ConsoleUI {
	
	private ClientConsole clientConsole;
	private Scanner scan;
	private boolean wait;
	private int logInFlag;
	
	// constructor /////////////////////////////////////////////
	public ConsoleUI() {
		setClientConsole( null );
		this.scan = new Scanner( System.in );
	}
	////////////////////////////////////////////////////////////
	
	
	public void start()
	{
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■"); // 22개
		System.out.println("  Refri Security 1.0  ");
		System.out.println("    ■    ■■■■    ■    ");
		System.out.println("    ■■■■■    ■■■■■    ");
		System.out.println("    ■■■  ■■■■  ■■■    ");
		System.out.println("     ■■  ■■■■  ■■     ");
		System.out.println("     ■■  ■■■■  ■■     ");
		System.out.println("      ■  ■  ■  ■      ");
		System.out.println("       ■■    ■■       ");
		System.out.println("         ■■■■         ");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println();
		
		startScreen();
		
		
	}
	
	private void startScreen()
	{
		System.out.println("");
		/////////////////////////////////////////////
		// 메뉴 선택 (flag 변수를 이용)
		// 0 : 초기 값
		// 1 : LogIn
		// 2 : SignUp
		// 3 : Exit
		// 10 : LogIn 성공
		// 11 : LogIn 실패
		//////////////////////////////////////////////
		
		int flag = 0;
		
		while ( true )
		{
			System.out.println("******************");
			System.out.println("* 1 : Log in     *");
			System.out.println("* 2 : Sing up    *");
			System.out.println("* 3 : Exit       *");
			System.out.println("******************");
			
			System.out.printf("Input :: ");
			flag = scan.nextInt();
			
			System.out.println();
			System.out.println();
			if (flag >= 1 && flag <= 3)
				break;
			else
				System.out.println("!! Exceptional an input : Re-enter, please. !!");
		}
		
		
		switch (flag) {
		case 1 : // log in 선택
			logInScreen();
			break;
		case 2 : // sign up 선택
			signUpScreen();
			break;
		case 3 :
			System.out.println("!! Thanks for using this program !!");
			System.exit(1);
			break;
		}
	}
	
	private void logInScreen()
	{
		String id, pw;
		int flag = 0;
		// 0 : default
		// -1 : id error
		// -2 : pw error
		// 1 : 로그인 성공
		
		System.out.println("*********************************");
		System.out.println("* Log In                        *");
		System.out.println("*   Enter your ID and Password  *");
		System.out.println("*********************************");
		
		while (true)
		{
			System.out.printf(" ID : ");
			id = scan.next();
			System.out.printf(" PW : ");
			pw = scan.next();
			
			// 아이디 패스워드 체크를 통해 로그인을 허용한다.
			//login = new CLogin(id, pw);
			//flag = login.LoginCheck();
			String msg = "getUser/" + id + "/";
			clientConsole.accept(msg);
			clientConsole.setWaitBool(true);
			
			while ( clientConsole.isWaitBool() );
			
			if (clientConsole.isFlag()) {
				System.out.println("Login Success!");
				break;
			}
			else
				System.out.println("Login Fail...");
		}
		
	}
	
	private void signUpScreen()
	{
		String id, pw, name;
		// 0 : default
		// 1 : 가입 할 수 있음
		// -1 : 아이디 중복
		
		System.out.println("**************************************************");// * : 5개 추가
		System.out.println("* Sign Up                                        *");
		System.out.println("*  Enter ID, Password and Name you want to make  *");
		System.out.println("**************************************************");// * : 5개 추가
		
		
		while (true)
		{
			System.out.printf(" ID : ");
			id = scan.next();
			
			System.out.printf(" PW : ");
			pw = scan.next();
			
			System.out.printf(" Name : ");
			name = scan.next();
			
			// CLogIn클래스를 통해 통신을해서 아이디와 비밀번호를 이렇게 입력해도 되는지 체크한다.
			//login = new CLogin(id, pw, name);
			//flag = login.SignUpCheck();
			clientConsole.accept("addUser/" + id + "/" + pw + "/" + name + "/");
			
			clientConsole.setWaitBool(true);
			while ( clientConsole.isWaitBool() );
			
			if (clientConsole.isFlag())
			{
				System.out.println("Sing Up Success!!");
				break;
				//login.SignUp();//sign up, 별도의 성공멘트를 추가해야한다.
			}
			else if ( !clientConsole.isFlag() )
			{
				System.out.printf(" !! This [%s] ID already exits.\n", id);
				System.out.println(" !! Please reenter ID and Password.");
			}
			else {
				System.out.printf("Sign up Error : %s, %s, dont know this input type case.\n", id, pw);
				break;
			}
		}
		
		startScreen();
	}
	
	public void refriListScreen()
	{
		System.out.printf("Hello \" %s \" user!\n", clientConsole.getNowUser().getName());
		
		System.out.println("***************************************");
		System.out.println("*      Refrigerator Choice Screen     *");
		System.out.println("*                                     *");
		System.out.println("* 0. Exit                             *");
		System.out.println("* 1. View your refrigerators          *");
		System.out.println("* 2. Create a new refrigerator        *");
		System.out.println("* 3. Delete a refrigerator            *");
		System.out.println("***************************************");
		
		while (true)
		{
			System.out.printf("input :: ");
			int flag = scan.nextInt();
			
			if (flag == 0) {
				System.out.println("!! Thanks for using this program !!");
				System.exit(1);
				return;
			}
			else if (flag == 1) {
				viewRefriScreen();
				return;
			}
			else if (flag == 2) {
				createRefriScreen();
				return;
			}
			else if (flag == 3) {
				DeleteRefriScreen();
				return;
			}
			else  {
				
			}
		}
	}
	
	public void createRefriScreen()
	{
		System.out.println("***************************************");
		System.out.println("* Create a new refrigerator           *");
		System.out.println("*   Enter a name of the refrigerator  *");
		System.out.println("***************************************");
		
		System.out.printf(" name : ");
		String name = scan.next();
		
		
		clientConsole.accept("addRefri/" + name + "/" + clientConsole.getNowUser().getID() + "/");
		clientConsole.setWaitBool(true);
		while ( clientConsole.isWaitBool() );
		
		if ( clientConsole.isFlag() ) {
			System.out.println("Create Success!\n");
		}
		else {
			System.out.println("Create Fail....\n");
		}
		
		//refriListScreen();
	}
	
	public void DeleteRefriScreen()
	{
		System.out.println("***************************************");
		System.out.println("* Delete a  refrigerator           *");
		System.out.println("*   Enter a number of the refrigerator  *");
		System.out.println("***************************************");
		
		System.out.printf(" name : ");
		int index = scan.nextInt();
		
		
		clientConsole.accept("rmoveRefri/"+ clientConsole.getNowUser().getID() + 
				"/"+clientConsole.getNowUser().getMyRefrigerator().get(index).getSerial()+ "/");
		clientConsole.setWaitBool(true);
		while ( clientConsole.isWaitBool() );
		
		if ( clientConsole.isFlag() ) {
			System.out.println("Create Success!\n");
		}
		else {
			System.out.println("Create Fail....\n");
		}
		
		//refriListScreen();
	}
	
	public void viewRefriScreen()
	{
		int i, flag;
		
		System.out.println("***************************************"); //39
		System.out.println("*        Your refrigerator list       *");
		System.out.println("*   Enter a name of the refrigerator  *");
		System.out.println("*                                     *");
		
		for(i =0;i<clientConsole.getNowUser().getMyRefrigerator().size();i++) {
			String ss = "* " + i + " : " + 
						clientConsole.getNowUser().getMyRefrigerator().get(i).getName();
			
			for (int j = ss.length(); j < 38; j++)
				ss += " ";
			ss += "*";
			
			System.out.println(ss);
		}
		
		System.out.println("***************************************");
		
		while (true)
		{
			System.out.printf(" Input a refrigerator number you want :: ");
			flag = scan.nextInt();
			
			if(flag>i)
				continue;
			else
				break;
		}
		
		viewRefriInerScreen(clientConsole.getNowUser().getMyRefrigerator().get(flag));
	}
	public void viewRefriInerScreen(CRefrigerator refri)
	{
		int i;
		System.out.println("***************************************"); // 39
		System.out.println("*       your refrigeratorIner         *");
		System.out.println("*   Enter a name of the refrigerator  *");
		
		refri.setClientConsole(clientConsole);
		refri.getRefriInfoFromServer();
		
		for(i =0;i<refri.getFoodList().size();i++)
		{
			String ss = "* " + i + ":" + refri.getFoodList().get(i).toString();
			
			for (int j = ss.length(); j < 38; j++)
				ss += " ";
			ss += "*";
					
			System.out.println( ss );
		}
		
		System.out.println("***************************************");
		
		
		while (true)
		{
			System.out.printf(" Input a refrigerator number you want :: ");
			int flag = scan.nextInt();
			
			if(flag>i)
				continue;
			
		}
		
	}
	
	
	public void nextScreen()
	{
		for (int i = 0 ; i < 20; i++)
			System.out.println("");
	}
	
	
	// getter setter /////////////////////////////////////////////

	public ClientConsole getClientConsole() {
		return clientConsole;
	}
	public void setClientConsole(ClientConsole clientConsole) {
		this.clientConsole = clientConsole;
	}
	public boolean isWait() {
		return wait;
	}
	public void setWait(boolean wait) {
		this.wait = wait;
	}
	public int getLogInFlag() {
		return logInFlag;
	}
	public void setLogInFlag(int logInFlag) {
		this.logInFlag = logInFlag;
	}

	
}
