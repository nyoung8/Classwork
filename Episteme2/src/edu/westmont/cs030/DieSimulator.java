/**
 * @author Nathan Young
 *Ch 02 Episteme
 *E2.12
 */

package edu.westmont.cs030;
import java.util.Random;

public class DieSimulator {
	/**
	 * Program that selects a random number between 0 and 6, and then displays an image of dice in the console based on the random number.
	 */
	public static void main(String[] args) {
		Random die = new Random();
		int dots = die.nextInt(6)+1;
		if (dots == 1) {
			System.out.println(" -------");
			System.out.println("|       |");
			System.out.println("|   @   |");
			System.out.println("|       |");
			System.out.println(" ------- ");	
		}
		if (dots == 2) {
			System.out.println(" -------");
			System.out.println("| @     |");
			System.out.println("|       |");
			System.out.println("|     @ |");
			System.out.println(" ------- ");
		}
		if (dots == 3) {
			System.out.println(" -------");
			System.out.println("| @     |");
			System.out.println("|   @   |");
			System.out.println("|     @ |");
			System.out.println(" ------- ");
		}
		if (dots == 4) {
			System.out.println(" -------");
			System.out.println("| @   @ |");
			System.out.println("|       |");
			System.out.println("| @   @ |");
			System.out.println(" ------- ");	
		}
		if (dots == 5) {
			System.out.println(" -------");
			System.out.println("| @   @ |");
			System.out.println("|   @   |");
			System.out.println("| @   @ |");
			System.out.println(" ------- ");
		}
		if (dots == 6) {
			System.out.println(" -------");
			System.out.println("| @   @ |");
			System.out.println("| @   @ |");
			System.out.println("| @   @ |");
			System.out.println(" ------- ");
		}
	}

}
