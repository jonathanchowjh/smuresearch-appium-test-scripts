const wdio = require("webdriverio");
const path = require("path");
const { wdioOptions } = require("../utils/constants");

module.exports = class Calculator {
  constructor() {}
  async init() {
    this.client = await wdio.remote(wdioOptions);
  }
  async close() {
    await this.client.deleteSession();
  }
};

const options = {
  ...wdioOptions,
  capabilities: {
    ...wdioOptions.capabilities,
    app: path.resolve(__dirname, "apks", "Calculator.apk"),
    appWaitActivity: "com.xlythe.calculator.material.Theme.Orange",
  },
};