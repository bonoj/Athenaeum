# Athenaeum
A book library with clean architecture powered by RxJava, Dagger, Retrofit, and the Google Books API.

Also featuring alternate architecture using Android Architecture Components: ViewModel replaces the presenter with LiveDat and Kotlin Coroutines for concurrency. Room Database is used to cachce all network calls locally.

View (Books and Details Activities) <-> Presenter/ViewModel <- Repository <- API
                                                                          <- Room
                                                                          
#### *API_KEY REQUIRED!*
The following line must be added to your gradle.properties file:

`API_KEY="insert-your-api-key-here"`

Google API key instructions available at https://support.google.com/cloud/answer/6158862.
