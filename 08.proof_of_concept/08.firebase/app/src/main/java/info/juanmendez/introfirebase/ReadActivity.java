package info.juanmendez.introfirebase;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import info.juanmendez.introfirebase.model.BlogPost;
import info.juanmendez.introfirebase.model.DinosaurFacts;

/**
 * Created by Juan on 2/28/2016.
 *
 * Notes:
 * Order by has no descending order.
 * They have made it so that we can get the top and bottom items using ascending order
 * So if we want the dinosour with less weight we will order by weight and then
 *
 * Query queryRef = ref.orderByChild("height").limitToFirst(1); //lightest
 * Query queryRef = ref.orderByChild("height").limitToLast(1); //heaviest
 *
 * Instead of WHERE weight = 500 we do tit this way
 * ref.orderByChild("weight").equalTo(500)
 *
 */
@EActivity(R.layout.activity_main)
public class ReadActivity extends AppCompatActivity {

    Firebase posts, dinosaurs;
    private static final String TAG = "Firebase/ReadData";

    @AfterViews
    void afterViews(){

        //at App level:Firebase.setAndroidContext(getApplicationContext());
        posts =  new Firebase(BuildConfig.UNIQUE_FIREBASE_ROOT_URL + "/posts");

        //readChildEvents();
        //writePosts();
        //readPosts();
        //readOnce();

        //writeDinosaurs();
        //readOrderedByHeight();
        //readOrderedByDimensionsHeight();
        readOrderedByKey();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    private void writePosts(){
        BlogPost blog = new BlogPost();
        blog.setAuthor("Kurt Vonnegut");
        blog.setTitle("Breakfast for Champions");
        addPost(blog );

        blog = new BlogPost();
        blog.setAuthor("David Foster Wallace");
        blog.setTitle("Infinite Jest");
        addPost(blog);
    }


    private void writeDinosaurs() {
        DinosaurFacts d = new DinosaurFacts();
        d.setHeight(2);
        d.setLength(12);
        d.setWeight(5000);

        Firebase dino = new Firebase(BuildConfig.UNIQUE_FIREBASE_ROOT_URL + "/dinosaurs/lambeosaurus" );
        dino.setValue(d);

        d = new DinosaurFacts();
        d.setHeight(4);
        d.setLength(9);
        d.setWeight(2500);

        dino = new Firebase(BuildConfig.UNIQUE_FIREBASE_ROOT_URL + "/dinosaurs/stegosaurus" );
        dino.setValue(d);
    }

    private void readOrderedByHeight(){

        Firebase dinosaurs =  new Firebase(BuildConfig.UNIQUE_FIREBASE_ROOT_URL + "/dinosaurs");
        Query queryRef = dinosaurs.orderByChild("height");
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                DinosaurFacts facts = snapshot.getValue(DinosaurFacts.class);
                Log.i(TAG, snapshot.getKey() + " was " + facts.getHeight() + " meters tall");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    private void readOrderedByKey(){

        Firebase dinosaurs =  new Firebase(BuildConfig.UNIQUE_FIREBASE_ROOT_URL + "/dinosaurs");
        Query queryRef = dinosaurs.orderByKey();

        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                DinosaurFacts facts = snapshot.getValue(DinosaurFacts.class);
                Log.i( TAG, snapshot.getKey() + " was " + facts.getHeight() + " meters tall");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
    }


    private void readOrderedByDimensionsHeight(){

        Firebase dinosaurs =  new Firebase(BuildConfig.UNIQUE_FIREBASE_ROOT_URL + "/dinosaurs");
        Query queryRef = dinosaurs.orderByChild("dimensions/height");
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                DinosaurFacts facts = snapshot.getValue(DinosaurFacts.class);
                Log.i( TAG, snapshot.getKey() + " was " + facts.getHeight() + " meters tall");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
    }

    private void addPost(BlogPost blog) {

        posts.push().setValue(blog, (firebaseError, firebase) -> {
            if (firebaseError != null) {
                Log.i(TAG, "Data could not be saved. " + firebaseError.getMessage());
            } else {
                Log.i(TAG, "Data saved successfully.");
            }
        });
    }

    /**
     * from Javascript documentation I can also tell count is based
     * on reading and getting snapshot.getChildrenCount()
     */
    private void readPosts(){

        posts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.i(TAG, "There are " + snapshot.getChildrenCount() + " blog posts");

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    //how great to map a DataSnapshot into a BlogPost
                    BlogPost post = postSnapshot.getValue(BlogPost.class);
                    Log.i(TAG, post.getAuthor() + " - " + post.getTitle());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.i(TAG, "The read failed: " + firebaseError.getMessage());
            }
        });
    }

    /**
     * I was able to go to https://essentials-220.firebaseio.com/ and removed duplicates
     * They were removed but also the handler below got
     * onChildRemoved Author: David Foster Wallace / Title: Infinite Jest
     *
     * Prior to that I also saw the new duplicates being handled here
     * onChildAdded Author: David Foster Wallace / Title: Infinite Jest
     */
    private void readChildEvents(){
        // Get a reference to our posts

        // Attach an listener to read the data at our posts reference
        posts.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                BlogPost newPost = snapshot.getValue(BlogPost.class);
                System.out.println("onChildAdded " + "Author: " + newPost.getAuthor() + " / Title: " + newPost.getTitle());
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String s) {
                BlogPost newPost = snapshot.getValue(BlogPost.class);
                System.out.println("onChildChanged " + "Author: " + newPost.getAuthor() + " / Title: " + newPost.getTitle());
            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {
                BlogPost newPost = snapshot.getValue(BlogPost.class);
                System.out.println("onChildRemoved " + "Author: " + newPost.getAuthor() + " / Title: " + newPost.getTitle());
            }

            @Override
            public void onChildMoved(DataSnapshot snapshot, String s) {
                BlogPost newPost = snapshot.getValue(BlogPost.class);
                System.out.println("onChildMoved " + "Author: " + newPost.getAuthor() + " / Title: " + newPost.getTitle());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.i(TAG, "on canceled " + firebaseError.getMessage());
            }
        });
    }

    private void readOnce(){

        Firebase ref =  new Firebase( BuildConfig.UNIQUE_FIREBASE_ROOT_URL + "/votes");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.i( TAG, "read once " + snapshot.getValue().toString() );
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }

        });
    }
}
