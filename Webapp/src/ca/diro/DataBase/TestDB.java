package ca.diro.DataBase;

import static org.junit.Assert.*;
import java.sql.*;
import java.util.*;

import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ca.diro.DataBase.Command.*;

/**
 * @author william
 *
 */
public class TestDB {

	

	DataBase myDb ;
	
	 @Before
	    public void before() throws ClassNotFoundException, SQLException {
	       String restore = "DataBaseRestore.sql" ;
		 	myDb = new DataBase(restore);
	        myDb.createTables();
	        myDb.populateTable();
	    }    
	        
	    @After
	    public void after() throws SQLException {
	        myDb.emptyDataBase();
	        myDb.dbClose();
	        myDb = null;
	    }    
	
	       
	    
	@Test
	public void testDbConnect() throws ClassNotFoundException, SQLException {
		DataBase myDbTest = new DataBase();
		assertTrue(myDbTest.dbConnect());
	}

    
	@Test
	public void testDbDisconnect() {
		try {
			DataBase myDbTest = new DataBase();
			myDbTest.dbClose();
		} catch (Exception e) {
			fail();
		}
	}
	

	
	/**
	 * Test method for {@link DataBase.DataBase#dbExecute(DataBase.Command)}.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@Test
	
	public void testDbExecuteNull() throws ClassNotFoundException, SQLException {
		
		boolean boo = myDb.executeDb(null); 
		assertFalse(boo);			
	}
	
	@Test
    public void testdescTableSet() throws SQLException {
        // test the method descTableSet and creation of databases tables 
		String[] tableNames = {  "SIGNEDUSER", "SESSIONUSER",
								"EVENT", "SUBSEVENTGENERAL","SUBSEVENTSIGNED", "COMMENTEVENT" };
		Set<String> tableSet = DBHelper.convertToSet(tableNames);
		Set<String> dbTablesIn = myDb.descTableSet();
       
        assertTrue(tableSet.equals(dbTablesIn));
	}
	
 
	@Ignore
	 public void testPopulateDataBase() throws SQLException, ClassNotFoundException {
	       //"verify all inserts in database have bee done well;
	       assertTrue(myDb.populateTable());
	       
		}
@Test
	public void testLisPassedEvent() throws ClassNotFoundException, SQLException {
		// test to command giving  passed list event 
		Command cmd = new ListPassedEvent();
		boolean boo = myDb.executeDb(cmd); 
		
		ResultSet rs = cmd.getResultSet();
		
		while (rs.next()) {
			//System.out.println(rs.getString(1));
		}
		assertTrue(boo);
	}

	@Test
	public void testListCommingEvent() throws ClassNotFoundException, SQLException {
		// test to command giving  coming list of events 
		Command cmd = new ListComingEvent();
		boolean boo = myDb.executeDb(cmd); 
		
		ResultSet rs = cmd.getResultSet();
		
		while (rs.next()) {
			//System.out.println(rs.getString(2));
		}
		assertTrue(boo);
		
		
	}

	@Test
	public void testListCancelledEvent() throws ClassNotFoundException, SQLException {
		// test to command giving  cancelled list of events 
		Command cmd = new ListCancelledEvent();
		boolean boo = myDb.executeDb(cmd); 
		
		ResultSet rs = cmd.getResultSet();
		
		while (rs.next()) {
			//System.out.println(rs.getString(2));
		}
		assertTrue(boo);
		
		
	}

	@Test
	public void testListRegisterEvent() throws ClassNotFoundException, SQLException {
		// test to command giving   list of events where a registered user attend 
		String userId = "2" ;
		Command cmd = new ListRegisterEvent(userId);
		boolean boo = myDb.executeDb(cmd); 
		
		ResultSet rs = cmd.getResultSet();
		
		while (rs.next()) {
			//System.out.println(rs.getString(2));
		}
		assertTrue(boo);
	}
	//************* Command ListEventByUser *****************//
	@Test
	public void testListEventByUser() throws SQLException  {
		// test to command giving the list of all event creted buy a user 
		String userId = "1" ;
		Command cmd = new ListEventByUser(userId);
		boolean boo = myDb.executeDb(cmd); 
		
		ResultSet rs = cmd.getResultSet();
		
		while (rs.next()) {
			//System.out.println(rs.getString(2));
		}
		assertTrue(boo);
			
	}
	
