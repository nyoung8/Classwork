/**
 * @author Nathan Young
 * Episteme 4
 * Car.java
 * 2/12/2019
 * Built using the javadoc from Class Car by djp3 on canvas.westmont.edu
 */
package edu.westmont.cs030;

/**
 * This class represents a car for the purposes of determining how much it costs to operate
 * @author Nathan
 *
 */
public class Car {

	private double originalPrice;
	private double depreciation;
	private double maintenance;
	private double mpg;
	
	/**
	 * 
	 * @param purchasePrice The cost of purchasing the car (must be positive or will be
set to zero)
	 * @param depreciationPerYear The dollar value that the car loses in value per year
(positive or will be set to zero)
	 * @param maintenanceCostPerMile The dollar value that the car requires per mile to
maintain (positive or will be set to zero)
	 * @param milesPerGallon How many miles the car can drive on one gallon of gas
(positive or will be set to zero)
	 */
	public Car(double purchasePrice, double depreciationPerYear, 
			   double maintenanceCostPerMile, double milesPerGallon) {
		if (purchasePrice>=0.0) {
		originalPrice = purchasePrice;
		}
		else {
			originalPrice = 0.0;
		}
		
		if (depreciationPerYear>=0.0) {
		depreciation = depreciationPerYear;
		}
		else {
			depreciation = 0.0;
		}
		if (maintenanceCostPerMile>=0.0) {
			maintenance = maintenanceCostPerMile;
		}
		else {
			maintenance = 0.0;
		}
		if (milesPerGallon>=0.0) {
			mpg = milesPerGallon;
		}
		else {
			mpg = 0.0;
		}
	}
	
	/**
	 * @return The purchase price of the car
	 */
	public double getPurchasePrice() {
		return originalPrice;
	}
	
	/**
	 * @return How much value (in dollars) the car loses per year
	 */
	public double getDepreciationPerYear() {
		return depreciation;
	}
	
	/**
	 * @return How much it costs (in dollars) to keep the car running per year
	 */
	public double getMaintenanceCostPerMile() {
		return maintenance;
	}
	
	/**
	 * @return The number of miles the car can drive per gallon of gas
	 */
	public double getMilesPerGallon() {
		return mpg;
	}
	
	/**
	 * @param years The number of years
	 * @return The amount one can earn by selling the car after @param years years. (never
negative)
	 */
	public double resaleValue(double years) {
		double resale = (originalPrice-(depreciation*years));
		
		if (resale>=0.0) {
			return resale;
		}
		else {
			return 0.0;
		}

	}
	
	/**
	 * 
	 * @param miles The number of miles driven
	 * @param dollarsPerGallon The cost of gas in dollars per gallon
	 * @return The cost of driving the car for the given number of miles at the given cost
per gallon
	 */
	public double totalGasCost(double miles, double dollarsPerGallon) {
		return miles/mpg*dollarsPerGallon;
	}
	
	/**
	 * @param yearsOwned How many years will you own this car?
	 * @param milesPerYear How many miles will it be driven per year?
	 * @param dollarsPerGallon How much does gas cost?
	 * @return The total cost of purchasing, driving and buying gas for the car and then
selling it.
	 */
	public double calculateCostOfOwnership(double yearsOwned, double milesPerYear, double dollarsPerGallon) {
		double totalCost = originalPrice-resaleValue(yearsOwned);
		totalCost += this.totalGasCost(milesPerYear*yearsOwned,dollarsPerGallon);
		totalCost += this.getMaintenanceCostPerMile()*milesPerYear*yearsOwned; 
		// I separated the sum to not make a single long line that may be difficult to read.
		return totalCost;
	}
}
