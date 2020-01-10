package senetGame;

public class Senet {
	
	private Player player1;
	private Player player2;
	
	private Board board;
	
	public void play(String playerName1, String playerName2) {
		boolean isPlayer1Starting = determineStartingPlayer(playerName1, playerName2);
		
		player1 = new Player(playerName1, isPlayer1Starting);
		player2 = new Player(playerName2, !isPlayer1Starting);
		
		String startingPlayer = isPlayer1Starting ? player1.getPlayerIdentifier() : player2.getPlayerIdentifier(); 
		
		System.out.println(startingPlayer +" starts.");
		
		board = new Board();
	}


	// true = player 1 and false = player2
	private boolean determineStartingPlayer(String playerName1, String playerName2) {
		Dice dice = new Dice();

		while(true) {
			// player 1
			if(dice.throwSticks(playerName1) == 1) {
				return true;
			}
			if(dice.throwSticks(playerName2) == 1) {
				return false;
			}
		}
	}
}
