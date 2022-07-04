package apps;

import com.google.common.collect.ImmutableMap;

import apps.Helpers.Direction;
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

public class OrganicMaps {
  AppiumDriver driver;
  FunctionGraph graph;

  public OrganicMaps(String apk) throws Exception {
    DesiredCapabilities cap = new DesiredCapabilities();
    cap.setCapability("platformName", "Android");
    cap.setCapability("platformVersion", "12");
    cap.setCapability("udid", "R5CR10N16WM");
    cap.setCapability("deviceName", "Galaxy S21 Ultra 5G");
    
    cap.setCapability("app", apk);
    cap.setCapability("appWaitActivity", "com.mapswithme.maps.MwmActivity");
    cap.setCapability("automationName", "UiAutomator2");
    cap.setCapability("noReset", true);
    cap.setCapability("fullReset", false);
    cap.setCapability("autoGrantPermissions", true);

    URL url = new URL("http://127.0.0.1:4723/wd/hub");
    driver = new AppiumDriver(url, cap);
    this.createPath();
  }

  public AppiumDriver getDriver() {
    return this.driver;
  }

  public void run() throws Exception {
    FunctionGraph current = graph;
    current = current.nodes.get(1);
    current.func.apply(null);
    current = current.nodes.get(0);
    current.func.apply(null);
  }

  public void createPath() {
    HashMap<String, FunctionGraph> dict = new HashMap<>();
    dict.put("page_map", new FunctionGraph("page_map", pageMap()));
    dict.put("nav_question", new FunctionGraph("nav_question", navigateMenu(0)));
    dict.put("nav_search", new FunctionGraph("nav_search", navigateMenu(1)));
    dict.put("nav_star", new FunctionGraph("nav_star", navigateMenu(2)));
    dict.put("nav_menu", new FunctionGraph("nav_menu", navigateMenu(3)));
    dict.put("page_search", new FunctionGraph("page_search", pageSearch()));
    
    graph = dict.get("page_map");
    FunctionGraph current = graph;
    current.nodes.add(dict.get("nav_question"));
    current.nodes.add(dict.get("nav_search"));
    current.nodes.add(dict.get("nav_star"));
    current.nodes.add(dict.get("nav_menu"));
    current = current.nodes.get(1);
    current.nodes.add(dict.get("page_search"));
  }

  public Function<Void, Integer> sampleFunction() {
    Function<Void, Integer> func = Void -> {
      try {

      } catch (Exception err) {
        err.printStackTrace();
      }
      return 5;
    };
    return func;
  }

  public Function<Void, Integer> pageMap() {
    Function<Void, Integer> func = Void -> {
      try {
        List<WebElement> sop = new ArrayList<>();
        WebElement position = this.driver.findElement(By.id("app.organicmaps:id/my_position"));
        WebElement zoomin = this.driver.findElement(By.id("app.organicmaps:id/nav_zoom_in"));
        WebElement zoomout = this.driver.findElement(By.id("app.organicmaps:id/nav_zoom_out"));
        WebElement surfaceview = this.driver.findElement(By.id("app.organicmaps:id/layers_button"));
        
        Helpers.safeClick(this.driver, position);
        Helpers.safeClick(this.driver, zoomin);
        Helpers.safeClick(this.driver, zoomout);
        Helpers.safeClick(this.driver, surfaceview);
        List<WebElement> views = this.driver.findElements(By.id("app.organicmaps:id/btn"));
        if (views.size() >= 2) {
          Helpers.safeClick(this.driver, views.get(1));
        }
        Helpers.swipe(this.driver, Direction.UP);
        Helpers.swipe(this.driver, Direction.DOWN);
        Helpers.swipe(this.driver, Direction.LEFT);
        Helpers.swipe(this.driver, Direction.RIGHT);
      } catch (Exception err) {
        err.printStackTrace();
      }
      return 5;
    };
    return func;
  }

  public Function<Void, Integer> navigateMenu(int idx) {
    Function<Void, Integer> func = Void -> {
      try {
        WebElement menu = this.driver.findElement(By.id("app.organicmaps:id/buttons_frame"));
        List<WebElement> menuList = menu.findElements(By.className("android.widget.ImageView"));
        menuList = Helpers.getClickables(menuList);
        WebElement button = menuList.get(idx);
        Helpers.safeClick(this.driver, button);
        Thread.sleep(3000);
      } catch (Exception err) {
        err.printStackTrace();
      }
      return 5;
    };
    return func;
  }

  public Function<Void, Integer> pageSearch() {
    Function<Void, Integer> func = Void -> {
      try {
        // Switch Tabs (optional if)
        WebElement tabs = this.driver.findElement(By.id("app.organicmaps:id/tabs"));
        List<WebElement> tabList = tabs.findElements(By.className("android.widget.LinearLayout"));
        tabList = Helpers.getClickables(tabList);
        Helpers.safeClick(this.driver, tabList.get(0));

        // Choose Catergories (Optional if)
        WebElement pages = this.driver.findElement(By.id("app.organicmaps:id/pages"));
        List<WebElement> pageList = pages.findElements(By.className("android.widget.TextView"));
        Helpers.safeClick(this.driver, pageList.get(0));

        // Search bar (Optional if)
        WebElement textArea = this.driver.findElement(By.id("app.organicmaps:id/query"));
        System.out.println(textArea.getText());
        textArea.sendKeys("xxxx");

        // Results selection
        WebElement results = this.driver.findElement(By.id("app.organicmaps:id/results_frame"));
        List<WebElement> resultsList = results.findElements(By.className("android.widget.RelativeLayout"));
        Helpers.safeClick(this.driver, resultsList.get(0));

        // Cancel
        Helpers.tap(this.driver, 0.5, 0.3);
      } catch (Exception err) {
        err.printStackTrace();
      }
      return 5;
    };
    return func;
  }
}
