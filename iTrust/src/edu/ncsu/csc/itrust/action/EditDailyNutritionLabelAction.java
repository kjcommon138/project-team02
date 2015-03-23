/**
 * 
 */
package edu.ncsu.csc.itrust.action;

import java.sql.SQLException;
import java.util.List;

import edu.ncsu.csc.itrust.dao.mysql.DailyNutritionLabelDAO;
import edu.ncsu.csc.itrust.beans.DailyNutritionLabelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/** This class is responsible for retrieving food diaries for a patient 
 */
public class EditDailyNutritionLabelAction {
	private DailyNutritionLabelDAO nutritionLabelDAO;
	private long loggedInMID;
	
	/**
	 * @param factory The DAOFactory used to create the DAOs used in this action
	 * @param loggedInMID MID of the patient who is viewing their food diary entries
	 */
	public EditDailyNutritionLabelAction(DAOFactory factory, long loggedInMID) {
		this.nutritionLabelDAO = factory.getDailyNutritionLabelDAO();
		this.loggedInMID = loggedInMID;
	}
	
	/**
	 * Gets all the nutrition labels for a logged in patient
	 * 
	 * @return a list of nutrition labels
	 * @throws SQLException
	 * @throws DBException
	 */
	public List<DailyNutritionLabelBean> getDailyNutritionLabels() throws SQLException, DBException {
		if(loggedInMID >= 900000000) {
			return nutritionLabelDAO.getAllLabelEntries();
		}
		return nutritionLabelDAO.getAllEntriesFor(loggedInMID);
	}
	
	/**
	 * Adds a nutrition label to the logged in patient.
	 * 
	 * @param The nutrition label bean
	 * @throws SQLException
	 * @throws DBException
	 */
	public void addNutritionLabel(DailyNutritionLabelBean b) throws SQLException, DBException {
		this.nutritionLabelDAO.insertLabelEntry(b);
	}
	
	/**
	 * Edits a nutrition label of the logged in patient.
	 * 
	 * @param b The nutrition label bean
	 * @throws SQLException
	 * @throws DBException
	 */
	public void editNutritionLabel(DailyNutritionLabelBean b) throws SQLException, DBException {
		this.nutritionLabelDAO.editLabelEntry(b);
	}
}
