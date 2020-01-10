package senetGame;

public class Senet {
	
	private Player player1;
	private Player player2;
	
	private Board board;
	private Dice dice;
	private ConsoleIO consoleIO;
	
	public void play() {
		consoleIO = new ConsoleIO();
		dice = new Dice();
		
		System.out.println("Fill in the name of player 1:");
//		String playerName1 = consoleIO.readInput();
		String playerName1 = "Job";
		
		System.out.println("Fill in the name of player 2:");
//		String playerName2 = consoleIO.readInput();
		String playerName2 = "Merk";
		
		
		boolean isPlayer1Starting = determineStartingPlayer(playerName1, playerName2);
		
		player1 = new Player(playerName1, isPlayer1Starting);
		player2 = new Player(playerName2, !isPlayer1Starting);
		
		Player currentPlayer = isPlayer1Starting ? player1 : player2; 
		
		System.out.println(currentPlayer.getPlayerIdentifier() +" starts.");
		
		board = new Board();
		boolean won = false;
		while (!won) {
			turn(currentPlayer);
			// Check if board still has any pawns of current player remaining
			if (board.doesBoardContainTile(currentPlayer.getColourSign())) {
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
//		int diceValue = dice.throwSticks(player);
		System.out.println("Enter your dice value");
		int diceValue = Integer.parseInt(consoleIO.readInput());
		System.out.println(player.getPlayerIdentifier() +" threw: "+ diceValue +".");
		
		boolean selectedValidPawn = false;
		int pawnLocation = -1;
		
		while (!selectedValidPawn) {
			System.out.println("Which pawn do you want to move?");
			pawnLocation = -1;
			while (pawnLocation == -1) {
				try {
					String pawnLocationString = consoleIO.readInput();
					pawnLocation = Integer.parseInt(pawnLocationString);
				} catch (Exception e) {
					System.out.println("Your input was invalid. Please enter a valid value.");
				}
			}
			
			if(board.get(pawnLocation) == player.getColourSign()) {
				selectedValidPawn = board.move(pawnLocation, diceValue, true);
			} else {
				System.out.println("The field you selected does not contain one of your pawns.");
			}			
		}
		
		int nextLocation = pawnLocation+diceValue;
		System.out.println(player.getPlayerIdentifier() +": moved their pawn from: "+ pawnLocation +" -> "+ nextLocation);
		
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
