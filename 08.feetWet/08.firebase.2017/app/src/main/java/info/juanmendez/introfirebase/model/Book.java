package info.juanmendez.introfirebase.model;

/**
 * Created by Juan on 3/5/2016.
 */
public class Book {
    private String title;
    private String author;
    private String shopUrl;

    public Book(String title, String author, String shopUrl) {
        this.title = title;
        this.author = author;
        this.shopUrl = shopUrl;
    }

    public Book() {
        this( "", "", "");
    }

    public String getShopUrl() {
        return shopUrl;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", shopUrl='" + shopUrl + '\'' +
                '}';
    }
}
