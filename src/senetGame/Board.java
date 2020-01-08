package senetGame;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
	
	HashMap<Integer, Tile> board = new HashMap<Integer, Tile>();
	
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
			
			// DEBUG
			if (i == 11 ||i == 12) {
				
				board.put(i, Tile.X);
			}
		}
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
	
	public void move(int index, int distance) {
		Tile currentPawn = board.get(index);
		
		// Does your next position have a pawn of you
//		if(board.get(index+distance).equals(currentPawn)) {
//			return false;
//		}
		
		board.put(index+distance, currentPawn);
		board.put(index, Tile.DOT);
		this.print();
	}
	
	public boolean moveVerifier(int currentLocation, int distance) {
		Tile currentPawn = board.get(currentLocation);
		Tile opposingPawn = currentPawn.equals(Tile.X) ? Tile.O : Tile.X;
		
		// Does your next position have a pawn of you
		if(board.get(currentLocation+distance).equals(currentPawn)) {
			System.out.println("Next location is already occupied by one of your own pawns.");
			return false;
		}
		
		// check if pawns are a group of 2
		if(board.get(currentLocation+distance).equals(opposingPawn)) {
			// One in front of the pawn and one behind the pawn
			if (board.get(currentLocation+distance -1 ).equals(opposingPawn) || board.get(currentLocation+distance +1).equals(opposingPawn)) {	
				System.out.println("Next location of pawn has two of your opponent's pawns standing next to each other.");
				return false;
			}
		}
		
		// row of 3 opponent's pawns
		ArrayList<Integer> opposingPawnLocations = new ArrayList<Integer>();
		for (int i = currentLocation; i <= currentLocation+distance; i++) {
			Tile tile = board.get(i);
			// Get all pawns
			if(tile == opposingPawn) {
				opposingPawnLocations.add(i);
			}
		}
		
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
		
		
		return true;
	}
}
