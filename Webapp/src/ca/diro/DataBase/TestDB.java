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
	
	public void testDbExecute() throws ClassNotFoundException, SQLException {
		
		Command cmd = new CommandHello();
		boolean boo = myDb.executeDb(cmd); 
		
		ResultSet rs = cmd.getResultSet();
		
		while (rs.next()) {
			System.out.println(rs.getString(4));
		}
		assertTrue(boo);
			
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
		String info = "{userId:1}" ;
		Command cmd = new ListRegisterEvent(info);
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
		String info = "{userId:1}" ;
		Command cmd = new ListEventByUser(info);
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
		String info = "{userId:1}" ;
		Command cmd = new PageInfoUser(info);
		boolean boo = myDb.executeDb(cmd); 
		
		ResultSet rs = cmd.getResultSet();
		
		while (rs.next()) {
			//System.out.println(rs.getString(2));
		}
		assertTrue(boo);
	}
	
	@Test
	
	//**************Command PageInfoEvent ********************//
	public void testPageInfoEvent() throws JSONException, SQLException  {
		// test to command giving   all informations of a user  
		String info = "{eventId:1}" ;
		Command cmd = new PageInfoEvent(info,myDb);
		boolean boo = myDb.executeDb(cmd); 
		
		ResultSet rs = cmd.getResultSet();
		
		while (rs.next()) {
			//System.out.println(rs.getString(2));
		}
		assertTrue(boo);
	}
	
	//******************Command CommentEvent  ********************//
	public void testCommentEvent() throws ClassNotFoundException, SQLException {
		// test to command st a comment done by a user 
		String info = "{eventId:1, userId:1, description: trop bien}" ;
		Command cmd = new CommentEvent(info,myDb);
		boolean boo = myDb.executeDb(cmd); 
		
		ResultSet rs = cmd.getResultSet();
		
		while (rs.next()) {
			//System.out.println(rs.getString(2));
		}
		assertTrue(boo);
	}

	//************* CancelEvent Command   ********************************
	@Test
	public void testCancelEvent() throws ClassNotFoundException, SQLException {
		// test verify cancelled event 
		String info = "{eventId:1}" ;
		Command cmd = new CancelEvent(info, myDb);
		boolean boo = ((CancelEvent) cmd).cancelQuery(); 
		assertTrue(boo);	
	}
	
	//************ command RemoveEvent *******************//
	@Ignore
	public void testremoveSubcriteEvent() throws ClassNotFoundException, SQLException {
		// test for removing cancelled events from list of subscripted events
		String info = "{eventId:1}" ;
		Command cmd = new CancelEvent(info, myDb);
		boolean boo = ((CancelEvent) cmd).removeSubcriteEvent() ; 
		assertTrue(boo);
		
	}
	
	@Test
	public void testNofifySignedUser() throws ClassNotFoundException, SQLException {
		// test verify if notify list for signed user is well done event 
		String info = "{eventId:1}" ;
		Command cmd = new CancelEvent(info, myDb);
		ResultSet rs = ((CancelEvent) cmd).getResultSet(); 
		List<String> result = new ArrayList<String>();
		
		while (rs.next()) {
			result.add(rs.getString(1));
			//System.out.println(result.get(0));
		}
		
		assertTrue(result.get(0).equals("1") && result.get(1).equals("2"));	
	}
	
	//***************** Command SubscriteToEvent **************************//
		
	@Ignore
	public void testAnonymSubsEvent() throws ClassNotFoundException, SQLException {
		// test verify the subscription of an anonymous user at on event 
		String info = "{eventId:1}";
		SubscriteToEvent cmd = new SubscriteToEvent(info,myDb);
		boolean boo = cmd.anonymSubsEvent(info); 
		assertTrue(boo);
	}
	
	@Ignore
	public void testSignedUserSubs() throws ClassNotFoundException, SQLException {
		// test verify the subscription of a signed  user at on event 
		String info = "{eventId:1,userId:2}" ;
		SubscriteToEvent cmd = new SubscriteToEvent(info,myDb);
		boolean boo = cmd.signedUserSubs(info); 
		assertTrue(boo);	
	}
	
	@Ignore
	public void testCreateUserAcount()  {
		// test verify creation of user account 
		 
		String info =  "{ fullName:billy joe ,userName:bil, password:bilson," +
							"email:bily@crabler.bi, age:456, description:jouer de tenis }";
		Command cmd = new CreateUserAccount(info,myDb);
		boolean boo = ((CreateUserAccount) cmd).createNewAccount(info); 
		assertTrue(boo);
	}
	
	//**************  Class Command  OpenSession *************************
	@Test
	public void testOpenSession() throws SQLException, JSONException  {
		//test opening session by signed user  
		
		String info = "{userName:gil,password:gilson}" ;
		Command cmd = new OpenSession(info);
		boolean boo = myDb.executeDb(cmd); 
		
		ResultSet rs = cmd.getResultSet();
		
		while (rs.next()) {
			//System.out.println(rs.getString(1));
		}
		assertTrue(boo);
	}
	
	//**************  Class Command AddEvent *************************
	@Ignore
	public void testAddNewEvent()  {
		// test verify creation of user account 
		
		String info = "{userId:1 ,title:bataille de chocolat, datetime:2014-12-07 23:21:45, location:plateau mont royal," +
				"numberplaces:42,  description:jeu de tir  tres evmouvant }";

		Command cmd = new AddEvent(info,myDb);
		boolean boo = ((AddEvent) cmd).addNewEvent(); 
		assertTrue(boo);
	}
	
	//**************  Class Command DeleteEvent *************************
	
	@Ignore
	public void testDeleteEvent() throws ClassNotFoundException, SQLException {
		// test verify delete of event by a user
		String info = "{eventId:1}";
		Command cmd = new DeleteEvent(info,myDb);
		boolean boo = ((DeleteEvent) cmd).removeEvent(info); 
		assertTrue(boo);
	}
	
	//**************  Class Command EditEvent *************************
	@Test
	public void testChangeEventTitle() throws JSONException {
		// test verify delete of event by a user
		String str = "{eventId:1, title: parade }";
		Command cmd = new EditEvent(str , myDb);
		boolean boo = ((EditEvent) cmd).changeEventTitle(); 
		assertTrue(boo);
	}
	
	@Test
	public void testchangeEventDatetime() throws JSONException  {
		// test verify changin  of date in an event 
		String str = "{eventId:1, datetime: '2017-05-15 12:00:00' }";
		Command cmd = new EditEvent(str , myDb);
		boolean boo = ((EditEvent) cmd).changeEventDatetime(); 
		assertTrue(boo);
	}
	@Test 
	public void testchangeEventLocation() throws JSONException  {
		// test verify changing  of date in an event 
		String str = "{eventId:1, location: gatineau prairie }";
		Command cmd = new EditEvent(str , myDb);
		boolean boo = ((EditEvent) cmd).changeEventLocation(); 
		assertTrue(boo);
	}
	
	@Test 
	public void testChangeEventPlaces() throws JSONException  {
		// test verify changing  of the maximum number of places  
		String str = "{eventId:1, place:20 }";
		Command cmd = new EditEvent(str, myDb);
		boolean boo = ((EditEvent) cmd).changeEventPlaces(); 
		assertTrue(boo);
	}
	
	@Test 
	public void testChangeEventDesc() throws JSONException  {
		// test verify changing  of description 
		String str = "{eventId:1, description: Vraiment la classe venez et vous verrez }";
		Command cmd = new EditEvent(str , myDb);
		boolean boo = ((EditEvent) cmd).changeEventDescription(); 
		assertTrue(boo);
	}
	
	//**************  Class Command ModifYAccount *************************
	@Test
	public void testChangeUserFullName() throws JSONException  {
		// test verify change of user's full name 
		String str = "{userId:1, fullName: blanchard }";
		Command cmd = new ModifyAccount(str , myDb);
		boolean boo = ((ModifyAccount) cmd).changeUserFullName(); 
		assertTrue(boo);
	}
	
	@Test
	public void testchangeUserEmail() throws JSONException {
		// test verify changing  email of user
		String str = "{userId:1, email: blanchard@beau.gt }";
		Command cmd = new ModifyAccount(str , myDb);
		boolean boo = ((ModifyAccount) cmd).changeUserEmail(); 
		assertTrue(boo);
	}
	@Test 
	public void testchangeUserName() throws JSONException  {
		// test verify changing  of user name
		String str = "{userId:1, userName: blanchard@beau.gt }";
		Command cmd = new ModifyAccount(str , myDb);
		boolean boo = ((ModifyAccount) cmd).changeUserName(); 
		assertTrue(boo);
	}
	
	@Test 
	public void testChangeUserPassword() throws JSONException {
		// test verify changing  of password 
		String str = "{userId:1, password: blanchard@beau.gt }";
		Command cmd = new ModifyAccount(str , myDb);
		boolean boo = ((ModifyAccount) cmd).changeUserPassword(); 
		assertTrue(boo);
	}
	
	@Test 
	public void testChangeUserDesc() throws JSONException  {
		// test verify changing  of description 
		String str = "{userId:1, description: blanchard aime bien sauter à la corde }";
		Command cmd = new ModifyAccount(str , myDb);
		boolean boo = ((ModifyAccount) cmd).changeUserDescription(); 
		assertTrue(boo);
	}
	
	@Test 
	public void testChangeUserAge() throws ClassNotFoundException, SQLException, JSONException {
		// test verify changing  of age
		String str = "{userId:1, age: 897 }";
		Command cmd = new ModifyAccount(str , myDb);
		boolean boo = ((ModifyAccount) cmd).changeUserAge(); 
		assertTrue(boo);
	}
	
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
			System.out.println(rs.getString(2));
		}
		assertTrue(boo);
			
	}
	
}
