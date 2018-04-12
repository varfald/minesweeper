package minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import minesweeper.UserInterface;
import minesweeper.core.Clue;
import minesweeper.core.Field;
import minesweeper.core.GameState;
import minesweeper.core.Mine;
import minesweeper.core.Tile;
import minesweeper.core.Tile.State;

/**
 * Console user interface.
 */
public class ConsoleUI implements UserInterface {
	/** Playing field. */
	private Field field;

	/** Input reader. */
	private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * Reads line of text from the reader.
	 * 
	 * @return line as a string
	 */
	private String readLine() {
		try {
			return input.readLine();
		} catch (IOException e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * minesweeper.consoleui.UserInterface#newGameStarted(minesweeper.core.Field)
	 */
	@Override
	public void newGameStarted(Field field) {
		this.field = field;
		do {
			update();
			processInput();
			if (field.getState() == GameState.SOLVED) {
				System.out.println("You won");

			}
			if (field.getState() == GameState.FAILED) {

				System.out.println("Game over!");
				System.exit(0);
			}

		} while (true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see minesweeper.consoleui.UserInterface#update()
	 */
	@Override
	public void update() {
		System.out.println("Zostavajuci pocet min: " + field.getRemainingMineCount());
		System.out.println("  0 1 2 3 4 5 6 7 8 ");
		int charBorder = 65;
		for (int row = 0; row < field.getRowCount(); row++) {
			System.out.print((char) (charBorder + row));
			System.out.print(" ");
			for (int column = 0; column < field.getColumnCount(); column++) {
				Tile tile = field.getTile(row, column);
				if (tile != null) {

					if (tile.getState() == State.CLOSED) {
						System.out.print("-");
					} else if (tile.getState() == State.MARKED) {

						System.out.print("M");
					} else if (tile instanceof Mine) {
						System.out.print("X");
					} else {
						System.out.print(((Clue) tile).getValue());
					}
					System.out.print(" ");
				}
			}
			System.out.println();
		}

	}

	/**
	 * Processes user input. Reads line from console and does the action on a
	 * playing field according to input string.
	 */

	private void processInput() {
		System.out.println("Vzor zadania: X – ukoncenie hry, MA1 – oznacenie dlazdice v riadku A a stlpci 1,\r\n"
				+ "OB4 – odkrytie dlazdice v riadku B a stlpci 4");
		System.out.println("Zadaj vstup: ");
		try {
			handleInput(readLine());
		} catch (WrongFormatException e) {

			System.out.println(e.getMessage());
		}

	}

	private void handleInput(String input) throws WrongFormatException {
		input = input.toUpperCase();
		input= input.trim();

		Pattern pattern = Pattern.compile("([X])|(M([A-Z])([0-9]))|(O([A-Z])([0-9]))");
		Matcher matcher = pattern.matcher(input);

		if (matcher.matches()) {

			if (input.charAt(0) == 'X') {
				System.out.println("Hra bola ukoncena hracom!");
				System.exit(0);
			}

			int row = input.charAt(1);
			row -= 65;
			int column = Character.getNumericValue(input.charAt(2));
			if (field.getRowCount() < row) {
				throw new WrongFormatException("Nie je tolko riadkov!");
			}
			if (field.getColumnCount() < column) {
				throw new WrongFormatException("Nie je tolko stlpcov!");
			}

			if (input.charAt(0) == 'M') {
				field.markTile(row, column);
			}
			if (input.charAt(0) == 'O') {

				field.openTile(row, column);
			}

		} else
			throw new WrongFormatException("Nespravne zadany vstup");

	}
}
