Lab 4 Android app and Beacons Setup
==================================


Setting up the Beacon ids
-------------------------

The beacons should have the *UUID*. Only the **Major** needs to be different. *Minor* is not relevant.   
This particular set-up is similar to the case where beacons are place in the different stores of a brand.
In our case, we placed a beacon in each of the four corners of the room. You can have as many beacons
as you want as long as they have same UUIDs and different Majors.
   
We have used the following Beacon id values:
   - UUID : 00726f626f7470616a616d61732e6361
   - Majors : 0040, 0041, 0042, 0043 (one for each beacon, we had 4)
   - Minor : 0030
   
Setting Beacon ids in Android project
-------------------------------------
The Android app checks the UUID of detected Beacon and sends the Major id to the nodejs server.
The UUId is hard-coded in file BTUTils.java (line 25)

   
Libraries used
--------------
- Butterknife[http://jakewharton.github.io/butterknife]
- Retrofit[http://square.github.io/retrofit/]
- Timber[https://github.com/JakeWharton/timber]
- Dexter[https://github.com/Karumi/Dexter]
- Bluetooth LE Library for Android[https://android-arsenal.com/details/1/693]
- Blueteeth[https://android-arsenal.com/details/1/3512]

























