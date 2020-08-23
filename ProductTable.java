package data;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing. table .AbstractTableModel;
public class ProductTable extends AbstractTableModel{
private Connection connection;
private Statement statement;
private ResultSet resultSet;
private ResultSetMetaData metaData;
private int numberOfRows;
private boolean connectedToDatabase = false;

	public ProductTable() throws SQLException{
		connectedToDatabase = true;
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoicedatabase", "root", "123danny");
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	}
///////////////////////////////////////////////////////////////////////////////////////Insert
	public void insertInfo(double unit,double sale,int stock,String author,String publish,int isbn,String title)throws SQLException {
		PreparedStatement pstat = connection.prepareStatement("INSERT INTO product (unitPrice,salePrice,stock,author,publisher,ISBN,title) VALUES (?,?,?,?,?,?,?)");
		pstat . setDouble (1, unit);
		pstat . setDouble (2, sale);
		pstat . setInt (3, stock);
		pstat . setString (4, author);
		pstat . setString (5, publish);
		pstat . setInt (6, isbn);
		pstat . setString (7, title);
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

	public void setQuery( String query ) throws SQLException, IllegalStateException {
		if (connectedToDatabase == false) throw new IllegalStateException ("Not Connected to Database");
			resultSet = statement.executeQuery( query );
			metaData = resultSet.getMetaData();
			resultSet . last () ;
			numberOfRows = resultSet.getRow();
			fireTableStructureChanged();
		}
	//get product id
		public int getProductId(String [] array,int j) throws SQLException {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoicedatabase", "root", "123danny");
			resultSet = statement.executeQuery("Select idproduct From invoicedatabase.product");
			while(resultSet.next()) {
			
				array[j] = resultSet.getString("idproduct");
				j++;
			}
			return j;
		}
//////////////////////////////////////////////////////////////////////////update
	public void updateTitle(String title,int id )throws SQLException {
		PreparedStatement pstat2 = connection.prepareStatement("UPDATE product SET title= ? WHERE idproduct= ?");
		pstat2.setString (1, title);
		pstat2.setInt (2, id);
		pstat2.executeUpdate();
	}
	public void updateAuthor(String author,int id )throws SQLException {
		PreparedStatement pstat2 = connection.prepareStatement("UPDATE product SET author=? WHERE idproduct=?");
		pstat2.setString (1, author);
		pstat2.setInt (2, id);
		pstat2.executeUpdate();
	}
	public void updatePublisher(String publish,int id )throws SQLException {
		PreparedStatement pstat2 = connection.prepareStatement("UPDATE product SET publisher= ? WHERE idproduct= ?");
		pstat2.setString (1, publish);
		pstat2.setInt (2, id);
		pstat2.executeUpdate();
	}//when statement not working make new pstat variable
	public void updateISBN(int isbn,int id )throws SQLException {
		PreparedStatement pstat4 = connection.prepareStatement("UPDATE product SET ISBN = ? WHERE idproduct = ?");
		pstat4.setInt (1, isbn);
		pstat4.setInt (2, id);
		pstat4.executeUpdate();
	}
	public void updateUPrice(double unit,int id)throws SQLException {
		PreparedStatement pstat4 = connection.prepareStatement("UPDATE invoicedatabase.product SET unitPrice= ? WHERE idproduct = ?");
		pstat4.setDouble (1, unit);
		pstat4.setInt (2, id);
		pstat4.executeUpdate();
	}
	public void updateSaleP(double sale,int id)throws SQLException {
		PreparedStatement pstat2 = connection.prepareStatement("UPDATE product SET salePrice= ? WHERE idproduct = ?");
		pstat2.setDouble (1, sale);
		pstat2.setInt (2, id);
		pstat2.executeUpdate();
	}
	public void updateStock(int stock,int id)throws SQLException {
		PreparedStatement pstat4 = connection.prepareStatement("UPDATE product SET stock= ? WHERE idproduct = ?");
		pstat4.setInt (1, stock);
		pstat4.setInt (2, id);
		pstat4.executeUpdate();
	}
///////////////////////////////////////////////////////////////////////////delete
	public void deleteInfo(int value)throws SQLException {
		PreparedStatement pstat3 = connection.prepareStatement("DELETE FROM product WHERE idproduct = ?");
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
		}//end of if
	}//end of disconnect method
}//end of product table
