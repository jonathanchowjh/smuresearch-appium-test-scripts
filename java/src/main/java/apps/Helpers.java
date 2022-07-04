package apps;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileCommand;

import java.io.Console;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Helpers {

  // === CLICK HELPERS ===
  public static List<WebElement> getClickables(List<WebElement> elements) {
    // create clickable list
    List<WebElement> clickables = new ArrayList<>();
    for (WebElement element : elements) {
      boolean clickable = isClickable(element);
      if (clickable) {
        clickables.add(element);
      }
    }
    return clickables;
  }

  public static Integer clickRandom(
    AppiumDriver driver,
    List<WebElement> elements,
    Integer max
  ) throws Exception {
    if (elements.size() == 0) return -1;
    int modulus = max == null
      ? elements.size()
      : Math.min(max, elements.size());
    int selected = (int) Math.floor(Math.random() * modulus);
    safeClick(driver, elements.get(selected));
    return selected;
  }

  public static void safeClick(AppiumDriver driver, WebElement element)
    throws Exception {
    if (isClickable(element)) {
      // driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
      Thread.sleep(2000); 
      element.click();
    } else {
      throw new Error("ERROR: Element Unclickable: " + element.getClass());
    }
  }

  public static boolean isClickable(WebElement element) {
    return Boolean.parseBoolean(element.getAttribute("clickable"));
  }

  // === FUNCTION HELPERS ===

  // === PRINT HELPERS ===
  public static void printByInnerElement(
    List<WebElement> elements,
    String innerClass,
    String innerId,
    String printClass
  ) {
    for (int i = 0; i < elements.size(); i++) {
      List<WebElement> inners = innerClass != null
        ? (elements.get(i).findElements(By.className(innerClass)))
        : elements.get(i).findElements(By.id(innerId));
      for (WebElement inner : inners) {
        System.out.println(
          "OUTER IDX: " + i + " => " + inner.getAttribute(printClass)
        );
      }
    }
    System.out.println("");
  }

  public static List<WebElement> filterInnerWebElement(
    List<WebElement> elements,
    String innerClass,
    String innerId,
    String printClass,
    List<String> printCompare
  ) {
    List<WebElement> ret = elements;
    ret.removeIf(ele -> {
      List<WebElement> inners = innerClass != null
        ? (ele.findElements(By.className(innerClass)))
        : ele.findElements(By.id(innerId));
      for (WebElement inner : inners) {
        if (printCompare.contains(inner.getAttribute(printClass))) {
          return true;
        }
      }
      return false;
    });
    return ret;
  }

  public static void timeout(long seconds) throws Exception {
    Console cnsl = System.console();
    Future<String> future = CompletableFuture.supplyAsync(() -> cnsl.readLine());
    try {
      String s = future.get(seconds, TimeUnit.SECONDS);
    } catch (Exception e) {
      System.out.println("Time out has occurred");  
      future.cancel(true);
    }
  }

  enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
  }

  public static void swipe(AppiumDriver driver, Direction direction) throws Exception {
    double[] touchPoints = {0.5, 0.5, 0.5, 0.5};
    if (direction.equals(Direction.UP)) {
      touchPoints[1] = 0.3;
      touchPoints[3] = 0.7;
    } else if (direction.equals(Direction.DOWN)) {
      touchPoints[1] = 0.7;
      touchPoints[3] = 0.3;
    } else if (direction.equals(Direction.RIGHT)) {
      touchPoints[0] = 0.7;
      touchPoints[2] = 0.3;
    } else if (direction.equals(Direction.LEFT)) {
      touchPoints[0] = 0.3;
      touchPoints[2] = 0.7;
    }  
    Thread.sleep(3000);
    Dimension dimension = driver.manage().window().getSize();
    Point start = new Point((int)(dimension.width*touchPoints[0]), (int)(dimension.height*touchPoints[1]));
    Point end = new Point((int)(dimension.width*touchPoints[2]), (int)(dimension.height*touchPoints[3]));
    W3cActions.doSwipe(driver, start, end, 1000);  //with duration 1s
  }

  public static void tap(AppiumDriver driver, double w, double h) {
    Dimension dimension = driver.manage().window().getSize();
    Point forTap = new Point((int)(dimension.width*w), (int)(dimension.height*h));
    W3cActions.doTap(driver, forTap, 200); //with duration 200ms
  }
}
