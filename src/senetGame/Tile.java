package senetGame;

public enum Tile {
	// https://stackoverflow.com/questions/3978654/best-way-to-create-enum-of-strings#3978690
	DOT {
		public String toString() {
			return ".";
		}
	},
	O,
	X,
}
