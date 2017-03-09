import info.juanmendez.learn.rx.model.Pet;
import io.reactivex.*;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subscribers.ResourceSubscriber;
import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Created by musta on 3/8/2017.
 */

public class TestRx2 {

    @Test
    public void singleDemo(){
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

    @Test
    public void flowableDemo(){
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
    @Test
    public void disposingDemo(){

        ResourceSubscriber<Integer> naughtySubscriber = new ResourceSubscriber<Integer>() {
            public void onNext(Integer integer) {

                //I just change my mind and would like to stop
                if( integer == 6 ){
                    dispose();
                }

                log( "naughtySubscriber.onNext " + integer );
            }

            public void onError(Throwable t) {
                log( "naughtySubscriber.error " + t.getMessage() );
            }

            public void onComplete() {
                log( "naughtySubscriber.completed" );
            }
        };


        ResourceSubscriber<Integer> goodSubscriber = new ResourceSubscriber<Integer>() {
            public void onNext(Integer integer) {
                log( "goodSubscriber.integer " + integer );
            }

            public void onError(Throwable t) {
                log( "goodSubscriber.error " + t.getMessage() );
            }

            public void onComplete() {
                log( "goodSubscriber.completed" );
            }
        };

        Flowable<Integer> f = Flowable.just( 1, 2,3, 4, 5, 6, 7, 8, 9, 10 );
        f.subscribe( naughtySubscriber );
        f.subscribe( goodSubscriber );
    }

    /**
     * one of my favorite observers has been PublishSubject, and BehaviorSubject
     */
    @Test
    public void publishDemo(){
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
    @Test
    public void maybeDemo(){
        Maybe<Integer> maybe = Maybe.create(new MaybeOnSubscribe<Integer>() {
            public void subscribe(MaybeEmitter<Integer> e) throws Exception {

                //e.onSuccess(1);
                //e.onComplete();
                throw new Exception("I feel like just revolting against RX");
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
    @Test
    public void mapDemo(){

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

    public void log( Object output ){
        System.out.println( output );
    }
}
