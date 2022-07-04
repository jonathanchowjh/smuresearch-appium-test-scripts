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

public class Antennapod {

  AppiumDriver driver;
  FunctionGraph graph;
  FunctionGraph curr;

  public Antennapod(String apk) throws Exception {
    DesiredCapabilities cap = new DesiredCapabilities();
    cap.setCapability("platformName", "Android");
    cap.setCapability("platformVersion", "12");
    cap.setCapability("udid", "R5CR10N16WM");
    cap.setCapability("deviceName", "Galaxy S21 Ultra 5G");
    
    cap.setCapability("app", apk);
    cap.setCapability("appWaitActivity", ".activity.MainActivity");
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
    current = current.nodes.get(0);
    current.func.apply(null);
    current = current.nodes.get(0);
    current.func.apply(null);
    current = current.nodes.get(0);
    current.func.apply(null);
  }

  public Function<Void, Integer> menuSelect(int idSelect) {
    Function<Void, Integer> func = Void -> {
      Integer id = idSelect;
      try {
        // OPEN MENU
        WebElement toolbar = this.driver.findElement(By.id("de.danoeh.antennapod:id/toolbar"));
        WebElement menu = toolbar.findElement(By.className("android.widget.ImageButton"));
        Helpers.safeClick(driver, menu);

        // GO TO SECTION
        WebElement nav = driver.findElement(By.id("de.danoeh.antennapod:id/nav_list"));
        List<WebElement> nav_list = nav.findElements(By.className("android.widget.RelativeLayout"));
        nav_list = Helpers.getClickables(nav_list);
        Helpers.printByInnerElement(
          nav_list,
          null,
          "de.danoeh.antennapod:id/txtvTitle",
          "text"
        );
        Helpers.safeClick(this.driver, nav_list.get(id));
        // id = Helpers.clickRandom(this.driver, nav_list, 5);
      } catch (Exception err) {
        err.printStackTrace();
      }
      return id;
    };
    return func;
  }

  public Function<Void, Integer> addPodcast() {
    Function<Void, Integer> func = Void -> {
      Integer id = null;
      try {
        // CHOOSE PODCAST
        WebElement buttonsList = this.driver.findElement(By.id("de.danoeh.antennapod:id/discover_grid"));
        List<WebElement> buttons = buttonsList.findElements(By.className("android.widget.LinearLayout"));
        buttons = Helpers.getClickables(buttons);
        Helpers.printByInnerElement(
          buttons,
          "android.widget.ImageView",
          null,
          "content-desc"
        );
        id = Helpers.clickRandom(this.driver, buttons, null);

        // CLICK SUBSCRIBE
        WebElement subscribeButton =
          this.driver.findElement(
              By.id("de.danoeh.antennapod:id/subscribeButton")
            );
        Helpers.safeClick(this.driver, subscribeButton);
      } catch (Exception err) {
        err.printStackTrace();
      }
      return id;
    };
    return func;
  }

  public Function<Void, Integer> podcastTapeList() {
    Function<Void, Integer> func = Void -> {
      Integer id = null;
      try {
        // CLICK STAY ON PAGE BUTTONS
        while (Math.random() < 0.65) {
          podcastTapeListSOP();
        }
      } catch (Exception err) {
        err.printStackTrace();
      }
      return id;
    };
    return func;
  }

  public int podcastTapeListSOP() throws Exception {
    // GET DOWNLOAD PLAY BUTTONS
    WebElement sopList = this.driver.findElement(By.id("de.danoeh.antennapod:id/recyclerView"));
    List<WebElement> playDownload = sopList.findElements(By.className("android.widget.FrameLayout"));
    playDownload.removeIf(ele -> {
      return !Arrays
        .asList(new String[] { "Play", "Download" })
        .contains(ele.getAttribute("content-desc"));
    });
    // GET FILTER BUTTON
    WebElement toolbar = this.driver.findElement(By.id("de.danoeh.antennapod:id/toolbar"));
    WebElement linearLayout = toolbar.findElement(By.className("androidx.appcompat.widget.LinearLayoutCompat"));
    WebElement sort = linearLayout.findElement(By.id("de.danoeh.antennapod:id/sort_items"));
    WebElement filter = linearLayout.findElement(By.id("de.danoeh.antennapod:id/filter_items"));
    WebElement refresh = linearLayout.findElement(By.id("de.danoeh.antennapod:id/refresh_item"));
    List<WebElement> sop = new ArrayList<>();
    sop.add(sort);
    sop.add(filter);
    sop.add(refresh);
    sop.addAll(playDownload);
    sop = Helpers.getClickables(sop);
    int selected = (int) Math.floor(Math.random() * sop.size());
    // int selected = 1;
    Helpers.safeClick(this.driver, sop.get(selected));
    System.out.println("podcastTapeListSOP: clicked :: " + selected);
    if (selected == 0) {  // SORT
      if (Math.random() < 0.2) {
        WebElement cancel = this.driver.findElement(By.id("android:id/button2"));
        Helpers.safeClick(this.driver, cancel);
        System.out.println("podcastTapeListSOP: clicked sort cancel");
      }
      List<WebElement> sortSelect = this.driver.findElements(By.className("android.widget.CheckedTextView"));
      sortSelect = Helpers.getClickables(sortSelect);
      Helpers.clickRandom(this.driver, sortSelect, null);
      System.out.println("podcastTapeListSOP: clicked sort select");
    }
    if (selected == 1) {  // FILTER
      List<WebElement> filterSelect = this.driver.findElements(By.className("android.widget.RadioButton"));
      filterSelect = Helpers.getClickables(filterSelect);
      while (Math.random() < 0.65) {
        Helpers.clickRandom(this.driver, filterSelect, null);
        System.out.println("podcastTapeListSOP: clicked filter select");
      }
      WebElement confirm = this.driver.findElement(By.id("android:id/button1"));
      Helpers.safeClick(this.driver, confirm);
      System.out.println("podcastTapeListSOP: clicked filter confirm");
    }
    return selected;
  }

