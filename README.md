

# Live Player

![alt text](https://github.com/pbj-apps/Live-ios-sdk/blob/main/banner.png)

Stream your PBJ.live content from your Android App.


# Stream to your Android app in 4 simple steps

## 1. Import the Live-android-sdk package
Add in gradle at project level

You must use :
- a github username 
- a personal access token (https://docs.github.com/en/github/authenticating-to-github/keeping-your-account-and-data-secure/creating-a-personal-access-token)

```groovy
maven {
            url "https://maven.pkg.github.com/pbj-apps/Live-android-sdk"
            credentials {
                username "GithubUserName"
                password "personal_access_token"
            }
        }
```
Then, add in gradle at app level

```groovy
implementation "com.pbj.live:live:$live_version"
```

You can find the latest version in the package section of this repository

https://github.com/pbj-apps/Live-android-sdk/packages/748723

## 2. Initialize the LivePlayerSDK with your credentials on App start

```kotlin
 val liveSdk = LiveSDK.initialize(context, "yourApiKey", ApiEnvironment)

```

## 3. (Optional) Check if there is a live episode beforehand
Typical usage is that you have a "Watch live" button that you only want to show if there is an actual episode currently live.

```kotlin
liveSdk.isEpisodeLive { isLive ->

}
```

You can also pass your showId as a parameter to query live episodes, but this time for a specific show. You can find your showId in your web dashboard. Select the show you want and grab it's id from the browser's url.

## 4. Start a Player activity

```kotlin
liveSdk.startLivePlayer(context) // Optionally pass a showId.
```
Without a showId parameter, the player will display the first live show it finds.

## Example App

Checkout the example App provided in this repository to see a typical integration. With the test app, you can input your Organization api key and battle test your own environment.

## Got a question? Found an issue?
Create a github issue and we'll help you from there ❤️
