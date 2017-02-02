package info.juanmendez.learn.rx;

import info.juanmendez.learn.rx.model.Pet;
import io.reactivex.*;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subscribers.ResourceSubscriber;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Created by @juanmendezinfo on 1/28/2017.
 */
public class Rx2Intro {

    public static void main(final String[] args ){

    }

    public static void singleDemo(){
        Single.just( 1 ).subscribe(new SingleObserver<Integer>() {
            public void onSubscribe(Disposable d) {
                log( "subscribe");
            }

            public void onSuccess(Integer integer) {
                log( "just completed " + integer );
            }

            public void onError(Throwable e) {
                log( "error " + e.getMessage() );
            }
        });
    }

    /**
     * no emmits, just complete|error
     */
    public static void completableDemo(){
        Completable completable = Completable.create(new CompletableOnSubscribe() {
            public void subscribe(CompletableEmitter e) throws Exception {
                e.onComplete();
            }

        }).subscribeOn(Schedulers.io());

        completable.subscribe(new CompletableObserver() {
                                  public void onSubscribe(Disposable d) {
                                      log( "on subscribe" );
                                  }

                                  public void onComplete() {
                                      log( "on onComplete" );
                                  }

                                  public void onError(Throwable e) {
                                      log( "on onError " + e.getMessage() );
                                  }
                              }

        );
    }

    public static void flowableDemo(){
        Flowable.just(1,2,3,4,5).subscribe(new Subscriber<Integer>() {
            public void onSubscribe(Subscription s) {
                //how many do you want? if you don't know and want all, you can go with Integer.MAX_VALUE
                s.request( 2 );
            }

            public void onNext(Integer integer) {
                log( "integer " + integer );
            }

            public void onError(Throwable t) {
                log( "error! " + t.getMessage() );
            }

            public void onComplete() {
                log( "completed!" );
            }
        });
    }

    /**
     * Naughty Subscriber, wants to stop listening when it reaches a value equal to six.
     * Good Subscriber, listens through!
     */
    public static void disposingDemo(){

        ResourceSubscriber<Integer> naughtySubscriber = new ResourceSubscriber<Integer>() {
            public void onNext(Integer integer) {

                //I just change my mind and would like to stop
                if( integer == 6 ){
                    dispose();
                }

                log( "integer " + integer );
            }

            public void onError(Throwable t) {
                log( "error! " + t.getMessage() );
            }

            public void onComplete() {
                log( "completed!" );
            }
        };

        ResourceSubscriber<Integer> goodSubscriber = new ResourceSubscriber<Integer>() {
            public void onNext(Integer integer) {
                log( "integer " + integer );
            }

            public void onError(Throwable t) {
                log( "error! " + t.getMessage() );
            }

            public void onComplete() {
                log( "completed!" );
            }
        };

        Flowable<Integer> f = Flowable.just( 1, 2,3, 4, 5, 6, 7, 8, 9, 10 );
        f.subscribe( naughtySubscriber );
        f.subscribe( goodSubscriber );
    }

    /**
     * one of my favorite observers has been PublishSubject, and BehaviorSubject
     */
    public static void publishDemo(){
        PublishSubject<Integer> ps = PublishSubject.create();
        DisposableObserver<Integer> subscriber = new DisposableObserver<Integer>() {
            public void onNext(Integer integer) {

                //I just change my mind and would like to stop
                if( integer == 6 ){
                    dispose();
                }

                log( "integer " + integer );
            }

            public void onError(Throwable t) {
                log( "error! " + t.getMessage() );
            }

            public void onComplete() {
                log( "completed!" );
            }
        };


        ps.subscribe(subscriber);
        ps.subscribe(new DisposableObserver<Integer>() {
            public void onNext(Integer integer) {
                log( "anonymous subscriber" + integer );
            }

            public void onError(Throwable e) {

            }

            public void onComplete() {
                log( "anonymous subscriber completed!" );
            }
        });

        ps.onNext(1);
        ps.onNext(1);
        ps.onNext(2);
        ps.onNext(6);
        ps.onNext(4);
        ps.onComplete();
    }

    /**
     * Either it commits to emit a single value, completes without ever emitting, or simply throws an error.
     * You cannot do complete after emitting first.
     */
    public static void maybeDemo(){
        Maybe<Integer> maybe = Maybe.create(new MaybeOnSubscribe<Integer>() {
            public void subscribe(MaybeEmitter<Integer> e) throws Exception {

                e.onSuccess(1);
                //e.onComplete();
                //throw new Exception("I feel like just revolting against RX");
            }
        });


        maybe.subscribe(new DisposableMaybeObserver<Integer>() {
            public void onSuccess(Integer integer) {
                log( "success: " + integer );
            }

            public void onError(Throwable e) {
                log( "error: " + e.getMessage() );
            }

            public void onComplete() {
                log( "completed!" );
            }
        });
    }

    /**
     * not just subscribe but make the subscription catch pets not strings!
     */
    public static void mapDemo(){

        Flowable.just( "Felipe", "Chino", "Manchas", "Linda", "Amelia" )
                .map(new Function<String, Pet>() {
                    public Pet apply(String s) throws Exception {
                        return new Pet( s );
                    }
                }).subscribe(new Subscriber<Pet>() {
                    public void onSubscribe(Subscription s) {
                        s.request( Integer.MAX_VALUE );
                    }

                    public void onNext(Pet pet) {
                        log( "next " + pet );
                    }

                    public void onError(Throwable t) {
                        log( "pet error " + t.getMessage() );
                    }

                    public void onComplete() {
                        log( "pets completed!" );
                    }
                });
    }

    public static void log( Object output ){
        System.out.println( output );
    }
}
