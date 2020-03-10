/**
 * @author Nathan Young
 * Episteme 15
 * Polynomial.java
 */

package edu.westmont.cs030;

import java.util.TreeMap;

/**
 *  A class that represents a mathematical polynomial
 */
public class Polynomial { 
	
	TreeMap<Integer, Term> terms= new TreeMap<>();
	
	
	/**
	 * Create a polynomial with one term
	 * @param first The first term added to the polynomial
	 */
	public Polynomial(Term first){
		if (first.equals(new Term(0,first.getExponent()))) {
			System.out.println("Error: please enter a term with a non-zero integer coefficient");
		}
		else {
			this.terms.put(first.getExponent(), first);	
		}
	}
	
	
	/**
	 * Add this polynomial to another polynomial and return a new polynomial that is the result
	 * @param other The polynomial to be added to this polynomial
	 */
	public Polynomial add(Polynomial other){
		TreeMap<Integer, Term> polynomial = other.getAllTerms(); //gets all the terms in the other polynomial
		
		//For each term that is in this polynomial, add it to the terms in the other polynomial
		for (Integer power : this.terms.keySet()){
			if (polynomial.containsKey(power)) {
				Term newValue=polynomial.get(power).add(this.terms.get(power));
				if (newValue.getCoefficient()!=0) {
					polynomial.put(power, newValue);					
				}
				else {
					polynomial.remove(power);
				}
			}
			else {
				polynomial.put(power,this.terms.get(power));
			}
		}
		return other;
	}
	
	
	/**
	 * Multiply this polynomial to another polynomial and return a new polynomial that is the result
	 * @param other The other polynomial to be multiplied with
	 */
	public Polynomial multiply(Polynomial other){
		TreeMap<Integer, Term> polynomial = other.getAllTerms(); 
		Term startTerm=new Term(1,1);
		Polynomial combinedPoly = new Polynomial(startTerm);
		
		//For each term in this polynomial, multiply it with the other polynomial and add it to combinedPoly
		for (Integer powerFactor1 : this.terms.keySet()) {
			for(Integer powerFactor2 : polynomial.keySet()) {		
				Term productTerm=this.terms.get(powerFactor1).multiply(polynomial.get(powerFactor2));
				Polynomial multipliedValue=new Polynomial(productTerm);
				combinedPoly = combinedPoly.add(multipliedValue);				
			}
		}
		
		//Creating the combinedPoly polynomial required a starting term (1,1), so we must remove it now
		Polynomial fixer=new Polynomial(new Term(-1,1));
		combinedPoly=combinedPoly.add(fixer);
		return combinedPoly;
	}
	
	/**
	 *  Print out a polynomial to the console in a user friendly format
	 * 
	 */
	public void print(){
		if (this.terms.size()==1) {
			System.out.print(this.terms.get(this.terms.firstKey()).asString());
		}
		else {			
			//Save the first term and remove it from the TreeMap
			Integer firstKey = this.terms.firstKey();
			Term firstValue = this.terms.get(firstKey);
			this.terms.remove(this.terms.firstKey());
			
			//Prints out all terms except the first one followed by plus signs
			for (Integer power : this.terms.descendingKeySet()){
				System.out.print(this.terms.get(power).asString()+" + ");
			}
			//Prints out the first term and adds it back to the TreeMap
			System.out.print(firstValue.asString());
			this.terms.put(firstKey,firstValue);
		}
		
	}
	
	/**
	 * Return the number of terms in the most compact version of this polynomial
	 * @return
	 */
	public int numberOfTerms() {
		return this.terms.size();
	}

	/**
	 * Return the term that has the given exponent, if it's not present return null
	 * @param exponent
	 * @return
	 */
	public Term getTerm(int exponent) {
		return this.terms.get(exponent);
	}
	
	/**
	 * method that allows other methods to access the TreeMap associated with other polynomials
	 * @return All terms of the polynomial as a TreeMap
	 */
	private TreeMap<Integer, Term> getAllTerms(){
		return this.terms;
	}
	

}
