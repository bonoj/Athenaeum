# Athenaeum
A book library with clean architecture powered by RxJava, Dagger, Retrofit, and the Google Books API.

Also featuring alternate architecture using Android Architecture Components: ViewModel replaces the presenter with LiveData and Kotlin Coroutines for concurrency. Room Database caches network calls locally.

View (Activities) <-> Presenter/ViewModel <- Repository <- Database <- API
                                                                          
                                                                          
#### *API_KEY REQUIRED!*
The following line must be added to your gradle.properties file:

`API_KEY="insert-your-api-key-here"`

Google API key instructions available at https://support.google.com/cloud/answer/6158862.
