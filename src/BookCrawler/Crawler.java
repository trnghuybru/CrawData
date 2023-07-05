package BookCrawler;



import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {
	public void crawlinks(String url) {
		ArrayList<String> arrayLinks = new ArrayList<String>();
		try {
			Document document = Jsoup.connect(url).get();
			Elements links1 = document.select("ul#products_grid h2.product-name-no-ellipsis a");
			for (Element link: links1) {
				String text = link.attr("href");
				arrayLinks.add(text);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int j=0;j<arrayLinks.size();j++) {
			crawFahasa(arrayLinks.get(j));
		}
	}
	
	public void crawTiki(String url) {
		Book book = new Book();
		EcoBook ecoBook = new EcoBook(222, "Tiki","https://tiki.vn/sach-ky-nang-song/c870" );
		double price;
		try {
			Document document = Jsoup.connect(url).get();
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void crawFahasa(String url) {
		Book book = new Book();
		EcoBook sitEcoBook = new EcoBook(111, "Fahasa", "https://www.fahasa.com/sach-trong-nuoc/tam-ly-ky-nang-song.html");
		double price;
		try {
			Document document = Jsoup.connect(url).get();
			
			book.setBookid(randomID());
			
			Elements elementsName = document.select("h1");
			if (!elementsName.isEmpty()) {
				String textName = elementsName.text();
				book.setName(textName);
			}else {
				book.setName("");
			}
			
			
			
			Elements elementsXB =  document.select("div.product-view-sa_two div.product-view-sa-supplier span");
			if (!elementsXB.isEmpty()) {
				Element textXB = elementsXB.get(1);
				book.setPublisher(textXB.text());
			}else {
				book.setPublisher("");
			}
			
			
			Elements elementsTG =  document.select("div.product-view-sa_one div.product-view-sa-author span");
			if (!elementsTG.isEmpty()) {
				Element textTG = elementsTG.get(1);
				String author = textTG.text();
				book.setAuthor(author);
			}else {
				book.setAuthor("");
			}
			
			
			Elements elementsYear =  document.select("td.data_publish_year");
			if (!elementsYear.isEmpty()) {
				String textYear = elementsYear.text();
				book.setYear(textYear);
			}else {
				book.setYear("");
			}
			
			Elements elementsPrice1 =  document.select("p.special-price span.price");
			String textPr1 = elementsPrice1.text();
			if (!textPr1.isEmpty()) {
				price = convertVndToDouble(textPr1);
			}else {
				price = 0;
			}
			
			
			Elements elementsPrice =  document.select("p.old-price span.price");
			if(!elementsPrice.isEmpty()) {
				String textPr = elementsPrice.text();
				Double price1 = convertVndToDouble(textPr);
				book.setLprice(price1);
			}else {
				book.setLprice(price);
				
			}
			
			
			
			
			
			
			ConnectDB.insertBook(book);
			ConnectDB.insertBookSite(book, sitEcoBook , price);
			System.out.println(book);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	public void storeXML() {
//		//Connect
//		Connection connection = ConnectDB.getConnection();
//		try {
//			Statement statement = connection.createStatement();
//			String querry = "SELECT * FROM sach";
//			ResultSet resultSet = statement.executeQuery(querry);
//			
//			//Tao XML
//			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//			org.w3c.dom.Document document = dBuilder.newDocument();
//			org.w3c.dom.Element rootElement = document.createElement("books");
//			document.appendChild(rootElement);
//			
//			while (resultSet.next()) {
//				org.w3c.dom.Element book = document.createElement("book");
//                rootElement.appendChild(book);
//
//                org.w3c.dom.Element name = document.createElement("name");
//                name.appendChild(document.createTextNode(resultSet.getString("Name")));
//                book.appendChild(name);
//
//                org.w3c.dom.Element ncc = document.createElement("nhacungcap");
//                ncc.appendChild(document.createTextNode(resultSet.getString("NhaCungCap")));
//                book.appendChild(ncc);
//                
//                org.w3c.dom.Element nxb = document.createElement("nhaxuatban");
//                nxb.appendChild(document.createTextNode(resultSet.getString("NhaXuatBan")));
//                book.appendChild(nxb);
//                
//                org.w3c.dom.Element author = document.createElement("tacgia");
//                author.appendChild(document.createTextNode(resultSet.getString("TacGia")));
//                book.appendChild(author);
//                
//                org.w3c.dom.Element price = document.createElement("gia");
//                price.appendChild(document.createTextNode(resultSet.getString("Gia")));
//                book.appendChild(price);
//			}
//			
//			//Ghi tai lieu vao File
//			File file = new File("book.xml");
//			FileWriter fWriter = new FileWriter(file);
//			TransformerFactory tFactory = TransformerFactory.newInstance();
//			Transformer transformer = tFactory.newTransformer();
//			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//			transformer.transform(new DOMSource(document), new StreamResult(fWriter));
//			fWriter.close();
//			
//			resultSet.close();
//			statement.close();
//			connection.close();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ParserConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (TransformerConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (TransformerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
	
//	public static int getIDByNameBook(String name) {
//		String[] nameStrings = name.split(" ");
//		String sql1= "SELECT bookid FROM book WHERE ";
//		int id;
//		try {
//			Connection connection = ConnectDB.getConnection();
//			Statement statement = connection.createStatement();
//			ResultSet rs = statement.executeQuery(sql1);
//			
//		}
//		catch (Exception e) {
//			// TODO: handle exception
//			e.printSta8-9
//		
//	}
	
	public static double convertVndToDouble(String vnd) {
	    String[] parts = vnd.split(" ");
	    String numberPart = parts[0];
	    String valueString = numberPart.replace(".", "");
	    double value = Double.parseDouble(valueString);
	    return value;
	}
	public static void main(String[] args) {
		Crawler crawler = new Crawler();
		for (int i=1;i<=195;i++) {
			String linkString = "https://www.fahasa.com/sach-trong-nuoc/tam-ly-ky-nang-song.html?order=num_orders&limit=48&p="+i+"";
			crawler.crawlinks(linkString);
		}
		
		System.out.println("ok");
	}
	
	public static int randomID() {
		boolean check=false;
		double randomDouble;
		int random;
		do {
			check=false;
			randomDouble = Math.random();
			randomDouble = randomDouble*9999+1;
			random = (int)randomDouble;
			String sql1= "SELECT bookid FROM book ";
			try {
				Connection connection = ConnectDB.getConnection();
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(sql1);
				while (rs.next()) {
					if (rs.getInt(1)==random) {
						check=true;
					}
				}
				statement.close();
				rs.close();
				connection.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		} while (check==true);
		return random;
	}
}