  public Function<Void, Integer> podcastTape() {
    Function<Void, Integer> func = Void -> {
      Integer id = null;
      try {
        // GET PODCAST LIST
        WebElement tapesList = this.driver.findElement(By.id("de.danoeh.antennapod:id/recyclerView"));
        List<WebElement> tapes = tapesList.findElements(By.className("android.widget.FrameLayout"));
        tapes = Helpers.getClickables(tapes);
        tapes.removeIf(ele -> {
          return Arrays
            .asList(new String[] { "Play", "Download" })
            .contains(ele.getAttribute("content-desc"));
        });
        Helpers.printByInnerElement(
          tapes,
          null,
          "de.danoeh.antennapod:id/left_padding",
          "content-desc"
        );
        // CLICK TAPE
        id = Helpers.clickRandom(this.driver, tapes, null);
        System.out.println("Clicked Tape: " + id + "\n");
        if (id == -1) return -1;
        // SOP BUTTON
        while (Math.random() < 0.7) {
          podcastTapeSOP();
        }
        // CLICK BACK
        WebElement toolbar = this.driver.findElement(By.id("de.danoeh.antennapod:id/toolbar"));
        WebElement backButton = toolbar.findElement(By.className("android.widget.ImageButton"));
        Helpers.safeClick(this.driver, backButton);
        System.out.println("Clicked Back Button\n");
      } catch (Exception err) {
        err.printStackTrace();
      }
      return id;
    };
    return func;
  }

  private int podcastTapeSOP() throws Exception {
    // TAPE PLAY FUNCTIONS
    List<WebElement> sop = new ArrayList<>();
    WebElement play = this.driver.findElement(By.id("de.danoeh.antennapod:id/butAction1"));
    WebElement download = this.driver.findElement(By.id("de.danoeh.antennapod:id/butAction2"));
    WebElement options = optionsButton();
    sop.add(options);
    sop.add(play);
    sop.add(download);
    sop = Helpers.getClickables(sop);
    int id = Helpers.clickRandom(this.driver, sop, null);
    if (id == 0) {  // OPTIONS
      List<WebElement> optionsSelect = optionsSelection(
        Arrays.asList(new String[]{"Open Podcast", "Share..."})
      );
      Helpers.clickRandom(this.driver, optionsSelect, null);
    }
    System.out.println("podcastTapeSOP: clicked :: " + id);
    return id;
  }

  private WebElement optionsButton() throws Exception {
    // options buttons
    WebElement toolbar = this.driver.findElement(By.id("de.danoeh.antennapod:id/toolbar"));
    WebElement options = toolbar.findElement(By.className("android.widget.ImageView"));
    return options;
  }

  private List<WebElement> optionsSelection(
    List<String> removeFromList
  ) throws Exception {
    WebElement optionsDialog = this.driver.findElement(By.className("android.widget.ListView"));
    List<WebElement> optionButtons = optionsDialog.findElements(By.className("android.widget.LinearLayout"));
    optionButtons = Helpers.getClickables(optionButtons);
    optionButtons = Helpers.filterInnerWebElement(
      optionButtons,
      "android.widget.TextView",
      null,
      "text",
      removeFromList
    );
    return Helpers.getClickables(optionButtons);
  }

  public void createPath() {
    HashMap<String, FunctionGraph> dict = new HashMap<>();
    dict.put("menu_select", new FunctionGraph("menu_select", menuSelect(5)));
    dict.put("podcast_add", new FunctionGraph("podcast_add", addPodcast()));
    dict.put(
      "podcast_choose_tape",
      new FunctionGraph("podcast_choose_tape", podcastTapeList())
    );
    dict.put("podcast_tape", new FunctionGraph("podcast_tape", podcastTape()));
    graph = dict.get("menu_select");
    curr = graph;
    curr.nodes.add(dict.get("podcast_add"));
    curr = curr.nodes.get(0);
    curr.nodes.add(dict.get("podcast_choose_tape"));
    curr = curr.nodes.get(0);
    curr.nodes.add(dict.get("podcast_tape"));
  }
}
