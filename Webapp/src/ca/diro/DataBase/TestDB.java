package ca.diro.DataBase;

import static org.junit.Assert.*;
import java.sql.*;
import java.util.*;

import org.junit.Ignore;
import org.junit.Test;
import ca.diro.DataBase.Command.*;

/**
 * @author william
 *
 */
public class TestDB {

	DataBase myDb ;
	
	public TestDB() throws ClassNotFoundException{
		myDb = new DataBase() ;
	}
	
	@Test
	public void testDbConnect() {
		
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
			System.out.println(rs.getString(7));
		}
		assertTrue(boo);
			
	}

	
	@Test
    public void testdescTableSet() throws SQLException {
        // test the method descTableSet and creation of databases tables 
		String[] tableNames = { "GENERALUSER", "SIGNEDUSER", "SESSIONUSER",
								"EVENT", "SUBSEVENTGENERAL","SUBSEVENTSIGNED", "COMMENTEVENT" };
		Set<String> tableSet = DataBase.convertToSet(tableNames);
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
		String userId = "1" ;
		Command cmd = new ListRegisterEvent(userId);
		boolean boo = myDb.executeDb(cmd); 
		
		ResultSet rs = cmd.getResultSet();
		
		while (rs.next()) {
			//System.out.println(rs.getString(2));
		}
		assertTrue(boo);
	}
	
	@Test
	public void testListEventByUser() throws ClassNotFoundException, SQLException {
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
	@Test
	public void testPaseInfoUser() throws ClassNotFoundException, SQLException {
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
	
	@Test
	public void testCommentEvent() throws ClassNotFoundException, SQLException {
		// test to command st a comment done by a user 
		String info= "1" ;
		Command cmd = new CommentEvent(info);
		boolean boo = myDb.executeDb(cmd); 
		
		ResultSet rs = cmd.getResultSet();
		
		while (rs.next()) {
			//System.out.println(rs.getString(2));
		}
		assertTrue(boo);
	}
	
	@Test
	public void testCancelEvent() throws ClassNotFoundException, SQLException {
		// test verify cancelled event 
		String info= "1" ;
		Command cmd = new CancelEvent(info);
		boolean boo = ((CancelEvent) cmd).cancelQuery(info); 
		assertTrue(boo);	
	}
	
	@Ignore
	public void testremoveSubcriteEvent() throws ClassNotFoundException, SQLException {
		// test for removing cancelled events from list of subscripted events
		String info= "1" ;
		Command cmd = new CancelEvent(info);
		boolean boo = ((CancelEvent) cmd).removeSubcriteEvent(info) ; 
		assertTrue(boo);
		
	}
	
	@Test
	public void testNofifySignedUser() throws ClassNotFoundException, SQLException {
		// test verify if notify list for signed user is well done event 
		String info= "1" ;
		Command cmd = new CancelEvent(info);
		boolean boo = ((CancelEvent) cmd).nofifySignedUser(info); 
		assertTrue(boo);	
	}
	
	@Test
	public void testNofifyAnonymUser() throws ClassNotFoundException, SQLException {
		// test verify if notify list for anonymous user is well done event 
		String info= "1" ;
		Command cmd = new CancelEvent(info);
		boolean boo = ((CancelEvent) cmd).nofifyAnonymUser(info); 
		assertTrue(boo);	
	}
	
		
	@Ignore
	public void testAnonymSubsEvent() throws ClassNotFoundException, SQLException {
		// test verify the subscription of an anonymous user at on event 
		String info= "1" ;
		SubscriteToEvent cmd = new SubscriteToEvent(info);
		boolean boo = cmd.anonymSubsEvent(info); 
		assertTrue(boo);
	}
	
	@Ignore
	public void testSignedUserSubs() throws ClassNotFoundException, SQLException {
		// test verify the subscription of a signed  user at on event 
		String info= "1" ;
		SubscriteToEvent cmd = new SubscriteToEvent(info);
		boolean boo = cmd.signedUserSubs(info); 
		assertTrue(boo);	
	}
	
	@Ignore
	public void testCreateUserAcount() throws ClassNotFoundException, SQLException {
		// test verify creation of user account 
		String info= "1" ;
		Command cmd = new CreateUserAccount(info);
		boolean boo = ((CreateUserAccount) cmd).createNewAccount(info); 
		assertTrue(boo);
	}
	
	//**************  Class Command  OpenSession *************************
	@Test
	public void testOpenSession() throws ClassNotFoundException, SQLException {
		//test opening session by signed user  
		String info = "info" ;
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
	public void testAddNewEvent() throws ClassNotFoundException, SQLException {
		// test verify creation of user account 
		String info= "1" ;
		Command cmd = new AddEvent(info);
		boolean boo = ((AddEvent) cmd).addNewEvent(info); 
		assertTrue(boo);
	}
	
	//**************  Class Command DeleteEvent *************************
	
	@Ignore
	public void testDeleteEvent() throws ClassNotFoundException, SQLException {
		// test verify delete of event by a user
		String info= "1" ;
		Command cmd = new DeleteEvent(info);
		boolean boo = ((DeleteEvent) cmd).removeEvent(info); 
		assertTrue(boo);
	}
	
	//**************  Class Command EditEvent *************************
	@Test
	public void testChangeEventTitle() throws ClassNotFoundException, SQLException {
		// test verify delete of event by a user
		String eventId = "1" ;
		String title = "handball";
		
		Command cmd = new EditEvent(eventId);
		boolean boo = ((EditEvent) cmd).changeEventTitle(eventId ,title); 
		assertTrue(boo);
	}
	
	@Test
	public void testchangeEventDatetime() throws ClassNotFoundException, SQLException {
		// test verify changin  of date in an event 
		String eventId = "1" ;
		String date = "2017-05-15 12:00:00";
		Command cmd = new EditEvent(eventId);
		boolean boo = ((EditEvent) cmd).changeEventDatetime(eventId ,date); 
		assertTrue(boo);
	}
	@Test 
	public void testchangeEventLocation() throws ClassNotFoundException, SQLException {
		// test verify changing  of date in an event 
		String eventId = "1" ;
		String location = "gatineau prairie";
		Command cmd = new EditEvent(eventId);
		boolean boo = ((EditEvent) cmd).changeEventLocation(eventId ,location); 
		assertTrue(boo);
	}
	
	@Test 
	public void testChangeEventPlaces() throws ClassNotFoundException, SQLException {
		// test verify changing  of the maximum number of places  
		String eventId = "1" ;
		String place = "78";
		Command cmd = new EditEvent(eventId);
		boolean boo = ((EditEvent) cmd).changeEventPlaces(eventId ,place); 
		assertTrue(boo);
	}
	
	@Test 
	public void testChangeEventDesc() throws ClassNotFoundException, SQLException {
		// test verify changing  of description 
		String eventId = "1" ;
		String description = "Vraiment la classe venez et vous verrez";
		Command cmd = new EditEvent(eventId);
		boolean boo = ((EditEvent) cmd).changeEventDesc(eventId ,description); 
		assertTrue(boo);
	}
	
	//**************  Class Command ModifYAccount *************************
	@Test
	public void testChangeUserFullName() throws ClassNotFoundException, SQLException {
		// test verify change of user's full name 
		String userId = "1" ;
		String fullname = "blanchard";
		
		Command cmd = new ModifyAccount(userId);
		boolean boo = ((ModifyAccount) cmd).changeUserFullName(userId ,fullname); 
		assertTrue(boo);
	}
	
	@Test
	public void testchangeUserEmail() throws ClassNotFoundException, SQLException {
		// test verify changing  email of user
		String userId = "1" ;
		String email = "blanchar@gla.nu";
		Command cmd = new ModifyAccount(userId);
		boolean boo = ((ModifyAccount) cmd).changeUserEmail(userId ,email); 
		assertTrue(boo);
	}
	@Test 
	public void testchangeUserName() throws ClassNotFoundException, SQLException {
		// test verify changing  of user name
		String userId = "1" ;
		String userName = "blanco";
		Command cmd = new ModifyAccount(userId);
		boolean boo = ((ModifyAccount) cmd).changeUserName(userId ,userName); 
		assertTrue(boo);
	}
	
	@Test 
	public void testChangeUserPassword() throws ClassNotFoundException, SQLException {
		// test verify changing  of password 
		String userId = "1" ;
		String password = "blancson";
		Command cmd = new ModifyAccount(userId);
		boolean boo = ((ModifyAccount) cmd).changeUserPassword(userId ,password); 
		assertTrue(boo);
	}
	
	@Test 
	public void testChangeUserDesc() throws ClassNotFoundException, SQLException {
		// test verify changing  of description 
		String userId = "1" ;
		String description = "Je suis trop cool avec le sport";
		Command cmd = new ModifyAccount(userId);
		boolean boo = ((ModifyAccount) cmd).changeUserDesc(userId ,description); 
		assertTrue(boo);
	}
	
	@Test 
	public void testChangeUserAge() throws ClassNotFoundException, SQLException {
		// test verify changing  of age
		String userId = "1" ;
		String age = "88";
		Command cmd = new ModifyAccount(userId);
		boolean boo = ((ModifyAccount) cmd).changeUserAge(userId,age); 
		assertTrue(boo);
	}
	
	
}
