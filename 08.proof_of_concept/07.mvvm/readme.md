# MVVM

This is my favorite pattern from the `MV*` family. I've used it previously in [Knockout.js](http://knockoutjs.com/) and also [Angular](https://angularjs.org/). The demo which was here got to be from two years ago. Around that time [`Android Databinding`](https://developer.android.com/topic/libraries/data-binding/index.html) was only one way which wasn't as fun as in those two Javascript frameworks mentioned. I left it behind and recently discovered all the advances made to it. Now it's possible to do one way binding like this `text="@{user.name}"` which just prints the value, and double binding like this `text="@={user.name}"` which allows an `EditText` element to update `user.name`. 

So I made a whole learning in a new repo. Using [Android Databinding](https://developer.android.com/topic/libraries/data-binding/index.html) makes the code a lot cleaner, and easier to develop. 

[Learning Android DataBinding](https://github.com/juanmendez/learning-android-databinding)

