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

  public Antennapod() throws Exception {
    DesiredCapabilities cap = new DesiredCapabilities();
    cap.setCapability("platformName", "Android");
    cap.setCapability("platformVersion", "12");
    cap.setCapability("udid", "R5CR10N16WM");
    cap.setCapability("deviceName", "Galaxy S21 Ultra 5G");
    cap.setCapability(
      "app",
      "/Users/hartonotjakrawinata/workCode/appium/apks/de.danoeh.antennapod_2050296.apk"
    );
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

  public Function<Void, Integer> menuSelect() {
    Function<Void, Integer> func = Void -> {
      Integer id = 5;
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

  public Function<Void, Integer> choosePodcastTape() {
    Function<Void, Integer> func = Void -> {
      Integer id = null;
      try {
        // GET PODCAST LIST
        WebElement buttonsList = this.driver.findElement(By.id("de.danoeh.antennapod:id/recyclerView"));
        List<WebElement> buttons = buttonsList.findElements(By.className("android.widget.FrameLayout"));
        buttons = Helpers.getClickables(buttons);
        List<WebElement> downloadPlayButtons = new ArrayList<>();

        // SPLIT TO TAPE AND DOWNLOAD AND PLAY
        downloadPlayButtons.addAll(buttons);
        downloadPlayButtons.removeIf(ele -> {
          return !Arrays
            .asList(new String[] { "Play", "Download" })
            .contains(ele.getAttribute("content-desc"));
        });
        buttons.removeIf(ele -> {
          return Arrays
            .asList(new String[] { "Play", "Download" })
            .contains(ele.getAttribute("content-desc"));
        });
        Helpers.printByInnerElement(
          buttons,
          null,
          "de.danoeh.antennapod:id/left_padding",
          "content-desc"
        );

        // CLICK DOWNLOAD PLAY WITH 0.5 PROB ELSE CLICK NEXT PAGE
        while (Math.random() < 0.5) {
          id = Helpers.clickRandom(this.driver, downloadPlayButtons, null);
          System.out.println("Clicked Download Play button: " + id);
        }
        id = Helpers.clickRandom(this.driver, buttons, null);
        System.out.println("Clicked Tape: " + id + "\n");
      } catch (Exception err) {
        err.printStackTrace();
      }
      return id;
    };
    return func;
  }

  public Function<Void, Integer> podcastTape() {
    Function<Void, Integer> func = Void -> {
      Integer id = null;
      try {
        // TAPE PLAY FUNCTIONS
        List<WebElement> buttons = new ArrayList<>();
        buttons.add(
          this.driver.findElement(By.id("de.danoeh.antennapod:id/butAction1"))
        );
        buttons.add(
          this.driver.findElement(By.id("de.danoeh.antennapod:id/butAction2"))
        );
        buttons = Helpers.getClickables(buttons);
        Helpers.printByInnerElement(
          buttons,
          "android.widget.TextView",
          null,
          "text"
        );
        id = Helpers.clickRandom(this.driver, buttons, null);

        // RUN WITH PROBABILITY
        while (Math.random() < 0.8) {
          if (Math.random() < 0.5) {
            id = podcastTapeOptions();
            System.out.println("Clicked podcastTapeOptions: " + id);
            continue;
          }
          id = Helpers.clickRandom(this.driver, buttons, null);
          System.out.println("Clicked podcastTapePlay: " + id);
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

  public int podcastTapeOptions() throws Exception {
    // options buttons
    WebElement toolbar = this.driver.findElement(By.id("de.danoeh.antennapod:id/toolbar"));
    WebElement options = toolbar.findElement(By.className("android.widget.ImageView"));
    Helpers.safeClick(this.driver, options);

    WebElement optionsDialog = this.driver.findElement(By.className("android.widget.ListView"));
    List<WebElement> optionButtons = optionsDialog.findElements(By.className("android.widget.LinearLayout"));
    optionButtons = Helpers.getClickables(optionButtons);
    optionButtons = Helpers.filterInnerWebElement(
      optionButtons,
      "android.widget.TextView",
      null,
      "text",
      Arrays.asList(new String[] { "Open Podcast", "Share…" })
    );
    // Helpers.printByInnerElement(
    //   optionButtons,
    //   "android.widget.TextView",
    //   null,
    //   "text"
    // );
    return Helpers.clickRandom(this.driver, optionButtons, null);
  }

  public void createPath() {
    HashMap<String, FunctionGraph> dict = new HashMap<>();
    dict.put("menu_select", new FunctionGraph("menu_select", menuSelect()));
    dict.put("podcast_add", new FunctionGraph("podcast_add", addPodcast()));
    dict.put(
      "podcast_choose_tape",
      new FunctionGraph("podcast_choose_tape", choosePodcastTape())
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
