
package BookCrawler;

import java.util.Date;


public class Book {
    private int bookid;
    private String name;
    private String author;
    private String year;
    private String publisher;
    private double lprice;

    public Book(int bookid, String name, String author, String year, String publisher, double lprice) {
        this.bookid = bookid;
        this.name = name;
        this.author = author;
        this.year = year;
        this.publisher = publisher;
        this.lprice = lprice;
    }
    
    public Book() {}

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public double getLprice() {
        return lprice;
    }

    public void setLprice(double lprice) {
        this.lprice = lprice;
    }

    @Override
    public String toString() {
        return "Book{" + "bookid=" + bookid + ", name=" + name + ", author=" + author + ", year=" + year + ", publisher=" + publisher + ", lprice=" + lprice + '}';
    }
    
    
}