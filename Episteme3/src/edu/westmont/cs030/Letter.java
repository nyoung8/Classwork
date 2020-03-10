/**
 * Nathan Young
 * Episteme Ch3
 * Letter.java
 * 2/4/19
 */

package edu.westmont.cs030;

/**
 * The following class is an object that can be used to write a letter
 */
public class Letter {

	private String sender;
	private String recipient;
	private String draft;
	
	/**
	 * Constructor of the Letter that gathers information about who is sending and recieving
	 * @param from A string of the name of who the letter is from.
	 * @param to A string of the name of who the letter is to be sent to.
	 */
	public Letter(String from, String to) {
		sender = from;
		recipient = to;
		draft = "";
	}
	
	/**
	 * Method that adds one line to the letter
	 * @param line A string that will be added to the letter as a single line
	 */
	public void addLine(String line) {
		String newline = "";
		for (int i=0;i<(20-line.length())/2;i++) {
			newline += "   ";
		}
		for (int i=0; i<line.length();i++) {
			newline += line.charAt(i)+"  ";
		}
		for (int i=0;i<(17-line.length())/2;i++) {
			newline += "   ";
		}
		draft = draft + newline + "\n";
	}
	//R  C  F  Y  P

	/**
	 * A getter that outputs the letter with a formal greeting and close.
	 * @return A string of the entire letter
	 */
	public String getText() {
		draft = "Dear "+recipient+":\n"+draft;
		this.addLine("Sincerely,");
		this.addLine(sender);
		return draft;
	}
}
