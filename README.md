# Android News Reader App

## Technologies used

Android Studio Jellyfish | 2023.3.1, Kotlin, Kotlin Coroutines and Flows, Jetpack Compose for UI, Navigation: Compose Nav Destinations, Dagger Hilt for DI,  Room for local db caching,
Retrofit for Remote API calls, Coil for loading images, Unit testing: JUnit4, Mockk, Truth.   

## Architecture 

Clean architecture with MVI, SOLID princples are appliead on this project.

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
