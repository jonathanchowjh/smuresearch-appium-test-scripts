package apps;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileCommand;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Telegram {
  AppiumDriver driver;
  FunctionGraph graph;
  FunctionGraph curr;

  public Telegram(String apk) throws Exception {
    DesiredCapabilities cap = new DesiredCapabilities();
    cap.setCapability("platformName", "Android");
    cap.setCapability("platformVersion", "12");
    cap.setCapability("udid", "R5CR10N16WM");
    cap.setCapability("deviceName", "Galaxy S21 Ultra 5G");
    
    cap.setCapability("app", apk);
    cap.setCapability("appWaitActivity", "org.telegram.ui.LaunchActivity");
    cap.setCapability("automationName", "UiAutomator2");
    cap.setCapability("noReset", true);
    cap.setCapability("fullReset", false);
    cap.setCapability("autoGrantPermissions", true);

    URL url = new URL("http://127.0.0.1:4723/wd/hub");
    driver = new AppiumDriver(url, cap);
    // this.createPath();
  }

  public AppiumDriver getDriver() {
    return this.driver;
  }

  public void run() throws Exception {
    FunctionGraph current = graph;
  }

  public void createPath() {

  }
}
