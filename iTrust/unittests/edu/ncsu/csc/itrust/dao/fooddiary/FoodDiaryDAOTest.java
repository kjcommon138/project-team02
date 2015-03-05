package edu.ncsu.csc.itrust.dao.fooddiary;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.DBBuilder;
import edu.ncsu.csc.itrust.beans.FoodDiaryBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.FoodDiaryDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class FoodDiaryDAOTest {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private FoodDiaryDAO diaryDao = new FoodDiaryDAO(this.factory);
	private long mid = 1L;
	TestDataGenerator gen;
	
	private FoodDiaryBean b1 = new FoodDiaryBean();
	private FoodDiaryBean b2 = new FoodDiaryBean();
	
	java.sql.Date newDate = new java.sql.Date(Calendar.getInstance().getTimeInMillis() - 86400000);
	FoodDiaryDAO fdDaoMock = new FoodDiaryDAO(null);
	
	@Before
	public void setUp() throws Exception {

		DBBuilder tables = new DBBuilder();
		tables.dropTables();
		tables.createTables();
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();

		//foodDiaryBeans go here
		b1.setCalories(1);
		b1.setEntryDate(newDate);
		b1.setFoodEntryID(1);
		b1.setFoodName("Beans");
		b1.setGramsCarb(2);
		b1.setGramsFat(3);
		b1.setGramsFiber(4);
		b1.setGramsProtein(4);
		b1.setGramsSugar(9);
		b1.setMealType("Lunch");
		b1.setMilligramSodium(342);
		b1.setPatient(mid);
		b1.setServings(1);
		
		b2.setCalories(82);
		b2.setEntryDate(newDate);
		b2.setFoodEntryID(6);
		b2.setFoodName("Pancakes");
		b2.setGramsCarb(2);
		b2.setGramsFat(3);
		b2.setGramsFiber(4);
		b2.setGramsProtein(4);
		b2.setGramsSugar(9);
		b2.setMealType("Breakfast");
		b2.setMilligramSodium(342);
		b2.setPatient(mid);
		b2.setServings(1);
	}

	@Test
	public final void testGetEntry() {
		try {
			List<FoodDiaryBean> beans = diaryDao.getAllFoodDiaryEntriesFor(mid);
			assertEquals(0, beans.size());
			
			diaryDao.insertFoodEntry(b1);
			beans = diaryDao.getAllFoodDiaryEntriesFor(mid);
			assertEquals(1, beans.size());

			FoodDiaryBean b3 = diaryDao.getEntry(1);
			assertEquals(b3.getFoodEntryID(), b1.getFoodEntryID());
		} catch (SQLException s) {
			fail("SQLException caught");
		} catch (DBException d) {
			fail("DBException caught");
		} catch (Exception e) {
			fail("General exception caught");
		}
	}

	@Test
	public final void testGetEntry2() {
		try {
			FoodDiaryBean bean = fdDaoMock.getEntry(0);
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
	public final void testGetAllFoodDiaryEntriesFor() {
		try {
			List<FoodDiaryBean> beans = diaryDao.getAllFoodDiaryEntriesFor(mid);
			assertEquals(0, beans.size());
		} catch (SQLException s) {
			fail("SQLException caught");
		} catch (DBException d) {
			fail("DBException caught");
		}
	}

	@Test
	public final void testGetAllFoodDiaryEntriesFor2() {
		try {
			List<FoodDiaryBean> beans = fdDaoMock.getAllFoodDiaryEntriesFor(mid);
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
	public final void testInsertFoodEntry() {
		try {
			List<FoodDiaryBean> beans = diaryDao.getAllFoodDiaryEntriesFor(mid);
			assertEquals(0, beans.size());
			
			diaryDao.insertFoodEntry(b1);
			beans = diaryDao.getAllFoodDiaryEntriesFor(mid);
			assertEquals(1, beans.size());
		} catch (SQLException s) {
			fail("SQLException caught");
		} catch (DBException d) {
			fail("DBException caught");
		}
	}

	@Test
	public final void testInsertFoodEntry2() {
		try {
			fdDaoMock.insertFoodEntry(b1);
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
	public final void testEditFoodDiaryEntry() {
		try {
			List<FoodDiaryBean> beans = diaryDao.getAllFoodDiaryEntriesFor(mid);
			assertEquals(0, beans.size());
			
			diaryDao.insertFoodEntry(b1);
			beans = diaryDao.getAllFoodDiaryEntriesFor(mid);
			assertEquals(1, beans.size());
			
			FoodDiaryBean b3 = beans.get(0);
			b3.setCalories(293);
			diaryDao.editFoodDiaryEntry(b3);

			beans = diaryDao.getAllFoodDiaryEntriesFor(mid);
			assertEquals(1, beans.size());
			
			assertFalse(2 == diaryDao.getEntry(1).getFoodEntryID());
		} catch (SQLException s) {
			fail("SQLException caught");
		} catch (DBException d) {
			fail("DBException caught");
		} catch (Exception e) {
			fail("General exception caught");
		}
	}

	@Test
	public final void testEditFoodDiaryEntry2() {
		try {
			fdDaoMock.editFoodDiaryEntry(b2);
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
	public final void testGetAllFoodDiaryEntries() {
		try {
			List<FoodDiaryBean> beans = diaryDao.getAllFoodDiaryEntries();
			assertEquals(beans.size(), 0);
			
			diaryDao.insertFoodEntry(b1);
			beans = diaryDao.getAllFoodDiaryEntries();
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
	public final void testGetAllFoodDiaryEntries2() {
		try {
			List<FoodDiaryBean> beans = fdDaoMock.getAllFoodDiaryEntries();
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
	public final void testRemoveFoodDiaryEntry() {
		try {
			List<FoodDiaryBean> beans = diaryDao.getAllFoodDiaryEntriesFor(mid);
			assertEquals(0, beans.size());
			
			diaryDao.insertFoodEntry(b1);
			beans = diaryDao.getAllFoodDiaryEntriesFor(mid);
			assertEquals(1, beans.size());

			diaryDao.removeFoodDiaryEntry(b1);
			beans = diaryDao.getAllFoodDiaryEntriesFor(mid);
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
	public final void testRemoveFoodDiaryEntry2() {
		try {
			fdDaoMock.removeFoodDiaryEntry(b1);
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
