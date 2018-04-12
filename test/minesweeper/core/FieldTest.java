package minesweeper.core;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FieldTest {
	static final int ROWS = 9;
	static final int COLUMNS = 9;
	static final int MINES = 10;

	@Test
	public void isSolved() {
		Field field = new Field(ROWS, COLUMNS, MINES);

		assertEquals(GameState.PLAYING, field.getState());

		int open = 0;
		for (int row = 0; row < field.getRowCount(); row++) {
			for (int column = 0; column < field.getColumnCount(); column++) {
				if (field.getTile(row, column) instanceof Clue) {
					field.openTile(row, column);
					open++;
				}
				if (field.getRowCount() * field.getColumnCount() - open == field.getMineCount()) {
					assertEquals(GameState.SOLVED, field.getState());
				} else {
					assertNotSame(GameState.FAILED, field.getState());
				}
			}
		}

		assertEquals(GameState.SOLVED, field.getState());
	}

	@Test
	public void generate() {
		Field field = new Field(ROWS, COLUMNS, MINES);
		assertEquals(ROWS, field.getRowCount());
		assertEquals(COLUMNS, field.getColumnCount());
		assertEquals(MINES, field.getMineCount());
		
		int mineCount = 0;
		int clueCount = 0;
		for (int row = 0; row < field.getRowCount(); row++) {
			for (int column = 0; column < field.getColumnCount(); column++) {
				Tile tile = field.getTile(row, column);
				assertNotNull(tile);
				if (tile instanceof Mine) {
					mineCount++;
				}
				if (tile instanceof Clue) {
					clueCount++;
				}
			}
		}
		assertEquals(MINES, mineCount);
		assertEquals(ROWS * COLUMNS - MINES, clueCount);
	}
}
