package data;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing. table .AbstractTableModel;
/**
 * 
 * @author Danny Dinelli
 *
 */
public class CustomerTable extends AbstractTableModel{
private Connection connection;
private Statement statement;
private ResultSet resultSet;
private ResultSetMetaData metaData;
private int numberOfRows;
private boolean connectedToDatabase = false;
	/**
	 * Default constructor
	 */
	public CustomerTable() throws SQLException{
		connectedToDatabase = true;
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoicedatabase", "root", "123danny");
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	}
	///////////////////////////////////////////////////////////////////////////////////////Insert
	/**
	 * InsertInfo allows the information below to be added to the customer table
	 * @param fName - Allows first name entry into first name field in customer table
	 * @param lName - Allows last name entry into last name field in customer table
	 * @param street - Allows street name entry into street field name in customer table
	 * @param townCity - Allows town/city name entry into TownCity field name in customer table
	 * @param county - Allows county entry into county field name in customer table
	 * @param country - Allows country entry into country field name in customer table
	 * @param email - Allows email address entry into email field name in customer table
	 * @param phone - Allows phone number entry into street field name in customer table
	 */
	public void insertInfo(String fName,String lName,String street,String townCity,String county,String country,String email,String phone)throws SQLException {
		PreparedStatement pstat = connection.prepareStatement("INSERT INTO customer (FirstName,LastName,Street,TownCity,County,Country,email,Phone) VALUES (?,?,?,?,?,?,?,?)");
		pstat . setString (1, fName);
		pstat . setString (2, lName);
		pstat . setString (3, street);
		pstat . setString (4, townCity);
		pstat . setString (5, county);
		pstat . setString (6, country);
		pstat . setString (7, email);
		pstat . setString (8, phone);
		pstat .executeUpdate();
	}
	///////////////////////////////////////////////////////////////////////////////////////////////Retrieve query
	public Class getColumnClass( int column ) throws IllegalStateException {
		if ( connectedToDatabase == false ) throw new IllegalStateException ( "Not Connected toDatabase");
		try{
			String className = metaData.getColumnClassName( column + 1 );// return Class object that represents className
		return Class . forName( className );
		}
		catch ( Exception exception ) {
			exception . printStackTrace () ;
		}
		return Object. class ; // if problems occur above, assume type Object
		}
	
	public int getColumnCount() throws IllegalStateException {
		if (!connectedToDatabase) throw new IllegalStateException ( "Not Connected to Database");
		try {
			return metaData.getColumnCount();
		}
		catch ( SQLException sqlException ){
			sqlException . printStackTrace () ;
		}
		return 0; // if problems occur above, return 0 for number of columns
	}
	
	public String getColumnName( int column ) throws IllegalStateException {
		if (connectedToDatabase == false ) throw new IllegalStateException ("Not Connected to Database");
		try {
		return metaData.getColumnName( column + 1 );
		}
		catch ( SQLException sqlException ){
		sqlException . printStackTrace () ;
		}
		return ""; // if problems, return empty string for column name
	}
	public int getRowCount() throws IllegalStateException {
		if ( !connectedToDatabase ) throw new IllegalStateException ("Not Connected to Database");
		return numberOfRows;
	}	
	
