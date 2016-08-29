# Realm using Multiversion Concurrency Control

This short demo shows how SongFormFragment in MainActivity can do the following

  - Have a form to create a song from Song.java
  - Use Realm to add it to its realm database
  - Tell MainActivity to close the fragment

MainActivity does the following
  - Allow to display fragment form, and also remove it.
  - Having realm's default instance ensure to close it
  - Having realm's default instance ensure to close any event listeners
  - Have an event listener to update number of songs being added to realm.

RealmApplication, does the initial Realm configuration.

This application is not fancy, it lacks any bus system to communicate between fragment and activity.
Some other libraries which were used.
  - [Android Annotations](http://androidannotations.org/)
  - [Retrolambda](https://github.com/orfjackal/retrolambda)

What I learned:
  - I used Retrolambda instead of relying on Jack Toolchain. Which couldn't work for me and found others with the same problems.

It would be interesting to use [Dagger](http://square.github.io/dagger/) for this same application.