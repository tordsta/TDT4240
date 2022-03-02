# TDT4240-group-24

To run the tests manually: ./gradlew :android:testDebugUnitTest -i

The server does not need to be run locally, as we provide one out of the box. If there is an issue, you might still need to do it.

Follow these steps:

You have to have Node and npm installed, then:

1. `cd server`
2. `npm i`
3. `npm start`

Your server should be running locally on port 3000

In the source code, find the `SERVER_IP`, and replace it with your local one (not "localhost", but the actual IP of the device you run the server on, because Android device/emulator won't see it.)
