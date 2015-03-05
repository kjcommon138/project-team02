package edu.ncsu.csc.itrust.action;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.FoodDiaryBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class ViewMyNutritionActionTest {

	private ViewMyNutritionAction action;
	private DAOFactory factory;
	private long mid = 1L;
	TestDataGenerator gen;
	
	@Before
	public void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		
		this.factory = TestDAOFactory.getTestInstance();
		this.action = new ViewMyNutritionAction(factory, mid);
	}

	@Test
	public final void testGetMyFoodDiaryEntries() {
		try {
			List<FoodDiaryBean> beans = action.getMyFoodDiaryEntries();
			assertTrue(beans.size() < 10);
		} catch (SQLException s) {
			fail("SQLException caught");
		} catch (DBException d) {
			fail("DBException caught");
		}
	}

	@Test
	public final void testAddAFoodDiaryEntry() {
		try {
			List<FoodDiaryBean> beans = action.getMyFoodDiaryEntries();
			int size = beans.size();
			
			FoodDiaryBean entry = beans.get(0);
			entry.setFoodEntryID(100);
			entry.setGramsCarb(3);
			action.addAFoodDiaryEntry(entry);
			assertEquals(size + 1, action.getMyFoodDiaryEntries().size());
		} catch (SQLException s) {
			fail("SQLException caught");
		} catch (DBException d) {
			fail("DBException caught");
		} catch (Exception e) {
			fail("General exception caught");
		}
	}

}
