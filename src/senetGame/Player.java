package senetGame;

public class Player {
	private String name;
	private Tile coloursign;
	
	public Player(String name, boolean startingPlayer) {
		this.name = name;
		
		if(startingPlayer) {
			coloursign = Tile.X;
		} else {
			coloursign = Tile.O;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public Tile getColourSign() {
		return coloursign;
	}
	
	public String getPlayerIdentifier() {
		return String.format("(%s) %s", coloursign.toString(), name);
	}
}
