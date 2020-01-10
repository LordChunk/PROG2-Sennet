package senetGame;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
	
	private HashMap<Integer, Tile> board = new HashMap<Integer, Tile>();
	
	public Board() {
		int boardSize = 30;
		
		// For loop starts at 1 to easily identify the actual spot on the board
		for (int i = 1; i <= boardSize; i++) {
			if(i % 2 == 1 && i <= 10) {
				board.put(i, Tile.O);
			} else if(i % 2 == 0 && i <= 10) {
				board.put(i, Tile.X);
			} else {
				board.put(i, Tile.DOT);
			}
			
		}
		// Move pawn into correct starting position 
		move(10, 1, false);
	}
	
	public Tile get(int key) {
		return board.get(key);
	}
	
	public void print() {
		System.out.println("+----------+");
		String outputLine = "";
		for (int i = 1; i <= 10; i++) {
			outputLine += board.get(i);
		}
		System.out.println("|"+outputLine+"|");
		outputLine = "";
		for (int i = 20; i > 10; i--) {
			outputLine += board.get(i);
		}
		System.out.println("|"+outputLine+"|");
		outputLine = "";
		for (int i = 21; i <= 30; i++) {
			outputLine += board.get(i);
		}
		System.out.println("|"+outputLine+"|");
		System.out.println("+----------+");
	}
	
	public boolean move(int currentLocation, int distance, boolean verify) {
		Tile currentPawn = board.get(currentLocation);
		Tile opposingPawn = currentPawn.equals(Tile.X) ? Tile.O : Tile.X;
		int valueOfNextField = currentLocation+distance;
		
		// Verify move
		if(verify && !moveVerifier(currentLocation, distance)) {			
			return false;
		}
		// Reset current field
		board.put(currentLocation, Tile.DOT);
		
		// RULE 2
		if(board.get(valueOfNextField).equals(opposingPawn)) {
			board.put(currentLocation, opposingPawn);
		}
		
		// RULE 3
		if(valueOfNextField == 27) {
			for (int i = 1; i <= 10; i++) {
				if(board.get(i) != Tile.DOT) {
					board.put(i, currentPawn);
					break;
				}
			}
		}
		
		// RULE 4
		if (valueOfNextField != 30) {			
			board.put(valueOfNextField, currentPawn);
		}
		this.print();
		return true;
	}

	
	public boolean moveVerifier(int currentLocation, int distance) {
		// input sanitation
		if (currentLocation > 30 || currentLocation < 1) {
			System.out.println("Input out of bounds. Please check your dice and location values.");
			return false;
		}
		Tile currentPawn = board.get(currentLocation);
		Tile opposingPawn = currentPawn.equals(Tile.X) ? Tile.O : Tile.X;
		int valueOfNextField = currentLocation+distance;
		
		// RULE X
		// Does your next position have a pawn of you
		if(board.get(valueOfNextField).equals(currentPawn)) {
			System.out.println("Next location is already occupied by one of your own pawns.");
			return false;
		}
		
		/*
		 * Rule 1 is checked for by RULE 2 checking against all other rules
		 */
		
		// RULE 1
		if(board.get(currentLocation+distance).equals(opposingPawn)) {
			// RULE 2
			// One in front of the pawn and one behind the pawn
			if (board.get(valueOfNextField -1 ).equals(opposingPawn) || board.get(valueOfNextField +1).equals(opposingPawn)) {	
				System.out.println("Next location of pawn has two of your opponent's pawns standing next to each other.");
				return false;
			}
			
			// RULE 7
			if(valueOfNextField == 26 || valueOfNextField == 28 || valueOfNextField == 29) {
				System.out.println("Your opponent already has a pawn in protected field "+ valueOfNextField +".");
				return false;
			}
		}
		
		// RULE 6
		// row of 3 opponent's pawns 
		ArrayList<Integer> opposingPawnLocations = getTilesFromRange(currentLocation, distance, opposingPawn);
		
		// Check if there are at least 3 items in the array
		// https://stackoverflow.com/questions/4940799/check-to-see-if-3-values-in-array-are-consecutive
		if(opposingPawnLocations.size() > 2) {
			for (int i = 0; i < opposingPawnLocations.size() -2; i++) {
			    if (opposingPawnLocations.get(i + 1) == opposingPawnLocations.get(i) + 1 
		    		&& opposingPawnLocations.get(i + 2) == opposingPawnLocations.get(i) + 2) {
			    		System.out.println("Your pawn is blocked by 3 consecutive pawns from your opponent");
			    		return false;
			    	}
			}
		}
		
		// RULE 8
		if(valueOfNextField == 30) {
			ArrayList<Integer> pawns = getTilesFromRange(1, 19, currentPawn);
			
			if(pawns.size() > 0) {
				System.out.println("You cannot move your pawn to field 30 as not all of your pawns are on the last row yet.");
				return false;
			}
		}
		
		// RULE 9
		if(valueOfNextField > 30) {
			System.out.println("You cannot move to past field 30.");
			return false;
		}
		
		return true;
	}
	
	public ArrayList<Integer> getAllTileLocationsOfType(Tile tileType) {
		return getTilesFromRange(1, 29, tileType);
	}
	
	private ArrayList<Integer> getTilesFromRange(int startLocation, int distance, Tile tileType) {
		ArrayList<Integer> pawnLocations = new ArrayList<Integer>();
		
		for (int i = startLocation; i <= startLocation+distance; i++) {
			Tile tile = board.get(i);
			// Get all pawns
			if(tile == tileType) {
				pawnLocations.add(i);
			}
		}
		
		return pawnLocations;
	}
	
	public boolean checkForAnyValidMoves(int diceValue, Tile tile) {
		ArrayList<Integer> allTileLocations = getAllTileLocationsOfType(tile);
		boolean allMovesAreInvalid = true;
		for (Integer tileLocation : allTileLocations) {
			if(moveVerifier(tileLocation, diceValue)) {
				allMovesAreInvalid = false;
			}
		}
		
		return !allMovesAreInvalid;
	}
}