	public Object getValueAt( int row, int column )throws IllegalStateException {
		
		if ( !connectedToDatabase ) throw new IllegalStateException ("Not Connected to Database");
		try {
		resultSet . absolute ( row + 1 );
		return resultSet .getObject( column + 1 );
		}
		catch ( SQLException sqlException ){
		sqlException . printStackTrace () ;
		}
		return ""; // if problems, return empty string object
	}
	/**
	 * Allows query to be made to the database
	 * @param query - Allows query to be sent to SQL
	 */
	public void setQuery( String query ) throws SQLException, IllegalStateException {
		if (connectedToDatabase == false) throw new IllegalStateException ("Not Connected to Database");
		resultSet = statement.executeQuery( query );
		metaData = resultSet.getMetaData();
		resultSet . last () ;
		numberOfRows = resultSet.getRow();
		fireTableStructureChanged();
		}
	///for getting a array of ids
	/**
	 * Method used to retrieve Customer id's from database
	 * @param array - Array used to hold  and retrieved id's from the database
	 * @param j - Used to set start position in array
	 * @return - returns number of id's in array
	 */
	public int getCustomerId(String [] array,int j) throws SQLException {
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoicedatabase", "root", "123danny");
		resultSet = statement.executeQuery("Select CustomerID From Customer");
		while(resultSet.next()) {
			array[j] = resultSet.getString("CustomerID");
			j++;
		}
		return j;
	}
//////////////////////////////////////////////////////////////////////////update
	/**
	 * Method used to update first name in customer database
	 * @param fName - updated first name
	 * @param id - selects which id is to be updated
	 */
	public void updateFName(String fName,int id )throws SQLException {
		PreparedStatement pstat2 = connection.prepareStatement("UPDATE customer SET FirstName= ?WHERE CustomerID = ?");
		pstat2.setString (1, fName);
		pstat2.setInt (2, id);
		pstat2.executeUpdate();
	}
	/**
	 * Method used to update last name in customer database
	 * @param lName - updates last name
	 * @param id - selects which id is to be updated
	 */
	public void updateLName(String lName,int id)throws SQLException {
		PreparedStatement pstat2 = connection.prepareStatement("UPDATE customer SET LastName= ?WHERE CustomerID = ?");
		pstat2.setString (1, lName);
		pstat2.setInt (2, id);
		pstat2.executeUpdate();
	}
	/**
	 * Method used to update street name in customer database
	 * @param newStreet - updates street name
	 * @param id - selects which id is to be updated
	 */
	public void updateStreet(String newStreet,int id)throws SQLException {
		PreparedStatement pstat2 = connection.prepareStatement("UPDATE customer SET Street= ?WHERE CustomerID = ?");
		pstat2.setString (1, newStreet);
		pstat2.setInt (2, id);
		pstat2.executeUpdate();
	}
	/**
	 * Method used to update Town/City name in customer database
	 * @param newTC - Updates Town/City name
	 * @param id - selects which id is to be updated
	 */
	public void updateTownCity(String newTC,int id)throws SQLException {
		PreparedStatement pstat2 = connection.prepareStatement("UPDATE customer SET TownCity= ?WHERE CustomerID = ?");
		pstat2.setString (1, newTC);
		pstat2.setInt (2, id);
		pstat2.executeUpdate();
	}
	/**
	 * Method used to update county name in customer database
	 * @param newCount - Updates county name
	 * @param id - selects which id is to be updated
	 */
	public void updateCounty(String newCount,int id)throws SQLException {
		PreparedStatement pstat2 = connection.prepareStatement("UPDATE customer SET County= ?WHERE CustomerID = ?");
		pstat2.setString (1, newCount);
		pstat2.setInt (2, id);
		pstat2.executeUpdate();
	}
	/**
	 * Method used to update country name in customer database
	 * @param newCountry - Updates country name
	 * @param id - selects which id is to be updated
	 */
	public void updateCountry(String newCountry,int id)throws SQLException {
		PreparedStatement pstat2 = connection.prepareStatement("UPDATE customer SET Country= ?WHERE CustomerID = ?");
		pstat2.setString (1, newCountry);
		pstat2.setInt (2, id);
		pstat2.executeUpdate();
	}
	/**
	 * Method used to update country name in customer database
	 * @param newEmail - Updates email name
	 * @param id - selects which id is to be updated
	 */
	public void updateEmail(String newEmail,int id)throws SQLException {
		PreparedStatement pstat2 = connection.prepareStatement("UPDATE customer SET email= ?WHERE CustomerID = ?");
		pstat2.setString (1, newEmail);
		pstat2.setInt (2, id);
		pstat2.executeUpdate();
	}
	/**
	 *  Method used to update phone number in customer database
	 * @param newPhone - updates phone number
	 * @param id - selects which id is to be updated
	 */
	public void updatePhone(String newPhone,int id)throws SQLException {
		PreparedStatement pstat2 = connection.prepareStatement("UPDATE customer SET Phone= ?WHERE CustomerID = ?");
		pstat2.setString (1, newPhone);
		pstat2.setInt (2, id);
		pstat2.executeUpdate();
	}
	///////////////////////////////////////////////////////////////////////////delete
	/**
	 * Method used to delete entry in customer table
	 * @param value - value used to delete entry
	 */
	public void deleteInfo(int value)throws SQLException {
		PreparedStatement pstat3 = connection.prepareStatement("DELETE FROM customer WHERE CustomerID = ?");
		pstat3.setInt (1, value);
		pstat3.executeUpdate();
	}
/////////////////////////////////////////////////////////////////////////disconnect		
	public void disconnectFromDatabase() {
		if(connectedToDatabase == true) {
			try {
				resultSet.close();
				statement.close();
				connection.close();
			}//end of try
			catch(Exception exception){
				exception.printStackTrace();
			}//end of catch
			finally {
				connectedToDatabase = false;
			}
		}
	}
}