# Expresso testing with Realm Demo

This is the beginning of testing with Expresso. [Since there is already an application I am using Realm.] (https://github.com/juanmendez/jm_android_dev/tree/master/09.database_connection/07.realm/02.realm_band_to_songs)
The purpose of this project is to test whenever the user creates a band and adds a song. I already found a bug with Expresso and gratefully it was easy to fix.


  - build.gradle has the blueprint of how to install Expresso along with Dagger and Recyclerview
  - First test starts up the song form fills it up and test to see if the title of how many songs have been created has updated
  - Second test starts up the band form and is able to insert a band and hit the OK button. (more tests to come)

Some other libraries which were used.
  - [Android Annotations](http://androidannotations.org/)
  - [Retrolambda](https://github.com/orfjackal/retrolambda)
  - [Dagger] (http://square.github.io/dagger/)
  - [RxAndroid] (https://github.com/ReactiveX/RxAndroid)

What I learned:
  - There is a bit of configuration on gradle in order to run testing while dealing with RecyclerView, and other libraries.
  - I had already an issue after filling up a song form and the submit button wasn't working. It was an error after a but which was found during testing. Through breakpoints I was able to fix it, and testing proved to be a handy tool.