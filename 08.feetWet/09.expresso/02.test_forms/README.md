# Realm using Multiversion Concurrency Control

This short demo shows how SongFormDialog in MainActivity can do the following

  - Have a form to create a song from Song.java
  - Associate new song to a band.
  - Use Realm to add it to its realm database
  - Tell MainActivity to close the dialog


ArtistFormDialog in MainActivity can do the following

  - Have a form to create a band from Band.java
  - Use Realm to add it to its realm database
  - Tell MainActivity to close the dialog


MainActivity does the following
  - Allow to display any dialog and remove it
  - Having realm's default instance ensure to close it
  - Having realm's default instance ensure to close any event listeners
  - Have an event listener to update number of songs being added to realm.

RealmApplication, does the initial Realm configuration.

Some other libraries which were used.
  - [Android Annotations](http://androidannotations.org/)
  - [Retrolambda](https://github.com/orfjackal/retrolambda)
  - [Dagger] (http://square.github.io/dagger/)
  - [RxAndroid] (https://github.com/ReactiveX/RxAndroid)

What I learned:
  - I used Retrolambda instead of relying on Jack Toolchain. Which couldn't work for me and found others with the same problems.
  - Adding a song which points to a band wasn't just a simple transaction. The band associated came from UI thread, and needed to be pulled from current background thread