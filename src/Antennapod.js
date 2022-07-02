const wdio = require("webdriverio");
const path = require("path");
const { wdioOptions } = require("../utils/constants");

module.exports = class Antennapod {
  constructor() {}
  async init() {
    this.driver = await wdio.remote(options);
  }
  async close() {
    await this.driver.deleteSession();
  }
  client() {
    return this.driver;
  }
  async run() {
    const field = await this.driver.$("//android.widget.ImageButton");
    await field.click();
    this.driver.pause(5000);
    const f2 = await this.driver.$("//android.widget.RelativeLayout[@index=0]");
    await f2.click();
    this.driver.pause(5000);
  }
};

const options = {
  ...wdioOptions,
  capabilities: {
    ...wdioOptions.capabilities,
    app: path.resolve(
      __dirname,
      "..",
      "apks",
      "de.danoeh.antennapod_2050296.apk"
    ),
    appWaitActivity: ".activity.MainActivity",
  },
};
