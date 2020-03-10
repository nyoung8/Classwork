package edu.westmont.cs030;

import java.util.Scanner;

/**
 * 
 * @author Nathan Young
 * Episteme 4
 * CarApp.java
 * 2/12/2019
 */

/**
 * A class that models an app to calculate the cost of owning a car
 * and compares it to the cost of owning another car.
 */
public class CarApp 
{

	/**
	 * Uses the Car class to collect data, create 2 car objects, and then 
	 * compare the costs of the two cars. Tells the user which car is cheaper.
	 */
	public static void main(String[] args) 
	{

		try(Scanner in = new Scanner(System.in)){
			
			int i = 0;
			int j = 0;
			while (i < 27)
			{
			   i = i + 2;
			   j++;
			}
			System.out.println("j=" + j);
			
	    	  System.out.println("Welcome to the car comparison app!");
	    	  System.out.println("Please enter the following information"); //Following lines collect info
	    	  
	    	  double dollarsPerGallon;
	    	  double milesPerYear;
	    	  double yearsOwned;
	    	  
	    	  System.out.print("\tHow much does gas cost per gallon? ");
	    	  dollarsPerGallon=in.nextDouble();
	    	  
	    	  System.out.print("\tHow many miles do you drive per year? ");
	    	  milesPerYear=in.nextDouble();
	    	  
	    	  System.out.print("\tHow many years will you own the cars? ");
	    	  yearsOwned=in.nextDouble();
	    	  
	    	  System.out.println("Please enter information for the first car:");
	    	  
	    	  double price1;
	    	  double mpg1;
	    	  double depreciation1;
	    	  double maintenance1;
	    	
	    	  System.out.print("\tHow much does it cost? ");
	    	  price1=in.nextDouble();
	    	  
	    	  System.out.print("\tHow many miles will it go on one gallon of gas? ");
	    	  mpg1=in.nextDouble();
	    	  
	    	  System.out.print("\tHow much does it depreciate per year? ");
	    	  depreciation1=in.nextDouble();
	    	  
	    	  System.out.print("\tHow much does it cost to maintain per mile? ");
	    	  maintenance1=in.nextDouble();
	    	  
	    	  Car option1=new Car(price1, depreciation1, maintenance1, mpg1); //First car object is created
	    	  
	    	  
	    	  System.out.println("Please enter information for the second car:");
	    	  
	    	  double price2;
	    	  double mpg2;
	    	  double depreciation2;
	    	  double maintenance2;
	    	
	    	  System.out.print("\tHow much does it cost? ");
	    	  price2=in.nextDouble();
	    	  
	    	  System.out.print("\tHow many miles will it go on one gallon of gas? ");
	    	  mpg2=in.nextDouble();
	    	  
	    	  System.out.print("\tHow much does it depreciate per year? ");
	    	  depreciation2=in.nextDouble();
	    	  
	    	  System.out.print("\tHow much does it cost to maintain per mile? ");
	    	  maintenance2=in.nextDouble();
	    	  
	    	  Car option2=new Car(price2, depreciation2, maintenance2, mpg2); //Second car object
	    	  
	    	  String MESSAGE_1="The cost of owning the first car for " + (int)yearsOwned + " years is:  ";
	    	  String MESSAGE_2="The cost of owning the second car for " + (int)yearsOwned + " years is: ";

	    	  double costCar1=option1.calculateCostOfOwnership(yearsOwned, milesPerYear, dollarsPerGallon);
	    	  double costCar2=option2.calculateCostOfOwnership(yearsOwned, milesPerYear, dollarsPerGallon);
	    	  
	    	  System.out.printf("%s%8.2f \n", MESSAGE_1, costCar1); //Costs of the cars are printed out
	    	  System.out.printf("%s%8.2f \n", MESSAGE_2, costCar2);
	    	  
	    	  String bestChoice; //Program tells the user which car to buy based on the cost.
	    	  if(costCar1==costCar2) {
	    		  System.out.println("Either car will cost the same");
	    	  } else {
	    		  if(costCar1>costCar2) {
	    			  bestChoice= " second ";
	    		  }
	    		  else  {
	    			  bestChoice= " first ";
	    		  }
	    		  System.out.println("You should purchase the" + bestChoice + "car");
	    	  }
	    		 
	    	  
		}
	}
}
