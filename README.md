<h1 align="center">Converter</h1>


<p align="center">  
💰 Converter demonstrates modern Android development with Dagger, Coroutines, Flow, Jetpack (Room, ViewModel), and Material Design based on MVVM architecture.                                                     

## The app contains 3 base screens:   

- Main screen for display latest rates. It is possible to set up a base currency to calculate the exchange rate.  
By clicking on a specific currency, you can see more details.                                                
- Calculator screen to convert currency pairs.                                                     
- Settings screen with possibility share app.                                                    
</p>
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
  - MVVM Architecture (View - ViewModel - Model)
  - [Modularization](https://developer.android.com/topic/modularization)
  - [Clean architecture](https://github.com/android10/Android-CleanArchitecture)
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


### Modularization

The application is divided according to the principle of multimodularity, within the modules, the division into packages according to the clean architecture.
![image](https://user-images.githubusercontent.com/106435152/230303989-b5872f78-32b7-4846-b339-500d28e5f74d.png)

<div align="center">
  <img src="https://user-images.githubusercontent.com/106435152/230418354-21643b7b-ff10-45b0-88f6-cb467ccecb24.png" alt="image">
</div>


### Architecture Overview

**Converter** is based on the MVVM architecture and the Repository pattern, which follows the [Google's official architecture guidance](https://developer.android.com/topic/architecture).
![image](https://user-images.githubusercontent.com/106435152/230314584-e727242a-5368-4a93-923e-351358bd1438.png)

## Result
Application completely in Kotlin. Support for changing device configuration has been implemented, the main logic is covered by unit tests, ui is covered by UI tests.
