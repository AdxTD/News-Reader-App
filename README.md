# Android News Reader App

## Technologies used

Android Studio Jellyfish | 2023.3.1, Kotlin, Kotlin Coroutines and Flows, Jetpack Compose for UI, Navigation: Compose Nav Destinations, Dagger Hilt for DI,  Room for local db caching,
Retrofit for Remote API calls, Coil for loading images, Unit testing: JUnit4, Mockk, Truth.   

## Architecture 

Clean architecture with MVI, SOLID princples are applied on this project.

Packaging:
* core: ui: for common UI resuable components - utils, common: for common kotlin resources and resuable tools
* di: for dependency injection providing app modules and binding repositories
* feature_articles: layered into three layers: (any other new feature will follow same structure)
 * ** data: local (db, dao, entities ..) - remote (api, dto, responses .. ) - mappers - repository (implementations). 
 * ** domain: repository (interfaces) - models - utils - usecases (contains main buisness logic)
 * ** presentation: article_listing - article_details (each contains corresponding events, states, viewModels, screens, ui components)
* test: for unit testing
* androidTest: for UI testing, end-to-end and integration tests

p.s. project can be transformed to multi-module structure for a better modularization, and to save gradle building time by following same strucure and creating a new gradle module out of each of the prvious main packages 
(i.e. core:ui module, core:util, feature_x:ui module, feature_x:data module, testing module ..etc) to keep coupling between modules at its lowest.


## Brief description about app logic and features

The app operates by default on cached data to minimize api calls, and to keep working when there's no internet connections.
When user refreshes list of articles, the app will fetch latest version of data from server, updating the cached version and keep operating on it as a single source of truth.

Current available features:
* Listing news articles
* Search: on both titles and content of current news
* Sorting and ordering: sorting by titles alphabitically, or by published date, also ordering the sort by descending or ascending
* Voice recognition: app will trigger a new request to remote api to refresh the data when user says "reload"
* View article details, displaying all available content

## UI / UX

* App catches and displays errors or no results status appropriately, through a snackbar or a small layout in the middle when there’s no data displayed
* App will trigger the search after appropriate period of time when user stops typing, no multiple searches will happen at the same time, new one will cancel the old one avoiding any conflicts or misuse
* App will keep user choices for sorting and ordering even after refreshing the data or making a new search, and vis-versa, like will save the same query even when reloading or chaning sort options and so on .. 
* The App is performant, intuitive and easy to use without any complexities, suitable animations and icons are used to ease the user experience and keep them happy :D

  
