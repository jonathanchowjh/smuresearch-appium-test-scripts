# Appium Mobile Tests

[Appiium Documentation](https://appium.io/docs/en/about-appium/api/)
[screenshot fix](https://github.com/android/android-test/issues/911)

### Prerequisites
- ```npm i -g appium```
- java version 1.8
- Android Studio install
- $ANDROID_HOME && $JAVA_HOME export
- sdkmanager cmdtools install
- sdkmanager install tools, platform-tools, platform
- $ANDROID_HOME/tools/bin/uiautomatorviewer (add swt2.jar if required)
  - [screenshot fix](https://github.com/android/android-test/issues/911)
- ```npm run read_apk``` -> to see apk class name
- ```npm run read_activity``` -> to see apk activity name
- add environment variables to ./java/src/main/java/apps

### RUN CODE
```
npm i
cd java
// RUN ./src/main/java/Automate.java file
```
# smuresearch-appium-test-scripts
