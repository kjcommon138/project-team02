package edu.ncsu.csc.itrust.http;


import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;
import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * SearchUserTest
 */
public class SearchUserTest extends iTrustHTTPTest{
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	/**
	 * testGetPatient
	 * @throws Exception
	 */
	public void testGetPatient() throws Exception {
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Basic Health Information").click();
		assertEquals("iTrust - Please Select a Patient", wr.getTitle());
		wr.getForms()[1].setParameter("FIRST_NAME", "Random");
		wr.getForms()[1].setParameter("LAST_NAME", "Person");
		wr.getForms()[1].getButtons()[0].click();
		wr = wc.getCurrentPage();		
		WebTable wt = wr.getTableStartingWith("MID");
		assertEquals("MID", wt.getCellAsText(0, 0));
		assertEquals("", wt.getCellAsText(1, 0));
		assertEquals("Random", wt.getCellAsText(1, 1));
		assertEquals("Person", wt.getCellAsText(1, 2));
	}
	
	/**
	 * testGetPatient2
	 * @throws Exception
	 */
	public void testGetPatient2() throws Exception {
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Basic Health Information").click();
		assertEquals("iTrust - Please Select a Patient", wr.getTitle());
		wr.getForms()[1].setParameter("FIRST_NAME", "Andy");
		wr.getForms()[1].getButtons()[0].click();
		wr = wc.getCurrentPage();
		WebTable wt = wr.getTableStartingWith("MID");
		assertEquals("MID", wt.getCellAsText(0, 0));
		assertEquals("", wt.getCellAsText(1, 0));
		assertEquals("Andy", wt.getCellAsText(1, 1));
		assertEquals("Programmer", wt.getCellAsText(1, 2));
	}
	
	/**
	 * testGetPatient3
	 * @throws Exception
	 */
	public void testGetPatient3() throws Exception {
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("UAPs").click();
		assertEquals("iTrust - Please Select a Personnel", wr.getTitle());
		wr.getForms()[1].setParameter("FIRST_NAME", "Kelly");
		wr.getForms()[1].setParameter("LAST_NAME", "Doctor");
		wr.getForms()[1].getButtons()[0].click();
		wr = wc.getCurrentPage();
		WebTable wt = wr.getTables()[1];
		assertEquals("MID", wt.getCellAsText(0, 0)); /*
		assertEquals("", wt.getCellAsText(1, 0));
		assertEquals("Kelly", wt.getCellAsText(1, 1));
		assertEquals("Doctor", wt.getCellAsText(1, 2));*/
	}
	
	/**
	 * This test will verify that a partial search of a name correctly displays
	 * the results of any patient containing the search text
	 * @throws Exception
	 */
	//Test removed due to deprecation from HW2
	/*	public void testPartialNameSearch() throws Exception {
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Basic Health Information").click();
		assertEquals("iTrust - Please Select a Patient", wr.getTitle());
		wr.getForms()[1].setParameter("FIRST_NAME", "s");
		wr.getForms()[1].setParameter("LAST_NAME", "");
		wr.getForms()[1].getButtons()[0].click();
		wr = wc.getCurrentPage();		
		WebTable wt = wr.getTableStartingWith("MID");
		assertEquals("MID", wt.getCellAsText(0, 0));
		assertEquals("", wt.getCellAsText(1, 0));
		assertEquals("Sing", wt.getCellAsText(1, 1));
		assertEquals("Along", wt.getCellAsText(1, 2));
	} */
	
	/**
	 * This test will verify that a partial search of a MID correctly displays
	 * the results of any patient whose MID contains the search text.
	 * @throws Exception
	 */
	//Test removed due to deprecation from HW2
	/*	public void testPartialMIDSearch() throws Exception {
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Basic Health Information").click();
		assertEquals("iTrust - Please Select a Patient", wr.getTitle());
		wr.getForms()[1].setParameter("FIRST_NAME", "40");
		wr.getForms()[1].setParameter("LAST_NAME", "");
		wr.getForms()[1].getButtons()[0].click();
		wr = wc.getCurrentPage();		
		WebTable wt = wr.getTableStartingWith("MID");
		assertEquals("MID", wt.getCellAsText(0, 0));
		assertEquals("", wt.getCellAsText(1, 0));
		assertEquals("Sing", wt.getCellAsText(1, 1));
		assertEquals("Along", wt.getCellAsText(1, 2));
	}  */
	



}
