# EventLock2

Show your calendar events on the lockscreen!

### Why?

* After Kitkat, the lockscreen widgets were disabled and so calendar widget can't be put on the lockscreen.
* AOSP lockscreen for lolipop/marshmallow does not show calendar events on lockscreen either.

### How?

* By showing the event notification when the device is locked(but screen is on) and hiding it when user unlocks the phone.
* Looks as if the event was shown on the lockscreen.
* If this doesn't look good to you, you can use [EventLock] (https://github.com/theawless/EventLock) instead, which uses xposed framework and implants the event notification into the lockscreen.

### Requirements

* Android 5.0 and above.

### License
The code is released under The MIT License (MIT).
