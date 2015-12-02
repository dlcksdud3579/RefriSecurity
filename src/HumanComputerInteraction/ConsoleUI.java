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
		System.out.println("�����������������������"); // 22��
		System.out.println("  Refri Security 1.0  ");
		System.out.println("    ��    �����    ��    ");
		System.out.println("    ������    ������    ");
		System.out.println("    ����  �����  ����    ");
		System.out.println("     ���  �����  ���     ");
		System.out.println("     ���  �����  ���     ");
		System.out.println("      ��  ��  ��  ��      ");
		System.out.println("       ���    ���       ");
		System.out.println("         �����         ");
		System.out.println("�����������������������");
		System.out.println();
		
		startScreen();
		
		
	}
	
	private void startScreen()
	{
		System.out.println("");
		/////////////////////////////////////////////
		// �޴� ���� (flag ������ �̿�)
		// 0 : �ʱ� ��
		// 1 : LogIn
		// 2 : SignUp
		// 3 : Exit
		// 10 : LogIn ����
		// 11 : LogIn ����
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
		case 1 : // log in ����
			logInScreen();
			break;
		case 2 : // sign up ����
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
		// 1 : �α��� ����
		
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
			
			// ���̵� �н����� üũ�� ���� �α����� ����Ѵ�.
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
				// ���� ȭ�� ������ ���
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
		// 1 : ���� �� �� ����
		// -1 : ���̵� �ߺ�
		
		System.out.println("**************************************************");// * : 5�� �߰�
		System.out.println("* Sign Up                                        *");
		System.out.println("*  Enter ID, Password and Name you want to make  *");
		System.out.println("**************************************************");// * : 5�� �߰�
		
		
		while (true)
		{
			System.out.printf(" ID : ");
			id = scan.next();
			
			System.out.printf(" PW : ");
			pw = scan.next();
			
			System.out.printf(" Name : ");
			name = scan.next();
			
			// CLogInŬ������ ���� ������ؼ� ���̵�� ��й�ȣ�� �̷��� �Է��ص� �Ǵ��� üũ�Ѵ�.
			//login = new CLogin(id, pw, name);
			//flag = login.SignUpCheck();
			clientConsole.accept("addUser/" + id + "/" + pw + "/" + name + "/");
			clientConsole.setWaitBool(true);
			
			while ( clientConsole.isWaitBool() );
			
			if (clientConsole.isFlag())
			{
				System.out.println("Sing Up Success!!");
				break;
				//login.SignUp();//sign up, ������ ������Ʈ�� �߰��ؾ��Ѵ�.
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
