/**
 * 
 */
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

/**
 * @author Kevin-Lenovo
 *
 */
public class EditMyNutritionActionTest {
	private EditMyNutritionAction action;
	private EditMyNutritionAction action2;
	private DAOFactory factory;
	private long mid = 1L;
	private long mid2 = 90000000000L;
	TestDataGenerator gen;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		
		this.factory = TestDAOFactory.getTestInstance();
		this.action = new EditMyNutritionAction(factory, mid);
		this.action2 = new EditMyNutritionAction(factory, mid2);
	}

	/**
	 * Test method for {@link edu.ncsu.csc.itrust.action.EditMyNutritionAction#getMyFoodDiaryEntries()}.
	 */
	@Test
	public final void testGetMyFoodDiaryEntries() {
		try {
			List<FoodDiaryBean> beans = action.getMyFoodDiaryEntries();
			assertTrue(beans.size() == 6);
			
			List<FoodDiaryBean> bean2 = action2.getMyFoodDiaryEntries();
			assertTrue(bean2.size() > 6);
		} catch (SQLException s) {
			fail("SQLException caught");
		} catch (DBException d) {
			fail("DBException caught");
		}
	}
	/**
	 * Test method for {@link edu.ncsu.csc.itrust.action.EditMyNutritionAction#getSingleEntry(int)}.
	 */
	@Test
	public final void testGetSingleEntry() {
		try {
			List<FoodDiaryBean> beans = action.getMyFoodDiaryEntries();
			assertEquals(beans.size(), 6);
			
			FoodDiaryBean b1 = beans.get(0);
			FoodDiaryBean b2 = action.getSingleEntry(b1.getFoodEntryID());
			
			assertEquals(b1.getCalories(), b2.getCalories(), .00001);
		} catch (Exception e) {
			fail("Exception caught");
		}
	}

	/**
	 * Test method for {@link edu.ncsu.csc.itrust.action.EditMyNutritionAction#deleteFoodDiaryEntry(edu.ncsu.csc.itrust.beans.FoodDiaryBean)}.
	 */
	@Test
	public final void testDeleteFoodDiaryEntry() {
		try {
			List<FoodDiaryBean> beans = action.getMyFoodDiaryEntries();
			assertEquals(beans.size(), 6);
			
			action.deleteFoodDiaryEntry(beans.get(0));
			beans = action.getMyFoodDiaryEntries();
			assertEquals(beans.size(), 5);			
		} catch (Exception e) {
			fail("Exception caught");
		}
	}

	/**
	 * Test method for {@link edu.ncsu.csc.itrust.action.EditMyNutritionAction#editFoodDiaryEntry(edu.ncsu.csc.itrust.beans.FoodDiaryBean)}.
	 */
	@Test
	public final void testEditFoodDiaryEntry() {
		try {
			List<FoodDiaryBean> beans = action.getMyFoodDiaryEntries();
			assertEquals(beans.size(), 6);
			
			FoodDiaryBean b1 = beans.get(0);
			b1.setCalories(5000);
			
			action.editFoodDiaryEntry(b1);
			FoodDiaryBean b2 = action.getMyFoodDiaryEntries().get(0);
			
			assertEquals(b2.getCalories(), b1.getCalories(), .00001);
		} catch (Exception e) {
			fail("Exception caught");
		}
	}
}
