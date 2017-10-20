# Dialog PreferenceFragments

This is a demo to find out how to create `PreferenceFragments` in dialog mode. I got an error when opening the dialog once again. The bug is related to a duplicate fragment id. Which is not true, but one way I solve it is by previous to opening the dialog checking if the fragment is found by its id, and in that case it gets removed. So this error is preventable.

I personally like the Dialog-PreferenceFragment when the options are just a few rather than using a regular fragment.

![screenshot](github/screenshot.PNG)