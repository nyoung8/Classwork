/**
 * @author Nathan Young
 * Episteme 15
 * TermTest.java
 */
package edu.westmont.cs030;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TermTest {

	@Test
	public void testGetters() { 
		Term x = new Term(1,1);
		assertTrue(x.getCoefficient()==1);
		assertTrue(x.getExponent()==1);
	}
	
	
	
	@Test
	public void testMultiplication() {
		Term z = new Term(1,1);
		Term y = new Term(1,1);
		assertTrue(z.multiply(y).equals(new Term(1,2)));
		
		Term a = new Term(10,4);
		Term b = new Term(3,6);
		assertTrue(a.multiply(b).equals(new Term(30,10)));	
	}
	
	@Test
	public void testAddition() {
		Term q = new Term(1,1);
		Term r = new Term(1,1);		
		assertTrue(q.add(r).equals(new Term(2,1)));
		
		Term n = new Term(10,1);
		Term m = new Term(25,1);		
		assertTrue(n.add(m).equals(new Term(35,1)));
	}
	
	@Test
	public void testString() {
		Term l = new Term(2,3);
		assertTrue(l.asString().equals("2x^3"));
		
		Term j = new Term(1,0);
		assertTrue(j.asString().equals("1"));
		
		Term k = new Term(1,1);
		assertTrue(k.asString().equals("x"));
		
		Term o = new Term(3,1);
		assertTrue(o.asString().equals("3x"));
		
		Term t = new Term(1,10);
		assertTrue(t.asString().equals("x^10"));
	}
	

}
