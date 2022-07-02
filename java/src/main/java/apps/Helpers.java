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
import java.util.Currency;
import java.util.List;
import java.util.Random;
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
    if (elements.size() == 0) return null;
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
      driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
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
}
