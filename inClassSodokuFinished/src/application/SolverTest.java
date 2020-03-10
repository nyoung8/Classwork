package application;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class SolverTest {
	
	Random random = new Random();

	@Test
	void testHelper() {
		Solver solver = new Solver();
		
		List<String> test = new ArrayList<String>();
		test.add("1");
		assertTrue(solver.helperValid(test));
		test.add("2");
		assertTrue(solver.helperValid(test));
		test.add("3");
		assertTrue(solver.helperValid(test));
		test.add("4");
		assertTrue(solver.helperValid(test));
		test.add("5");
		assertTrue(solver.helperValid(test));
		test.add("6");
		assertTrue(solver.helperValid(test));
		test.add("7");
		assertTrue(solver.helperValid(test));
		test.add("8");
		assertTrue(solver.helperValid(test));
		test.add("9");
		assertTrue(solver.helperValid(test));
		test.add("9");
		assertFalse(solver.helperValid(test));
		
	}
	
	@Test
	void testCharsValid() {
		Solver solver = new Solver();
		String[][] copy = new String[9][9];
		
		for(int r = 0 ; r < 9 ; r++) {
			for(int c = 0 ; c < 9 ; c++) {
				copy[c][r] = new String(""+(r+1));
			}
		}
		copy[0][0] = new String("");
		assertTrue(solver.charsValid(copy));
		copy[0][0] = new String("A");
		assertFalse(solver.charsValid(copy));
	}
	
	@Test
	void testRowsValid() {
		Solver solver = new Solver();
		String[][] copy = new String[9][9];
		
		for(int r = 0 ; r < 9 ; r++) {
			for(int c = 0 ; c < 9 ; c++) {
				copy[c][r] = new String(""+(c+1));
			}
		}
		assertTrue(solver.rowsValid(copy));
		copy[0][0] = new String("1");
		copy[1][0] = new String("1");
		assertFalse(solver.rowsValid(copy));
	}
	
	@Test
	void testColumnsValid() {
		Solver solver = new Solver();
		String[][] copy = new String[9][9];
		
		for(int r = 0 ; r < 9 ; r++) {
			for(int c = 0 ; c < 9 ; c++) {
				copy[c][r] = new String(""+(r+1));
			}
		}
		assertTrue(solver.columnsValid(copy));
		copy[0][0] = new String("1");
		copy[0][1] = new String("1");
		assertFalse(solver.columnsValid(copy));
	}
	
	
	@Test
	void testSquaresValid() {
		Solver solver = new Solver();
		String[][] copy = new String[9][9];
		
		for(int r = 0 ; r < 9 ; r++) {
			for(int c = 0 ; c < 9 ; c++) {
				copy[c][r] = new String("");
			}
		}
		assertTrue(solver.squaresValid(copy));
		copy[0][0] = new String("1");
		copy[0][1] = new String("1");
		assertFalse(solver.squaresValid(copy));
	}
	
	
	@Test
	void testDuplicate() {
		Solver solver = new Solver();
		String[][] start = new String[9][9];
		
		for(int r = 0 ; r < 9 ; r++) {
			for(int c = 0 ; c < 9 ; c++) {
				start[c][r] = UUID.randomUUID().toString();
			}
		}
		String[][] end = solver.duplicate(start);
		for(int r = 0 ; r < 9 ; r++) {
			for(int c = 0 ; c < 9 ; c++) {
				assertTrue(start[c][r].equals(end[c][r]));
				start[c][r] = UUID.randomUUID().toString();
			}
		}
		for(int r = 0 ; r < 9 ; r++) {
			for(int c = 0 ; c < 9 ; c++) {
				assertFalse(start[c][r].equals(end[c][r]));
			}
		}
	}

}
