package minesweeper;

import minesweeper.consoleui.ConsoleUI;
import minesweeper.core.Field;

/**
 * Main application class.
 */
public class Minesweeper {
	/** User interface. */
	private UserInterface userInterface;
	private static Minesweeper instance;
	private static long startMillis;
	
	
	public static long getStartMillis() {
		return startMillis;
	}

	public static void setStartMillis(long startMillis) {
		Minesweeper.startMillis = startMillis;
	}

	public BestTimes bestTimes = new BestTimes();

	/**
	 * Constructor.
	 */
	private Minesweeper() {
		instance = this;
		startMillis = getStartMillis();
		userInterface = new ConsoleUI();
		Field field = new Field(9, 9, 10);
		startMillis = getStartMillis();
		userInterface.newGameStarted(field);
	}

	public static Minesweeper getInstance() {
		return instance;
	}

	public static int getPlayingSeconds() {
		return (int) (System.currentTimeMillis() - startMillis) / 1000;

	}

	public BestTimes getBestTimes() {
		return bestTimes;
	}

	public static void main(String[] args) {
		setStartMillis(startMillis);
		new Minesweeper();

	}

}