	//************ Command PageInfoUser ***********************//
	@Test
	public void testPageInfoUser() throws JSONException, SQLException  {
		// test to command giving   all informations of a user  
		String userId = "1" ;
		Command cmd = new PageInfoUser(userId);
		boolean boo = myDb.executeDb(cmd); 
		
		ResultSet rs = cmd.getResultSet();
		
		while (rs.next()) {
			//System.out.println(rs.getString(2));
		}
		assertTrue(boo);
	}
	
	
	//**************Command PageInfoEvent ********************//
	@Test
	public void testPageInfoEvent() throws JSONException, SQLException  {
		// test to command giving   all informations of a user  
		String eventId = "1" ;
		PageInfoEvent cmd = new PageInfoEvent(eventId,myDb);
		boolean boo = myDb.executeDb(cmd); 
		
		ResultSet rs = cmd.getResultSet();
		
		while (rs.next()) {
			//System.out.println(rs.getString(2));
		}
		//System.out.println(cmd.getAvailablePlaces()) ;
		
		assertTrue(boo);
	}
	
	//******************Command CommentEvent  ********************//
	@Test
	public void testCommentEvent() throws ClassNotFoundException, SQLException {
		// test to command st a comment done by a user 
		String eventId = "1" ; String  userId = "1" ; String description = "trop bien" ;
		Command cmd = new CommentEvent(eventId,userId,description,myDb);
		boolean boo = myDb.executeDb(cmd); 
		
		ResultSet rs = cmd.getResultSet();
		
		while (rs.next()) {
			//System.out.println(rs.getString(2));
		}
		assertTrue(boo);
	}

	//************* Command  CancelEvent   ********************************
	@Test
	public void testCancelEvent() throws ClassNotFoundException, SQLException {
		// test verify cancelled event 
		String eventId = "1" ;
		Command cmd = new CancelEvent(eventId , myDb);
		boolean boo = myDb.executeDb(cmd); 
		assertTrue(boo);	
	}
	
	//************ command RemoveEvent *******************//
	@Ignore
	public void testremoveSubcriteEvent() throws ClassNotFoundException, SQLException {
		// test for removing cancelled events from list of subscripted events
		String eventId = "1" ;
		Command cmd = new CancelEvent(eventId , myDb);
		boolean boo = ((CancelEvent) cmd).removeSubcriteEvent(eventId) ; 
		assertTrue(boo);
		
	}
	
	//****************** Command NotifyCancelEvent ****************************//
	@Test
	public void testNofifySignedUser() throws ClassNotFoundException, SQLException {
		// test verify if notify list for signed user is well done event 
		String eventId = "1" ;
		Command cmd = new NotifyCancelledEvent(eventId , myDb);
		myDb.executeDb(cmd);
		ResultSet rs = cmd.getResultSet(); 
		List<String> result = new ArrayList<String>();
		
		while (rs.next()) {
			result.add(rs.getString(1));
			//System.out.println(result.get(0));
		}
		
		assertTrue(result.get(0).equals("1") && result.get(1).equals("2"));	
	}
	
	//***************** Command SubscriteToEvent **************************//
		
