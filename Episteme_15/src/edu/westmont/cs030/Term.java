/**
 * @author Nathan Young
 * Episteme 15
 * Term.java
 */
package edu.westmont.cs030;

/**
 * Creates a math term with a coefficient and a power of x
 */
public class Term {
	
	private int coef;
	private int power;
	
	/**
	 * creates a term
	 * @param coefficient The coefficient in front
	 * @param power The power of the x variable for the term
	 */
	public Term(int coefficient, int power){
		this.coef = coefficient;
		this.power = power;
	}	
	
	/**
	 * getter for a coefficient
	 * @return The coefficient of the term
	 */
	public int getCoefficient(){
		return coef;
	}
	
	/**
	 * getter for an exponent
	 * @return The exponent of the term
	 */
	public int getExponent(){
		return power;
	}
	
	/**
	 * Method that multiplies two terms together
	 * @param other The term to be multiplied
	 * @return The product of the two terms
	 */
	public Term multiply(Term other){
		return( new Term(this.coef*other.getCoefficient(), 
				this.power + other.getExponent()));
	}
	
	/**
	 * Adds two terms of the same exponent together
	 * @param other The term to be added
	 * @return The sum of the two terms
	 */
	public Term add(Term other) {
		return(new Term(this.coef+other.getCoefficient(),
				other.getExponent()));
	}
	
	/**
	 * Returns a string form of the term
	 * @return A string written in simplified math terms as (coefficient)x^(exponent)
	 */
	public String asString(){
		if(power==0){
			return("" + coef);
		} else if(power==1){
			if(coef == 1){
				return("x");
			} else{
				return("" + coef + "x");
			}
		} else{
			if(coef==1){
				return("x^" + power);
			} else{
				return("" + coef + "x^" + power);
			}
		}
	}
	
	/**
	 * Checks if two terms are equal (same coefficient and exponent)
	 * @param other The other term to be checked for equality
	 * @return true if the terms are the same, false otherwise
	 */
	public boolean equals(Term other) {
		if (this.getCoefficient()==other.getCoefficient() && this.getExponent()==other.getExponent()) {
			return true;
		}
		else {
			return false;
		}
	}

}
