### Hockey Roster

The application consumes data from the following [endpoint](https://jc-xerxes.gpshopper.com/android_eval.json)

Tools used include:

- Retrofit
- RxJava
- Gson
- Butterknife
- Timber
- Picasso
- JUnit and Mockito

#### A bit on the design of the application.

While the app is designed to employ the traditional Master/Detail UI pattern, I wanted to also support being able to swipe left and right while in the detail page. Some ideas that came to mind included

 - Having a ListActivity that hosts a ListFragment, and that navigates to another activity with a viewpager in it.

 - Using one activity to manage the two distinct fragments

I ultimately wasn't comfortable with either of these architectures because of the need to transfer data about the list of contacts either from one activity's presenter to another, or from one fragment to another.

I also considered just using a singleton-scoped repository-like model object that contained all information about rosters, but opted to stay away from static representation of the dataset.

The architecture I did decide on is probably a bit odd as I'm using a single activity to toggle between displaying either its view of Roster List items or its swipeable view-pager like list of Detail items.

Some difficulties with this design included having to put more care and attention to orientation changes, transitions, and managing the toolbar's state. Benefits included not having to transfer any data and just maintaining all of the roster data in one presenter. No need for serializing, parceling, intent extras, or any fragment arguments.

All in all, I felt the minor challenges of using one activity to support both views were outweighed by the opportunities for code reuse: The MainActivity is able to inherit from the HybridListDetailActivity and both recyclerviews are able to share the same adapter/viewholder, or at the least some subclass of the adapter/viewholder. Working with the SnapHelper class to simulate viewpager behavior within a recycler view also turned out to be fairly straightforward.

It may have been possible to attempt using one recycler view to represent both the List and Detail screen, but this didn't seem very clean to me, and I don't think it is a worthwhile optimization (if it as optimization at all).

#### On Testing

The test cases in this application cover the MainScreenPresenter's logic, but the HybridListDetailActivity logic of the activity makes the case for testing the activity's interactions as well. Given more time, I would have liked to incorporate some espresso tests. Also, it would have been helpful to further setup the MockApi and use a tool like MockWebServer to test the network layer.