	@Test
	public void testSubsEvent() throws ClassNotFoundException, SQLException {
		// test verify the subscription of an anonymous user at on event 
		String eventId = "2";  String userId = "3" ; 
		boolean select = true ;
		SubscriteToEvent cmd = new SubscriteToEvent(userId, eventId, select);
		boolean boo = myDb.executeDb(cmd); 
		//System.out.println(boo);
		assertTrue(boo);
	}
	
	
	
//************************ command CreateUserAccount ********************************//
	@Test
	public void testCreateUserAcount() throws SQLException  {
		// test verify creation of user account 
		 
		String  fullname = "lion joe" ; String userName = "lion"  ; String password = "lionson" ;
							String email = "bily@crabler.bi" ; String  age = "2002-02-14"; String  description = "jouer de tenis ";
		Command cmd = new CreateUserAccount(userName, password ,fullname , email, age, description, myDb);
		boolean boo = myDb.executeDb(cmd); 
		ResultSet rs = ((CreateUserAccount) cmd).getCurrentUserId();
		if (rs.next()){ 
			//System.out.println(rs.getString(1));
		}
		assertTrue(boo);
	}
	
	//**************  Class Command  OpenSession *************************
	@Test
	public void testOpenSession() throws SQLException, JSONException  {
		//test opening session by signed user  
		
		String userName = "gil" ; String password = "gilson" ;
		Command cmd = new OpenSession(userName, password);
		boolean boo = myDb.executeDb(cmd); 
		ResultSet rs = cmd.getResultSet();
		
		rs.next() ;
		//System.out.println(rs.getString(1));
			//System.out.println(rs);
		
		assertTrue(boo);
	}
	
	//**************  Class Command AddEvent *************************
	@Test
	public void testAddNewEvent() throws SQLException  {
		// test verify creation of user account 
		
		String userId = "1"; String title = "bataille de chocolat" ; 
		String   datetime = "2014-12-07 23:21:00" ; String  location = "plateau mont royal" ;
		String numberplaces = "42" ; String description = "jeu de tir  tres evmouvant ";

		Command cmd = new AddEvent(userId,title, datetime, location, numberplaces, description, myDb);
		boolean boo = myDb.executeDb(cmd); 
		ResultSet rs = ((AddEvent) cmd).getCurrentEventId();
		if (rs.next()){ 
			//System.out.println(rs.getString(1));
		}
		assertTrue(boo);
	}
	
	//**************  Class Command DeleteEvent *************************
	
	@Test
	public void testDeleteEvent() throws ClassNotFoundException, SQLException {
		// test verify delete of event by a user
		String eventId = "1";
		DeleteEvent cmd = new DeleteEvent(eventId);
		boolean boo =  myDb.executeDb(cmd); 
		assertTrue(boo);
	}
	
	//**************  Class Command EditEvent *************************
	@Test
	public void testEditEvent() throws SQLException {
		// test verify delete of event by a user
		String eventId = "1" ; String  title = "parade "; String dateTime = "2017-05-24 14:30:30" ; String location = "place des arts " ;
		String nbrPlaces = "23"; String description = "nouveau jeu amusant " ;
		Command cmd = new EditEvent(eventId ,title, dateTime, location, nbrPlaces, description);
		boolean boo = myDb.executeDb(cmd);
		assertTrue(boo);
	}
	
	
	
	//**************  Class Command ModifYAccount *************************
	@Test
	public void testModifyUserAccount() throws SQLException  {
		// test verify change of user's full name 
		String userId =  "1" ; String fullName = "blanchard"; String email = "blanchard@legrand.tc"; String username = "blanco";
		String password = "blankison" ; String age = "2005-07-12"; String description = "jaime la nage les raquette le soccer";
		Command cmd = new ModifyAccount(userId ,fullName, email, username, password, age, description);
		boolean boo = myDb.executeDb(cmd) ; 
		
		assertTrue(boo);
	}
	
	//*************** Reseach Event *****************//
	@Test
	public void testResearchEvent() throws ClassNotFoundException, SQLException {
		
	
		LinkedList<String> stack = new LinkedList<String>();
		//stack.push("course");
		//stack.push("handball");
		stack.push("antoine");
			
		Command cmd = new ResearchEvent(stack);
		boolean boo = myDb.executeDb(cmd); 
		
		ResultSet rs = cmd.getResultSet();
		
		while (rs.next()) {
			//System.out.println(rs.getString(2));
		}
		assertTrue(boo);
			
	}
	
}
