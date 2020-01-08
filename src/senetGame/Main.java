package senetGame;

public class Main {	
	public static void main(String[] args) {
		ConsoleIO consoleIO = new ConsoleIO();
		
		System.out.println("Fill in the name of player 1:");
//		String playerName1 = consoleIO.readInput();
		String playerName1 = "Job";
		
		System.out.println("Fill in the name of player 2:");
//		String playerName2 = consoleIO.readInput();
		String playerName2 = "Merk";
		
		Senet currentGame = new Senet();		
		currentGame.play(playerName1, playerName2);
	}
}
