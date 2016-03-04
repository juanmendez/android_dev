package info.juanmendez.introfirebase.model;

/**
 * Created by Juan on 3/2/2016.
 */
public class DinosaurFacts {
    long height;
    long length;
    long weight;

    public DinosaurFacts() {
        // empty default constructor, necessary for Firebase to be able to deserialize blog posts
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }
}
