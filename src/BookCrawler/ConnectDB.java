package BookCrawler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class ConnectDB {
	static String urlDB= "jdbc:mysql://localhost:3306/bookapp";
	static String user="root";
	static String password="";
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			// TODO: handle exception
		}
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(urlDB,user,password);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	public static void insertBook(Book book) {
		Connection connection = getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("insert into book (bookid, name, author,year, publisher, lprice) Values (?, ?, ?, ?,?, ?)");
			ps.setInt(1, book.getBookid());
			ps.setString(2, book.getName());
			ps.setString(3, book.getAuthor());
			ps.setString(4, book.getYear());
			ps.setString(5, book.getPublisher());
			ps.setDouble(6, book.getLprice());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void insertBookSite(Book book, EcoBook site, Double price, String url) {
		Connection connection = getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("insert into booksite (bookid , siteid, price, url) values (?,?,?,?)");
			ps.setInt(1, book.getBookid());
			ps.setInt(2, site.getSiteid());
			ps.setDouble(3, price );
			ps.setString(4, url);
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
}
