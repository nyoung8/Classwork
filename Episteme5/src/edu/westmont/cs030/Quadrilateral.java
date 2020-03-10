/**
 * @author Nathan Young
 * Episteme 5
 * Quadrilateral.java
 * 2/19/2019
 */

package edu.westmont.cs030;

import java.util.Scanner;

/**
 * A system class for the Quadrilateral object that evaluates the shape
 * based on 4 points on a two-dimensional plane.
 * Also, an application that uses the system class to determine the most specific
 * shape for a set of four points on a plane.
 */
public class Quadrilateral {
	
	private double x1;
	private double y1;
	private double x2;
	private double y2;
	private double x3;
	private double y3;
	private double x4; 
	private double y4;
	private double EPSILON = 0.01;
	
	/**
	 * The constructor of the Quadrilateral that initializes the 4 points. Points
	 * must be in either clockwise or counter-clockwise order
	 * @param x1 Horizontal distance of the first point
	 * @param y1 Vertical distance of the first point
	 * @param x2 Horizontal distance of the second point
	 * @param y2 Vertical distance of the second point
	 * @param x3 Horizontal distance of the third point
	 * @param y3 Vertical distance of the third point
	 * @param x4 Horizontal distance of the fourth point
	 * @param y4 Vertical distance of the fourth point
	 */
	public Quadrilateral(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
		
		this.x1=x1;
		this.y1=y1;
		this.x2=x2;
		this.y2=y2;
		this.x3=x3;
		this.y3=y3;
		this.x4=x4;
		this.y4=y4;
	}
	
	/**
	 * Method that calculates the distance between two points of the Quadrilateral
	 * @param x1 The horizontal distance of the first point
	 * @param y1 The vertical distance of the first point
	 * @param x2 The horizontal distance of the second point
	 * @param y2 The vertical distance of the second point
	 * @return
	 */
	private double getLength(double x1, double y1, double x2, double y2) {
		double xDistance=x1-x2;
		double yDistance=y1-y2;
		double distance=Math.sqrt(Math.pow(xDistance, 2)+Math.pow(yDistance, 2)); //Distance formula
		return distance;
	}
	
	/**
	 * Uses the law of cosines to determine the measure of an angle in degrees given three side lengths of a triangle.
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param x3
	 * @param y3
	 * @return Returns the estimated angle of the first entered point in degrees.
	 */
	private double lawOfCosines(double x1, double y1, double x2, double y2, double x3, double y3) {
		double side1 = this.getLength(x1, y1, x2, y2);
		double side2 = this.getLength(x2, y2, x3, y3);
		double side3 = this.getLength(x3, y3, x1, y1);
		double angle = Math.toDegrees(Math.acos((Math.pow(side1, 2)+Math.pow(side3, 2)-Math.pow(side2, 2))/(2*side1*side3)));
		return angle;
	}
	
	/**
	 * This method verifies that the user input an actual quadrilateral, and did not just enter an irregular shape
	 * whose angles do not add up to 360 degrees, did not enter a line of 4 points, and did not enter the same 
	 * coordinate 4 times. 
	 * @param x1 The horizontal distance of the first point
	 * @param y1 The vertical distance of the first point
	 * @param x2 The horizontal distance of the second point
	 * @param y2 The vertical distance of the second point
	 * @param x3 The horizontal distance of the third point
	 * @param y3 The vertical distance of the third point
	 * @param x4 The horizontal distance of the fourth point
	 * @param y4 The vertical distance of the fourth point
	 * @return Evaluates to true if the points form a legitimate quadrilateral.
	 */
	private boolean validPoints(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
		double SUM_OF_ANGLES_QUAD = 360.0; //expected sum of the angles in one quadrilateral
		double SUM_OF_ANGLES_TRI = 180.0; //expected sum of the angles in one triangle
		
		//splits the quadrilateral into two seperate triangles and uses the law of cosines to estimate the angles
		double triangle1Angle1 = this.lawOfCosines(x1, y1, x2, y2, x3, y3);
		double triangle1Angle2 = this.lawOfCosines(x2, y2, x3, y3, x1, y1);
		double triangle1Angle3 = SUM_OF_ANGLES_TRI - triangle1Angle1 - triangle1Angle2;
		double triangle2Angle1 = this.lawOfCosines(x3, y3, x4, y4, x1, y1);
		double triangle2Angle2 = this.lawOfCosines(x4, y4, x1, y1, x3, y3);
		double triangle2Angle3 = SUM_OF_ANGLES_TRI - triangle2Angle1 - triangle2Angle2;
		
		double quadAngles = triangle1Angle1 + triangle1Angle2 + triangle1Angle3 + triangle2Angle1 + triangle2Angle2 + triangle2Angle3;
		if (!this.isEqualTo(SUM_OF_ANGLES_QUAD, quadAngles)) { //checks if the actual angles add up to what we expect
			return false;
		}
		
		//the following statements check that no points are on top of one another
		
		else if (x1==x2 && x2==x3 && x3==x4) {
			return false;
		}
		
		else if (y1==y2 && y2==y3 && y3==y4) {
			return false;
		}
		
		else if (x1==x2 && x2==x3 && x3==x4 && y1==y2 && y2==y3 && y3==y4) {
			return false;
		}

		else {
			return true;
		}
	}
	
