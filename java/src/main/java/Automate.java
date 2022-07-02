// This is a software to automate user actions on mobile devices using the Appium framework
// Each app needs to be customized

import apps.Antennapod;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileCommand;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Automate {

  // START OF CODE
  static int curSeq;
  static Antennapod antennapod;

  public enum button {
    FIRST_BUTTON, // Trending
    SECOND_BUTTON, // Subscriptions
    THIRD_BUTTON, // Bookmarked Playlists
  }

  public static void main(String[] args) {
    long startTime = System.nanoTime();
    long endTime, delta;
    try {
      antennapod = new Antennapod();
      antennapod.run();
      // curSeq = 0;
      // while (true) {
      //     endTime = System.nanoTime();
      //     delta = (endTime - startTime) / 1000000000;
      //     System.out.println("Elapsed time since start of experiment: " + delta + "s");
      //     if (delta > EXPERIMENT_DURATION)
      //         System.exit(0);     // Experiment ended
      //     curSeq++;
      // }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
