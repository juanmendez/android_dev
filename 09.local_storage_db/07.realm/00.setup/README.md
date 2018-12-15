# Realm installation and configuration

This demo starts with configuring the application to install Realm

MainActivity does the following
  - Starts with instantiating Song class, and saves it in Realm.
  = There will be an error when rotating, because the primary key will be tried to be inserted again.
  - Not a work of art, but a good way to start developing with Realm.

RealmApplication, does the initial Realm configuration.