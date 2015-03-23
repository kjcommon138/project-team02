package edu.ncsu.csc.itrust.action;

import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.DBBuilder;
import edu.ncsu.csc.itrust.beans.DailyNutritionLabelBean;
import edu.ncsu.csc.itrust.beans.DailyNutritionLabelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.DailyNutritionLabelDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class DailyNutritionLabelDAOTest {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private DailyNutritionLabelDAO labelDao = new DailyNutritionLabelDAO(this.factory);
	private long mid = 1L;
	TestDataGenerator gen;
	
	private DailyNutritionLabelBean b1 = new DailyNutritionLabelBean();
	private DailyNutritionLabelBean b2 = new DailyNutritionLabelBean();
	private String label1 = "Atkins Diet";
	private String label2 = "South Beach Diet";
	
	java.sql.Date date1 = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
	java.sql.Date date2 = new java.sql.Date(Calendar.getInstance().getTimeInMillis() - 86400000);
	DailyNutritionLabelDAO labelDaoMock = new DailyNutritionLabelDAO(null);

	@Before
	public void setUp() throws Exception {
		DBBuilder tables = new DBBuilder();
		tables.dropTables();
		tables.createTables();
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		
		//DailyNutritionLabelBeans go here
		b1.setDate(date1);
		b1.setLabel(label1);
		b1.setMid(mid);
		
		b2.setDate(date2);
		b2.setLabel(label2);
		b2.setMid(mid);
	}

	@Test
	public final void testGetEntry() {
		try {
			List<DailyNutritionLabelBean> beans = labelDao.getAllEntriesFor(mid);
			assertEquals(0, beans.size());
			
			labelDao.insertLabelEntry(b1);
			beans = labelDao.getAllEntriesFor(mid);
			assertEquals(1, beans.size());
			
			DailyNutritionLabelBean b3 = labelDao.getEntry(mid, date1);
			assertEquals(b3.getLabel(), b1.getLabel());
		} catch (DBException d) {
			fail("DBException caught");
		} catch (SQLException s) {
			fail("SQLException caught");
		}
	}

	@Test
	public final void testGetEntry2() {
		try {
			DailyNutritionLabelBean bean = labelDaoMock.getEntry(mid, date1);
			fail("Exception wasn't caught.");
		} catch (SQLException s) {
			fail("SQLException caught");
		} catch (DBException d) {
			fail("DBException caught");
		} catch (Exception e) {
			//If an exception was caught, the test was successful
		}
	}
	
	@Test
	public final void testGetAllEntriesFor() {
		try {
			List<DailyNutritionLabelBean> beans = labelDao.getAllEntriesFor(mid);
			assertEquals(0, beans.size());
		} catch (SQLException s) {
			fail("SQLException caught");
		} catch (DBException d) {
			fail("DBException caught");
		}
	}
	
	@Test
	public final void testGetAllEntriesFor2() {
		try {
			List<DailyNutritionLabelBean> beans = labelDaoMock.getAllEntriesFor(mid);
			fail("Exception wasn't caught.");
		} catch (SQLException s) {
			fail("SQLException caught");
		} catch (DBException d) {
			fail("DBException caught");
		} catch (Exception e) {
			//If an exception was caught, the test was successful
		}
	}
	
	@Test
	public final void testinsertLabelEntry() {
		try {
			List<DailyNutritionLabelBean> beans = labelDao.getAllEntriesFor(mid);
			assertEquals(0, beans.size());
			
			labelDao.insertLabelEntry(b2);
			beans = labelDao.getAllEntriesFor(mid);
			assertEquals(1, beans.size());
		} catch (SQLException s) {
			fail("SQLException caught");
		} catch (DBException d) {
			fail("DBException caught");
		}
	}

	@Test
	public final void testinsertLabelEntry2() {
		try {
			labelDaoMock.insertLabelEntry(b1);
			fail("Exception wasn't caught.");
		} catch (SQLException s) {
			fail("SQLException caught");
		} catch (DBException d) {
			fail("DBException caught");
		} catch (Exception e) {
			//If an exception was caught, the test was successful
		}
	}
	
	@Test
	public final void testeditLabelEntry() {
		try {
			List<DailyNutritionLabelBean> beans = labelDao.getAllEntriesFor(mid);
			assertEquals(0, beans.size());
			
			labelDao.insertLabelEntry(b1);
			beans = labelDao.getAllEntriesFor(mid);
			assertEquals(1, beans.size());
			
			DailyNutritionLabelBean b3 = beans.get(0);
			b3.setLabel(label2);
			labelDao.editLabelEntry(b3);
			
			beans = labelDao.getAllEntriesFor(mid);
			assertEquals(1, beans.size());
			
			assertFalse(label1.equals(labelDao.getEntry(mid, date1).getLabel()));
		} catch (SQLException s) {
			fail("SQLException caught");
		} catch (DBException d) {
			fail("DBException caught");
		} catch (Exception e) {
			fail("General exception caught");
		}
	}

	@Test
	public final void testeditLabelEntry2() {
		try {
			labelDaoMock.editLabelEntry(b2);
			fail("Exception wasn't caught.");
		} catch (SQLException s) {
			fail("SQLException caught");
		} catch (DBException d) {
			fail("DBException caught");
		} catch (Exception e) {
			//If an exception was caught, the test was successful
		}
	}

	
	@Test
	public final void testgetAllLabelEntries() {
		try {
			List<DailyNutritionLabelBean> beans = labelDao.getAllLabelEntries();
			assertEquals(beans.size(), 0);
			
			labelDao.insertLabelEntry(b1);
			beans = labelDao.getAllLabelEntries();
			assertEquals(beans.size(), 1);
		}  catch (SQLException s) {
			fail("SQLException caught");
		} catch (DBException d) {
			fail("DBException caught");
		} catch (Exception e) {
			fail("General exception caught");
		}
	}
	
	@Test
	public final void testgetAllLabelEntries2() {
		try {
			List<DailyNutritionLabelBean> beans = labelDaoMock.getAllLabelEntries();
			fail("Exception wasn't caught.");
		} catch (SQLException s) {
			fail("SQLException caught");
		} catch (DBException d) {
			fail("DBException caught");
		} catch (Exception e) {
			//If an exception was caught, the test was successful
		}
	}

	
	@Test
	public final void testremoveLabelEntry() {
		try {
			List<DailyNutritionLabelBean> beans = labelDao.getAllEntriesFor(mid);
			assertEquals(0, beans.size());
			
			labelDao.insertLabelEntry(b1);
			beans = labelDao.getAllEntriesFor(mid);
			assertEquals(1, beans.size());

			labelDao.removeLabelEntry(b1);
			beans = labelDao.getAllEntriesFor(mid);
			assertEquals(0, beans.size());
		} catch (SQLException s) {
			fail("SQLException caught");
		} catch (DBException d) {
			fail("DBException caught");
		} catch (Exception e) {
			fail("General exception caught");
		}
	}
	
	@Test
	public final void testremoveLabelEntry2() {
		try {
			labelDaoMock.removeLabelEntry(b1);
			fail("Exception wasn't caught.");
		} catch (SQLException s) {
			fail("SQLException caught");
		} catch (DBException d) {
			fail("DBException caught");
		} catch (Exception e) {
			//If an exception was caught, the test was successful
		}
	}

}
