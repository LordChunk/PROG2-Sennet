package senetGame;
public class Senet {
	
	private Player player1;
	private Player player2;
	
	private int playMode;
	private Board board;
	private Dice dice;
	private ConsoleIO consoleIO;
	
	public void play() {
		consoleIO = new ConsoleIO();
		dice = new Dice();
		
		System.out.println("Would you like to start a normal game (0) or a test position (1)");
		
		playMode = -1;
		while (playMode != 0 && playMode != 1) {			
			playMode = consoleIO.readInputInt();
		}
		
		String playerName1;
		String playerName2;
		
		// Check for test mode
		if(playMode > 0) {
			playerName1 = "player 1";
			playerName2 = "player 2";
			System.out.println("Test mode enabled. Dice rolls are disabled.");
		} else {
			System.out.println("Fill in the name of player 1:");
			playerName1 = consoleIO.readInput();
			System.out.println("Fill in the name of player 2:");		
			playerName2 = consoleIO.readInput();			
		}
		
		
		
		boolean isPlayer1Starting = determineStartingPlayer(playerName1, playerName2);
		
		player1 = new Player(playerName1, isPlayer1Starting);
		player2 = new Player(playerName2, !isPlayer1Starting);
		
		Player currentPlayer = isPlayer1Starting ? player1 : player2; 
		
		System.out.println(currentPlayer.getPlayerIdentifier() +" starts.");
		
		board = new Board(playMode);
		boolean won = false;
		while (!won) {
			turn(currentPlayer);
			// Check if board still has any pawns of current player remaining
			if (board.getAllTileLocationsOfType(currentPlayer.getColourSign()).size() != 0) {
				// Switch players
				if(currentPlayer.equals(player1)) {
					currentPlayer = player2;
				} else {
					currentPlayer = player1;
				}
			} else {
				won = true;
			}
		}
		
		System.out.println(currentPlayer.getPlayerIdentifier() +" has won the game!");
	}

	private void turn(Player player) {
		System.out.println(player.getPlayerIdentifier() +" starts their turn.");
		int diceValue;
		if (playMode == 0) {			
			diceValue = dice.throwSticks(player);
		} else {			
			System.out.println("Enter your dice value");
			diceValue = Integer.parseInt(consoleIO.readInput());
		}
		System.out.println(player.getPlayerIdentifier() +" threw: "+ diceValue +".");
		
		// Check if there are any valid moves
		if(board.checkForAnyValidMoves(diceValue, player.getColourSign())) {			
			boolean selectedValidPawn = false;
			int pawnLocation = -1;
			
			while (!selectedValidPawn) {
				System.out.println("Which pawn do you want to move?");
				pawnLocation = consoleIO.readInputInt();
				
				if(board.get(pawnLocation) == player.getColourSign()) {
					selectedValidPawn = board.move(pawnLocation, diceValue, true);
				} else {
					System.out.println("The field you selected does not contain one of your pawns.");
				}			
			}
			
			int nextLocation = pawnLocation+diceValue;
			System.out.println(player.getPlayerIdentifier() +": moved their pawn from: "+ pawnLocation +" -> "+ nextLocation);
		} else {
			System.out.println("There were no valid moves.");
		}		
	}

	// true = player 1 and false = player2
	private boolean determineStartingPlayer(String playerName1, String playerName2) {
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
