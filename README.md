<h1 align="center">Converter</h1>


<p align="center">  
💰 Converter demonstrates modern Android development with Dagger, Coroutines, Flow, Jetpack (Room, ViewModel), and Material Design based on MVVM architecture.                                                     
</br>
<p align="center">  
    The app contains 3 base screen:                                                       
</br> 
 <p align="center">  
-Main screen for display latest rates. It is possible to set up a base currency to calculate the exchange rate.                                               
</br> 
 <align="center">  
By clicking on a specific currency, you can see more details.                                                
</br> 
  <p align="center">  
    -Calculator screen to convert currency pairs.                                                     
</br> 
<p align="center">  
  -Settings screen with possibility share app.                                                    
</p>

![image](https://user-images.githubusercontent.com/106435152/230350531-48363d79-6905-46fb-829f-b07fc6df1122.png)
<img src="/preview/screenshot.png"/>


## Download
Go to the [Releases]() to download the latest APK.

<img src="/preview/preview.gif" align="right" width="320"/>


## Tech stack & Open-source libraries
- Minimum SDK level 24
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- Jetpack
  - Lifecycle: Observe Android lifecycles and handle UI states upon the lifecycle changes.
  - ViewModel: Manages UI-related data holder and lifecycle aware. Allows data to survive configuration changes such as screen rotations.
  - Room: Constructs Database by providing an abstraction layer over SQLite to allow fluent database access.
  - [Dagger](https://developer.android.com/training/dependency-injection/dagger-basics): for dependency injection.
- Architecture
  - MVVM Architecture (View - DataBinding - ViewModel - Model)
  - [Modularization](https://developer.android.com/topic/modularization)
  - [Claen architecture](https://github.com/android10/Android-CleanArchitecture)
  - Repository Pattern
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit): Construct the REST APIs and paging network data.
-  Gson: A modern JSON library for Kotlin and Java.
- [Turbine](https://github.com/cashapp/turbine): A small testing library for kotlinx.coroutines Flow.
- Espresso: A testing library.
- Mockito: A testing library.
- JUnit4: A testing library.
- Custom View.
- [Material-Components](https://github.com/material-components/material-components-android): Material design components.

## Architecture

**Converter** is based on the MVVM architecture and the Repository pattern, which follows the [Google's official architecture guidance](https://developer.android.com/topic/architecture).
![image](https://user-images.githubusercontent.com/106435152/230314584-e727242a-5368-4a93-923e-351358bd1438.png)
**Converter** was built with [Guide to app architecture](https://developer.android.com/topic/architecture), so it would be a great sample to show how the architecture works in real-world projects.

### Architecture Overview

<div align="center">
  <img src="https://user-images.githubusercontent.com/106435152/230343649-69f0dcde-7816-4d7f-bdc1-882f6520cbc2.png" alt="image">
</div>

- Each layer follows [unidirectional event/data flow](https://developer.android.com/topic/architecture/ui-layer#udf); the UI layer emits user events to the data layer, and the data layer exposes data as a stream to other layers.
- The data layer is designed to work independently from other layers and must be pure, which means it doesn't have any dependencies on the other layers.

### UI Layer

<div align="center">
  <img src="https://user-images.githubusercontent.com/106435152/230344293-f967b003-8d24-49a9-b3b9-5c9190cdd649.png" alt="image">
</div>

The UI layer consists of UI elements to configure screens that could interact with users and [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) that holds app states and restores data when configuration changes.

### Data Layer

<div align="center">
  <img src="https://user-images.githubusercontent.com/106435152/230344569-dce3905f-4851-4548-bf84-9d9ac2c47e83.png" alt="image">
</div>

The data Layer consists of repositories, which include business logic, such as querying data from the local database and requesting remote data from the network. It is implemented as an offline-first source of business logic and follows the [single source of truth](https://en.wikipedia.org/wiki/Single_source_of_truth) principle.<br>


### Modularization

The application is divided according to the principle of multimodularity, within the modules, the division into packages according to the clean architecture.
![image](https://user-images.githubusercontent.com/106435152/230303989-b5872f78-32b7-4846-b339-500d28e5f74d.png)

<div align="center">
  <img src="https://user-images.githubusercontent.com/106435152/230323078-f8cd58b9-0f86-4da8-b4e8-e10068770f43.png" alt="image">
</div>

## Result
Application completely in Kotlin. Support for changing device configuration has been implemented, the main logic is covered by unit test test, ui is covered by UI tests. The aircraft route is easily changeable through different implementations of interpalations algorithms supplied via DI.
