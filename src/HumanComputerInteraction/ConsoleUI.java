package HumanComputerInteraction;

import java.util.Scanner;

import Client.*;

public class ConsoleUI {
	
	private ClientConsole clientConsole;
	private Scanner scan;
	private CUser 	nowUser;
	
	// constructor /////////////////////////////////////////////
	public ConsoleUI() {
		setClientConsole( null );
		this.scan = new Scanner( System.in );
		setUser( null );
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
			String msg = "getUser/" + id;
			clientConsole.accept(msg);
			
			if (flag == -1) {
				System.out.printf(" This [%s] ID does not exit\n\n", id);
			}
			else if (flag == -2) {
				System.out.printf(" This [%s] PW does not exit\n\n", pw);
			}
			else if (flag == 1) {
				// 다음 화면 나오게 출력
				refriListScreen();
				break;
			}
			else if (flag == 0) { // develop mode
				System.out.println(" Develope master login ");
				refriListScreen();
				break;
			}
		}
	}
	
	private void signUpScreen()
	{
		String id, pw, name;
		int flag = 0;
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
	
	private void refriListScreen()
	{
		
	}
	
	
	private void nextScreen()
	{
		for (int i = 0 ; i < 20; i++)
			System.out.println("");
	}
	
	
	// getter setter /////////////////////////////////////////////
	public CUser getUser()
	{
		return this.nowUser;
	}
	public void setUser(CUser user) 
	{
		this.nowUser = user;
	}
	public ClientConsole getClientConsole() {
		return clientConsole;
	}
	public void setClientConsole(ClientConsole clientConsole) {
		this.clientConsole = clientConsole;
	}

	
}
