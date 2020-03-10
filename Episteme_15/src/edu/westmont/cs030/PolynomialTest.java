/**
 * @author Nathan Young
 * Episteme 15
 * PolynomialTest.java
 */
package edu.westmont.cs030;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PolynomialTest { 

	@Test
	public void testGetters() {
		Polynomial small=new Polynomial(new Term(2,2));
		assertTrue(small.getTerm(2).equals(new Term(2,2)));
		assertTrue(small.numberOfTerms()==1);
	}
	
	@Test
	public void testBasicAddition() {
		Polynomial small=new Polynomial(new Term(2,2));
		Polynomial plus = new Polynomial(new Term(3,2));
		Polynomial sum = small.add(plus);
		Polynomial expected = new Polynomial(new Term(5,2));
		assertTrue(sum.getTerm(2).equals(expected.getTerm(2)));
	}
	
	@Test
	public void testUnlikeAddition() {
		Polynomial small= new Polynomial(new Term(1,4));
		Polynomial plus = new Polynomial(new Term(3,2));
		Polynomial sum = small.add(plus);
		assertTrue(sum.getTerm(2).equals(new Term(3,2)));
		assertTrue(sum.getTerm(4).equals(new Term(1,4)));
		assertTrue(sum.numberOfTerms()==2);
	}
	
	@Test
	public void testMultistepAddition() {
		Polynomial a = new Polynomial(new Term(3,3));
		Polynomial b = new Polynomial(new Term(1,2));
		Polynomial c = new Polynomial(new Term(2,1));
		Polynomial d = new Polynomial(new Term(5,5));
		Polynomial e = new Polynomial(new Term(-2,2));
		Polynomial f = new Polynomial(new Term(9,1));
		Polynomial g = new Polynomial(new Term(11,0));
		Polynomial big= a.add(b).add(c).add(d);
		Polynomial plus = e.add(f).add(g);
		Polynomial sum = big.add(plus);
		assertTrue(sum.getTerm(5).equals(new Term(5,5)));
		assertTrue(sum.getTerm(3).equals(new Term(3,3)));
		assertTrue(sum.getTerm(2).equals(new Term(-1,2)));
		assertTrue(sum.getTerm(1).equals(new Term(11,1)));
		assertTrue(sum.getTerm(0).equals(new Term(11,0)));
		assertTrue(sum.numberOfTerms()==5);
	}
	
	@Test
	public void testRemovalAddition() {
		Polynomial leftover = new Polynomial(new Term(1,1));
		Polynomial normal = new Polynomial(new Term(5,5));
		Polynomial opposite= new Polynomial(new Term(-5,5));
		Polynomial sum=normal.add(opposite).add(leftover);
		assertTrue(sum.numberOfTerms()==1);
	}
	
	@Test
	public void testBasicMultiplication() {
		Polynomial small=new Polynomial(new Term(2,2));
		Polynomial factor = new Polynomial(new Term(3,2));
		Polynomial product = small.multiply(factor);
		Polynomial expected = new Polynomial(new Term(6,4));
		assertTrue(product.getTerm(4).equals(expected.getTerm(4)));
	}
	
	@Test
	public void testUnlikeMult() {
		Polynomial small= new Polynomial(new Term(1,4));
		Polynomial factor = new Polynomial(new Term(3,2));
		Polynomial product = small.multiply(factor);
		Polynomial expected = new Polynomial(new Term(3,6));
		assertTrue(product.getTerm(6).equals(expected.getTerm(6)));

	}
	
	@Test
	public void testMultistepMult() {
		Polynomial a = new Polynomial(new Term(3,3));
		Polynomial b = new Polynomial(new Term(1,2));
		Polynomial c = new Polynomial(new Term(2,1));
		Polynomial d = new Polynomial(new Term(5,5));
		Polynomial e = new Polynomial(new Term(-2,2));
		Polynomial f = new Polynomial(new Term(9,1));
		Polynomial g = new Polynomial(new Term(11,0));
		Polynomial big= a.add(b).add(c).add(d);
		Polynomial factor = e.add(f).add(g);
		Polynomial prod = big.multiply(factor);
		assertTrue(prod.getTerm(7).equals(new Term(-10,7)));
		assertTrue(prod.getTerm(6).equals(new Term(45,6)));
		assertTrue(prod.getTerm(5).equals(new Term(49,5)));
		assertTrue(prod.getTerm(4).equals(new Term(25,4)));
		assertTrue(prod.getTerm(3).equals(new Term(38,3)));
		assertTrue(prod.getTerm(2).equals(new Term(29,2)));
		assertTrue(prod.getTerm(1).equals(new Term(22,1)));
		assertTrue(prod.numberOfTerms()==7);
	}
	
	@Test
	public void testPrint() {
		Polynomial a = new Polynomial(new Term(3,3));
		a.print();	
		Polynomial b = new Polynomial(new Term(2,2));
		Polynomial sum = a.add(b);
		sum.print();
		System.out.println("\nIgnore previous. It was printed for testing purposes");
	}
	

}
