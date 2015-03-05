package edu.ncsu.csc.itrust.bean;

import edu.ncsu.csc.itrust.beans.FoodDiaryBean;

import java.sql.Date;
import java.util.Calendar;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FoodDiaryBeanTest
{
    FoodDiaryBean fbTest1;
    FoodDiaryBean fbTest2;
    Date newDate1;
    Date newDate2;
    Date newDate3;
	
	@Before
	public void setUp() throws Exception {
        fbTest1 = new FoodDiaryBean();
        fbTest2 = new FoodDiaryBean();
        newDate1 = new Date(Calendar.getInstance().getTimeInMillis());
        newDate2 = new Date(Calendar.getInstance().getTimeInMillis() - 0x5265c00L);
        newDate3 = new Date(Calendar.getInstance().getTimeInMillis() + 0x5265c00L);
    }

    @Test
    public final void testHashCode()
    {
        fbTest1.setFoodEntryID(1);
        assertEquals(fbTest1.hashCode(), fbTest1.getFoodEntryID());
        fbTest2.setFoodEntryID(2);
        assertFalse(fbTest1.hashCode() == fbTest2.hashCode());
    }

    @Test
    public final void testGetPatient()
    {
        fbTest1.setPatient(1L);
        assertEquals(fbTest1.getPatient(), 1L);
        fbTest2.setPatient(37328L);
        assertFalse(fbTest1.getPatient() == fbTest2.getPatient());
    }

    @Test
    public final void testGetFoodEntryID()
    {
        fbTest1.setFoodEntryID(1);
        assertEquals(1L, fbTest1.getFoodEntryID());
        fbTest2.setFoodEntryID(2);
        assertFalse(fbTest1.getFoodEntryID() == fbTest2.getFoodEntryID());
    }

    @Test
    public final void testGetMealType()
    {
        try
        {
            fbTest1.setMealType("Breakfast");
            fbTest2.setMealType("Lunch");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception was caught - Failed at getMealType.");
        }
        assertEquals("Breakfast", fbTest1.getMealType());
        assertFalse(fbTest1.getMealType().equals(fbTest2.getMealType()));
    }

    @Test
    public final void testGetFoodName()
    {
        try
        {
            fbTest1.setFoodName("Eggs");
            fbTest2.setFoodName("Sandwich");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception was caught - Failed at getFoodName.");
        }
        assertEquals("Eggs", fbTest1.getFoodName());
        assertFalse(fbTest1.getFoodName().equals(fbTest2.getFoodName()));
    }

    
	@Test
    public final void testGetServings()
    {
        try
        {
            fbTest1.setServings(2);
            fbTest2.setServings(8);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception was caught - Failed at getServings.");
        }
        assertEquals(2, fbTest1.getServings(), .000001);
        assertFalse(fbTest1.getServings() == fbTest2.getServings());
    }

    
	@Test
    public final void testGetCalories()
    {
        try
        {
            fbTest1.setCalories(99);
            fbTest2.setCalories(183);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception was caught - Failed at getCalories.");
        }
        assertEquals(99, fbTest1.getCalories(), .000001);
        assertFalse(fbTest1.getCalories() == fbTest2.getCalories());
    }

    
	@Test
    public final void testGetGramsFat()
    {
        try
        {
            fbTest1.setGramsFat(3);
            fbTest2.setGramsFat(5);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception was caught - Failed at getGramsFat.");
        }
        assertEquals(3, fbTest1.getGramsFat(), .000001);
        assertFalse(fbTest1.getGramsFat() == fbTest2.getGramsFat());
    }

    
	@Test
    public final void testGetMilligramsSodium()
    {
        try
        {
            fbTest1.setMilligramSodium(354);
            fbTest2.setMilligramSodium(1300);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception was caught - Failed at getMilligramsSodium.");
        }
        assertEquals(354, fbTest1.getMilligramsSodium(), .000001);
        assertFalse(fbTest1.getMilligramsSodium() == fbTest2.getMilligramsSodium());
    }

    
	@Test
    public final void testGetGramsCarb()
    {
        try
        {
            fbTest1.setGramsCarb(23);
            fbTest2.setGramsCarb(11);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception was caught - Failed at getGramsCarb.");
        }
        assertEquals(23, fbTest1.getGramsCarb(), .000001);
        assertFalse(fbTest1.getGramsCarb() == fbTest2.getGramsCarb());
    }

    
	@Test
    public final void testGetGramsSugar()
    {
        try
        {
            fbTest1.setGramsSugar(21);
            fbTest2.setGramsSugar(4);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception was caught - Failed at getGramsSugar.");
        }
        assertEquals(21, fbTest1.getGramsSugar(), .000001);
        assertFalse(fbTest1.getGramsSugar() == fbTest2.getGramsSugar());
    }

    
	@Test
    public final void testGetGramsFiber()
    {
        try
        {
            fbTest1.setGramsFiber(13);
            fbTest2.setGramsFiber(19);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception was caught - Failed at getGramsFiber.");
        }
        assertEquals(13, fbTest1.getGramsFiber(), .000001);
        assertFalse(fbTest1.getGramsFiber() == fbTest2.getGramsFiber());
    }

    
	@Test
    public final void testGetGramsProtein()
    {
        try
        {
            fbTest1.setGramsProtein(21);
            fbTest2.setGramsProtein(5);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception was caught - Failed at getGramsProtein.");
        }
        assertEquals(21, fbTest1.getGramsProtein(), .000001);
        assertFalse(fbTest1.getGramsProtein() == fbTest2.getGramsProtein());
    }

    @Test
    public final void testGetEntryDate()
    {
        try
        {
            fbTest1.setEntryDate(newDate1);
            fbTest2.setEntryDate(newDate2);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception was caught - Failed at getEntryDate.");
        }
        assertEquals(newDate1, fbTest1.getEntryDate());
        assertFalse(fbTest1.getEntryDate().equals(fbTest2.getEntryDate()));
    }

    @Test
    public final void testSetFoodEntryID()
    {
        try
        {
            fbTest1.setFoodEntryID(5);
            assertEquals(5L, fbTest1.getFoodEntryID());
            fbTest1.setFoodEntryID(992);
            assertFalse(fbTest1.getFoodEntryID() == fbTest2.getFoodEntryID());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception was caught - Failed at setFoodEntryID.");
        }
    }

    @Test
    public final void testSetPatient()
    {
        fbTest1.setPatient(6L);
        assertFalse(fbTest1.getPatient() == fbTest2.getPatient());
        fbTest1.setPatient(5L);
    }

    @Test
    public final void testSetMealType()
    {
        try
        {
            fbTest1.setMealType("Breakfast");
            assertEquals("Breakfast", fbTest1.getMealType());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception was caught - Failed at setMealType.");
        }
    }

    @Test
    public final void testSetMealType2()
    {
        try
        {
            fbTest1.setMealType(null);
            fail("exception not thrown for null value.");
        }
        catch(Exception exception) {
        	//If an exception is caught, the test passes
        }
    }

    @Test
    public final void testSetMealType3()
    {
        try
        {
            fbTest1.setMealType("");
            fail("exception not thrown for empty value.");
        }
        catch(Exception exception) {
        	//If an exception is caught, the test passes
        }
    }

    @Test
    public final void testSetFoodName()
    {
        try
        {
            fbTest1.setFoodName("Doughnuts");
            assertEquals("Doughnuts", fbTest1.getFoodName());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception was caught - Failed at setMealType.");
        }
    }

    @Test
    public final void testSetFoodName2()
    {
        try
        {
            fbTest1.setFoodName(null);
            fail("exception not thrown for null value.");
        }
        catch(Exception exception) {
        	//If an exception is caught, the test passes
        }
    }

    @Test
    public final void testSetFoodName3()
    {
        try
        {
            fbTest1.setFoodName("");
            fail("exception not thrown for empty value.");
        }
        catch(Exception exception) {
        	//If an exception is caught, the test passes
        }
    }

    
	@Test
    public final void testSetServings()
    {
        try
        {
            fbTest1.setServings(3);
            assertEquals(3, fbTest1.getServings(), .000001);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception was caught - Failed at setServings.");
        }
    }

    @Test
    public final void testSetServings2()
    {
        try
        {
            fbTest1.setServings(0);
            fail("exception not thrown for 0.");
        }
        catch(Exception exception) {
        	//If an exception is caught, the test passes
        }
    }

    @Test
    public final void testSetServings3()
    {
        try
        {
            fbTest1.setServings(-1);
            fail("exception not thrown for negative value.");
        }
        catch(Exception exception) {
        	//If an exception is caught, the test passes
        }
    }

    
	@Test
    public final void testSetCalories()
    {
        try
        {
            fbTest1.setCalories(73);
            assertEquals(73, fbTest1.getCalories(), .000001);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception was caught - Failed at setCalories.");
        }
    }

    
	@Test
    public final void testSetCalories2()
    {
        try
        {
            fbTest1.setCalories(0);
            assertEquals(0, fbTest1.getCalories(), .000001);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception was caught - Failed at setCalories2.");
        }
    }

    @Test
    public final void testSetCalories3()
    {
        try
        {
            fbTest1.setCalories(-1);
            fail("exception not thrown for negative value.");
        }
        catch(Exception exception) {
        	//If an exception is caught, the test passes
        }
    }

    
	@Test
    public final void testSetGramsFat()
    {
        try
        {
            fbTest1.setGramsFat(73);
            assertEquals(73, fbTest1.getGramsFat(), .000001);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception was caught - Failed at setGramsFat.");
        }
    }

    
	@Test
    public final void testSetGramsFat2()
    {
        try
        {
            fbTest1.setGramsFat(0);
            assertEquals(0, fbTest1.getGramsFat(), .000001);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception was caught - Failed at setGramsFat2.");
        }
    }

    @Test
    public final void testSetGramsFat3()
    {
        try
        {
            fbTest1.setGramsFat(-1);
            fail("exception not thrown for negative value.");
        }
        catch(Exception exception) {
        	//If an exception is caught, the test passes
        }
    }

    
	@Test
    public final void testSetMilligramSodium()
    {
        try
        {
            fbTest1.setMilligramSodium(73);
            assertEquals(73, fbTest1.getMilligramsSodium(), .000001);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception was caught - Failed at setMilligramSodium.");
        }
    }

    
	@Test
    public final void testSetMilligramSodium2()
    {
        try
        {
            fbTest1.setMilligramSodium(0);
            assertEquals(0, fbTest1.getMilligramsSodium(), .000001);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception was caught - Failed at setMilligramSodium2.");
        }
    }

    @Test
    public final void testSetMilligramSodium3()
    {
        try
        {
            fbTest1.setMilligramSodium(-1);
            fail("exception not thrown for negative value.");
        }
        catch(Exception exception) {
        	//If an exception is caught, the test passes
        }
    }

    
	@Test
    public final void testSetGramsCarb()
    {
        try
        {
            fbTest1.setGramsCarb(73);
            assertEquals(73, fbTest1.getGramsCarb(), .000001);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception was caught - Failed at setGramsCarb.");
        }
    }

    
	@Test
    public final void testSetGramsCarb2()
    {
        try
        {
            fbTest1.setGramsCarb(0);
            assertEquals(0, fbTest1.getGramsCarb(), .000001);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception was caught - Failed at setGramsCarb2.");
        }
    }

    @Test
    public final void testSetGramsCarb3()
    {
        try
        {
            fbTest1.setGramsCarb(-1);
            fail("exception not thrown for negative value.");
        }
        catch(Exception exception) {
        	//If an exception is caught, the test passes
        }
    }

    
	@Test
    public final void testSetGramsSugar()
    {
        try
        {
            fbTest1.setGramsSugar(73);
            assertEquals(73, fbTest1.getGramsSugar(), .000001);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception was caught - Failed at setGramsSugar.");
        }
    }

    
	@Test
    public final void testSetGramsSugar2()
    {
        try
        {
            fbTest1.setGramsSugar(0);
            assertEquals(0, fbTest1.getGramsSugar(), .000001);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception was caught - Failed at setGramsSugar2.");
        }
    }

    @Test
    public final void testSetGramsSugar3()
    {
        try
        {
            fbTest1.setGramsSugar(-1);
            fail("exception not thrown for negative value.");
        }
        catch(Exception exception) {
        	//If an exception is caught, the test passes
        }
    }

    
	@Test
    public final void testSetGramsFiber()
    {
        try
        {
            fbTest1.setGramsFiber(73);
            assertEquals(73, fbTest1.getGramsFiber(), .000001);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception was caught - Failed at setGramsFiber.");
        }
    }

    
	@Test
    public final void testSetGramsFiber2()
    {
        try
        {
            fbTest1.setGramsFiber(0);
            assertEquals(0, fbTest1.getGramsFiber(), .000001);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception was caught - Failed at setGramsFiber2.");
        }
    }

    @Test
    public final void testSetGramsFiber3()
    {
        try
        {
            fbTest1.setGramsFiber(-1);
            fail("exception not thrown for negative value.");
        }
        catch(Exception exception) {
        	//If an exception is caught, the test passes
        }
    }

    
	@Test
    public final void testSetGramsProtein()
    {
        try
        {
            fbTest1.setGramsProtein(73);
            assertEquals(73, fbTest1.getGramsProtein(), .000001);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception was caught - Failed at setGramsProtein.");
        }
    }

    
	@Test
    public final void testSetGramsProtein2()
    {
        try
        {
            fbTest1.setGramsProtein(0);
            assertEquals(0, fbTest1.getGramsProtein(), .000001);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception was caught - Failed at setGramsProtein2.");
        }
    }

    @Test
    public final void testSetGramsProtein3()
    {
        try
        {
            fbTest1.setGramsProtein(-1);
            fail("exception not thrown for negative value.");
        }
        catch(Exception exception) {
        	//If an exception is caught, the test passes
        }
    }

    @Test
    public final void testSetEntryDate()
    {
        try
        {
            fbTest1.setEntryDate(newDate1);
            assertEquals(newDate1, fbTest1.getEntryDate());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception was caught - Failed at setEntryDate.");
        }
    }

    @Test
    public final void testSetEntryDate2()
    {
        try
        {
            fbTest1.setEntryDate(newDate3);
            fail("Exception was not caught - Failed at setEntryDate2.");
        }
        catch(Exception exception) { 
        	//If an exception is caught, the test passes
        }
    }

    @Test
    public final void testSetEntryDate3()
    {
        try
        {
            fbTest1.setEntryDate(null);
            fail("Exception was not caught - Failed at setEntryDate3.");
        }
        catch(Exception exception) {
        	//If an exception is caught, the test passes
        }
    }

    @Test
    public final void testEqualsObject()
    {
        fbTest2.setFoodEntryID(4);
        assertTrue(fbTest1.equals(fbTest1));
        assertFalse(fbTest1.equals(fbTest2));
        assertFalse(fbTest1.equals(newDate1));
        fbTest2.setFoodEntryID(0);
        assertTrue(fbTest1.equals(fbTest2));
    }
}