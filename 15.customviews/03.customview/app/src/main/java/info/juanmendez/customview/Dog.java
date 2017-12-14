package info.juanmendez.customview;

/**
 * Created by juan on 12/14/17.
 */

public class Dog {
    private static Dog ourSingletonDog;


    public static Dog getOurSingletonDog() {
        if( ourSingletonDog == null )
            ourSingletonDog = new Dog();

        return ourSingletonDog;
    }
}
