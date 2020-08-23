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
public class InvoiceTable extends AbstractTableModel{
private Connection connection;
private Statement statement;
private ResultSet resultSet;
private ResultSetMetaData metaData;
private int numberOfRows;
private boolean connectedToDatabase = false;
/**
 * Default Constructor
 */
	public  InvoiceTable() throws SQLException{
		connectedToDatabase = true;
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoicedatabase", "root", "123danny");
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	}//end of constructor
///////////////////////////////////////////////////////////////////////////////////////Insert
	/**
	 * InsertInfo allows the information below to be added to the invoice table
	 * @param orderDate - Allows order date entry into order date field in invoice table
	 * @param subTotal - Allows sub total entry into sub total field in invoice table 
	 * @param discount - Allows discount entry into discount field in invoice table
	 * @param total - Allows total entry into total field in invoice table
	 * @param orderID -Allows order id entry into order id field in invoice table
	 * @param productID - Allows product id entry into product id field in invoice table
	 */
	public void insertInfo(String orderDate,double subTotal,double discount,double total,int orderID,int productID)throws SQLException {
		System.out.println("*" + orderID);
	PreparedStatement pstat = connection.prepareStatement("INSERT INTO invoicedatabase.invoice (orderDate,subTotal,discount,total,order_idorder,product_idproduct) VALUES (?,?,?,?,?,?)");
	pstat .setString  (1, orderDate);
	pstat . setDouble (2, subTotal);
	pstat . setDouble (3, discount);
	pstat.setDouble(4, total);
	pstat.setDouble(5, orderID);
	pstat.setDouble(6, productID);
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
	/////get order id
	/**
	 * Method used to retrieve order id's from database
	 * @param array - Array used to hold  and retrieved id's from the database
	 * @param j -  Used to set start position in array
	 * @return - returns number of id's in array
	 */
	public int getOrderId(String [] array,int j) throws SQLException {
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoicedatabase", "root", "123danny");
		resultSet = statement.executeQuery("Select idorder From invoicedatabase.order");
		while(resultSet.next()) {
		
			array[j] = resultSet.getString("idorder");
			j++;
		}
		return j;
	}
	//get product id
	/**
	 * Method used to retrieve product id's from database
	 * @param array - Array used to hold  and retrieved id's from the database
	 * @param j - Used to set start position in array
	 * @return - returns number of id's in array
	 */
	public int getProductId(String [] array,int j) throws SQLException {
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoicedatabase", "root", "123danny");
		resultSet = statement.executeQuery("Select idproduct From invoicedatabase.product");
		while(resultSet.next()) {
			array[j] = resultSet.getString("idproduct");
				j++;
			}
		return j;
		}
	/**
	 *  Method used to retrieve invoice id's from database
	 * @param array - Array used to hold  and retrieved id's from the database
	 * @param j - Used to set start position in array
	 * @return - returns number of id's in array
	 */
	public int getInvoiceId(String [] array,int j) throws SQLException {
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoicedatabase", "root", "123danny");
		resultSet = statement.executeQuery("Select idinvoice From invoice");
		while(resultSet.next()) {
		
			array[j] = resultSet.getString("idinvoice");
			j++;
		}
		return j;
	}
//////////////////////////////////////////////////////////////////////////update
	/**
	 * Method used to update all fields name in invoice database
	 * @param sub - updates sub total
	 * @param dis - updates discount
	 * @param total - updates total
	 * @param id - selects id to update
	 */
	public void updateAll(double sub,double dis,double total,int id )throws SQLException {
		PreparedStatement pstat2 = connection.prepareStatement("UPDATE invoice SET subTotal= ?,discount= ?,total = ?WHERE idinvoice = ?");
		pstat2.setDouble (1, sub);
		pstat2.setDouble (2, dis);
		pstat2.setDouble (3, total);
		pstat2.setDouble (4, id);
		pstat2.executeUpdate();
	}	
	/**
	 *  Method used to delete entry in invoice table
	 * @param value - value used to delete entry 
	 */
///////////////////////////////////////////////////////////////////////////delete
	public void deleteInfo(int value)throws SQLException {
	PreparedStatement pstat3 = connection.prepareStatement("DELETE FROM invoice WHERE idinvoice= ?");
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
}//end of Invoice Table
