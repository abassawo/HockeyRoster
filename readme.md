### Hockey Roster

The application consumes data from the following [endpoing](https://jc-xerxes.gpshopper.com/android_eval.json)

Tools uses include

[ ] Retrofit
[ ] RxJava
[ ] Gson
[ ] Butterknife
[ ] Timber
[ ] Picasso

A bit on the design of the application.

While the app is designed to employ the traditional Master/Detail UI pattern, I wanted to also support being able to swipe left and right while in the detail page. Some ideas that came to mind included

[ ] Having a ListActivity that hosts a ListFragment, and that navigates to another activity with a viewpager in it.

[ ] Using one activity to manage the two distinct fragments

I ultimately wasn't comfortable with either of these architectures because of the need to transfer data about the list of contacts either from one activity's presenter to another, or from one fragment to another.

I also considered just using a singleton-scoped repository-like model object that contained all information about rosters, but opted to stay away from static representation of the dataset.

The architecture I did decide on is probably a bit odd as I'm using a single activity to toggle between displaying either its view of Roster List items or its swipeable view-pager like list of Detail items.

Some difficulties with this design included having to put more care and attention to orientation changes, transitions, and managing the toolbar's state. Benefits included not having to transfer any data and just maintaining all of the roster data in one presenter. No need for serializing, parceling, or any fragment args. 

It may have been possible to attempt using one recycler view to represent both the List and Detail screen, but this didn't seem very clean to me, and I don't think it is a worthwhile optimization.