	/**
	 * Method that tests if two sides of the Quadrilateral are equal in length
	 * @param x1 The horizontal distance of the first point
	 * @param y1 The vertical distance of the first point
	 * @param x2 The horizontal distance of the second point
	 * @param y2 The vertical distance of the second point
	 * @param x3 The horizontal distance of the third point
	 * @param y3 The vertical distance of the third point
	 * @param x4 The horizontal distance of the fourth point
	 * @param y4 The vertical distance of the fourth point
	 * @return
	 */
	private boolean sameLength(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
		double firstLineDistance=this.getLength(x1,y1,x2,y2);
		double secondLineDistance=this.getLength(x3, y3, x4, y4);
		if (this.isEqualTo(firstLineDistance, secondLineDistance)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * A method that will account for rounding errors when calculating equality of double values
	 * @param value1 The first double to be compared to the second
	 * @param value2 The second double to be compared to the first
	 * @return Returns true if the values are essentially equal, false if the values are not equal
	 */
	private boolean isEqualTo(double value1, double value2) {
		if (Math.abs(value1-value2)<= EPSILON) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Tests whether the current Quadrilateral is a square
	 * @return Returns true if all the sides are the same length and both diagonals are the same length
	 */
	public boolean isSquare() {
		if (!this.validPoints(x1, y1, x2, y2, x3, y3, x4, y4)) {
			return false;
		}
		
		if (this.sameLength(x1, y1, x2, y2, x2, y2, x3, y3) //side one equals side two
				&& this.sameLength(x2, y2, x3, y3, x3, y3, x4, y4) //side two equals side three
				&& this.sameLength(x1, y1, x3, y3, x2, y2, x4, y4)) { //both diagonals are equal
				return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Tests whether the current Quadrilateral is a rectangle
	 * @return Returns true if opposite sides are the same length and both diagonals are the same length
	 */
	public boolean isRectangle() {
		if (!this.validPoints(x1, y1, x2, y2, x3, y3, x4, y4)) {
			return false;
		}
		
		if (this.sameLength(x1, y1, x2, y2, x3, y3, x4, y4) //side one equals side three
				&& this.sameLength(x2, y2, x3, y3, x4, y4, x1, y1) //side two equals side four
				&& this.sameLength(x1, y1, x3, y3, x2, y2, x4, y4)) { //diagonals are equal
			return true;
		}
		else {
			return false;		
		}	
	}
	
	/**
	 * Tests whether the current Quadrilateral is a rhombus
	 * @return Returns true if all the sides are the same length
	 */
	public boolean isRhombus() {
		if (!this.validPoints(x1, y1, x2, y2, x3, y3, x4, y4)) {
			return false;
		}
		
		if (this.sameLength(x1, y1, x2, y2, x2, y2, x3, y3) //side one equals side two
				&& this.sameLength(x2, y2, x3, y3, x3, y3, x4, y4)) { //side two equals side three
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Tests whether the current Quadrilateral is a parallelogram
	 * @return Returns true if opposite sides are the same length
	 */
	public boolean isParallelogram() {
		if (!this.validPoints(x1, y1, x2, y2, x3, y3, x4, y4)) {
			return false;
		}
		
		if (this.sameLength(x1, y1, x2, y2, x3, y3, x4, y4) //side one equals side three
				&& this.sameLength(x2, y2, x3, y3, x4, y4, x1, y1)) { //side two equals side four
			return true;
		}
		else {
		return false;
		}	
	}
	
	/**
	 * Tests whether the current Quadrilateral is a trapezoid
	 * @return Returns true if at least one pair of sides are parallel
	 */
	public boolean isTrapezoid() {
		if (!this.validPoints(x1, y1, x2, y2, x3, y3, x4, y4)) {
			return false;
		}
		
		//To guard against a divide by zero error when calculating the slope, we must test to find out
		//if any of the lines are vertical, having an undefined slope
		
		boolean slope1undefined=false;
		boolean slope2undefined=false;
		boolean slope3undefined=false;
		boolean slope4undefined=false;
		
		if (x2-x1==0) {
			slope1undefined=true;
		}

		if (x3-x2==0) {
			slope2undefined=true;
		}
		
		if (x4-x3==0) {
			slope3undefined=true;
		}
		
		if (x1-x4==0) {
			slope4undefined=true;
		}
		
		//If two lines are vertical then those lines are parallel and the shape is a trapezoid
		if (slope1undefined && slope3undefined && !slope2undefined && !slope4undefined) {
			return true;
		}
		
		else if (slope2undefined && slope4undefined && !slope1undefined && !slope3undefined) {
			return true;
		}
		
		//If one line is vertical then we must test the two opposite lines that are not vertical
		//to see if they have the same slope
		else if ((slope1undefined && !slope2undefined && !slope3undefined && !slope4undefined)
				|| (!slope1undefined && !slope2undefined && slope3undefined && !slope4undefined)) {
			double slope2=(y3-y2)/(x3-x2);
			double slope4=(y1-y4)/(x1-x4);
			if (this.isEqualTo(slope2, slope4)) {
				return true;
			}
			
			else {
				return false;
			}
		}
		
		else if ((!slope1undefined && slope2undefined && !slope3undefined && !slope4undefined)
				|| (!slope1undefined && !slope2undefined && !slope3undefined && slope4undefined)) {
			double slope1=(y2-y1)/(x2-x1);
			double slope3=(y4-y3)/(x4-x3);
			if (this.isEqualTo(slope1, slope3)) {
				return true;
			}
			
			else {
				return false;
			}
		}
		
		//If none of the lines are vertical, we must test all sides to see if they are parallel
		else if (!slope1undefined && !slope2undefined && !slope3undefined && !slope4undefined) {
			double slope1=(y2-y1)/(x2-x1);
			double slope2=(y3-y2)/(x3-x2);
			double slope3=(y4-y3)/(x4-x3);
			double slope4=(y1-y4)/(x1-x4);
			
			if (this.isEqualTo(slope1, slope3) || this.isEqualTo(slope2, slope4)) {
				return true;
			}
			else {
				return false;
			}
		}
		
		//If all lines are vertical or three lines are vertical, the shape is not a trapezoid
		else {
			return false;
		}
	}

	/**
	 * An application is run that asks the user to input the coordinates of a desired shape and determines 
	 * what the most specific classification of that shape is
	 * @param args
	 */
	public static void main(String[] args) {
		
		try(Scanner in = new Scanner(System.in)){
			
			System.out.println("Hello, please enter into the pairs of x and y coordinates for your shape in clockwwise or counter-clockwise order");
			System.out.println("Enter the x value and y value of each coordinate one at a time.");
			System.out.print("First x value: ");
			double x1 = in.nextDouble();
			
			System.out.print("First y value: ");
			double y1 = in.nextDouble();
			
			System.out.print("Second x value: ");
			double x2 = in.nextDouble();
			
			System.out.print("Second y value: ");
			double y2 = in.nextDouble();
			
			System.out.print("Third x value: ");
			double x3 = in.nextDouble();
			
			System.out.print("Third y value: ");
			double y3 = in.nextDouble();
			
			System.out.print("Fourth x value: ");
			double x4 = in.nextDouble();
			
			System.out.print("Fourth y value: ");
			double y4 = in.nextDouble();
			
			Quadrilateral enteredShape = new Quadrilateral(x1, y1, x2, y2, x3, y3, x4, y4);
			
			if (enteredShape.isSquare()) {
				System.out.print("Your shape is a square");
			}
			
			else if (enteredShape.isRectangle()) {
				System.out.print("Your shape is a rectangle");
			}
			
			else if (enteredShape.isRhombus()) {
				System.out.print("Your shape is a rhombus");
			}
			
			else if (enteredShape.isParallelogram()) {
				System.out.print("Your shape is a parallelogram");
			}
			
			else if (enteredShape.isTrapezoid()) {
				System.out.print("Your shape is a trapezoid");
			}
			
			else {
				System.out.print("The shape is not a square, rectangle, rhombus, parallelogram, nor trapezoid. I'm not sure what it is");
			}
			
		}

	}

}
