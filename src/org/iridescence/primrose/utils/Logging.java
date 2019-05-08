package org.iridescence.primrose.utils;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class Logging {
  public static final Logger logger = Logger.getAnonymousLogger();
  private static Handler fh;
  private static Handler ch;

  public static void initializeLogger(){
    logger.setUseParentHandlers(false);

    try {
      fh = new FileHandler("./Primrose.log", false);
      fh.setFormatter(new LoggerFormatter());
      logger.addHandler(fh);
    } catch (IOException e) {
      logger.severe(e.getMessage());
    }

    ch = new ConsoleHandler();
    ch.setFormatter(new LoggerFormatter());
    logger.addHandler(ch);
  }

  public static void cleanupLogger(){
    logger.info("Exiting Application!");
    fh.close();
    ch.close();
  }
}
