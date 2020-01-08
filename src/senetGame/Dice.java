package senetGame;

import java.util.Random;

public class Dice {
	
	private Random rand = new Random();
	
	public Integer throwSticks(String playerIdentifier) {
		int numberOfSticks = 4;
		
		switch (calculateNumberOfBlackSticks(numberOfSticks)) {
		case 0:
			System.out.println(playerIdentifier +" threw 6.");
			return 6;
		case 1:
			System.out.println(playerIdentifier +" threw 1.");
			return 1;
		case 2:
			System.out.println(playerIdentifier +" threw 2.");
			return 2;
		case 3:
			System.out.println(playerIdentifier +" threw 3.");
			return 3;
		case 4:
			System.out.println(playerIdentifier +" threw 4.");
			return 4;
		default:
	        return -1;
		}
	}
	
	
	private int calculateNumberOfBlackSticks(int numberOfSticks) {
		int numberBlacks = 0;
		
		for (int i = 0; i < numberOfSticks; i++) {
			// 1 = black and 0 = white
			if(rand.nextInt(2) == 1) {
				numberBlacks++;
			}
		}
		
		return numberBlacks;
	}
}
