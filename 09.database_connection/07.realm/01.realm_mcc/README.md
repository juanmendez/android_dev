# Realm using Multiversion Concurrency Control

This short demo shows how a fragment in MainActivity can do the following

  - Have a form to create a song from Song.java
  - Use Realm to add it to its realm database
  - Tell MainActivity to close the fragment

MainActivity does the following
  - Allow to display fragment form, and also remove it.
  - Having realm's default instance ensure to close it
  - Having realm's default instance ensure to close any event listeners
  - Have an event listener to update number of songs being added to realm.

The app's application RealmApplication, does the initial Realm configuration.
