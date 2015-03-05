package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.FoodDiaryBean;
import edu.ncsu.csc.itrust.beans.loaders.FoodDiaryBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

@SuppressWarnings({})
public class FoodDiaryDAO {
	private transient final DAOFactory factory;
	private transient final FoodDiaryBeanLoader abloader;
	
	/**
	 * Initializes the food diary DAO
	 * @param factory
	 */
	public FoodDiaryDAO(final DAOFactory factory) {
		this.factory = factory;
		this.abloader = new FoodDiaryBeanLoader();
	}
	
	/**
	 * Gets a single entry from the MySQL database "fooddiary" by entry ID
	 * @param entryID The id of the food diary entry to search
	 * @return The entry desired from the given parameter
	 * @throws SQLException
	 * @throws DBException
	 */
	public FoodDiaryBean getEntry(final int entryID) throws SQLException, DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			conn = factory.getConnection();
		
			pstring = conn.prepareStatement("SELECT * FROM fooddiary WHERE entry_id=?");
		
			pstring.setInt(1, entryID);
		
			final ResultSet results = pstring.executeQuery();
			final List<FoodDiaryBean> abList = this.abloader.loadList(results);
			results.close();
			pstring.close();
			return abList.get(0);
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstring);
		}
			
	}

	/**
	 * Gets the food diary entries for a given MID and orders them by date
	 * @param mid The MID of the patient or HCP
	 * @return The list of food diary entries for the MID
	 * @throws SQLException
	 * @throws DBException
	 */
	public List<FoodDiaryBean> getAllFoodDiaryEntriesFor(long mid) throws SQLException, DBException {
		Connection conn = null;
		
		PreparedStatement pstring = null;

		try{
			conn = factory.getConnection();
			pstring = conn.prepareStatement("SELECT * FROM fooddiary WHERE patient_id=? ORDER BY entry_date;");
			pstring.setLong(1, mid);
			final ResultSet results = pstring.executeQuery();

			final List<FoodDiaryBean> abList = this.abloader.loadList(results);
			results.close();
			pstring.close();
			return abList;
		} catch (SQLException e) {
		
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstring);
		}
	}
	
	/**
	 * Gets all the food diary entries.
	 * @return The list of all food diary entries
	 * @throws SQLException
	 * @throws DBException
	 */
	public List<FoodDiaryBean> getAllFoodDiaryEntries() throws SQLException, DBException {
		Connection conn = null;
		
		PreparedStatement pstring = null;

		try{
		conn = factory.getConnection();
		
		pstring = conn.prepareStatement("SELECT * FROM fooddiary ORDER BY patient_id;");
		
		
		final ResultSet results = pstring.executeQuery();
		final List<FoodDiaryBean> abList = this.abloader.loadList(results);
		results.close();
		pstring.close();
		return abList;
	} catch (SQLException e) {
		
		throw new DBException(e);
	} finally {
		DBUtil.closeConnection(conn, pstring);
	}

	}
	
	/**
	 * Inserts an food diary entry for the given patient
	 * @param entry The food diary entry
	 * @throws SQLException
	 * @throws DBException
	 */
	public void insertFoodEntry(final FoodDiaryBean entry) throws SQLException, DBException {
		Connection conn = null;
		PreparedStatement pstring = null;

		try{
		conn = factory.getConnection();

		pstring = conn.prepareStatement(
				"INSERT INTO fooddiary (patient_id, entry_date, meal_type, "
			  + "food_name, servings, calories, fat, sodium, carbs, sugar, fiber, protein) "
			  + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		pstring = this.abloader.loadParameters(pstring, entry);
		
		pstring.executeUpdate();
		pstring.close();
	} catch (SQLException e) {
		
		throw new DBException(e);
	} finally {
		DBUtil.closeConnection(conn, pstring);
	}
	}
	
	/**
	 * Edits the given food diary entry
	 * @param entry The entry to edit
	 * @throws SQLException
	 * @throws DBException
	 */
	public void editFoodDiaryEntry(final FoodDiaryBean entry) throws SQLException, DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try{
		conn = factory.getConnection();

		ps = conn.prepareStatement(
				"UPDATE fooddiary SET entry_date=?, meal_type=?, "
			  + "food_name=?, servings=?, calories=?, fat=?, sodium=?, carbs=?, sugar=?, "
			  + "fiber=?, protein=? WHERE entry_id=?");
		
		ps.setDate(1, (java.sql.Date) entry.getEntryDate());
		ps.setString(2, entry.getMealType());
		ps.setString(3, entry.getFoodName());
		ps.setDouble(4, entry.getServings());
		ps.setDouble(5, entry.getCalories());
		ps.setDouble(6, entry.getGramsFat());
		ps.setDouble(7, entry.getMilligramsSodium());
		ps.setDouble(8, entry.getGramsCarb());
		ps.setDouble(9, entry.getGramsSugar());
		ps.setDouble(10, entry.getGramsFiber());
		ps.setDouble(11, entry.getGramsProtein());
		ps.setInt(12, entry.getFoodEntryID());

		ps.executeUpdate();
		ps.close();
	} catch (SQLException e) {
		
		throw new DBException(e);
	} finally {
		DBUtil.closeConnection(conn, ps);
	}
	}
	
	/**
	 * Removes the entry specified in the parameter
	 * @param entry
	 * @throws SQLException
	 * @throws DBException
	 */
	public void removeFoodDiaryEntry(final FoodDiaryBean entry) throws SQLException, DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		try{
			conn = factory.getConnection();

			pstring = conn.prepareStatement("DELETE FROM fooddiary WHERE entry_id=?");
			pstring.setInt(1, entry.getFoodEntryID());
		
			pstring.executeUpdate();
			pstring.close();
		} catch (SQLException e) {
		
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstring);
		}
	}

}
