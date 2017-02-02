package info.juanmendez.learn.rx.model;

/**
 * Created by @juanmendezinfo on 2/1/2017.
 */
public class Pet {

    public String name;

    public Pet(String _name){
        name = _name;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "name='" + name + '\'' +
                '}';
    }
}
