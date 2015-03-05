/**
 * This class contains the getters and setters for the Food Diary class
 * @author Kevin Commons (kjcommon)
 */
package edu.ncsu.csc.itrust.beans;

import java.io.Serializable;
import java.util.Calendar;


public class FoodDiaryBean implements Serializable {
	/**
	 * Private Variables for the food diary bean class
	 */
	private static final long serialVersionUID = -1965704529780021183L;
	private int foodEntryID;
	private long patient;
	
	private java.sql.Date entryDate;
	private String mealType;
	private String foodName;
	private double servings;
	private double calories;
	private double gramsFat;
	private double milligramSodium;
	private double gramsCarb;
	private double gramsSugar;
	private double gramsFiber;
	private double gramsProtein;

	/**
	 * @return the patient
	 */
	public long getPatient() {
		return patient;
	}
	
	/**
	 * @return the foodEntryID
	 */
	public int getFoodEntryID() {
		return foodEntryID;
	}
	
	/**
	 * @return the meal type (breakfast, lunch, dinner, or snack)
	 */
	public String getMealType() {
		return mealType;
	}
	
	/**
	 * @return the name of the food
	 */
	public String getFoodName() {
		return foodName;
	}
	/**
	 * @return the servings
	 */
	public double getServings() {
		return servings;
	}

	/**
	 * @return the calories
	 */
	public double getCalories() {
		return calories;
	}

	/**
	 * @return the gramsFat
	 */
	public double getGramsFat() {
		return gramsFat;
	}

	/**
	 * @return the milligramSodium
	 */
	public double getMilligramsSodium() {
		return milligramSodium;
	}

	/**
	 * @return the gramsCarb
	 */
	public double getGramsCarb() {
		return gramsCarb;
	}

	/**
	 * @return the gramsSugar
	 */
	public double getGramsSugar() {
		return gramsSugar;
	}

	/**
	 * @return the gramsFiber
	 */
	public double getGramsFiber() {
		return gramsFiber;
	}

	/**
	 * @return the gramsProtein
	 */
	public double getGramsProtein() {
		return gramsProtein;
	}
	
	/**
	 * @return the entry date
	 */
	public java.sql.Date getEntryDate() {
		return entryDate;
	}
	
	/**
	 * @param foodEntryID the foodEntryID to set
	 */
	public void setFoodEntryID(int foodEntryID) {
		this.foodEntryID = foodEntryID;
	}

	/**
	 * @param patient the patient to set
	 */
	public void setPatient(long patient) {
		this.patient = patient;
	}

	/**
	 * @param mealType the mealType to set
	 * @throws Exception 
	 */
	public void setMealType(String mealType) throws Exception {
		if(mealType == null || mealType.isEmpty())
			throw new Exception("Meal Type cannot be empty.");
		this.mealType = mealType;
	}

	/**
	 * @param foodName the foodName to set
	 */
	public void setFoodName(String foodName) throws Exception {
		if(foodName == null || foodName.isEmpty())
			throw new Exception("Food Name cannot be empty.");
		this.foodName = foodName;
	}

	/**
	 * @param servings the servings to set
	 */
	public void setServings(double servings) throws Exception {
		if(servings <= 0)
			throw new Exception("Servings must be a positive integer.");
		this.servings = servings;
	}

	/**
	 * @param calories the calories to set
	 */
	public void setCalories(double calories) throws Exception {
		if(calories < 0)
			throw new Exception("Calories must be a non-negative integer.");
		this.calories = calories;
	}

	/**
	 * @param gramsFat the gramsFat to set
	 */
	public void setGramsFat(double gramsFat) throws Exception {
		if(gramsFat < 0)
			throw new Exception("Fat must be a non-negative integer.");
		this.gramsFat = gramsFat;
	}

	/**
	 * @param milligramSodium the milligramSodium to set
	 */
	public void setMilligramSodium(double milligramSodium) throws Exception {
		if(milligramSodium < 0)
			throw new Exception("Sodium must be a non-negative integer.");
		this.milligramSodium = milligramSodium;
	}

	/**
	 * @param gramsCarb the gramsCarb to set
	 */
	public void setGramsCarb(double gramsCarb) throws Exception {
		if(gramsCarb < 0)
			throw new Exception("Carbs must be a non-negative integer.");
		this.gramsCarb = gramsCarb;
	}

	/**
	 * @param gramsSugar the gramsSugar to set
	 */
	public void setGramsSugar(double gramsSugar) throws Exception {
		if(gramsSugar < 0)
			throw new Exception("Sugar must be a non-negative integer.");
		this.gramsSugar = gramsSugar;
	}

	/**
	 * @param gramsFiber the gramsFiber to set
	 */
	public void setGramsFiber(double gramsFiber) throws Exception {
		if(gramsFiber < 0)
			throw new Exception("Fiber must be a non-negative integer.");
		this.gramsFiber = gramsFiber;
	}

	/**
	 * @param gramsProtein the gramsProtein to set
	 */
	public void setGramsProtein(double gramsProtein) throws Exception {
		if(gramsProtein < 0)
			throw new Exception("Protein must be a non-negative integer.");
		this.gramsProtein = gramsProtein;
	}

	/**
	 * @param date the date to set
	 */
	public void setEntryDate(java.sql.Date date) throws Exception{
		if(date == null)
			throw new Exception("Entry Date must not be blank.");
		else if(date.after(new java.sql.Date(Calendar.getInstance().getTimeInMillis())))
			throw new Exception("Entry Date must be current or before today.");

		this.entryDate = (java.sql.Date) date.clone();
	}

	@Override
	public int hashCode() {
		return foodEntryID; // any arbitrary constant will do
	}

	
	/**
	 * Returns true if both id's are equal. Probably needs more advance field by field checking.
	 */
	@Override public boolean equals(Object other) {
	   
	    if ( this == other ){
	    	return true;
	    }

	    if ( !(other instanceof FoodDiaryBean) ){
	    	return false;
	    }
	    
	    FoodDiaryBean otherAppt = (FoodDiaryBean)other;
		return otherAppt.getFoodEntryID() == getFoodEntryID();
	    
	}
}
