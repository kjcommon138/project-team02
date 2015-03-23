package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.DailyNutritionLabelBean;
import edu.ncsu.csc.itrust.beans.loaders.DailyNutritionLabelBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

public class DailyNutritionLabelDAO {
	private transient final DAOFactory factory;
	private transient final DailyNutritionLabelBeanLoader abloader;
	
	/**
	 * Initializes the daily label DAO
	 * @param factory
	 */
	public DailyNutritionLabelDAO(final DAOFactory factory) {
		this.factory = factory;
		this.abloader = new DailyNutritionLabelBeanLoader();
	}

	/**
	 * Get a single entry from the MySQL database "diarylabels" by mid and date
	 * @param mid The mid of the patient we want to get label from
	 * @param date1 The date of the entry
	 * @return The entry desired from the given parameters
	 * @throws SQLException
	 * @throws DBException
	 */
	public DailyNutritionLabelBean getEntry(long mid, java.sql.Date date1) throws SQLException, DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			conn = factory.getConnection();
			pstring = conn.prepareStatement("SELECT * FROM diarylabels WHERE mid=? AND date1=?");
		
			pstring.setLong(1, mid);
			pstring.setDate(2, date1);
			
			final ResultSet results = pstring.executeQuery();			
			final List<DailyNutritionLabelBean> abList = this.abloader.loadList(results);
			
			results.close();
			pstring.close();
			
			if(abList.size() == 0)
				return null;
			return abList.get(0);
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstring);
		}
	}

	/**
	 * Gets the label entries for a given MID and orders them by date
	 * @param mid The MID of the patient
	 * @return The list of label entries for the MID
	 * @throws SQLException
	 * @throws DBException
	 */
	public List<DailyNutritionLabelBean> getAllEntriesFor(long mid) throws SQLException, DBException {
		Connection conn = null;
		PreparedStatement pstring = null;

		try{
			conn = factory.getConnection();
			pstring = conn.prepareStatement("SELECT * FROM diarylabels WHERE mid=? ORDER BY date1;");
			pstring.setLong(1, mid);
			final ResultSet results = pstring.executeQuery();

			final List<DailyNutritionLabelBean> abList = this.abloader.loadList(results);
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
	 * Inserts a single entry into diarylabels
	 * @param entry The entry to insert into diarylabels
	 * @throws SQLException
	 * @throws DBException
	 */
	public void insertLabelEntry(DailyNutritionLabelBean entry) throws SQLException, DBException {
		Connection conn = null;
		PreparedStatement pstring = null;

		try{
			DailyNutritionLabelBean bean1 = this.getEntry(entry.getPatient(), entry.getDate());
			if(bean1 != null)
				throw new SQLException("Label already exists for this date");
			
			conn = factory.getConnection();

			pstring = conn.prepareStatement(
				"INSERT INTO diarylabels (mid, date1, label) "
			  + "VALUES (?, ?, ?)");
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
	 * Edits the current entry listed in the paramters and updates the changes onto diarylabels
	 * @param entry The entry to update changes with
	 * @throws SQLException
	 * @throws DBException
	 */
	public void editLabelEntry(DailyNutritionLabelBean entry) throws SQLException, DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = factory.getConnection();
		
			ps = conn.prepareStatement("UPDATE diarylabels SET label=? WHERE mid=? AND date1=?");
		
			ps.setString(1, entry.getLabel());
			ps.setLong(2, entry.getPatient());
			ps.setDate(3, (java.sql.Date) entry.getDate());

			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {		
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Returns the list of all entries made inside diarylabels
	 * @return The list of all entries inside diarylabels
	 * @throws SQLException
	 * @throws DBException
	 */
	public List<DailyNutritionLabelBean> getAllLabelEntries() throws SQLException, DBException {
		Connection conn = null;
		PreparedStatement pstring = null;

		try{
			conn = factory.getConnection();
			pstring = conn.prepareStatement("SELECT * FROM diarylabels ORDER BY mid;");
		
			final ResultSet results = pstring.executeQuery();
			final List<DailyNutritionLabelBean> abList = this.abloader.loadList(results);
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
	 * Removes a single entry from the diarylabels SQL table using the date and mid from the entry parameter
	 * @param entry The entry to delete
	 * @throws SQLException
	 * @throws DBException
	 */
	public void removeLabelEntry(DailyNutritionLabelBean entry) throws SQLException, DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		try{
			conn = factory.getConnection();

			pstring = conn.prepareStatement("DELETE FROM diarylabels WHERE mid=? AND date1=?");
			pstring.setLong(1, entry.getPatient());
			pstring.setDate(2, entry.getDate());
		
			pstring.executeUpdate();
			pstring.close();
		} catch (SQLException e) {		
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstring);
		}
	}
	
}
