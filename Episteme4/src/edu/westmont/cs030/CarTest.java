package edu.westmont.cs030;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.Test;

class CarTest {
	
	private static Random r = new Random();
	private final double EPSILON = 1.0E-9;
	

	@Test
	void testConstructor() {
		try {
			new Car(-1.0,-1.0,-1.0,-1.0);
			new Car(0.0, 0.0,0.0, 0.0);
			new Car(1.0,1.0,1.0,1.0);
		}
		catch(RuntimeException e) {
			fail("Could not construct a car object");
		}
	}
	
	@Test
	void testGetters() {
		/* Make sure negative numbers are corrected */
		Car car = new Car(-1.0,-1.0,-1.0,-1.0);
		assertEquals(0.0,car.getPurchasePrice(),EPSILON);
		assertEquals(0.0,car.getDepreciationPerYear(),EPSILON);
		assertEquals(0.0,car.getMaintenanceCostPerMile(),EPSILON);
		assertEquals(0.0,car.getMilesPerGallon(),EPSILON);
		
		/* Check basic getters*/
		double purchasePrice = r.nextDouble() * 10000.0; 
		double depreciationPerYear = r.nextDouble() * 1000.0; 
		double maintenancePerYear = r.nextDouble() * 1000.0; 
		double milesPerGallon = r.nextDouble() * 5.00; 
		car = new Car(purchasePrice,depreciationPerYear,maintenancePerYear,milesPerGallon);
		
		assertEquals(purchasePrice,car.getPurchasePrice(),EPSILON);
		assertEquals(depreciationPerYear,car.getDepreciationPerYear(),EPSILON);
		assertEquals(maintenancePerYear,car.getMaintenanceCostPerMile(),EPSILON);
		assertEquals(milesPerGallon,car.getMilesPerGallon(),EPSILON);
	}
	
	@Test
	void testResaleValue() {
		/* Check basic result*/
		double purchasePrice = 10000.12;
		double depreciationPerYear = .04;
		double maintenancePerYear = 100.00;
		double milesPerGallon = 25.0;
		Car car = new Car(purchasePrice,depreciationPerYear,maintenancePerYear,milesPerGallon);
		
		assertEquals(10000.0,car.resaleValue(3),EPSILON);
		
		/*Check degenerate result */
		purchasePrice = 10000.12;
		depreciationPerYear = 10000.04;
		maintenancePerYear = 100.00;
		milesPerGallon = 25.0;
		car = new Car(purchasePrice,depreciationPerYear,maintenancePerYear,milesPerGallon);
		
		assertEquals(0.0,car.resaleValue(3),EPSILON);
	}
	
	@Test
	void testTotalGasCost() {
		double purchasePrice = 10000.12;
		double depreciationPerYear = .04;
		double maintenancePerYear = 100.00;
		double milesPerGallon = 25.0;
		Car car = new Car(purchasePrice,depreciationPerYear,maintenancePerYear,milesPerGallon);
		
		assertEquals(100.0,car.totalGasCost(1000.0,2.50),EPSILON);
		
		purchasePrice = 10000.12;
		depreciationPerYear = .04;
		maintenancePerYear = 100.00;
		milesPerGallon = 50.0;
		car = new Car(purchasePrice,depreciationPerYear,maintenancePerYear,milesPerGallon);
		
		assertEquals(50.0,car.totalGasCost(1000.0,2.50),EPSILON);
	}
	
	@Test
	void testTotalCostOfOwnership() {
		double purchasePrice = 25000.0;
		double depreciationPerYear = 1000.00;
		double maintenancePerYear = 100.00;
		double milesPerGallon = 25.0;
		Car car = new Car(purchasePrice,depreciationPerYear,maintenancePerYear,milesPerGallon);
		
		for(double i = 1.0; i< 10.1; i+=1.0) {
			double years = 10.0;
			double milesPerYear = 25000.0;
			double costOfGas = 2.50;
			double x = purchasePrice;
			x = x - car.resaleValue(years);
			x = x + car.totalGasCost(milesPerYear*years, costOfGas);
			x = x + car.getMaintenanceCostPerMile()*milesPerYear*years;
			assertEquals(x,car.calculateCostOfOwnership(years, milesPerYear, costOfGas),EPSILON);
		}	
		
	}

}