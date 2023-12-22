<h1 align="center">Lockscreen-styled Notification<br/>
   Send a notification that looks like as the lockscreen of device
</h1>

<p align="center">
    <img src="./photo/kotlin.png" width="1280" />
</p>


# [**Table Of Content**](#table-of-content)
- [**Table Of Content**](#table-of-content)
- [**Introduction**](#introduction)
- [**IMPLEMENTATION**](#implementation)
  - [**1. DEFIND PERMISSIONS IN ANDROID MANIFEST**](#1-defind-permissions-in-android-manifest)
- [**Made with 💘 and KOTLIN **](#made-with--and-kotlin-)

# [**Introduction**](#introduction)

Have you ever seen applications that can send their notification that overrides device's lookscreen?

Perhaps, you are think that it is complicated to impletement this function. Don't worry, because together, we will go into detail to build this function. Generally speaking, it is just sending normal notification and the different is content of the notification. Here is an activity instead of a notification.

<p align="center">
    <img src="./photo/photo_01.png" width="320" />
</p>
<h3 align="center">

***LOOK LIKE AS DEVICE'S LOCKSCREEN? ACTUALLY, IT IS A NOTIFICATION 😊***
</h3>

# [**IMPLEMENTATION**](#implementation)

## [**1. DEFIND PERMISSIONS IN ANDROID MANIFEST**](#1-define-permission-in-android)

First of all, to send notifications. We need some run-time permissions to can access system and schedule what content and when we send notifications. There permissions below is all we need to do it

```
<!-- For firing notification on Android 13 -->
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
<uses-permission android:name="android.permission.SCHEDULE_EXACT_ALARM" />
<uses-permission android:name="android.permission.USE_EXACT_ALARM" />

<!-- For showing Lockscreen Activity as device's lockscreen -->
<uses-permission android:name="android.permission.USE_FULL_SCREEN_INTENT" />
```

From Android 13 & higher, we need to define `POST_NOTIFICATIONS` permission as Google Android official document.

> **_NOTE:_**
    We highly recommend that you target Android 13 or higher as soon as possible to benefit from the additional control and flexibility of this feature. If you continue to target 12L (API level 32) or lower, you lose some flexibility with requesting the permission in the context of your app's functionality.
 
# [**Made with 💘 and KOTLIN <img src="https://www.vectorlogo.zone/logos/kotlinlang/kotlin-ar21.svg" width="60">**](#made-with-love-and-kotlin)