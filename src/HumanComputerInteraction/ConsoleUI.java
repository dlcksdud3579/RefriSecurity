package HumanComputerInteraction;

import java.util.Scanner;

public class ConsoleUI {
	
	private Scanner scan;
	
	// constructor /////////////////////////////////////////////
	public ConsoleUI() {
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
			System.out.println();
		}
		
		
		switch (flag) {
		case 1 : // log in 선택
			
			break;
		case 2 : // sign up 선택
			
			break;
		}
	}
	
	
	
	private void nextScreen()
	{
		for (int i = 0 ; i < 20; i++)
			System.out.println("");
	}
}
