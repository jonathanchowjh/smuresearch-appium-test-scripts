package apps;

import com.google.common.collect.ImmutableMap;

import apps.Helpers.Direction;
import apps.Helpers.Rand;
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
    current.func.apply(null);
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
        WebElement position = this.driver.findElement(By.id("app.organicmaps:id/my_position"));
        Helpers.safeClick(this.driver, position);
        while (Helpers.rand(Rand.ALMOST_CONFIRMED)) {
          // check if download is needed
          List<WebElement> download = this.driver.findElements(By.id("app.organicmaps:id/onmap_downloader"));
          if (download.size() != 0) {
            WebElement downloadButton = this.driver.findElement(By.id("app.organicmaps:id/downloader_button"));
            System.out.println("pageMap: Download Map");
            Helpers.safeClick(this.driver, downloadButton);
            boolean downloading = true;
            while (downloading) {
              System.out.println("pageMap: Downloading");
              Thread.sleep(5000);
              List<WebElement> downloadScreen = this.driver.findElements(By.id("app.organicmaps:id/onmap_downloader"));
              downloading = downloadScreen.size() != 0;
            }
          }
          if (Helpers.rand(Rand.NEUTRAL)) {
            // map swipe (optional while)
            RandomEnum<Direction> r = new RandomEnum<Direction>(Direction.class);
            Direction direction = r.random();
            Helpers.swipe(this.driver, direction);
            System.out.println("pageMap: swipe :: " + direction.name());
            continue;
          }
          // map manipulation (non swipe)
          List<WebElement> sop = new ArrayList<>();
          position = this.driver.findElement(By.id("app.organicmaps:id/my_position"));
          WebElement zoomin = this.driver.findElement(By.id("app.organicmaps:id/nav_zoom_in"));
          WebElement zoomout = this.driver.findElement(By.id("app.organicmaps:id/nav_zoom_out"));
          WebElement surfaceview = this.driver.findElement(By.id("app.organicmaps:id/layers_button"));
          sop.add(surfaceview);
          sop.add(position);
          sop.add(zoomin);
          sop.add(zoomout);
          int id = Helpers.clickRandom(this.driver, sop, null);
          String[] mapMinipulateType = { "surfaceview", "position", "position", "zoomout" };
          System.out.println("pageMap: manipulate :: " + id + " :: " + mapMinipulateType[id]);
          if (id == 0) {
            // if surfaceview (complete selection)
            List<WebElement> views = this.driver.findElements(By.id("app.organicmaps:id/btn"));
            Helpers.clickRandom(this.driver, views, null);
          }
        }
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
        String[] menuType = { "question", "search", "star", "options" };
        System.out.println("navigateMenu : clicked :: " + idx + " :: " + menuType[idx]);
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
        if (Helpers.rand(Rand.NEUTRAL)) {
          pageSearchSwitchTabs();
          int count = 0;
          while (pageSearchEmpytyPage()) {
            System.out.println("pageSearch: Empty Page switching tab");
            pageSearchSwitchTabs();
            if (++count > 10) {
              throw new Exception("pageSearch: Empty Page after 10 tab switch");
            }
          }
        }

        // Choose Catergories (MUST PLAY)
        WebElement pages = this.driver.findElement(By.id("app.organicmaps:id/pages"));
        List<WebElement> pageList = pages.findElements(By.className("android.widget.TextView"));
        Helpers.filterElements(pageList, new String[]{"Clear Search History"}, "content-desc", true);
        Helpers.safeClick(this.driver, pageList.get(0));
        System.out.println("pageSearch : choose categories :: " + 0 + " :: " + pageList.get(0).getAttribute("text"));

        // Search bar (Optional if)
        if (Helpers.rand(Rand.NEUTRAL)) {
          WebElement textArea = this.driver.findElement(By.id("app.organicmaps:id/query"));
          String keysToSend = "xxxx";
          textArea.sendKeys(keysToSend);
          System.out.println("pageSearch : search bar :: " + 0 + " :: " + keysToSend);
        }

        // Results selection
        WebElement results = this.driver.findElement(By.id("app.organicmaps:id/results_frame"));
        List<WebElement> resultsList = results.findElements(By.className("android.widget.RelativeLayout"));
        if (resultsList.size() == 0) {
          int selected = (int) Math.floor(Math.random() * resultsList.size());
          String selectedText = resultsList
            .get(selected)
            .findElement(By.className("android.widget.TextView"))
            .getAttribute("text");
          Helpers.safeClick(this.driver, resultsList.get(selected));
          System.out.println(
            "pageSearch : results select :: "
            + selected
            + " :: "
            + selectedText
          );
        }

        // // Cancel
        // Helpers.tap(this.driver, 0.8, 0.2);
        // System.out.println("pageSearch : cancel :: " + 0);
      } catch (Exception err) {
        err.printStackTrace();
      }
      return 5;
    };
    return func;
  }

  private void pageSearchSwitchTabs() throws Exception {
    WebElement tabs = this.driver.findElement(By.id("app.organicmaps:id/tabs"));
    List<WebElement> tabList = tabs.findElements(By.className("android.widget.LinearLayout"));
    tabList = Helpers.getClickables(tabList);
    int id = Helpers.clickRandom(this.driver, tabList, null);
    System.out.println("pageSearch : switch Tabs :: " + 0 + " :: " + tabList.get(id).getAttribute("content-desc"));
  }

  private boolean pageSearchEmpytyPage() {
    // If history is empty
    WebElement pages = this.driver.findElement(By.id("app.organicmaps:id/pages"));
    List<WebElement> pageList = pages.findElements(By.className("android.widget.TextView"));
    pageList = Helpers.getClickables(pageList);
    if (pageList.size() == 0) return true;
    return false;
  }
}
