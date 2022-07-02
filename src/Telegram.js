const wdio = require("webdriverio");
const path = require("path");
const { wdioOptions } = require("../utils/constants");

module.exports = class Telegram {
  constructor() {}
  async init() {
    this.client = await wdio.remote(options);
  }
  async close() {
    await this.client.deleteSession();
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
      "org.telegram.messenger_26366.apk"
    ),
    appWaitActivity: "org.telegram.ui.LaunchActivity",
  },
};
