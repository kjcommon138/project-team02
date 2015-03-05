/**
 * 
 */
package edu.ncsu.csc.itrust.action;

import java.sql.SQLException;
import java.util.List;
import edu.ncsu.csc.itrust.dao.mysql.FoodDiaryDAO;
import edu.ncsu.csc.itrust.beans.FoodDiaryBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/** This class is responsible for retrieving food diaries for a patient 
 */
public class ViewMyNutritionAction  {
	private FoodDiaryDAO foodDiaryDAO;
	private long loggedInMID;
	
	/**
	 * @param factory The DAOFactory used to create the DAOs used in this action
	 * @param loggedInMID MID of the patient who is viewing their food diary entries
	 */
	public ViewMyNutritionAction(DAOFactory factory, long loggedInMID) {
		this.foodDiaryDAO = factory.getFoodDiaryDAO();
		this.loggedInMID = loggedInMID;
	}
	
	/**
	 * Gets all the food diaries for a logged in patient
	 * 
	 * @return a list of food diary entries
	 * @throws SQLException
	 * @throws DBException
	 */
	public List<FoodDiaryBean> getMyFoodDiaryEntries() throws SQLException, DBException {
		if(loggedInMID >= 900000000) {
			return foodDiaryDAO.getAllFoodDiaryEntries();
		}
		return foodDiaryDAO.getAllFoodDiaryEntriesFor(loggedInMID);
	}
	
	/**
	 * Adds a food diary entry to the logged in patient.
	 * 
	 * @param The food entry bean
	 * @throws SQLException
	 * @throws DBException
	 */
	public void addAFoodDiaryEntry(FoodDiaryBean b) throws SQLException, DBException {
		this.foodDiaryDAO.insertFoodEntry(b);
	}
}
