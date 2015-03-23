package edu.ncsu.csc.itrust.beans;

import java.sql.Date;
import java.util.Calendar;

public class DailyNutritionLabelBean {
	/**
	 * Private Variables for the food diary bean class
	 */
	private long patient;
	private java.sql.Date date;
	private String label;
	
	/**
	 * Getter for the patient MID field.
	 * @return patient
	 */
	public long getPatient() {
		return this.patient;
	}

	/**
	 * Getter for the date field
	 * @return date
	 */
	public java.sql.Date getDate() {
		return this.date;
	}

	/**
	 * Getter for the label field
	 * @return field
	 */
	public String getLabel() {
		return this.label;
	}

	/**
	 * Setter for the patient (MID) field
	 * @param mid the mid to set
	 */
	public void setMid(long mid) {
		this.patient = mid;
	}

	/**
	 * Setter for the date field
	 * @param date2 The date to set for this label
	 * @throws Exception When the date is null or after the current date
	 */
	public void setDate(java.sql.Date date2) throws Exception{
		if(date2 == null)
			throw new Exception("Date must not be blank.");

		this.date = (java.sql.Date) date2.clone();
	}

	public void setLabel(String label1) throws Exception{
		if(label1 == null || label1.isEmpty())
			throw new Exception("Label cannot be empty.");
		this.label = label1;
	}
}
