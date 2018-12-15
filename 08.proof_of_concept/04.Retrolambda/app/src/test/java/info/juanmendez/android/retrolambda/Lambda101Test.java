package info.juanmendez.android.retrolambda;

import com.android.internal.util.Predicate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import info.juanmendez.android.retrolambda.interfaces.InterfaceWithArgs;
import info.juanmendez.android.retrolambda.interfaces.PersonInterface;
import info.juanmendez.android.retrolambda.interfaces.SimpleInterface;
import info.juanmendez.android.retrolambda.model.Person;

@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class, manifest="app/src/main/AndroidManifest.xml", sdk = 21)
public class Lambda101Test {

    static{
        ShadowLog.stream = System.out;
    }

    @Test
    public void testLambda(){

        //anonymous class
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.print("hello!");
            }
        }).start();


        /**
         * Lambda expressions can only appear in places where they will be assigned to a variable whose
         * type is a functional interface.
         *
         * A functional interface has a single abstract method
         */
        Runnable r = ()->Log.print("hello Lambda!");
        new Thread(r).start();

    }

    @Test
    public void useSimpleInterface(){
        SimpleInterface obj = ()->Log.print("Say something");
        obj.doSomething();
    }

    @Test
    public void useInterfaceWithArgs(){
        InterfaceWithArgs obj = (value1, value2) -> {
            Log.print( "multiplication " + (value1 * value2 ) );
        };

        obj.calculate(10, 20);
    }

    @Test
    public void testBuiltInterfaces(){

        //rather than creating two runnables.. we are going to use the lambda way..
        //the lambda way is below, and both work the same exept one has shorter code..

        Runnable r1 = ()->Log.print("Runnint thread 1");
        Runnable r2 = ()->Log.print("Runnint thread 2");

        //multiple lines.. then requires a code block.
        Runnable r3 = ()->{

            try {
                Thread.sleep( 10000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Log.print("Running thread 3");
        };

        new Thread(r1).start();
        new Thread(r2).start();
        new Thread(r3).start();
    }

    @Test
    public void builtInterfaces(){

        List<String> strings = new ArrayList<String>();
        strings.add("AAA");
        strings.add("bbb");
        strings.add("CCC");
        strings.add("ddd");
        strings.add("EEE");

        //Simple case-sensitive sort operation
        Collections.sort(strings);
        System.out.println("Simple sort");
        for(String str: strings){
            System.out.println(str);
        }

        /*
        Collections.sort(strings, new Comparator<String>() {
            @Override
            public int compare(String str1, String str2) {
                return str1.compareToIgnoreCase(str2);
            }
        });**/

        //Case-insensitive sort with an anonymous class
        /*
        Comparator<String> comp = (str1, str2)->{
            return str1.compareToIgnoreCase(str2);
        };*/

        //second argument could have been comp.. but I replaced it.
        Collections.sort(strings, (str1, str2) -> {
            return str1.compareToIgnoreCase(str2);
        });

        System.out.println("Sort with comparator");
        for(String str: strings){
            Log.print(str);
        }
    }

    @Test
    public void traversingCollection(){
        List<String> strings = new ArrayList<String>();
        strings.add("AAA");
        strings.add("bbb");
        strings.add("CCC");
        strings.add("ddd");
        strings.add("EEE");

        Collections.sort(strings);

        // Traverse with for:each
        /**
         * not possible
        strings.forEach((String str) -> {
            System.out.println(str);
        });**/
    }

    @Test
    public void predicateInterfaces(){
        List<Person> people = new ArrayList<>();

        people.add(new Person("Joe", 48));
        people.add(new Person("Mary", 30));
        people.add(new Person("Mike", 73));

        /**
        Predicate<Person> pred = new Predicate<Person>() {
            @Override
            public boolean apply(Person person) {
                return (person.getAge()>=65);
            }
        };**/

        Predicate<Person> pred = person -> (person.getAge()>=65);
        Predicate<Person> predYoung = person -> (person.getAge()<65);

        displayPeople(people, pred);
        displayPeople(people, predYoung);

        //extract method created the method displayPeople
        /**
         for(Person person: people ){
         if( pred.apply(person)){
         System.out.println( person );
         }
         */
    }

    private void displayPeople(List<Person> people, Predicate<Person> pred) {
        for(Person person: people ){
            if( pred.apply(person)){
                System.out.println( person );
            }
        }
    }


    @Test
    //method enhacement only
    public void staticMethodReference()
    {
        List<Person> people = new ArrayList<>();

        people.add(new Person("Joe", 48));
        people.add(new Person("Mary", 30));
        people.add(new Person("Mike", 73));

        Collections.sort( people, this::compareAges );

        for( Person p : people ){
            Log.print( p.toString() );
        }
    }

    private int compareAges( Person p1, Person p2 ){
        return ((Integer)p1.getAge()).compareTo(p2.getAge() );
    }

    @Test
    //method enhacement only
    public void testDefaultInterfaceMethod(){
        List<Person> people = new ArrayList<>();

        people.add(new Person("Joe", 48));
        people.add(new Person("Mary", 30));
        people.add(new Person("Mike", 73));


        for( Person p : people ){
            Log.print( p.getPersonInfo() );
        }
    }

    @Test
    //method enhacement only
    public void testInterfaceStaticMethods(){
        List<Person> people = new ArrayList<>();

        people.add(new Person("Joe", 48));
        people.add(new Person("Mary", 30));
        people.add(new Person("Mike", 73));

        for( Person p : people ){
            Log.print(PersonInterface.getPersonalInfo(p));
        }
    }
}