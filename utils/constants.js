module.exports = {
  wdioOptions: {
    path: "/wd/hub",
    port: 4723,
    capabilities: {
      platformName: "Android",
      platformVersion: "12",
      udid: "R5CR10N16WM",
      deviceName: "Galaxy S21 Ultra 5G",
      automationName: "UiAutomator2",
      noReset: true,
      fullReset: false,
      autoGrantPermissions: true,
    },
  },
};
