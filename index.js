const Antennapod = require("./src/Antennapod");
const Telegram = require("./src/Telegram");

async function main() {
  switch (process.argv[2]) {
    case "antennapod":
      const antennapod = new Antennapod();
      await antennapod.init();
      await antennapod.run();
      await timeout(3000);
      await antennapod.close();
      break;

    case "telegram":
      const telegram = new Telegram();
      await telegram.init();
      await telegram.run();
      await timeout(3000);
      await telegram.close();
      break;

    default:
      throw new Error("index.js: Error process argv");
  }
}

function timeout(ms) {
  return new Promise((resolve) => setTimeout(resolve, ms));
}

main()
  .then(() => process.exit(0))
  .catch((err) => {
    console.error(err);
    process.exit(1);
  });
