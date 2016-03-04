package info.juanmendez.introfirebase.model;

/**
 * Created by Juan on 2/28/2016.
 */
public class BlogPost {

    private String author;
    private String title;
    public BlogPost() {
        // empty default constructor, necessary for Firebase to be able to deserialize blog posts
    }
    public String getAuthor() {
        return author;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
