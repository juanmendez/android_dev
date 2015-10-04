package info.juanmendez.android.hellorx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import info.juanmendez.android.hellorx.model.DataGenerator;
import rx.Observable;

/**
 * Created by Juan on 9/18/2015.
 */

@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class, manifest="app/src/main/AndroidManifest.xml", sdk = 21)
public class TestingTransformations
{
    /**
     * Mapping one to one operation... circle to triangle...
     */
    @Test
    public void testOneToOne(){
        writeComment( "one to one transformation");

        // Simple map example...transform every greek letter string
        // into upper case.
        Observable.from(DataGenerator.generateGreekAlphabet())
                .map((letterString) -> {
                    return letterString.toUpperCase();
                })
                .subscribe((letterString) -> {
                    System.out.println(letterString);
                });
    }

    /**
     * FlatMap Operations  One To Many (mapping)
     */
    @Test
    public void testFlatMap(){

        writeComment( "flat Map");
        // flatMap -> Each greek letter is emitted as all upper and
        // all lower case...doubling the output.  One item in the origin
        // list generates multiple items.
        Observable.from(DataGenerator.generateGreekAlphabet())
                .flatMap((letterString) -> {
                    String[] returnStrings;

                    if (letterString.length() > 3) {
                        returnStrings = new String[]{letterString.toUpperCase(),
                                letterString.toLowerCase()
                        };

                    } else {
                        returnStrings = new String[]{"__" + letterString.toLowerCase()};
                    }

                    return Observable.from(returnStrings);
                })
                .subscribe((letterString) -> {
                    System.out.println(letterString);
                });
    }

    /**
     * scanning (previous + next event ) like fibonacci..
     */
    @Test
    public void testScanning(){
        writeComment( "scanning and emitting each iteration");
        Observable.from(DataGenerator.generateGreekAlphabet())
                .scan(new StringBuilder(), (accumBuffer, nextLetter) -> {

                    return accumBuffer.append(nextLetter);
                })
                .subscribe((total) -> {
                    System.out.println("Scan Event: " + total.toString());
                });
    }

    @Test
    public void testScanningToLast(){
        writeComment( "scanning through but emit only last!");
        Observable.from(DataGenerator.generateGreekAlphabet())
                .scan(new StringBuilder(), (accumBuffer, nextLetter) -> {

                    return accumBuffer.append(nextLetter);
                })
                .last()
                .subscribe((total) -> {
                    System.out.println("Scan Event: " + total.toString());
                });
    }


    /**
     * it is able to iterate and break a list into groups..
     */
    @Test
    public void testGroupBy(){
        writeComment("test group by");

        ArrayList<Integer> even = new ArrayList<Integer>();
        ArrayList<Integer> odd = new ArrayList<Integer>();
        ArrayList<Integer> three = new ArrayList<Integer>();
        Observable.from(DataGenerator.generateBigIntegerList())
                // Group based on odd or even using a string literal
                // for the key
                .groupBy((i) -> {
                    if( i % 3 == 0 )
                        return "THREE";

                    return 0 == (i % 2) ? "EVEN" : "ODD";
                })
                        // Subscribe to the Observable< GroupedObservable<String,Integer> >
                .subscribe((groupList) -> {

                    // For each group, spit out the key
                    System.out.println("Key: " + groupList.getKey() + " ------------------------");

                    // And subscribe to the items in the list...print them
                    // out with their key.
                    groupList.subscribe((x) -> {

                        if (groupList.getKey() == "EVEN") {
                            even.add(x);
                        } else if (groupList.getKey() == "ODD") {
                            odd.add(x);
                        } else if (groupList.getKey() == "THREE") {
                            three.add(x);
                        }
                    }, throwable -> {
                    }, () -> {

                        if (groupList.getKey() == "EVEN") {
                            writeComment("even numbers");
                            for (Integer e : even) {
                                System.out.println(e);
                            }
                        } else if (groupList.getKey() == "ODD") {
                            writeComment("odd numbers");
                            for (Integer e : odd) {
                                System.out.println(e);
                            }
                        } else if (groupList.getKey() == "THREE") {
                            writeComment("three numbers");
                            for (Integer e : three) {
                                System.out.println(e);
                            }
                        }
                    });

                });
    }

    public static void writeComment( String comment ){
        System.out.println( "----------------------------------------------------");
        System.out.println( comment );
        System.out.println("----------------------------------------------------\n");
    }

}
