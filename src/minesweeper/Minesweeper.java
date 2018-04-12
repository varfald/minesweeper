package minesweeper;

import minesweeper.consoleui.ConsoleUI;
import minesweeper.core.Clue;
import minesweeper.core.Field;
import minesweeper.core.Mine;
import minesweeper.core.Tile;
import minesweeper.core.Tile.State;

/**
 * Main application class.
 */
public class Minesweeper {
	/** User interface. */
	private UserInterface userInterface;

	/**
	 * Constructoooooor.   
	 */
	private Minesweeper() {
		 userInterface = new ConsoleUI();

		Field field = new Field(8, 8, 19);
		
		userInterface.newGameStarted(field);
		System.out.println(" hra skoncila");
	}

	

	
	public static void main(String[] args) {
		new Minesweeper();
	}
}
