package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solver {
	
	static Set<String> checker = new HashSet<String>();
	static {
		checker.add("1");
		checker.add("2");
		checker.add("3");
		checker.add("4");
		checker.add("5");
		checker.add("6");
		checker.add("7");
		checker.add("8");
		checker.add("9");
	}
	
	boolean helperValid(List<String> checkMe) {
		HashSet<String> base = new HashSet<String>();
		base.addAll(checker);
		for(String s:checkMe) {
			if(base.contains(s)) {
				base.remove(s);
			}
			else {
				return false;
			}
		}
		return true;
	}
	
	boolean charsValid(String[][] board) {
		for(int c = 0 ; c < 9 ; c++) {
			for(int r = 0 ; r < 9 ; r++) {
				if(	!board[c][r].equals("") &&
					!board[c][r].equals("1") &&
					!board[c][r].equals("2") &&
					!board[c][r].equals("3") &&
					!board[c][r].equals("4") &&
					!board[c][r].equals("5") &&
					!board[c][r].equals("6") &&
					!board[c][r].equals("7") &&
					!board[c][r].equals("8") &&
					!board[c][r].equals("9")) {
					return false;
				}
			}
		}
		return true;
	}


	boolean rowsValid(String[][] board) {
		for(int r = 0 ; r < 9 ; r++) {
			List<String> present = new ArrayList<String>();
			for(int c = 0 ; c < 9 ; c++) {
				if(!board[c][r].equals("")){
					present.add(board[c][r]);
				}
			}
			if(!helperValid(present)) {
				return false;
			}
		}
		return true;
	}
	
	boolean columnsValid(String[][] board) {
		for(int c = 0 ; c < 9 ; c++) {
			List<String> present = new ArrayList<String>();
			for(int r = 0 ; r < 9 ; r++) {
				if(!board[c][r].equals("")){
					present.add(board[c][r]);
				}
			}
			if(!helperValid(present)) {
				return false;
			}
		}
		return true;
	}
	
	boolean squaresValid(String[][] board) {
		for(int c = 0 ; c < 9 ; c+= 3) {
			for(int r = 0 ; r < 9 ; r+= 3) {
				List<String> present = new ArrayList<String>();
				for(int cc = 0 ; cc < 3 ; cc++) {
					for(int rr = 0 ; rr < 3 ; rr++) {
						if(!board[c+cc][r+rr].equals("")){
							present.add(board[c+cc][r+rr]);
						}
					}
				}
				if(!helperValid(present)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public String[][] duplicate(String[][] board) {
		String[][] copy = new String[9][9];
		
		for(int c = 0 ; c < 9 ; c++) {
			for(int r = 0 ; r < 9 ; r++) {
				copy[c][r] = board[c][r];
			}
		}
		return copy;
	}
	
	
	
	
	public String[][] solve(String[][] board) {
		
		//If we have an invalid board then quit this path
		if(		!charsValid(board) ||
				!rowsValid(board) ||
				!columnsValid(board) || 
				!squaresValid(board)) {
			return null;
		}
		
		boolean isSpace = false;
		for(int c = 0 ; c < 9 ; c++) {
			if(isSpace) {
				break;
			}
			for(int r = 0 ; r < 9 ; r++) {
				if(board[c][r].equals("")) {
					isSpace = true;
					break;
				}
			}
		}
		if(!isSpace) {
			return board;
		}
		
		String[][] copy = duplicate(board);
		List<String> tryUs = new ArrayList<String>();
		for(int e = 0 ; e < 9 ; e++) {
			tryUs.add(""+(e+1));
		}
		for(int c = 0 ; c < 9 ; c++) {
			for(int r = 0 ; r < 9 ; r++) {
				if(board[c][r].equals("")) {
					Collections.shuffle(tryUs);
					for(String e:tryUs) {
						copy[c][r] = e;
						String[][] solution = solve(copy);
						if(solution != null) {
							return solution;
						}
					}
					// If we tried every number in a space and none worked then there is no solution
					return null;
				}
			}
		}
		
		return null;
	}

}
