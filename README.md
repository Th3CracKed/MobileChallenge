

# HiddenFounders Mobile Challenge
Android application that lists the most starred Github repos that were created in last 30 days, using Retrofit2 with MVVM architecture more details at [implementation section](#implementation).
## Features
* As a User I should be able to list the most starred Github repos that were created in the last 30 days. 
* As a User I should see the results as a list. One repository per row. 
* As a User I should be able to see for each repo/row the following details :
  * Repository name
  * Repository description 
  * Numbers of stars for the repo. 
  * Username and avatar of the owner. 
  * [BONUS] As a User I should be able to keep scrolling and new results should appear (pagination).
  * **Currently Pagination is not implemented, to implement the logic of it, i started with pull to refresh (because time constraint)**
  * **Internet checking : if there no Internet Available, queue a request that will be treated when internet is available**

## Screenshot 
![Main Fragment](https://raw.githubusercontent.com/Th3CracKed/MobileChallenge/develop/screenshot1.png)![enter image description here](https://raw.githubusercontent.com/Th3CracKed/MobileChallenge/develop/screenshot2.png)
## Installation 
* You can import the android project clean and run, or use the prebuilt [Debug apk](https://github.com/Th3CracKed/MobileChallenge/blob/develop/app-debug.apk?raw=true)
 
## Implementation

I choose [MVVM Architecture](https://developer.android.com/topic/libraries/architecture/)
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)  No memory leaks (LiveData only updates app component observers that are in an active lifecycle state).
* [Lifecycle-Aware](https://developer.android.com/topic/libraries/architecture/lifecycle) help produce better-organized, and often lighter-weight code, it performs actions in response to a change in the lifecycle status of another component.
* [Data Binding](https://developer.android.com/topic/libraries/data-binding/) Help reduce boilerplate code.

[RxAndroid](https://github.com/ReactiveX/RxAndroid)  composing asynchronous and event-based programs by using observable sequences.
[RxNetwork](https://github.com/pwittchen/ReactiveNetwork) observe connectivity with the Internet continuously
[Dagger2](https://github.com/google/dagger) improve testability and reduce boilerplate code (**Not implemented** :( Future Updates Maybe?)

[OkHttp3](http://square.github.io/okhttp/) can use the SPDY protocol for lower latency, compression, multiplexing..., which make Android app more responsive, supports HTTP/2.

[Retrofit2](http://instructure.github.io/blog/2013/12/09/volley-vs-retrofit/) ![Retrofit2 Benchmark](http://i.imgur.com/tIdZkl3.png)

[Glide](https://inthecheesefactory.com/blog/get-to-know-glide-recommended-by-google/en)
> The way Glide loads an image to memory and do the caching is better than Picasso which let an image loaded far faster.
> 

**UX Design :** 
`ViewPager` with `Tablyout` at the bottom.
The reason of choosing this solution is to have slide functionnality built in, i could use `Bottom Navigation` but i doesn't support slide.

[RecyclerView](https://stackoverflow.com/questions/26728651/recyclerview-vs-listview) is a more flexible control comparing to ListView for handling "list data" that follows patterns of delegation of concerns and leaves for itself only one task - recycling items.
